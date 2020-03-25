package de.hpi.bpt.talos.talosUiPath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.hpi.bpt.talos.RPASAdapterLoader;

class TalosUiPathTests {
	
	@Test
	void testAdapterAvailable() {
		assertNotNull(RPASAdapterLoader.get("UiPath"));
	}

}
