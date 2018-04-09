package poseidon.lib.core.tool.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.string.StringTool;

public class IoOut extends Thread {
	private InputStream in = null;
	private final static Logger log = LoggerFactory.getLogger(IoOut.class);
	String result = "";

	public IoOut(InputStream in) {
		this.in = in;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.in));

			String line = "";
			while ((line = br.readLine()) != null) {

				line = StringTool.gbkToUtf8(line);
				result += line;
				log.debug(line);
			}
		} catch (IOException e) {
			log.error("IoError run", e);
		}
	}
}
