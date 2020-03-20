import de.hpi.bpt.talos.RPASAdapter;
import de.hpi.bpt.talosUiPath.UiPathAdapter;

module de.hpi.bpt.talos {
	exports de.hpi.bpt.talos;
	exports de.hpi.bpt.talosUiPath;
	uses RPASAdapter;
	provides RPASAdapter
		with UiPathAdapter;

	requires java.net.http;
	requires gson;
	requires java.sql;
}

