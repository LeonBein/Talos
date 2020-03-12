package de.hpi.bpt.talos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

public class UiPathBridge implements RPAAdapter<String>{

	private static final Map<String, String> uiPathConfig = getUiPathConfig();
	
	private static final HttpClient httpClient = HttpClient.newHttpClient();
	
	private String authToken;
	
	private String accountName;
	private String tenantName;
	private String clientId;
	private String userKey;
	
	
	public UiPathBridge() {
		this.accountName = uiPathConfig.get("accountName");
		this.tenantName = uiPathConfig.get("tenantName");
		this.clientId = uiPathConfig.get("clientId");
		this.userKey = uiPathConfig.get("userKey");
	}
	
	private String getOrchestratorURL() {
		return "https://platform.uipath.com/"+accountName+"/"+tenantName;
	}
	
	private String getAuthToken() {
		//TODO invalidate token if too old
		if(this.authToken == null) {
			return this.authenticate();
		} else {
			return this.authToken;
		}
	}
	
	
	private String authenticate() {
		System.out.print("Authenticating ... ");
		JsonObject body = new JsonObject();
	    body.addProperty("grant_type", "refresh_token");
		body.addProperty("client_id", clientId);
		body.addProperty("refresh_token", userKey);
		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(body.toString()))
				.uri(URI.create("https://account.uipath.com/oauth/token"))
				.header("Content-Type", "application/json")
				.build();

		HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("FAILURE");
			throw new RuntimeException("Unable to authenticate:", e);
		}
		JsonElement responseBody = new JsonParser().parse(response.body());
		this.authToken = responseBody.getAsJsonObject().get("access_token").getAsString();
		System.out.println("OK");
		return this.authToken;
	}
	
	private JsonArray getProcessReleases() {
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(getOrchestratorURL()+"/odata/Releases"))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+getAuthToken())
				.header("X-UIPATH-TenantName", tenantName)
				.build();

		HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return new JsonParser().parse(response.body()).getAsJsonObject().get("value").getAsJsonArray();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String startProcess(String name, ProcessInputs processInputs) {
		getAuthToken();
		System.out.print("Starting process "+name+" ... ");
		List<String> releases = new ArrayList<>();
		getProcessReleases().forEach(each -> {
			if(each.getAsJsonObject().get("ProcessKey").getAsString().equals(name))releases.add(each.getAsJsonObject().get("Key").getAsString());
		});
		if(releases.size() > 0) {
			String release = releases.get(0);
			JsonObject body = new JsonObject();
			JsonObject startInfo = new JsonObject();    
			startInfo.addProperty("ReleaseKey", release);
			startInfo.add("RobotIds", new JsonArray());
			startInfo.addProperty("NoOfRobots", 1);
			startInfo.addProperty("Strategy", "RobotCount");
			JsonObject inputArguments = new JsonObject();
			processInputs.data.forEach((key, value) -> {
				inputArguments.addProperty(key, value.toString());
			});
			startInfo.addProperty("InputArguments", inputArguments.toString());
			body.add("startInfo", startInfo);
			HttpRequest request = HttpRequest.newBuilder()
					.POST(HttpRequest.BodyPublishers.ofString(body.toString()))
					.uri(URI.create(getOrchestratorURL()+"/odata/Jobs/UiPath.Server.Configuration.OData.StartJobs"))
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer "+getAuthToken())
					.header("X-UIPATH-TenantName", tenantName)
					.build();
			HttpResponse<String> response;
			try {
				response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			} catch (InterruptedException | IOException e) {
				System.out.println("FAILURE");
				throw new RuntimeException("Unable to start job:", e);
			}
			JsonObject responseBody = new JsonParser().parse(response.body()).getAsJsonObject().get("value").getAsJsonArray().get(0).getAsJsonObject();
			String jobId = responseBody.get("Key").getAsString();
			String status = responseBody.get("State").getAsString();
			assert status.equals("Pending");
			System.out.println("OK");
			return jobId;
		} else {
			throw new RuntimeException("No releases found for process \""+name+"\"");
		}
	}
	
	@Override
	public void waitForTermination(String jobId, long timeout) throws InterruptedException {
		for(long waitingTime = 1000; timeout > 0; timeout -= waitingTime) {
			String status = getJobStatus(jobId);
			//System.out.println(status);
			switch(status) {
			case "Successful" : return;
			case "Pending " : case "Running" : break;
			case "Faulted" : throw new RuntimeException("Job execution failed");
			case "Canceling " : case "Terminating" : break;
			case "Canceled" : throw new RuntimeException("Job execution was canceled");
			}
			Thread.sleep(waitingTime);
			waitingTime *= 1.5;
			if(waitingTime > timeout/10)waitingTime = timeout/10;
		}
	}
	
	@Override
	public ProcessOutputs retrieveOutput(String jobId) {
		ProcessOutputs processOutputs = new ProcessOutputs();
		processOutputs.data = new JsonParser()
				.parse(getJob(jobId).get("OutputArguments").getAsString())
				.getAsJsonObject()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getAsString()));
		return processOutputs;
	}

	private String getJobStatus(String jobId) {
		return getJob(jobId).get("State").getAsString();
	}
	
	private JsonObject getJob(String jobId) {
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(getOrchestratorURL()+"/odata/Jobs?$filter=Key%20eq%20"+jobId))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+getAuthToken())
				.header("X-UIPATH-TenantName", tenantName)
				.build();

		HttpResponse<String> response;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		JsonArray results = new JsonParser().parse(response.body()).getAsJsonObject().get("value").getAsJsonArray();
		assert results.size() == 1;
		return results.get(0).getAsJsonObject();
	}
	
	@SuppressWarnings("unchecked")
	private  static Map<String, String> getUiPathConfig() {
		File configFile = new File(".uipathConfig");
		System.out.println("UiPath config file path: "+configFile.getAbsolutePath());
		try(Reader reader = new FileReader(configFile)) {
			Gson gson = new Gson();
			Map<String, String> dummy = new HashMap<>();
			return (Map<String, String>) gson.fromJson(reader, dummy.getClass());
		} catch(JsonIOException | JsonSyntaxException e) {
			throw new RuntimeException("Config for uipath not valid", e);
		} catch (IOException e) {
			throw new RuntimeException("No config for uipath found", e);
		}
	}

}
