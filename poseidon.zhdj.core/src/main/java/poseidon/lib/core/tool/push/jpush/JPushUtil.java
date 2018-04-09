package poseidon.lib.core.tool.push.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 推送工具类
 * @author tanxijun
 * 20160816
 */
public class JPushUtil {
	
	private final static Logger log = LoggerFactory.getLogger(JPushUtil.class);
			
	private static final String appKey ="bd49963f97934345afddda54";
	private static final String masterSecret = "aa440d6592cdc1a527a4b739";
	
	
	/**
	 * 根据别名发送通知
	 * @param info 发送的通知
	 * @param stateVal $stateProvider.state(@param)
	 * @param who 用户uid
	 * @return void JPushUtil.pushNotification("我是谭喜军,I come on", "register", "alias");
	 */
	public static void pushNotification(String info,String stateVal,String... who){
		ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_all_alias_alert(info,stateVal,who);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
        	log.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
        	log.error("Error response from JPush server. Should review and fix it. ", e);
        	log.info("HTTP Status: " + e.getStatus());
        	log.info("Error Code: " + e.getErrorCode());
        	log.info("Error Message: " + e.getErrorMessage());
        	log.info("Msg ID: " + e.getMsgId());
        }
	}
	
	
	/**
	 * 数据构造方法--给所有用户发送通知
	 * @param info 发送的通知
	 * @return PushPayload
	 */
	private static PushPayload buildPushObject_all_all_alert(String info) {
	    return PushPayload.alertAll(info);
	}
	
	
	/**
	 * 数据构造方法--根据别名发送通知
	 * @param info 发送的通知
	 * @param state $stateProvider.state(@param)
	 * @param who 用户身份表示  eg：uid
	 * @return PushPayload
	 */
	private static PushPayload buildPushObject_all_alias_alert(String info,String stateVal,String... who) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias(who))
//                .setNotification(Notification.newBuilder()
//                		.setAlert(info)
//                		//.ad("from", "JPush")
//                		.build())
////                .setOptions(Options.newBuilder()
////                        .setApnsProduction(true)
////                        .build())
//                .build();
//        
        
        
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(who))
                .setNotification(Notification.newBuilder()
                		.setAlert(info)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.addExtra("stateVal", stateVal).build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.addExtra("stateVal", stateVal).build())
                		.build())
//                .setOptions(Options.newBuilder()
//                    .setApnsProduction(true)
//                    .build())
                .build();
    }
	
	
	/**
	 * 数据构造方法--根据标签发送通知   如果传入多个标签则取这些标签的并集
	 * @param info 发送的通知
	 * @param tag 标签
	 * @return PushPayload
	 */
	private static PushPayload buildPushObject_all_tag_alert(String info,String... tag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(tag))
                .setNotification(Notification.alert(info))
                .build();
    }
}
