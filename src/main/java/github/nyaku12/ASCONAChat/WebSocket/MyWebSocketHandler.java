package github.nyaku12.ASCONAChat.WebSocket;

import com.google.gson.Gson;
import com.mysql.cj.log.Log;
import github.nyaku12.ASCONAChat.GeneralController;
import github.nyaku12.ASCONAChat.Message.Message;
import github.nyaku12.ASCONAChat.User.User;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    ConcurrentHashMap<String, Integer> connections = new ConcurrentHashMap<>(); // sessionId → userId
    ConcurrentHashMap<Integer, WebSocketSession> userConnections = new ConcurrentHashMap<>(); // userId → sessionId
    @Autowired
    GeneralController controller;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Gson gson = new Gson();
        System.out.println("connection succes: " + session.getId());
        Integer code = controller.checkUser(session.getHandshakeHeaders());
        switch (code){
            case -1:
                session.sendMessage(new TextMessage("Wrong Login"));
                session.close();
                break;
            case -2:
                session.sendMessage(new TextMessage("Wrong Login or Password"));
                session.close();
                break;
            case -3:
                session.sendMessage(new TextMessage("You already Logged in"));
                session.close();
                break;
            default:
                session.sendMessage(new TextMessage("succes"));
                controller.updateOnlineById(code, true);
                connections.put(session.getId(), code);
                userConnections.put(code, session);
                System.out.println("new connection with userId: " + code);
                break;
        }//проверка логина и пароля
        List<Message> messages = controller.getMessages(connections.get(session.getId()));
        for (int i = 0; i < messages.size(); i++){
            session.sendMessage(new TextMessage(gson.toJson(messages.get(i))));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gson = new Gson();

        String payload = message.getPayload();

        Map<String, Object> jsonMap = gson.fromJson(payload, Map.class);
        System.out.println("received message: " + message.getPayload());

        switch (jsonMap.get("type").toString()) {
            case ("createUser"):
                controller.createUser(jsonMap);
                break;
            case ("createChat"):
                controller.createChat(jsonMap);
                break;
            case ("sentMessage"):
                Message mess = controller.createMessage(jsonMap, connections.contains(jsonMap.get("chat")));
                if(userConnections.get(((Number) jsonMap.get("chat")).intValue()) != null){
                    System.out.println("user connected" + jsonMap.get("chat"));
                    userConnections.get(((Number) jsonMap.get("chat")).intValue()).sendMessage(new TextMessage(gson.toJson(mess)));
                }
                else System.out.println("user is not connected " + jsonMap.get("receiver"));
                break;
            case ("readMessage"):
                controller.readMessage(jsonMap);
                break;
            case ("getChats"):

                break;
            case ("joinchat"):
                controller.joinChat(jsonMap);
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("connection closed: " + session.getId());
        controller.updateOnlineById(connections.get(session.getId()), false);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Err: " + exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }
}