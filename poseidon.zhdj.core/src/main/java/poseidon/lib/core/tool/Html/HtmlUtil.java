package poseidon.lib.core.tool.Html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import poseidon.lib.core.tool.cipher.Md5Tool;
import poseidon.lib.core.tool.config.Config;
import poseidon.lib.core.tool.string.StringTool;
  
public class HtmlUtil {  
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
    /**
     * 图片鉴权域名
     */
    private static final String AUTH_IMG_HOST = Config.getValue("authImgHost","http://imgen.zjyou.cn");
    /**
     * true 支持懒加载
     */
    private static final Boolean AUTH_IMG_IS_LAZY = Boolean.parseBoolean(Config.getValue("authImgIsLazy","true"));
    /**
     * C鉴权秘钥
     */
    private static final String AUTH_KEY_C = Config.getValue("authKeyC","zjyouauth");
    /**
     * 鉴权有效时间  单位  秒
     */
    private static final long AUTH_VALID_TIME = Long.parseLong(Config.getValue("authValidTime","60"));
    /**
     * 是否打开鉴权开关
     * 默认关闭（如果关闭，则不会对url进行鉴权）
     */
    private static final Boolean AUTH_IS_OPEN = Boolean.parseBoolean(Config.getValue("authIsOpen","false"));
    
    /** 
     * @param htmlStr 
     * @return 
     *  删除Html标签 
     */  
    public static String delHTMLTag(String htmlStr) {
        if(htmlStr == null){
            return "";
        }
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
        Matcher m_html = p_html.matcher(htmlStr);  
        htmlStr = m_html.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        return htmlStr.trim(); // 返回文本字符串  
    }  
      
    public static String getTextFromHtml(String htmlStr){  
        htmlStr = delHTMLTag(htmlStr);  
        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);  
        return htmlStr;  
    }
    
    /**
     * 对内容（图片等）增加鉴权访问控制
     * @param html 待鉴权的内容
     * @return
     */
    public static String getAuthHtml(String html){
    	
    	return getAuthHtml(html, AUTH_IMG_IS_LAZY, AUTH_IMG_HOST);
    }
    
    /**
     * 对内容（图片等）增加鉴权访问控制
     * @param html 待鉴权的内容
     * @param isLazy 是否延迟加载
     * @return
     */
    public static String getAuthHtml(String html, boolean isLazy){
    	
    	return getAuthHtml(html, isLazy, AUTH_IMG_HOST);
    }
    
    /**
     * 对内容（图片等）增加鉴权访问控制
     * @param html 待鉴权的内容
     * @param isLazy 是否延迟加载
     * @param host 资源域名
     * @return
     */
    public static String getAuthHtml(String html, boolean isLazy, String host){
    	Document doc = Jsoup.parse(html);
    	Elements elements = doc.select("img");
    	String src = "";
    	for (Element element : elements) {
			src = getAuthUrl(element.attr("src"), host);
			
			if(isLazy){
				element.removeAttr("src");
				element.addClass("lazy").attr("data-original",src);
			}else {
				element.attr("src",src);
			}
		}
    	
    	return doc.body().html();
    }
    
    /**
     * 使用c鉴权方式加密url
     * @param url 资源路径       eg:/destination/31/31_campImage_3.jpg
     * @return
     */
    public static String getAuthUrl(String url){
    	
    	return getAuthUrl(url, AUTH_IMG_HOST);
    }
    
    /**
     * 使用c鉴权方式加密url
     * 详见：https://help.aliyun.com/document_detail/27135.html?spm=5176.doc27112.6.144.XPmdim
     * @param url 资源路径       eg:http://img.zjyou.cn/destination/31/31_campImage_3.jpg
     * @param host 加密资源域名    eg:http://imgen.zjyou.cn
     * @return
     */
    public static String getAuthUrl(String url, String host){
    	if(!AUTH_IS_OPEN){
    		return url;
    	}
    	String path = "";
    	String md5Hash = "";
    	String timestamp = Long.toHexString(System.currentTimeMillis()/1000-(1800-AUTH_VALID_TIME));
    	if(StringTool.isNotEmpty(url)){
    		path = url.replace(getHost(url), "");
    		if(path.startsWith("http:")){
     			return url;
     		}else if(!path.startsWith("/")){
    			path = "/" + path;
    		}
    		
    		md5Hash = Md5Tool.getMD5Str(AUTH_KEY_C + path + timestamp);
    		return host+"/"+md5Hash + "/" + timestamp + path;
    	}
    	return "";
    }
    
    /**
     * 使用c鉴权方式重新加密已加密url
     * @param enUrl  已经加密过的url
     * @return  返回一个重新加密的url
     */
    public static String getRepeatAuthUrl(String enUrl){
    	String host = getHost(enUrl);
    	if(!host.startsWith("http")||!AUTH_IMG_HOST.equals(host)){
    		return enUrl;
    	}
    	String newUrl = enUrl.replace(host, "");
    	String[] tempArr = newUrl.split("/");
    	
    	String pureUrl = "";
    	for(int i=3;i<tempArr.length;i++){
    		pureUrl += ("/"+tempArr[i]);
    	}
    	
    	return getAuthUrl(pureUrl);
    }
    
    /**
     * 获取域名
     * @param url
     * @return
     */
    private static String getHost(String url){
    	Pattern pattern = Pattern.compile("http.*cn");
    	Matcher matcher = pattern.matcher(url);
    	if (matcher.find()) {
			return matcher.group();
		}
    	return "";
    }
      
    public static void main(String[] args) {  
        String str = "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";  
        //System.out.println(getTextFromHtml(str));  
        
        //System.out.println(getAuthUrl("http://imgtest.zjyou.cn/recommend/P/indexpage/1/1058/9041/9041.jpg"));
        System.out.println(getAuthUrl("/recommend/P/indexpage/1/1058/9041/9041.jpg"));
        System.out.println(getAuthUrl("/test.jpg"));
        
        System.out.println(getAuthHtml("<p><img src='http://imgen.zjyou.cn/recommend/P/indexpage/1/1058/9041/9041.jpg'></p>"));
        
        
        System.out.println(getRepeatAuthUrl("http://imgen.zjyou.cn/47ee4d8ad2b085a06e40e8eed92948e8/586329e1/recommend/P/indexpage/1/1058/9041/9041.jpg"));
    }  
}  
