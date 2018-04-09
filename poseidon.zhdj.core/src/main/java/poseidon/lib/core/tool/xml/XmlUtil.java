package poseidon.lib.core.tool.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;

import net.sf.json.JSONObject;

public class XmlUtil {

	public static String getElementValue(String xml, String name) throws Exception {
    	Document doc = DocumentHelper.parseText(xml);   
    	Element root = doc.getRootElement();
    	Element element = root.element(name);
        return element.getStringValue();
    }
	
    public static String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));  
            SAXBuilder sb = new SAXBuilder();  
            org.jdom.Document doc = sb.build(is);  
            org.jdom.Element root = doc.getRootElement();  
            Map map = iterateElement(root);
            obj.put(root.getName(), map);
            String content = obj.getString("xml");
            obj = JSONObject.fromObject(content);
            return obj.toString().replaceAll("\\[|\\]", ""); 
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
    private static Map  iterateElement(org.jdom.Element element) {  
        List jiedian = element.getChildren();  
        org.jdom.Element et = null;  
        Map obj = new HashMap();  
        List list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList();  
            et = (org.jdom.Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  

	public static void main(String[] args) {
		String xml = "<xml><appid>wx2421b1c4370ec43b</appid><mch_id>10000100</mch_id><nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str><transaction_id>1008450740201411110005820873</transaction_id></xml>";
		String s = xml2JSON(xml);
		System.out.println(s);
		
	}
}
