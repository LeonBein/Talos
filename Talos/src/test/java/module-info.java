import de.hpi.bpt.talos.RPASAdapter;
import de.hpi.bpt.talos.MockAdapter;

open module de.hpi.bpt.talos {
	exports de.hpi.bpt.talos;
	uses RPASAdapter;
	provides RPASAdapter
		with MockAdapter;

	requires java.net.http;
	requires gson;
	requires java.sql;
	requires org.junit.jupiter.api;
}

