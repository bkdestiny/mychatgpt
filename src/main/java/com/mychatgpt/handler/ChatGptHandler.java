package com.mychatgpt.handler;


import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mychatgpt.dto.ChatRequestParameter;
import com.mychatgpt.util.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.websocket.Session;
import java.nio.channels.Channel;
import java.util.function.Consumer;

@Component
@Slf4j
public class ChatGptHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ChatModel chatModel;
    ChatRequestParameter chatRequestParameter=new ChatRequestParameter ();
    @OnEvent("message")
    public void message(SocketIOClient client, String message){
        long startTime=System.currentTimeMillis ();
        log.info("问题-->{}",message);
        Consumer<String> resConsumer=res->{
            try{
               log.info ("res-->{}",res);
               //client.sendEvent ("chatGptMessage",res);
            }catch (Exception e) {
                client.sendEvent ("chatGptMessage", "$end$");
                throw new RuntimeException ();
            }
        };
        //返回结果
        String answer= chatModel.getAnswer (resConsumer,chatRequestParameter,message);
        long endTime=System.currentTimeMillis ();
        long time=(endTime-startTime)/1000;
        log.info ("answer-->{}",answer);
        client.sendEvent ("chatGptMessage",answer+"                --- 耗时:"+String.valueOf (time)+"s ---");
        //client.sendEvent ("chatGptMessage","$end$");
    }
}
