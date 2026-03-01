package com.rubyjr.videocall.config;

import com.rubyjr.videocall.utilities.auth.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public static final String ENDPOINT_HANDSHAKE = "/ws";
    public static final String APP_DESTINATION = "/api";
    public static final String USER_DESTINATION = "/user";
    public static final String BRODCAST = "/app";
    public static final String UNICAST = "/private";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        messageBrokerRegistry.enableSimpleBroker(
                BRODCAST, // Para brodcast
                UNICAST // Para unicast
        );
        messageBrokerRegistry.setApplicationDestinationPrefixes(APP_DESTINATION); // Endpoint raíz para que los clientes envien información por el websocket
        messageBrokerRegistry.setUserDestinationPrefix(USER_DESTINATION); // // Mensajes privados hacia el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // Endpoint para conectarse con el websocket -- handshake
        stompEndpointRegistry.addEndpoint(ENDPOINT_HANDSHAKE)
                .setAllowedOrigins("*"); // Cors
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String authHeader = accessor.getFirstNativeHeader("Authorization");

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        throw new IllegalArgumentException("Missing or invalid Authorization header");
                    }

                    String token = authHeader.substring(7);

                    if (!jwtUtil.isTokenValid(token)) {
                        throw new IllegalArgumentException("Invalid JWT token");
                    }

                    String email = jwtUtil.getEmail(token);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, token, List.of());

                    accessor.setUser(usernamePasswordAuthenticationToken);

                }
                return message;
            }
        });
    }

}
