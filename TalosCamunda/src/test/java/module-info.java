open module de.hpi.bpt.talos.talosCamunda {
	exports de.hpi.bpt.talos.talosCamunda;

	requires camunda.engine;
	requires de.hpi.bpt.talos;
	requires javax.servlet.api;
	requires mybatis;
	requires de.hpi.bpt.talos.talosUiPath;

	requires org.junit.jupiter.api;
	requires de.ybroeker.camunda.junit.jupiter;
}