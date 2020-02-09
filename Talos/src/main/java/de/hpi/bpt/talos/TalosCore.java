package de.hpi.bpt.talos;

import java.util.HashMap;
import java.util.Map;

public class TalosCore {
	
	
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

}
