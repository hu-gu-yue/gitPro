/*
 *                   _ooOoo_
 *                  o8888888o
 *                  88" . "88
 *                  (| -_- |)
 *                  O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -  /// |   |
 *           | \_|  ''\---/''  |   |
 *           \  .-\__  `-`  ___/-. /
 *         ___`. .'  /--.--\  `. . __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         佛祖保佑       永无BUG
 */
package poseidon.lib.core.tool.QRCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import poseidon.lib.core.tool.oss.OSSServer;

@Controller
public class QRCodeTool {

	private final static Logger log = LoggerFactory.getLogger(QRCodeTool.class);

	private static String webServer;

	@Value("#{config['webServer']}")
	public void setWebServer(String val) {
		webServer = val;
	}

	private static String tribeQRCodeSavePath;

	@Value("#{config['tribeQRCodeSavePath']}")
	public void setTribeQRCodeSavePath(String val) {
		tribeQRCodeSavePath = val;
	}

	private static String campBookQRCodeSavePath;

	@Value("#{config['campBookQRCodeSavePath']}")
	public void setCampBookQRCodeSavePath(String val) {
		campBookQRCodeSavePath = val;
	}

	private static String actJoinQRCodeSavePath;

	@Value("#{config['actJoinQRCodeSavePath']}")
	public void setActJoinQRCodeSavePath(String val) {
		actJoinQRCodeSavePath = val;
	}

	private static String actQRCodeSavePath;

	@Value("#{config['actQRCodeSavePath']}")
	public void setActQRCodeSavePath(String val) {
		actQRCodeSavePath = val;
	}
	
	private static String specialtopicQRCodeSavePath;

	@Value("#{config['specialtopicQRCodeSavePath']}")
	public void setSpecialtopicQRCodeSavePath(String val) {
		specialtopicQRCodeSavePath = val;
	}
	
	private static String tribePostQRCodeSavePath;
	@Value("#{config['tribePostQRCodeSavePath']}")
	public void setTribePostQRCodeSavePath(String val) {
		tribePostQRCodeSavePath = val;
	}
	
	private static String mobileAddress;
	@Value("#{config['mobileAddress']}")
	public void setMobileAddress(String val) {
		mobileAddress = val;
	}
	
	private static String destinationQRcodeSavePath;
	@Value("#{config['destinationQRcodeSavePath']}")
	public void setDestinationQRcodeSavePath(String val) {
		destinationQRcodeSavePath = val;
	}
	
	private static String strategyQRcodeSavePath;
	@Value("#{config['strategyQRcodeSavePath']}")
	public void setStrategyQRcodeSavePath(String val) {
		strategyQRcodeSavePath = val;
	}
	
	// 图片宽度的一般
	private static final int IMAGE_WIDTH = 30;
	private static final int IMAGE_HEIGHT = 30;
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	private static final int FRAME_WIDTH = 2;

	/**
	 * 生成攻略的二维码图片并上传至OSS
	 *
	 * @param sid
	 */
	public static void encodeStrategy(Long sid) {
		File filePath = new File(strategyQRcodeSavePath);
		if(!filePath.exists()){
			try {
				FileUtils.forceMkdir(filePath);
			} catch (IOException e) {
				log.error("创建帖子二维码存放目录失败：", e);
			}
		}
		
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(mobileAddress + "strategy/content.html?sid=" + sid, 130, 130,
						FilenameUtils.concat(actQRCodeSavePath, "logo.png")),
						"jpg", new File(FilenameUtils.concat(strategyQRcodeSavePath, sid + ".jpg")));
			OSSServer.uploadImage("strategy/QRCode/" + sid + ".jpg",
					FilenameUtils.concat(strategyQRcodeSavePath, sid + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}
	
	/**
	 * 生成营地的二维码图片并上传至OSS
	 *
	 * @param did
	 */
	public static void encodeCamp(Long did) {
		File filePath = new File(destinationQRcodeSavePath);
		if(!filePath.exists()){
			try {
				FileUtils.forceMkdir(filePath);
			} catch (IOException e) {
				log.error("创建帖子二维码存放目录失败：", e);
			}
		}
		
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(mobileAddress + "camp/content.html?did=" + did, 130, 130,
						FilenameUtils.concat(actQRCodeSavePath, "logo.png")),
						"jpg", new File(FilenameUtils.concat(destinationQRcodeSavePath, did + ".jpg")));
			OSSServer.uploadImage("destination/QRCode/" + did + ".jpg",
					FilenameUtils.concat(destinationQRcodeSavePath, did + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}
	
	public static BufferedImage encodeUserCertify(Long uid, String certifiedType) {
		String url = "3".equals(certifiedType) ? "user/certify/carownerCertify.html?userId=":"user/certify/chiefCertify.html?userId=";
		try {
			BufferedImage image = genBarcode(mobileAddress + url + uid, 180, 180, FilenameUtils.concat(actQRCodeSavePath, "logo.png"));
			return image;
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
		return null;
	}

	public static void encodeTribePost(Long id) {
		File filePath = new File(tribePostQRCodeSavePath);
		if(!filePath.exists()){
			try {
				FileUtils.forceMkdir(filePath);
			} catch (IOException e) {
				log.error("创建帖子二维码存放目录失败：", e);
			}
		}
		
		try {
			BufferedImage image = genBarcode(webServer + "tribe/tribeTopicContent.html?id=" + id, 350, 350, FilenameUtils.concat(actQRCodeSavePath, "logo.png"));
			ImageIO.write(image, "jpg", new File(FilenameUtils.concat(tribePostQRCodeSavePath, id + ".jpg")));
			OSSServer.uploadImage("tribePost/QRCode/" + id + ".jpg", FilenameUtils.concat(tribePostQRCodeSavePath, id + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}

	/**
	 * 生成活动的二维码图片并上传至OSS
	 *
	 * @param aId
	 */
	public static void encodeAct(Long aId) {
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(webServer + "act/content.html?actId=" + aId,
							130, 130,
							FilenameUtils.concat(actQRCodeSavePath, "logo.png")),
					"jpg",
					new File(FilenameUtils.concat(actQRCodeSavePath, aId
							+ ".jpg")));
			OSSServer.uploadImage("act/QRCode/" + aId + ".jpg",
					FilenameUtils.concat(actQRCodeSavePath, aId + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}

	/**
	 * 拷贝图片
	 * @param aid 原活动
	 * @param aidResult 新活动
	 */
	public static void copyFileQR(Long aid ,String aidResult){
		String fileName = FilenameUtils.concat(actQRCodeSavePath, aid +".jpg");//封面
		String tartgetFileName = FilenameUtils.concat(actQRCodeSavePath, aidResult +".jpg");//封面

		try {
			if(new File(fileName).isFile()) {
				FileUtils.copyFile(new File(fileName), new File(tartgetFileName), false);// 拷贝一张原来的
				OSSServer.uploadImage("act/QRCode/" + aidResult + ".jpg",
						FilenameUtils.concat(actQRCodeSavePath, aidResult + ".jpg"));
			}
		} catch (IOException e) {
			log.error("上传海报失败:拷贝二维码图片失败", e);
			return;
		}
	}

	/**
	 * 生成部落的二维码并上传至OSS
	 * 
	 * @param tId
	 */
	public static void encodeTribe(Long tId) {
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(webServer + "tribe/tribeDetails.html?tribeId=" + tId,
							130, 130, FilenameUtils.concat(tribeQRCodeSavePath,
									"logo.png")),
					"jpg",
					new File(FilenameUtils.concat(tribeQRCodeSavePath, tId
							+ ".jpg")));
			OSSServer.uploadImage("tribe/QRCode/" + tId + ".jpg",
					FilenameUtils.concat(tribeQRCodeSavePath, tId + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}

	/**
	 * 生成微信支付的二维码并上传至OSS
	 * @param ajId
	 * @param codeUrl
     */
	public static String encodePay(Long ajId, String codeUrl) {
		String ORCodeUrl = "actJoin/QRCode/" + ajId + "/weixinpay.jpg";
		try {
			String destFile = FilenameUtils.concat(actJoinQRCodeSavePath, ajId + "/weixinpay.jpg");
			FileUtils.forceMkdir(new File(actJoinQRCodeSavePath + ajId));
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(codeUrl, 155, 155,
							FilenameUtils.concat(actQRCodeSavePath, "logo.png")),
					"jpg", new File(destFile));
			OSSServer.createFolder("actJoin/QRCode/" + ajId + "/");
			OSSServer.uploadImage(ORCodeUrl,destFile);
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
		return ORCodeUrl;
	}

	/**
	 * 生成微信支付的二维码并上传至OSS
	 * @param orderId
	 * @param codeUrl
	 */
	public static String encodeCampPay(Long orderId, String codeUrl) {
		String ORCodeUrl = "campOrder/QRCode/" + orderId + "/weixinpay.jpg";
		try {
			String destFile = FilenameUtils.concat(campBookQRCodeSavePath, orderId
					+ "/weixinpay.jpg");
			FileUtils.forceMkdir(new File(campBookQRCodeSavePath + orderId));
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(
					genBarcode(codeUrl, 155, 155,
							FilenameUtils.concat(actQRCodeSavePath, "logo.png")),
					"jpg", new File(destFile));
			OSSServer.createFolder("campOrder/QRCode/" + orderId + "/");
			OSSServer.uploadImage(ORCodeUrl,destFile);
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
		return ORCodeUrl;
	}

	/**
	 * 生成电子票号的二维码图片并上传至OSS
	 *
	 * @param ticket 电子票号
	 */
	public static void encodeTicket(String ticket) {
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(genBarcode(ticket, 200, 200, FilenameUtils.concat(actQRCodeSavePath, "logo.png")), "jpg",
					new File(FilenameUtils.concat(actQRCodeSavePath, ticket + ".jpg")));
			OSSServer.uploadImage("act/QRCode/" + ticket + ".jpg", FilenameUtils.concat(actQRCodeSavePath, ticket + ".jpg"));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}
	
	public static void encodeSpecialtopic(String url, String filename) {
		try {
			BufferedImage image = genBarcode(url, 130, 130, FilenameUtils.concat(actQRCodeSavePath, "logo.png"));//根据活动二维码模板生成专题二维码图像
			String subFilePath = "";
			if(filename.contains("/")){
				subFilePath = filename.substring(0, filename.lastIndexOf("/"));
			}
			File filePath = new File(specialtopicQRCodeSavePath + subFilePath);
			if(!filePath.exists()){
				FileUtils.forceMkdir(filePath);
			}
			File file = new File(FilenameUtils.concat(specialtopicQRCodeSavePath, filename));
			if(!file.exists()){
				// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
				ImageIO.write(image, "jpg", file);
				OSSServer.uploadImage("specialtopic/QRCode/" + filename, FilenameUtils.concat(specialtopicQRCodeSavePath, filename));
			}
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}

	/*
	 * 生成临时的二维码
	 * 
	 * @param width 二维码的宽度
	 * 
	 * @param height 二维码的高度
	 * 
	 * @param srcImagePath 中间嵌套的图片
	 * 
	 * @param destImagePath 二维码生成的地址
	 
	public static void encode(String content, int width, int height,
			String srcImagePath, String destImagePath) {
		try {
			// ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
			ImageIO.write(genBarcode(content, width, height, srcImagePath),
					"jpg", new File(destImagePath));
		} catch (IOException e) {
			log.error("encode error", e);
		} catch (WriterException e) {
			log.error("encode error", e);
		}
	}*/

	/**
	 * 得到BufferedImage
	 * 
	 * @param content
	 *            二维码显示的文本
	 * @param width
	 *            二维码的宽度
	 * @param height
	 *            二维码的高度
	 * @param srcImagePath
	 *            中间嵌套的图片
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static BufferedImage genBarcode(String content, int width,
			int height, String srcImagePath) throws WriterException,
			IOException {

		// 二维码写码器
		MultiFormatWriter mutiWriter = new MultiFormatWriter();

		// 读取源图像
		File file = new File(srcImagePath);
		BufferedImage scaleImage = ImageIO.read(file);

		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}

		java.util.Hashtable hint = new java.util.Hashtable();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hint.put(EncodeHintType.MARGIN, 0);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
				width, height, hint);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {

				// 读取图片
				if (x > halfW - IMAGE_HALF_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH
						&& y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW
							+ IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								- IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
								+ IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xFFFFFF;
				} else {
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? 0x0A0A0A
							: 0xFFFFFF;
				}

			}
		}

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}

}
