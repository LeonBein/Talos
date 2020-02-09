package de.hpi.bpt.talos;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

import com.google.gson.JsonObject;

import de.hpi.bpt.talos.UiPathBridge.ProcessInputs;

public class CamundaTaskWorker {
	  private final static Logger LOGGER = Logger.getLogger(CamundaTaskWorker.class.getName());

	  public static void main(String[] args) {
	    ExternalTaskClient client = ExternalTaskClient.create()
	        .baseUrl("http://localhost:8080/engine-rest")
	        .asyncResponseTimeout(10000) // long polling timeout
	        .build();

		UiPathBridge bridge = new UiPathBridge();

	    // subscribe to an external task topic as specified in the process
	    client.subscribe("RPA")
	        .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
	        .handler((externalTask, externalTaskService) -> {
				// Put your business logic here
				
				// Get a process variable
				//String item = (String) externalTask.getVariable("item");
				//Long amount = (Long) externalTask.getVariable("amount");
				//LOGGER.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");
				ProcessInputs processInputs = new ProcessInputs();
				processInputs.data.putAll(externalTask.getAllVariables());
//				JsonObject message = new JsonObject();
//				message.addProperty("x", 5);
//				message.addProperty("y", 42);
//				processInputs.data.put("message", message);
				System.out.println(externalTask.getActivityId());
				System.out.println(externalTask.getAllVariables());
				System.out.println(externalTask.getProcessDefinitionId());
				
				bridge.startProcess("DisplayMessage", processInputs);
					
				// Complete the task
				externalTaskService.complete(externalTask);
	        })
	        .open();
	  }
}
