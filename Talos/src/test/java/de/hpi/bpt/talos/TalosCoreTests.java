package de.hpi.bpt.talos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TalosCoreTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Tests whether the test environment is set up without errors
	 */
	@Test
	void greenLightTest() {
		assertTrue(true);
	}
	
	@Test
	void mockAvailableTest() {
		RPASAdapterLoader.get("MockAdapter");
	}

}
