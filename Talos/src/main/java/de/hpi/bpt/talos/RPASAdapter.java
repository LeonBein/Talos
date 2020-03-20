package de.hpi.bpt.talos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.hpi.bpt.talos.TalosCore.ProcessInputs;
import de.hpi.bpt.talos.TalosCore.ProcessOutputs;

public interface RPASAdapter<ProcessIdentifier> {
	
	public ProcessIdentifier startProcess(String name, ProcessInputs processInputs);

	public void waitForTermination(ProcessIdentifier jobId, long timeoutMs) throws InterruptedException;

	public ProcessOutputs retrieveOutput(ProcessIdentifier jobId);
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ProviderName {
		public String value();
	}

}
