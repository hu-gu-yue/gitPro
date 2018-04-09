package poseidon.zhdj.db.video.model;

import java.util.Date;

public class VideoModel {
    private Long vid;

    private Long zyId;//中烟传过来的资源编号

    private Integer docType;//文档类型：比如：media,document,和主播项目新增的

    private String zyType;//资源类型：比如：mp4,word,PPT,Excel,PDF

    private Integer videoType;//视频类型：比如：1：工作类，2：生活类

    private String headImg;//用户头像路径

    private String title;

    private String desc;

    private String url;

    private String videoImg;//缩略图路径

    private Integer views;

    private Integer status;

    private Date cTime;

    private Date mTime;

    private String uid;

    private String userName;

    private String unitName;

    private Integer act;

    private Integer voteCount;//投票数

    private Integer likeCount;//点赞数

    private Integer giftCount;

    private Integer giftScore;

    private Integer favoriteCount;

    private Integer starCount;//星标数

    private Integer isLike;//是否已点赞

    private Integer isVote;//是否已投票

    private Integer isViewed;//是否已阅读

    private Integer todayIsLike;//今天是否点赞

    private String voteContent;//点评金句

    private Integer isStar;//是否已投标

    private Integer isFavorite;//是否已收藏

    private String sign;//资源签名，数据交互用来验证

    private Integer uploadCount;//部门上传视频数

    private Integer totalCount;//总视频数

    private Long viewCount;//总浏览数

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getZyId() {
        return zyId;
    }

    public void setZyId(Long zyId) {
        this.zyId = zyId;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public String getZyType() {
        return zyType;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public void setZyType(String zyType) {
        this.zyType = zyType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCTime() {
        return cTime;
    }

    public void setCTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsVote() {
        return isVote;
    }

    public void setIsVote(Integer isVote) {
        this.isVote = isVote;
    }

    public void setHeadImg(String headImg) {

        this.headImg = headImg;
    }

    public Integer getGiftScore() {
        return giftScore;
    }

    public void setGiftScore(Integer giftScore) {
        this.giftScore = giftScore;
    }

    public String getVoteContent() {
        return voteContent;
    }

    public void setVoteContent(String voteContent) {
        this.voteContent = voteContent;
    }

    public Integer getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

    public Integer getTodayIsLike() {
        return todayIsLike;
    }

    public void setTodayIsLike(Integer todayIsLike) {
        this.todayIsLike = todayIsLike;
    }

    public Integer getIsStar() {
        return isStar;
    }

    public void setIsStar(Integer isStar) {
        this.isStar = isStar;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(Integer uploadCount) {
        this.uploadCount = uploadCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}