package poseidon.lib.core.tool.sms;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import poseidon.lib.core.tool.json.JsonTool;

public class AliSmsTool {
	private final static Logger log = LoggerFactory.getLogger(AliSmsTool.class);
	
	private static String aliSmsUrl = "http://gw.api.taobao.com/router/rest";
	private static String aliAppKey = "23343267";
	private static String aliAppSecret = "12798c0f0aed19605105784f1ca131f5";
	public static String SMS_TPL_COMMISSIONAPY = "SMS_10346007";//提现申请
	public static String SMS_TPL_CHECKTICKET = "SMS_10350961";//验证电子票后发送短信
	public static String SMS_TPL_ACTJOIN_TOJOINER_LINK = "SMS_10421101";//活动报名成功带链接(发送给参与人)
	public static String SMS_TPL_ACTJOIN_TOJOINER_NOLINK = "SMS_10361132";//活动报名成功不带链接(发送给参与人)
	public static String SMS_TPL_ACTJOIN_TOPUBLISHER = "SMS_10510027";//活动报名成功(发送给发起人)
	public static String SMS_TPL_FINDPWD = "SMS_10391100";//找回密码
	public static String SMS_TPL_REGISTER = "SMS_10396140";//注册
	public static String SMS_TPL_ACTJOIN_TOJOINER_LINK_TEST="SMS_10641348";//活动报名成功带链接(发送给参与人)(测试环境)
	public static String SMS_TPL_USER_CERTIFY="SMS_12670015";//用户认证
	public static String SMS_TPL_REFUSE_CAMP_ORDER="SMS_26260039";//商家拒绝用户订单
	public static String SMS_TPL_CHECK_CAMP_CODE="SMS_26105090";//验证电子票成功
	public static String SMS_TPL_ACCEPT_CAMP_ORDER="SMS_26160129";//预定营地成功
	public static String SMS_TPL_CANCEL_CAMP_ORDER="SMS_26290097";//用户取消营地预定
	public static String SMS_TPL_CANCEL_FOR_BUSINESS="SMS_26105092";//商家收到用户取消订单的短信
	public static String SMS_TPL_CANCEL_MORE_24HOUR="SMS_26295030";//时间大于24小时退款
	public static String SMS_TPL_CANCEL_OVER_TIME="SMS_26320091";//超过入住时间发起退款
	public static String SMS_TPL_RECEIVE_ORDER="SMS_26275092";//商家收到订单
	public static String SMS_TPL_REMINDER="SMS_26180057";//出行提醒
	
	/**
	 * 发送短信
	 * @param recNum 电话号码
	 * @param smsTemplateCode 内容模板
	 * @param smsParamMap 模板参数
	 */
	public static void sendSms(String recNum, String smsTemplateCode, Map<String, String> smsParamMap){
		TaobaoClient client = new DefaultTaobaoClient(aliSmsUrl, aliAppKey, aliAppSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		String smsParam = JsonTool.toJSON(smsParamMap);
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("自驾友");
		req.setSmsParamString(smsParam);
		req.setRecNum(recNum);//
		req.setSmsTemplateCode(smsTemplateCode);
		
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req); 
			log.debug("调用ali短信平台发送短信到" + recNum + "结果：" + rsp.getBody() 
			+ ",rsp.getErrorCode():" + rsp.getErrorCode() 
			+ ",rsp.getMsg():" + rsp.getMsg() 
			+ ",rsp.getSubCode():" + rsp.getSubCode() 
			+ ",rsp.getSubMsg():" + rsp.getSubMsg());
		} catch (ApiException e) {
			log.error("调用ali短信平台发送短信报错：", e);
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "123456");
		System.out.println(JsonTool.toJSON(map));
	}
}
