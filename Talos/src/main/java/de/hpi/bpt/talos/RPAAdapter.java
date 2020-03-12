package de.hpi.bpt.talos;

import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

public interface RPAAdapter<ProcessIdentifier> {
	
	public ProcessIdentifier startProcess(String name, ProcessInputs processInputs);

	public void waitForTermination(ProcessIdentifier jobId, long timeoutMs) throws InterruptedException;

	public ProcessOutputs retrieveOutput(ProcessIdentifier jobId);

}
