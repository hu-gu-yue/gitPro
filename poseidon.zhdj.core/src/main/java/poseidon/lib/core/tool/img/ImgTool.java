package poseidon.lib.core.tool.img;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

// 相关的参考资料
// http://www.blogjava.net/Alpha/archive/2007/08/20/138171.html
// http://www.gurge.com/amd/old/java/GIFEncoder/index.html

@SuppressWarnings("restriction")
public class ImgTool {
	private final static Logger log = LoggerFactory.getLogger(ImgTool.class);

	private static int width = 700;
	private static int height = 400;

	public ImgTool() {
		super();
	}

	/**
	 *
	 *
	 * @param srcPath
	 *            源路劲
	 * @param destPath
	 *            目标路劲
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param width
	 *            图片压缩后宽度
	 * @param height
	 *            图片压缩后高度
	 * @param readImageFormat
	 *            图片格式
	 * @throws IOException
	 *             异常
	 */
	/*
	 * public static void cropImage(String srcPath, String destPath, Integer x1,
	 * Integer y1, Integer w, Integer h, Integer width, Integer height, String
	 * formate) throws IOException { FileInputStream is = null; ImageInputStream
	 * iis = null; File srcfile = new File(srcPath); try { Image image =
	 * ImageIO.read(srcfile); BufferedImage bi = new BufferedImage(w, h,
	 * BufferedImage.TYPE_INT_RGB); Graphics g = bi.getGraphics();
	 * g.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0,
	 * null); FileOutputStream out = new FileOutputStream(srcfile);
	 * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	 * encoder.encode(bi); out.close();
	 *
	 * is = new FileInputStream(srcfile); iis =
	 * ImageIO.createImageInputStream(is);
	 *
	 * Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(formate);
	 * ImageReader reader = it.next(); reader.setInput(iis, true);
	 *
	 * ImageReadParam param = reader.getDefaultReadParam(); Rectangle rect = new
	 * Rectangle(x1, y1, width, height); param.setSourceRegion(rect);
	 *
	 * bi = reader.read(0, param); ImageIO.write(bi, formate, new
	 * File(destPath)); } finally { if (is != null) { is.close(); } if (iis !=
	 * null) { iis.close(); } } }
	 */

	// 对图片裁剪，并把裁剪新图片保存
	public static boolean cropImage(String srcPath, String destPath, int x,
			int y, int width, int height, String readImageFormat,
			String writeImageFormat) throws IOException {
		boolean flag = true;
		FileInputStream fis = null;
		ImageInputStream iis = null;
		try {
			fis = new FileInputStream(srcPath);
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(readImageFormat);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, writeImageFormat, new File(destPath));
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
		if (!(new File(destPath).exists())) {
			log.info("crop fail!!");
			flag = false;
		} else {
			log.info("crop success!!");
		}
		return flag;
	}

	/**
	 * 重置图形的边长大小
	 *
	 * @param srcImagePath
	 * @param destImagePath
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static boolean resizeImage(String srcImagePath,
									  String destImagePath, int width, int height) throws IOException {
		boolean flag = true;
		FileOutputStream out = null;
		try {
			File file = new File(srcImagePath);
			BufferedImage src = ImageIO.read(file);
			Image from = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2d = tag.createGraphics();
			tag = g2d.getDeviceConfiguration().createCompatibleImage(width,
					height, Transparency.TRANSLUCENT);
			g2d.dispose();
			g2d = tag.createGraphics();
			g2d.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC_OVER));
			g2d.drawImage(from, 0, 0, null);

			g2d.dispose();
			out = new FileOutputStream(destImagePath);
			ImageIO.write(tag, "png", new File(destImagePath));
		} catch (Exception e) {
			log.error("ResizeImage Exception", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		if (!(new File(destImagePath).exists())) {
			log.info("convert fail!!");
			flag = false;
		} else {
			log.info("convert success!!");
		}
		return flag;
	}

	/**
	 * 重置图形的边长大小  原始图片参数为图片的byte[]
	 *
	 * @param srcImage
	 * @param destImagePath  目标图片格式为jpg
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	/*public static boolean resizeImageCtz(byte[] srcImage,
										 String destImagePath, int width, int height) throws IOException {
		boolean flag = true;
		FileOutputStream out = null;
		try {
			InputStream is = new ByteArrayInputStream(srcImage);
			BufferedImage src = ImageIO.read(is);
			Image from = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(from, 0, 0, width, height, null); // 绘制缩小后的图

			out = new FileOutputStream(destImagePath); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			out.close();
		} catch (Exception e) {
			log.error("ResizeImage Exception", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		if (!(new File(destImagePath).exists())) {
			log.info("convert fail!!");
			flag = false;
		} else {
			log.info("convert success!!");
		}
		return flag;
	}*/

	/**
	 * 重置图形的边长大小 原始图片参数为图片绝对路径
	 *
	 * @param srcImagePath
	 * @param destImagePath  目标图片格式为jpg
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	/*public static boolean resizeImageCtz(String srcImagePath,
			String destImagePath, int width, int height) throws IOException {
		boolean flag = true;
		FileOutputStream out = null;
		try {
			File file = new File(srcImagePath);
			BufferedImage src = ImageIO.read(file);
			Image from = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(from, 0, 0, width, height, null); // 绘制缩小后的图

			out = new FileOutputStream(destImagePath); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			out.close();
		} catch (Exception e) {
			log.error("ResizeImage Exception", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		if (!(new File(destImagePath).exists())) {
			log.info("convert fail!!");
			flag = false;
		} else {
			log.info("convert success!!");
		}
		return flag;
	}*/

	/**
	 * 原始图片参数为图片绝对路径
	 * 不裁剪图片，仅仅压缩图片
	 * @param srcImagePath
	 * @param destImagePath  目标图片格式为jpg
	 * @throws IOException
	 */
	/*public static boolean resizeImageCtz(String srcImagePath,
										 String destImagePath) throws IOException {
		boolean flag = true;
		FileOutputStream out = null;
		try {
			File file = new File(srcImagePath);
			BufferedImage src = ImageIO.read(file);
			Image from = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(from, 0, 0, width, height, null); // 绘制缩小后的图

			out = new FileOutputStream(destImagePath); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			out.close();
		} catch (Exception e) {
			log.error("ResizeImage Exception", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		if (!(new File(destImagePath).exists())) {
			log.info("convert fail!!");
			flag = false;
		} else {
			log.info("convert success!!");
		}
		return flag;
	}*/

	/**
	 * 图片灰化操作
	 *
	 * @param srcImage
	 *            读取图片路径
	 * @param destPath
	 *            写入灰化后的图片路径
	 * @param imageFormat
	 *            图片写入格式
	 */
	public static boolean grayImage(String srcImage, String destPath,
			String imageFormat) {
		boolean flag = true;
		try {
			BufferedImage src = ImageIO.read(new File(srcImage));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, imageFormat, new File(destPath));
		} catch (Exception e) {
			log.error("GrayImage Exception", e);
		}
		if (!(new File(destPath).exists())) {
			log.info("gray fail!!");
			flag = false;
		} else {
			log.info("gray success!!");
		}
		return flag;
	}

	public static void saveImageToFile(String urlAddress, String fileAddress)
			throws Exception {
		// new一个URL对象
		URL url = new URL(urlAddress);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();

		// 把图片读入到内存中
		BufferedImage bi = ImageIO.read(inStream);

		// 生成图片
		ImageIO.write(bi, "jpg", new File(fileAddress));
	}

	public static void saveImageToFileReSizer(String urlAddress,
			String fileAddress) throws Exception {
		// new一个URL对象
		URL url = new URL(urlAddress);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();

		BufferedImage bufImg = ImageIO.read(inStream);// 把图片读入到内存中
		// 压缩代码
		String fileSuffix = fileAddress.substring(
				fileAddress.lastIndexOf(".") + 1, fileAddress.length());

		int dwidth = bufImg.getWidth() > width ? width : bufImg.getWidth();
		int dheight = bufImg.getHeight() > height ? height : bufImg.getHeight();
		bufImg = Thumbnails.of(bufImg).size(dwidth, dheight).outputQuality(0.9)
				.asBufferedImage();

		ImageIO.write(bufImg, fileSuffix, new File(fileAddress));
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	/**
	 * 生成三张大小不同的图片
	 *
	 * @param id
	 * @param orgFileName
	 * @param savePath
	 * @param imgSize
	 *            调整后图片尺寸
	 * @throws Exception
	 * @author zhouqi
	 */
	public static void genSMLImage(String orgFileName, String id,
			String savePath, int[] imgSize) throws Exception {
		File orgFile = new File(orgFileName);
		if (orgFile.isFile()) {
			try {
				FileUtils.forceMkdir(new File(savePath + "/" + id + "/"));
				// 生成一个图片的裁剪大小
				resizeImage(orgFileName, savePath + "/" + id + "/" + id
						+ "_S.png", imgSize[0], imgSize[1]);
				resizeImage(orgFileName, savePath + "/" + id + "/" + id
						+ "_M.png", imgSize[2], imgSize[3]);
				resizeImage(orgFileName, savePath + "/" + id + "/" + id
						+ "_L.png", imgSize[4], imgSize[5]);
				// by xiejingyang 不删除原来的文件
				/*
				 * if (!orgFile.delete()) { throw new
				 * Exception("File delete fail."); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new Exception("File don't exists.");
		}
	}

	/*public static void generateImageFromUrl(String url, String filefullpath){
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(false);
		driver.get(url);
		WebElement element=driver.findElementByCssSelector("html");
		String html = element.getText();
		HtmlImageGenerator2 imageGenerator = new HtmlImageGenerator2();
		imageGenerator.loadHtml(html);
		imageGenerator.getBufferedImage();
		imageGenerator.saveAsImage(filefullpath);

	}*/

	public static void main(String[] args) throws IOException {
		/*Robot robot = null;
        try{
			Desktop.getDesktop().browse(
					new URL("http://mt.zjyou.cn/topic/topicDetail.html?id=9200").toURI());
			robot = new Robot();
		}
		catch(Exception ex){

		}

		robot.delay(10000);
		Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		//最大化浏览器
		robot.keyRelease(KeyEvent.VK_F11);
		robot.delay(2000);
		Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
				height));
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		//保存图片
		ImageIO.write(bi, "jpg", new File("d:/google.jpg"));
		//generateImageFromUrl("http://mt.zjyou.cn/topic/topicDetail.html?id=9200", "d:/9200topicDetail.png");
		//cropImage("D:/mnt/poseidon/static/img/act/test.jpg","D:/mnt/poseidon/static/img/act/test2.jpg", 20,20,1180,450,"jpg","jpg");
        String fileName = "D:/mnt/poseidon/static/img/act/1.png";
		InputStream file = new FileInputStream("D:/mnt/poseidon/static/img/act/1.png");
		BufferedImage bufferedImg = ImageIO.read(file);


		int imgType=fileName.lastIndexOf(".");
		String imgTypeStr=fileName.substring(imgType);

		float imgRate = (float)bufferedImg.getWidth()/bufferedImg.getHeight();
		float rate = (float)1180/450;
		if (imgRate >= rate){
			float newWidth = bufferedImg.getWidth() * ((float)450/bufferedImg.getHeight());

			ImgTool.resizeImage("D:/mnt/poseidon/static/img/act/1.png", "D:/mnt/poseidon/static/img/act/test3333.png", (int)newWidth, 450);
			float x = (newWidth - 1180)/2;
			cropImage("D:/mnt/poseidon/static/img/act/test3333.png","D:/mnt/poseidon/static/img/act/test3333.png", (int)x,0,1180,450,"png","png");
		}
		else{
			float newHeight = bufferedImg.getHeight() * ((float)1180/bufferedImg.getWidth());
			ImgTool.resizeImage("D:/mnt/poseidon/static/img/act/1.png", "D:/mnt/poseidon/static/img/act/test3333.png", 1180, (int)newHeight);
			float y = (newHeight - 450)/2;
			cropImage("D:/mnt/poseidon/static/img/act/test3333.png","D:/mnt/poseidon/static/img/act/test3333.png", 0,(int)y,1180,450,"png","png");
		}*/

	}

}
