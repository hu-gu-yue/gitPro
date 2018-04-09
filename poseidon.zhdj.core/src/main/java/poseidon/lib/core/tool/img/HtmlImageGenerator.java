package poseidon.lib.core.tool.img;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;

import poseidon.lib.core.tool.img.util.FormatNameUtil;
import poseidon.lib.core.tool.img.util.SynchronousHTMLEditorKit;

public class HtmlImageGenerator {
	private JEditorPane editorPane;
	static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	public HtmlImageGenerator() {
		this.editorPane = createJEditorPane();
	}
	
	public void loadHtml(String html) {
	    this.editorPane.setText(html);
	    onDocumentLoad();
	}

	public void saveAsImage(String file) {
		saveAsImage(new File(file));
	}

	public void saveAsImage(File file) {
		BufferedImage img = getBufferedImage();
		try {
			String formatName = FormatNameUtil.formatForFilename(file.getName());
			ImageIO.write(img, formatName, file);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while saving '%s' image", new Object[] { file }), e);
		}
	}

	public BufferedImage getBufferedImage() {
		Dimension prefSize = this.editorPane.getPreferredSize();
		BufferedImage img = new BufferedImage(prefSize.width, this.editorPane.getPreferredSize().height, 2);
		Graphics graphics = img.getGraphics();
		this.editorPane.setSize(prefSize);
		this.editorPane.paint(graphics);
		return img;
	}

	public Dimension getDefaultSize() {
		return DEFAULT_SIZE;
	}

	protected void onDocumentLoad() {
	}

	protected JEditorPane createJEditorPane() {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setSize(getDefaultSize());
		editorPane.setEditable(false);
		SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
		editorPane.setEditorKitForContentType("text/html", kit);
		editorPane.setContentType("text/html");
		editorPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("page"))
					HtmlImageGenerator.this.onDocumentLoad();
			}
		});
		return editorPane;
	}

	public static void main(String[] args) throws Exception {
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		String html = "<p style=\"width:720px;font-size:24px;color:#323232;text-align:center;height:64px;line-height:64px;font-family:'Microsoft yahei', Arial;margin-bottom: 0px;\">电子票详情</p>"
						+ "<table style=\"width:720px;background-color:white;border: 1px solid #b5b5b5;margin-top:0px;position:relative;\">"
							+ "<tr>"
								+ "<td style=\"width:400px;font-size:18px;color:#323232;line-height:60px;overflow:hidden;font-family:'Microsoft yahei', Arial;\">【全国】狂野自驾 穿越蒙古、俄罗斯自驾13天</td>"
								+ "<td style=\"text-align:right;font-size:16px;color:#323232;line-height:60px;font-family:'Microsoft yahei', Arial;\">报名时间：2016-03-08 17:27:57</td>"
							+ "</tr>"
							+ "<tr><td colspan=\"2\" style=\"border-top:1px solid #b5b5b5;font-size:16px;font-weight:normal;padding-top:16px;margin-top:10px;margin-bottom:8px;padding-top:10px;color:#323232;font-family:'Microsoft yahei', Arial;\">活动票号：123456789123（共 <i style=\"color:#e65526;font-style:normal;\">1</i> 张）</td></tr>"
							+ "<tr>"
								+ "<td colspan=\"2\">"
									+ "<table>"
										+ "<tr>"
											+ "<td style=\"\"><img style=\"margin-top:0px;width:130px;height:130px;\" src=\"http://localhost:8081/act/QRCode/123456789123.jpg\"></td>"
											+ "<td style=\"padding-left:20px;\">"
												+ "<div style=\"margin-bottom:5px;\"><span style=\"align:left;color:#acacac;font-size:14px;font-family:'Microsoft yahei', Arial;font-style:normal;\">活动时间：<span style=\"color:#323232;\">2016-03-31 00:00:00 至 2015-07-27 00:00:00</span></span></div>"
												+ "<div style=\"margin-bottom:5px;\"><span style=\"align:left;color:#acacac;font-size:14px;font-family:'Microsoft yahei', Arial;\">活动地点：<span style=\"color:#323232;\">长沙</span></span></div>"
												+ "<div style=\"margin-bottom:5px;\"><span style=\"align:left;color:#acacac;font-size:14px;font-family:'Microsoft yahei', Arial;\">票据类型：<span style=\"color:#323232;\">免费票</span></span></div>"
												+ "<div style=\"margin-bottom:5px;\"><span style=\"align:left;color:#acacac;font-size:14px;font-family:'Microsoft yahei', Arial;\">联系姓名：<span style=\"color:#323232;\">ZTA</span></span></div>"
												+ "<div style=\"margin-bottom:5px;\"><span style=\"align:left;color:#acacac;font-size:14px;font-family:'Microsoft yahei', Arial;\">联系电话：<span style=\"color:#323232;\">18711124255</span></span></div>"
											+ "</td>"
										+ "</tr>"
									+"</table>"
								+ "</td>"
							+ "</tr>"
						+ "</table>";
		
		imageGenerator.loadHtml(html);
		imageGenerator.getBufferedImage();
		imageGenerator.saveAsImage("d:/hello-world.png");
	}
}
