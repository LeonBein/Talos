package de.hpi.bpt.talos;

import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

public interface RPAAdapter<ProcessIdentifier> {
	
	public default ProcessOutputs runProcess(String name, ProcessInputs processInputs) {
		ProcessIdentifier jobId = startProcess(name, processInputs);
		System.out.print("Waiting for process "+name+" to finish ... ");
		try {
			waitForTermination(jobId, 30 * 60 * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Execution of process "+name+" was interrupted unexpectedly: ", e);
		}
		System.out.println("OK");
		System.out.print("Finished execution of process "+name);
		
		return retrieveOutput(jobId);		
	}

	public ProcessIdentifier startProcess(String name, ProcessInputs processInputs);

	public void waitForTermination(ProcessIdentifier jobId, long timeoutMs) throws InterruptedException;

	public ProcessOutputs retrieveOutput(ProcessIdentifier jobId);

}
