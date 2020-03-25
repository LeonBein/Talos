package de.hpi.bpt.talos.talosCamunda;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import de.ybroeker.camunda.junit.jupiter.ProcessEngineExtension;

import org.camunda.bpm.engine.test.Deployment;

//import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@ExtendWith(ProcessEngineExtension.class)
public class ProcessUnitTest {

  private static final String PROCESS_DEFINITION_KEY = "talosCamunda";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }


  /**
   * Just tests if the process definition is deployable.
   */
//  @Test
//  @Deployment(resources = "process.bpmn")
//  public void testParsingAndDeployment() {
//    // nothing is done here, as we just want to check for exceptions during deployment
//  }
//
//  @Test
//  @Deployment(resources = "process.bpmn")
//  public void testHappyPath() {
//	  //ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
//	  
//	  // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
//  }

}
