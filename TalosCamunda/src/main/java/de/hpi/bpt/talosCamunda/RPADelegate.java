package de.hpi.bpt.talosCamunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.hpi.bpt.talos.UiPathBridge;
import de.hpi.bpt.talos.TalosCore.ProcessInputs;

public class RPADelegate implements JavaDelegate {

	UiPathBridge bridge = new UiPathBridge();
	
	Expression processName;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println(processName.getValue(execution));

		ProcessInputs processInputs = new ProcessInputs();
		processInputs.data.putAll(execution.getVariables());
		bridge.runProcess("DisplayMessage", processInputs);
	}

}
