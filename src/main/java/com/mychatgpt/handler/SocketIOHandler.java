package com.mychatgpt.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SocketIOHandler {
    @Autowired
    private SocketIOServer socketIOServer;

    public static Map<UUID,SocketIOClient> clientMap=new ConcurrentHashMap<> ();
    @OnConnect
    public void connect(SocketIOClient client){
        UUID sessionId=client.getSessionId ();
        clientMap.put (sessionId,client);
        log.info("{}连接服务器",client.getSessionId ());
    }
    @OnDisconnect
    public void disconnect(SocketIOClient client){
        UUID sessionId=client.getSessionId ();
        clientMap.remove (sessionId);
        log.info("{}断开连接服务器",client.getSessionId ());
    }
}
