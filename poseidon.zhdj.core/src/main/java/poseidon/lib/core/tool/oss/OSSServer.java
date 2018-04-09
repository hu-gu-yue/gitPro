package poseidon.lib.core.tool.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里云OSS服务类 提供对阿里云OSS中保存图片的增、删、改等管理能力
 * 
 * @author yindan
 * 
 */
@Controller
public class OSSServer {
	private final static Logger log = LoggerFactory.getLogger(OSSServer.class);

	private static String saveToAliyunOSS;

	@Value("#{config['saveToAliyunOSS']}")
	public void setSaveToAliyunOSS(String val) {
		saveToAliyunOSS = val;
	}

	/**
	 * 签名keyId
	 */
	private static String accessKeyId = "GSRIbGW0DLwgWre4";
	/**
	 * 签名secretKey，需要在阿里云控制台定期更新，并保持一致
	 */
	private static String accessKeySecret = "spnS9zXmi9EE6cx9ijAPF1DpY5rQV7";

	/**
	 * 自驾友的ECS、OSS服务都在青岛
	 */
	private static String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
	/**
	 * 图片服务bucketName
	 */
	private static String bucketName = "zjyou-bucket";

	private static final CannedAccessControlList[] ACLS = {
			CannedAccessControlList.Private,
			CannedAccessControlList.PublicRead,
			CannedAccessControlList.PublicReadWrite,
			CannedAccessControlList.Default };
	private static OSSClient ossClient = null;

	public static OSSClient getOSSClient() {
		if (null == ossClient) {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		}
		return ossClient;
	}

	/**
	 * 上传图片至阿里云OSS
	 * 
	 * @param key
	 *            中间有/分隔的key，表示带有文件夹，需要在对应文件夹下保存。如果文件夹不存在则会自动创建文件夹。
	 *            example：user/header2/222.png 1.
	 *            只要确保最上层文件夹user存在，即可使用putObject方法来新增子文件夹，再在子文件夹下加文件 2.
	 *            如果key对应的文件或者文件夹存在，则会直接替换
	 * 
	 * @param filePath
	 *            图片绝对路径 example：D:/222.png
	 * @throws FileNotFoundException
	 */
	public static void uploadImage(String key, String filePath)
			throws FileNotFoundException {
		String config = saveToAliyunOSS.trim();
		log.info("saveToAliyunOSS = " + config);
		if ("off".equals(config)) {
			return;
		} else if ("on".equals(config)) {
			// 获取指定文件的输入流
			File file = new File(filePath);
			InputStream content = new FileInputStream(file);
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(file.length());
			log.info("Begin to upload image. key = " + key + ", filePath = "
					+ filePath);

			// 上传Object.
			PutObjectResult result = getOSSClient().putObject(bucketName, key,
					content, meta);
			log.info("Success to upload image. result = " + result);
		}
	}

	/**
	 * 上传图片至阿里云OSS
	 * 
	 * @param key
	 *            中间有/分隔的key，表示带有文件夹，需要在对应文件夹下保存。如果文件夹不存在则会自动创建文件夹。
	 *            example：user/header2/222.png 1.
	 *            只要确保最上层文件夹user存在，即可使用putObject方法来新增子文件夹，再在子文件夹下加文件 2.
	 *            如果key对应的文件或者文件夹存在，则会直接替换
	 * 
	 * @param file
	 *            图片绝对路径 example：D:/222.png
	 * @throws FileNotFoundException
	 */
	/*
	 * public static void uploadImage(String key, FileInputStream content)
	 * throws FileNotFoundException {
	 * 
	 * // 创建上传Object的Metadata ObjectMetadata meta = new ObjectMetadata(); //
	 * 必须设置ContentLength meta.setContentLength(content.);
	 * log.info("Begin to upload image. key = " + key + ", filePath = " +
	 * filePath);
	 * 
	 * // 上传Object. PutObjectResult result =
	 * getOSSClient().putObject(bucketName, key, content, meta);
	 * log.info("Success to upload image. result = " + result); }
	 */

	/**
	 * 单独在阿里云OSS上创建文件夹 OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object
	 * 
	 * @param folderName
	 *            要创建的文件夹名称,在满足Object命名规则的情况下以"/"结尾 example：user/header2/
	 *            只要确保最上层文件夹user存在，即可使用createFolder方法来新增子文件夹，
	 */
	public static void createFolder(String folderName) {
		String config = saveToAliyunOSS.trim();
		log.info("saveToAliyunOSS = " + config);
		if ("off".equals(config)) {
			return;
		} else if ("on".equals(config)) {
			ObjectMetadata objectMeta = new ObjectMetadata();

			// 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
			byte[] buffer = new byte[0];
			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
			objectMeta.setContentLength(0);
			try {
				log.info("Begin to create folder. folderName = " + folderName);
				PutObjectResult result = getOSSClient().putObject(bucketName,
						folderName, in, objectMeta);
				log.info("Success to create folder. result = " + result);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
