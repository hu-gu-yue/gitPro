package poseidon.lib.core.tool.system;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.string.StringTool;

public class OSTool {
	private final static Logger log = LoggerFactory.getLogger(OSTool.class);

	public static void printLocalIpAddress() {
		try {
			Enumeration<?> allNetInterfaces;

			allNetInterfaces = NetworkInterface.getNetworkInterfaces();

			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isOS(String osName) {
		Properties prop = System.getProperties();
		String osText = prop.getProperty("os.name");
		osText = osText.toUpperCase();
		if (osText.startsWith(osName.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWindows() {
		return isOS("win");
	}

	public static boolean isLinux() {
		return isOS("lin");
	}

	public static void sleep(int seconds) {
		int count = seconds;
		if (seconds > 0) {
			try {
				while (count > 0) {
					Thread.sleep(1000);
					count--;
				}
			} catch (Exception e) {
			}
		}
	}

	public static void sleepms(int ms) {

		try {
			Thread.sleep(ms);

		} catch (Exception e) {
		}

	}

	public static boolean chmod(String fileName, String mode) {
		boolean flag = false;
		if (isLinux()) {
			try {
				// log.debug("chmod " + mode +" "+ fileName);
				Process process = Runtime.getRuntime().exec("chmod " + mode + " " + fileName);

				InputStream stderr = process.getErrorStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				// System.out.println("<ERROR>");
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				// System.out.println("</ERROR>");

				int result = process.waitFor();
				// log.info("[result is]"+result);
				if (result == 0) {
					flag = true;
				}

			} catch (Exception e) {
				flag = false;
				log.error("chmod failed.", e);
			}
		} else {
			flag = true;
		}
		return flag;
	}

	public static boolean executeCmd(String[] argv, String successCondition) {
		try {
			ProcessBuilder pb = new ProcessBuilder(argv);
			Process process = pb.start();
			IoOut ioOut = new IoOut(process.getInputStream());
			ioOut.start();
			IoError ioError = new IoError(process.getErrorStream());
			ioError.start();

			int exitValue;
			exitValue = process.waitFor();

			log.info("[executeCmd] exitValue is " + exitValue);
			if (exitValue == 0) {
				process.getOutputStream().close();
			}

			if (StringTool.isNotEmpty(successCondition) && !ioOut.result.contains(successCondition)) {
				return false;
			}

		} catch (Exception e) {
			log.error("[executeCmd] ", e);
			return false;
		}
		return true;
	}

	public static String genUUID() {
		String id = UUID.randomUUID().toString().replace("-", "");
		return id;
	}

	public static String genUUIDSALT() {
		String id = UUID.randomUUID().toString().replace("-", "");
		return id.substring(0, 6);
	}

	public static String genErrorID() {
		String id = "ERR:" + UUID.randomUUID().toString().replace("-", "");
		return id;
	}

	public static int genSixCode() {
		Random random = new Random(); // 生成随机类
		int code = 0;
		for (int i = 0; i < 6; i++) {
			code = (10 * code) + (random.nextInt(9) + 1);
		}
		log.info("curCode:" + code);
		return code;
	}
	
	public static int genFourCode(){
		Random random = new Random(); // 生成随机类
		int code = 0;
		for (int i = 0; i < 4; i++) {
			code = (10 * code) + (random.nextInt(9) + 1);
		}
		log.info("curCode:" + code);
		return code;
	}

	public static void closeObj(Closeable obj) {
		if (obj == null) {
			return;
		}

		try {
			obj.close();
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		int i = 0;
		for (i = 0; i < 200; i++) {
			OSTool.genSixCode();
		}

	}
}
