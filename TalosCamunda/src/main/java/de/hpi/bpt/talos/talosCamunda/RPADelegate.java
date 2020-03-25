package de.hpi.bpt.talos.talosCamunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.hpi.bpt.talos.TalosCore;
import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

public class RPADelegate implements JavaDelegate {

	TalosCore talos = TalosCore.create();
	
	Expression processName;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String processToExecute = processName.getValue(execution).toString();
		System.out.println("Starting RPA process \""+processToExecute+"\"");

		ProcessInputs processInputs = new ProcessInputs();
		processInputs.data.putAll(execution.getVariables());
		
		ProcessOutputs processOutputs = talos.runProcess(processToExecute, processInputs);
		processOutputs.data.forEach(execution::setVariable);
	}

}
