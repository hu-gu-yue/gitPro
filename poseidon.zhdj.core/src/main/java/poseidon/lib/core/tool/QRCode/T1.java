package poseidon.lib.core.tool.QRCode;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import com.google.zxing.WriterException;

public class T1 {

	public static void main(String[] args) {
		try {
			ImageIO.write(QRCodeTool.genBarcode("http://m.zjyou.cn/user/chiefCertifyApy.html", 130, 130, FilenameUtils.concat("D:/mnt/poseidon/static/img/act/QRCode", "logo.png")), "jpg", new File("D:/b.jpg"));
		} catch (IOException e) {
		} catch (WriterException e) {
		}
	}

}
