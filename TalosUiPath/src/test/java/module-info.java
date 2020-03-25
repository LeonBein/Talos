import de.hpi.bpt.talos.RPASAdapter;
import de.hpi.bpt.talos.talosUiPath.UiPathAdapter;

open module de.hpi.bpt.talos.talosUiPath {
	exports de.hpi.bpt.talos.talosUiPath;
	
	provides RPASAdapter 
	with UiPathAdapter;

	requires de.hpi.bpt.talos;
	requires java.net.http;
	requires gson;
	requires java.sql;

	requires org.junit.jupiter.api;
}