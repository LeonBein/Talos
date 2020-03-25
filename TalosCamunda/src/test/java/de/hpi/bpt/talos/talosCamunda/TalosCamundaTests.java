package de.hpi.bpt.talos.talosCamunda;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.hpi.bpt.talos.RPASAdapterLoader;

class TalosCamundaTests {
	
	@Test
	void testUiPathAdapterAvailable() {
		assertNotNull(RPASAdapterLoader.get("UiPath"));
	}
	
}
