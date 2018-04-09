package poseidon.lib.core.tool.push;


import poseidon.lib.core.tool.push.android.AndroidUnicast;

/**
 * 推送工具
 */
public class PushUtil {

    public static class AfterOpen {
        public static final String GO_ACTIVITY = "go_activity";
    }

    public static void pushMessage(String deviceToken, String ticker, String title, String content, String afterOpen,
                                   String activity, String playSound, String playVibrate) {
        pushAndroidMessage(deviceToken, ticker, title, content, afterOpen, activity, playSound, playVibrate);
    }

    public static void pushAndroidMessage(String deviceToken, String ticker, String title, String content, String afterOpen,
                                          String activity, String playSound, String playVibrate) {
        AndroidUnicast unicast = new AndroidUnicast();

        try {
            unicast.setAppMasterSecret(UmengNotification.appMasterSecret);
            unicast.setPredefinedKeyValue("appkey", UmengNotification.appKey);
            unicast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
            unicast.setPredefinedKeyValue("device_tokens", deviceToken);
            unicast.setPredefinedKeyValue("ticker", ticker);
            unicast.setPredefinedKeyValue("title", title);
            unicast.setPredefinedKeyValue("text", content);
            unicast.setPredefinedKeyValue("after_open", afterOpen);
            unicast.setPredefinedKeyValue("activity", activity);
            unicast.setPredefinedKeyValue("display_type", "notification");
            unicast.setPredefinedKeyValue("production_mode", "true");
            unicast.setPredefinedKeyValue("play_sound", playSound);
            unicast.setPredefinedKeyValue("play_vibrate", playVibrate);
            unicast.setExtraField("new", "true");
            unicast.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
