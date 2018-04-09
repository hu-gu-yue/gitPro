package poseidon.lib.core.tool.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTool {
	private final static Logger log = LoggerFactory.getLogger(FileTool.class);

	/*
	 * 从文件中加载长度小于maxLen长度的字符串
	 * 
	 * @param fileName
	 */
	public static String load(String fileName) {
		String text = "";
		File file = new File(fileName);
		if (file.exists()) {
			try {
				text = FileUtils.readFileToString(file);
			} catch (IOException e) {
				log.error("load", e);
				text = "";
			}
		}
		return text;
	}

	/*
	 * 保存string到文件夹中
	 * 
	 * @param fileName
	 * 
	 * @param text
	 */
	public static void save(String fileName, String text) {
		File file = new File(fileName);
		File dir = new File(file.getParent());
		try {
			FileUtils.forceMkdir(dir);
			FileUtils.writeStringToFile(file, text);
		} catch (IOException e) {
			log.error("save", e);
		}
	}

	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	public static boolean deleteDirectory(String dir) {
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.info("delete directory " + dir + " fail!!!!");
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = delFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			log.info("delete directory fail!");
			return false;
		}
		if (dirFile.delete()) {
			return true;
		} else {
			log.info("delete directory " + dir + " fail！");
			return false;
		}
	}

	public static ArrayList<String> getAllFiles(String dir, ArrayList<String> listFile) {
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.info("It is not a dir");
			return null;
		} else {
			File[] files = dirFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					listFile.add(files[i].getAbsolutePath());
				} else {
					getAllFiles(files[i].getAbsolutePath(), listFile);
				}
			}
		}
		return listFile;
	}
}
