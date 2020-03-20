package de.hpi.bpt.talos;

import de.hpi.bpt.talos.RPASAdapter.ProviderName;
import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

@ProviderName("TalosTesting")
public class MockAdapter implements RPASAdapter<Integer> {

	@Override
	public Integer startProcess(String name, ProcessInputs processInputs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void waitForTermination(Integer jobId, long timeoutMs) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProcessOutputs retrieveOutput(Integer jobId) {
		// TODO Auto-generated method stub
		return null;
	}

}
