package websocket;

import java.io.IOException;
import java.util.*;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import poseidon.lib.core.tool.checkempty.CheckEmptyTool;
import poseidon.lib.core.tool.string.StringTool;

import javax.servlet.http.HttpSession;

public class SystemWebSocketHandler implements WebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	
	public static final Map<String, List<WebSocketSession>> user = new HashMap<>();//存放建立连接的用户

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("................afterConnectionEstablished............");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.debug("..............handleMessage............" + message.getPayload().toString() + "   session id = " + session.getId());

		if(message instanceof TextMessage){
			if(StringTool.isNotEmpty(message.getPayload().toString())){
				String str = message.getPayload().toString();

				JSONObject jsonObject = JSONObject.fromObject(str);
				String chatId = jsonObject.getString("chatId");

				//通过message.getPayload().toString()  拆分出聊天室的key 跟 聊天内容content
				List<WebSocketSession> users = SystemWebSocketHandler.user.get(chatId);
				if(CheckEmptyTool.emptyCollection(users)){
					users = new ArrayList<>();
					user.put(chatId, users);
				}
				if(!users.contains(session)){//如果没有登陆过聊天室则添加，否则同一个session不添加进list
					users.add(session);
				}
				broadcast(chatId, str);
			}
		}

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		Iterator iter = user.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			List<WebSocketSession> tempsessionlist = (List<WebSocketSession>)entry.getValue();
			Iterator it = tempsessionlist.iterator();
			while(it.hasNext()){
				WebSocketSession tempsession = (WebSocketSession)it.next();
				if (tempsession.getId().equals(session.getId())){
					it.remove();
					log.debug("----handleTransportError--------移除sessionkey-----------" + entry.getKey().toString());
				}
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		Iterator iter = user.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			List<WebSocketSession> tempsessionlist = (List<WebSocketSession>)entry.getValue();
			Iterator it = tempsessionlist.iterator();
			while(it.hasNext()){
				WebSocketSession tempsession = (WebSocketSession)it.next();
				if (tempsession.getId().equals(session.getId())){
					it.remove();
					log.debug("----afterConnectionClosed--------移除sessionkey-----------" + entry.getKey().toString());
				}
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 根据key，获取对应的当前正在观看的用户的session列表，再讲content内容发送给客户端
	 * @param chatId   聊天室的唯一标示
	 * @param content
	 * @throws IOException
	 */
	public static void broadcast(String chatId, String content) throws IOException{

		TextMessage message = new TextMessage(content);
		List<WebSocketSession> users = SystemWebSocketHandler.user.get(chatId);
		for(WebSocketSession user: users){
			if(user != null && user.isOpen()){
				user.sendMessage(message);
				log.debug("----sendMessage--------message = " + content + " chatId = " + chatId);
			}
		}

	}

	/**
	 * 根据key，获取对应的websoketsession，再讲content内容发送给客户端
	 * @param key
	 * @param content
	 * @throws IOException
     */
	/*public static void sendMessage(String key, String content) throws IOException{
		TextMessage message = new TextMessage(content);
		WebSocketSession user = SystemWebSocketHandler.user.get(key);
		if(user != null && user.isOpen()){
			user.sendMessage(message);
			log.debug("----sendMessage--------message = " + content + " key = " + key);
		}
	}*/

	/**
	 * 根据key获取对应的websocket 的session
	 * @param key
	 * @return
     */
//	public static WebSocketSession getSessionByKey(String key){
//		return SystemWebSocketHandler.user.get(key);
//	}
}
