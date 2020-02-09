package de.hpi.bpt.talos.testArea;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

public class CamundaTaskDelegate implements JavaDelegate, ActivityBehavior {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
