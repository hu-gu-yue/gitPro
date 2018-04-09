package poseidon.lib.core.tool.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import poseidon.lib.core.tool.date.DateTool;
import poseidon.lib.core.tool.oss.OSSServer;
import poseidon.lib.core.tool.string.StringTool;
import poseidon.lib.core.tool.system.OSTool;

@Controller
public class HttpImgTool {
	/** 宽 */
	private static int width = 700;
	/** 高 */
	private static int height = 400;

	static String topImgSavePath;

	@Value("#{config['topImgSavePath']}")
	public void setTopImgSavePath(String val) {
		HttpImgTool.topImgSavePath = val;
	}

	private final static Logger log = LoggerFactory
			.getLogger(HttpImgTool.class);

	/**
	 * Description: 保存文件到对应的文件夹, 按规则生成文件名称，并返回相对路径 规则: 保存的路径 / 父路径 / 日期 /
	 * 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg 返回: 父路径 / 日期 /
	 * 1502031140888_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
	 */
	public static String saveurlImage(String url, long uid, String ossFolder,
			Date curDate) {
		String fileName = null; // 文件名称
		String dirPrefix; // 父路径 / 日期 /
		String dirPath; // 保存的路径 / 父路径 / 日期 /
		String filePath; // 保存的路径 / 父路径 / 日期 /
							// 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
		String dateStr = DateTool.DateToString(curDate);
		// 文件后缀
		String fileSuffix = FilenameUtils.getExtension(url);
		if (StringTool.isEmpty(fileSuffix)) {
			fileSuffix = "jpg";
		}
		try {
			dirPrefix = FilenameUtils.concat(ossFolder, dateStr);
			dirPath = FilenameUtils.concat(topImgSavePath, dirPrefix);
			fileName = DateTool.DateToFileName(new Date()) + "_" + uid + "_"
					+ OSTool.genUUID() + "." + fileSuffix;
			filePath = FilenameUtils.concat(dirPath, fileName);
			log.debug("dirPath = " + dirPath);
			log.debug("filePath" + filePath);
			FileUtils.forceMkdir(new File(dirPath));
			ImgTool.saveImageToFile(url, filePath);

			// 先在OSS上创建目录，再上传
			OSSServer.createFolder(dirPrefix + "/");
			OSSServer.uploadImage(FilenameUtils.concat(dirPrefix, fileName),
					filePath);
		} catch (Exception e) {
			log.error("Fail to upload file to OSS.", e);
			return "";
		}
		return FilenameUtils.concat(dirPrefix, fileName);
	}

	/**
	 * 根据活动内容中图片url获取图片保存至OSS中
	 * 
	 * @param url
	 * @param uid
	 * @return
	 */
	public static String saveurlImageReSizer(String url, long uid,
			String ossFolder, Date curDate) {
		String fileName = null; // 文件名称
		String dirPrefix; // 父路径 / 日期 /
		String dirPath; // 保存的路径 / 父路径 / 日期 /
		String filePath; // 保存的路径 / 父路径 / 日期 /
							// 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
		String dateStr = DateTool.DateToString(curDate);
		// 文件后缀
		String fileSuffix = FilenameUtils.getExtension(url);
		if (StringTool.isEmpty(fileSuffix)) {
			fileSuffix = "jpg";
		}
		try {
			dirPrefix = FilenameUtils.concat(ossFolder, dateStr);
			dirPath = FilenameUtils.concat(topImgSavePath, dirPrefix);
			fileName = DateTool.DateToFileName(curDate) + "_" + uid + "_"
					+ OSTool.genUUID() + "." + fileSuffix;
			filePath = FilenameUtils.concat(dirPath, fileName);
			log.debug("dirPath" + dirPath);
			log.debug("filePath" + filePath);
			FileUtils.forceMkdir(new File(dirPath));
			ImgTool.saveImageToFileReSizer(url, filePath);

			// 先在OSS上创建目录，再上传
			OSSServer.createFolder(dirPrefix + "/");
			OSSServer.uploadImage(FilenameUtils.concat(dirPrefix, fileName),
					filePath);

			log.info("Success to upload file to OSS.");
		} catch (Exception e) {
			log.error("Fail to upload file to OSS.", e);
			return "";
		}
		return FilenameUtils.concat(dirPrefix, fileName);
	}
	
	/**
	 * Description: 保存文件到对应的文件夹, 按规则生成文件名称，并返回相对路径 规则: 保存的路径 / 父路径 / 日期 /
	 * 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg 返回: 父路径 / 日期 /
	 * 1502031140888_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
	 */
	public static String saveKindeditorImage(MultipartFile file, long uid,
			String ossFolder, Date curDate) {
		String fileName = null; // 文件名称
		String dirPrefix; // 父路径 / 日期 /
		String dirPath; // 保存的路径 / 父路径 / 日期 /
		String filePath; // 保存的路径 / 父路径 / 日期 /
							// 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
		String dateStr = DateTool.DateToString(curDate);
		if (file == null || file.isEmpty()) {
			return fileName;
		}

		// 原始文件名称
		String originalFileName = file.getOriginalFilename();

		if (originalFileName == null) {
			return fileName;
		}
		// 文件后缀
		String fileSuffix = FilenameUtils.getExtension(originalFileName);

		try {
			dirPrefix = FilenameUtils.concat(ossFolder, dateStr);
			dirPath = FilenameUtils.concat(topImgSavePath, dirPrefix);
			fileName = DateTool.DateToFileName(curDate) + "_" + uid + "_" + OSTool.genUUID() + "." + fileSuffix;
			filePath = FilenameUtils.concat(dirPath, fileName);
			log.debug("dirPath" + dirPath);
			log.debug("filePath" + filePath);

			FileUtils.forceMkdir(new File(dirPath));
			file.transferTo(new File(filePath));

			// 先在OSS上创建目录，再上传
			OSSServer.createFolder(dirPrefix + "/");
			OSSServer.uploadImage(FilenameUtils.concat(dirPrefix, fileName), filePath);
			log.info("Success to upload file to OSS.");
		} catch (Exception e) {
			log.error("Fail to upload file to OSS.", e);
			return "";
		}
		
		log.debug("----------------test--------------" + filePath + ":" + FilenameUtils.concat(dirPrefix, fileName));
		return filePath + ":" + FilenameUtils.concat(dirPrefix, fileName);
	}

	/**
	 * Description: 保存文件到对应的文件夹, 按规则生成文件名称，并返回相对路径 规则: 保存的路径 / 父路径 / 日期 /
	 * 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg 返回: 父路径 / 日期 /
	 * 1502031140888_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
	 */
	public static String saveMultipartImage(MultipartFile file, long uid,
			String ossFolder, Date curDate) {
		String fileName = null; // 文件名称
		String dirPrefix; // 父路径 / 日期 /
		String dirPath; // 保存的路径 / 父路径 / 日期 /
		String filePath; // 保存的路径 / 父路径 / 日期 /
							// 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
		String dateStr = DateTool.DateToString(curDate);
		if (file == null || file.isEmpty()) {
			return fileName;
		}

		// 原始文件名称
		String originalFileName = file.getOriginalFilename();

		if (originalFileName == null) {
			return fileName;
		}
		// 文件后缀
		String fileSuffix = FilenameUtils.getExtension(originalFileName);

		try {
			dirPrefix = FilenameUtils.concat(ossFolder, dateStr);
			dirPath = FilenameUtils.concat(topImgSavePath, dirPrefix);
			fileName = DateTool.DateToFileName(curDate) + "_" + uid + "_" + OSTool.genUUID() + "." + fileSuffix;
			filePath = FilenameUtils.concat(dirPath, fileName);
			log.debug("dirPath" + dirPath);
			log.debug("filePath" + filePath);

			FileUtils.forceMkdir(new File(dirPath));
			file.transferTo(new File(filePath));

			// 先在OSS上创建目录，再上传
			OSSServer.createFolder(dirPrefix + "/");
			OSSServer.uploadImage(FilenameUtils.concat(dirPrefix, fileName), filePath);
			log.info("Success to upload file to OSS.");
		} catch (Exception e) {
			log.error("Fail to upload file to OSS.", e);
			return "";
		}

		return FilenameUtils.concat(dirPrefix, fileName);
	}

	/**
	 * 保存图片
	 * 
	 * @param file
	 * @param savePath
	 * @param parentDirName
	 * @param uid
	 * @param curDate
	 * @return
	 */
	public static String saveMultipartImageReSizer(MultipartFile file,
			long uid, String ossFolder, Date curDate) {
		String fileName = null; // 文件名称
		String dirPrefix; // 父路径 / 日期 /
		String dirPath; // 保存的路径 / 父路径 / 日期 /
		String filePath; // 保存的路径 / 父路径 / 日期 /
							// 1502031140888_uid_XXXXXXXXXXXXXXXXXXXXXXXXXX.jpg
		String dateStr = DateTool.DateToString(curDate);
		if (file == null || file.isEmpty()) {
			return fileName;
		}

		// 原始文件名称
		String originalFileName = file.getOriginalFilename();

		if (originalFileName == null) {
			return fileName;
		}
		// 文件后缀
		String fileSuffix = FilenameUtils.getExtension(originalFileName);

		try {

			dirPrefix = FilenameUtils.concat(ossFolder, dateStr);
			dirPath = FilenameUtils.concat(topImgSavePath, dirPrefix);
			fileName = DateTool.DateToFileName(new Date()) + "_" + uid + "_"
					+ OSTool.genUUID() + "." + fileSuffix;
			filePath = FilenameUtils.concat(dirPath, fileName);
			log.debug("dirPath" + dirPath);
			log.debug("filePath" + filePath);

			FileUtils.forceMkdir(new File(dirPath));

			InputStream input = null;
			input = file.getInputStream();
			BufferedImage bufImg = ImageIO.read(input);// 把图片读入到内存中
			// 压缩代码

			int dwidth = bufImg.getWidth() > width ? width : bufImg.getWidth();
			int dheight = bufImg.getHeight() > height ? height : bufImg
					.getHeight();
			bufImg = Thumbnails.of(bufImg).size(dwidth, dheight)
					.outputQuality(0.9).asBufferedImage();

			ImageIO.write(bufImg, fileSuffix, new File(filePath));

			// 先在OSS上创建目录，再上传
			OSSServer.createFolder(dirPrefix + "/");
			OSSServer.uploadImage(FilenameUtils.concat(dirPrefix, fileName),
					filePath);
			log.info("Success to upload file to OSS.");
		} catch (Exception e) {
			log.error("save image filed.", e);
			return "";
		}

		return FilenameUtils.concat(dirPrefix, fileName);
	}

	/**
	 * 保存file至指定OSS目录下面
	 * 
	 * @param file
	 * @param savePath
	 * @param ossFolder
	 * @param fileName
	 * @return
	 */
	public static String saveToPath(MultipartFile file, String savePath,
			String ossFolder, String fileName) {
		String filePath;
		try {
			filePath = FilenameUtils.concat(savePath, fileName);
			file.transferTo(new File(filePath));

			OSSServer.createFolder(ossFolder);
			OSSServer.uploadImage(ossFolder + fileName, filePath);
			log.info("Success to upload file to OSS.");
		} catch (Exception e) {
			log.error("Fail to upload file to OSS.", e);
			return "";
		}
		return fileName;

	}
}
