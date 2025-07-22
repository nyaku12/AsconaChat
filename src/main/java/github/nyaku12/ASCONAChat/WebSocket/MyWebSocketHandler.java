package github.nyaku12.ASCONAChat.WebSocket;

import com.google.gson.Gson;
import github.nyaku12.ASCONAChat.GeneralController;
import github.nyaku12.ASCONAChat.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    ConcurrentHashMap<String, Integer> connections = new ConcurrentHashMap<>();
    @Autowired
    GeneralController controller;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
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
                break;
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
                controller.createMessage(jsonMap);
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