package github.nyaku12.ASCONAChat.WebSocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MyWebSocketHandler mywebSocketHandler;

    public WebSocketConfig(MyWebSocketHandler myWebSocketHandler){
        this.mywebSocketHandler = myWebSocketHandler;
    }

    public void configureMessageBroker(MessageBrokerRegistry config) {
        // в миллисекундах: [интервал пинга, интервал ожидания ответа]
        config.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[] {10000, 10000}); // 10 сек пинг, 10 сек ожидание
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mywebSocketHandler, "/ws")
                .setAllowedOrigins("*"); // разрешить все источники, можно настроить
    }
}
