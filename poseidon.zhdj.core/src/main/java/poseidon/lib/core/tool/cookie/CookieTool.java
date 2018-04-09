package poseidon.lib.core.tool.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/20.
 */
public class CookieTool {
    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     */
    public static void addCookie(HttpServletResponse response, String name, String value){
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setPath("/");
        int maxAge = 604800; //cookie生命周期  以秒为单位
        if(maxAge>0)  cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            try {
                return URLDecoder.decode(cookie.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 删除cookie
     * @param response
     * @param request
     */
    public static int deleteCookie(HttpServletResponse response ,HttpServletRequest request){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey("qcj_uid")){
            Cookie cookie = (Cookie)cookieMap.get("qcj_uid");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            if(cookieMap.containsKey("qcj_userName")){
                Cookie cookie1 = (Cookie)cookieMap.get("qcj_userName");
                cookie1.setPath("/");
                cookie1.setMaxAge(0);
                response.addCookie(cookie1);
            }
            return 1;
        } else {
            return 0;
        }
    }


}
