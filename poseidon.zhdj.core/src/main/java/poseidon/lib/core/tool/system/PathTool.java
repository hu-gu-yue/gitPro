package poseidon.lib.core.tool.system;

import java.net.URLDecoder;

public class PathTool {
	public static String getAppPath(String path) {
		String appPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
		try {
			appPath = URLDecoder.decode(appPath, "utf-8");
		} catch (Exception e1) {
			appPath = "./";
		}
		return appPath;
	}
}