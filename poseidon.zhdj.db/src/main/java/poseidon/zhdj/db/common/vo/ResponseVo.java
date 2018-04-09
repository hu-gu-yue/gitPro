package poseidon.zhdj.db.common.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseVo<T> {

	private final static Logger log = LoggerFactory.getLogger(ResponseVo.class);

	boolean success;
	String message;
	int count;
	T data;

	public ResponseVo() {
		success = false;
		message = "";
		data = null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}
	
	/**
	 * 设置成功响应
	 */
	public void setSuccess() {
		this.success = true;
	}

	/**
	 * 设置成功响应、消息
	 */
	public void setSuccess(String message) {
		this.success = true;
		this.message = message;
		
		
	}

	/**
	 * 设置成功响应状态、消息、数据
	 */
	public void setSuccess(String message, T data){
		this.success = true;
		this.message = message;
		this.data = data;
		log.info(message);
	}
	
	/**
	 * 设置成功响应数据,消息为默认的OK
	 */
	public void setSuccess( T data){
		this.success = true;
		this.message = "Ok";
		this.data = data;
		log.info(message);
		
	}
	
	/**
	 * 个人主页发私信所专用函数
	 */
	public void setSuccesschatuser( T data,int count){
		this.success = true;
		this.message = "Ok";
		this.data = data;
		this.count=count;
		log.info(message);
		
	}
	/**
	 * 设置错误响应，没有异常
	 */
	public void setError(String message) {
		this.success = false;
		this.message = message;
		Thread current = Thread.currentThread();  
		StackTraceElement element= current.getStackTrace()[2];
		String info = " ("+element.getFileName()+":"+element.getLineNumber()+") ";
		log.error(info + message);
	}

	/**
	 * 设置错误响应,出现了异常
	 */
	
	public void setError(String message, Exception ex) {
		this.success = false;
		this.message = message;
		log.error(message, ex);
	}
	
	public void setError(String message, Exception ex, T data){
		this.success = false;
		this.message = message;
		this.data = data;
		log.error(message, ex);
	}
}
