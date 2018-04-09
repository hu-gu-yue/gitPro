package poseidon.lib.core.tool.excel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * 导出Excel工具类
 * @author tanxijun
 */
public class JXLSTool {

	private final static Logger log = LoggerFactory
			.getLogger(JXLSTool.class);
	/**
	 * EXCEL模板路径
	 * 该路径在工程的src/main/resources/templateExcel
	 */
	public static final String EXCEL_TEMPLATE_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/templateExcel/";
	
	/**
	 * 导出Excel文件流
	 * @param response
	 * @param map 数据源
	 * @param excelTempName excel模板      eg:campOrderTemp.xls
	 * @param destFileName 导出文件的名字    eg:campOrder.xls
	 */
	public static void downloadExcel(HttpServletResponse response,Map<String,Object> map,String excelTempName,String destFileName){
		log.debug("---下载Excel开始---");
		String templateFileName= EXCEL_TEMPLATE_PATH + excelTempName;
		
		XLSTransformer transformer = new XLSTransformer();
        InputStream in=null;
        OutputStream out=null;
        
        try {
        	//设置响应
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(destFileName, "UTF-8"));
            response.setContentType("application/vnd.ms-excel");
        	
            in=new BufferedInputStream(new FileInputStream(templateFileName));
            Workbook workbook = transformer.transformXLS(in, map);
            out=response.getOutputStream();
            //将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException e) {
        	e.printStackTrace();
        	log.error(e.getLocalizedMessage(),e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage(),e);
        } finally {
            if (in!=null){try {in.close();} catch (IOException e) {}}
            if (out!=null){try {out.close();} catch (IOException e) {}}
        }
	}
}
