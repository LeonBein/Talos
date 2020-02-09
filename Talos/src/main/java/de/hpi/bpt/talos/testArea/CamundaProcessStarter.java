package de.hpi.bpt.talos.testArea;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class CamundaProcessStarter {
	
	private static final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();
	
	public static void main(String[] args) {
		try {	

			HttpRequest request = HttpRequest.newBuilder()
					.POST(HttpRequest.BodyPublishers.ofString("{\n" + 
							"\"variables\": {\n" + 
							"	\"amount\": {\n" + 
							"		\"value\":555,\n" + 
							"		\"type\":\"long\"\n" + 
							"	},\n" + 
							"	\"item\": {	\n" + 
							"		\"value\": \"item-xyz\"\n" + 
							"	}	\n" + 
							"}	\n" + 
							"}"))
					.uri(URI.create("http://localhost:8080/engine-rest/process-definition/key/Test_Process/start"))
					.header("Content-Type", "application/json")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			System.out.println(response.statusCode());
			System.out.println(response.body());
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
