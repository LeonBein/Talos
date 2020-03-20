package de.hpi.bpt.talos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hpi.bpt.talosUiPath.UiPathAdapter;

public class TalosCore {
	
	public static final long defaultTimeout = TimeUnit.MINUTES.toMillis(30);
	
	private RPASAdapter<?> rpaAdapter;
	
	public static class ProcessInputs {
		public Map<String, Object> data;
		public ProcessInputs() {
			this.data = new HashMap<>();
		}
	}
	
	public static class ProcessOutputs {
		public Map<String, Object> data;
		public ProcessOutputs() {
			this.data = new HashMap<>();
		}
	}
	
	public static TalosCore create() {
		return new TalosCore(new UiPathAdapter());
	}
	
	
	private TalosCore(RPASAdapter<?> adapter) {
		this.rpaAdapter = adapter;
	}

	public ProcessOutputs runProcess(String name, ProcessInputs processInputs) {
		return runProcess(name, processInputs, this.rpaAdapter);
	}
	
	private <ProcessIdentifier> ProcessOutputs runProcess(String name, ProcessInputs processInputs, RPASAdapter<ProcessIdentifier> rpaAdapter) {
		ProcessIdentifier jobId = rpaAdapter.startProcess(name, processInputs);
		System.out.print("Waiting for process "+name+" to finish ... ");
		try {
			rpaAdapter.waitForTermination(jobId, defaultTimeout);
		} catch (InterruptedException e) {
			throw new RuntimeException("Execution of process "+name+" was interrupted unexpectedly: ", e);
		}
		System.out.println("OK");
		System.out.print("Finished execution of process "+name);
		
		return rpaAdapter.retrieveOutput(jobId);
	}
	
}
