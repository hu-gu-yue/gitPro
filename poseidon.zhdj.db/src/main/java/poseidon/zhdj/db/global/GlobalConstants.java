package poseidon.zhdj.db.global;

/**
 * 全局常量类
 * Created by hlf on 2017/5/9.
 */
public class GlobalConstants {
    /** 活动类型 1 和主播 */
    public final static int ACT_HZB = 1;
    /** 活动类型 2 青创精 */
    public final static int ACT_QCJ = 2;

    // 礼物类型
    public static class GiftType {
        /**  1 芙蓉花*/
        public final static int GIFT1 = 1;
        /**  2 房车*/
        public final static int GIFT2 = 2;
        /**  3 航母*/
        public final static int GIFT3 = 3;
        /**  4 灯塔*/
        public final static int GIFT4 = 4;
        /**  5 超跑*/
        public final static int GIFT5 = 5;
        /**  6 飞机*/
        public final static int GIFT6 = 6;
    }

    /** 礼物最多送出的个数 */
    public final static int MAX_SEND = 20;

    /** 所有视频格式 */
    public final static String[] VIDEO_TYPES = {"AVI", "ASF", "WMV", "RM", "RMVB", "VCD", "MPEG", "MOV", "DVD","DV", "3GP" ,"MP4" ,"FLV","MPG"};

    /** 所有文档格式 */
    public final static String[] DOC_TYPES = {"TXT", "HTML", "XLS", "DOC", "PDF", "HLP", "WPS", "RTF", "DOCX", "XLSX", "PPT", "PPTX"};

    /** 所有图片格式 */
    public final static String[] IMG_TYPES = {"BMP","GIF","JPG","PIC","PNG","TIF","JPEG","PSD","SVG","SWF"};

    /** 只能投票10次 */
    public final static int VOTE_TIMES = 10;

    /** 只能星标2次 */
    public final static int STAR_TIMES = 2;

    // 幸运榜类型
    public static class LuckyType {
        /**  1 点赞榜*/
        public final static int LIKE = 1;
        /**  2 评论榜*/
        public final static int REPLY = 2;
        /**  3 围观榜*/
        public final static int CIRCUSEE = 3;
    }

    // 排名类型类型
    public static class RankType {
        /**  1 点赞排名*/
        public final static int LIKE = 1;
        /**  2 投票排名*/
        public final static int VOTE = 2;
    }

    /** 获奖作品数 */
    public final static int PRIZE_COUNT = 40;

    // 礼物价值分数
    public static class GiftScore {
        /** 芙蓉花 5分 */
        public final static int SCORE1 = 5;
        /** 房车 5分 */
        public final static int SCORE2 = 10;
        /** 航母 5分 */
        public final static int SCORE3 = 100;
        /** 灯塔 5分 */
        public final static int SCORE4 = 50;
        /** 超跑 5分 */
        public final static int SCORE5 = 20;
        /** 飞机 5分 */
        public final static int SCORE6 = 30;
    }

    /** 围观奖20个 */
    public final static int CIRCUSEE_NUM = 20;

    // 礼物价值分数
    public static class ReplyType {
        /** 进入直播间 */
        public final static int IN = 1;
        /** 评价文字 */
        public final static int WORDS = 2;
        /** 点赞 */
        public final static int LIKE = 3;
        /** 送礼 */
        public final static int GIFT = 4;
    }

    //视频类型,1:工作类；2：生活类
    public static class VideoType {
        public final static int WORK = 1;
        public final static int LIFE = 2;
    }

    //文档类型,1:文档；2：视频,3:图片,4:音乐
    public static class VideoDocumentType {
        public final static int DOCUMENT = 1;
        public final static int VIDEO = 2;
        public final static int IMG = 3;
        public final static int MUSIC = 4;
    }

    //文档操作类型：查看、评论、点赞
    public static class VideoOperaType {
        public final static String VIEW = "view";
        public final static String REPLY = "comment";
        public final static String LIKE = "praises";
    }

    /** 单位16个 */
    public final static String[] UNIT_LIST = {"公司总部","市场营销中心","技术研发中心","原料采购中心","长沙卷烟厂","常德卷烟厂","郴州卷烟厂","零陵卷烟厂", "四平卷烟厂","吴忠卷烟厂","湖南中烟投资管理有限公司"
                                                ,"湖南中烟物流有限责任公司","湖南金叶烟草薄片公司","湘西鹤盛原烟发展公司","浏阳天福打叶复烤公司","常德芙蓉烟叶复烤有限责任公司"};
}
