package weixin;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class WeiXinPayModel implements Serializable{
	private String attach;
	private String body;
	private String nonceStr;
	private String timeStamp;
	private String notifyUrl;
	private String openId;
	private String outTradeNo;
	private String remoteIp;
	private String transactionId;
	private String totalFee;
	private String tradeStatus;
	private String paySign;
	private Long uid;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date cTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date mTime;

	public WeiXinPayModel() {
	}

	public WeiXinPayModel(String attach,String body, String nonceStr, String timeStamp, String notifyUrl, String openId, String outTradeNo, String remoteIp,
						  	String transactionId, String totalFee,String tradeStatus,String paySign, Long uid,Date cTime,Date mTime) {
		this.attach = attach;
		this.body = body;
		this.nonceStr = nonceStr;
		this.timeStamp = timeStamp;
		this.notifyUrl = notifyUrl;
		this.openId = openId;
		this.outTradeNo = outTradeNo;
		this.remoteIp = remoteIp;
		this.transactionId = transactionId;
		this.totalFee = totalFee;
		this.tradeStatus = tradeStatus;
		this.paySign = paySign;
		this.uid = uid;
		this.cTime = cTime;
		this.mTime = mTime;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getmTime() {
		return mTime;
	}

	public void setmTime(Date mTime) {
		this.mTime = mTime;
	}
}
