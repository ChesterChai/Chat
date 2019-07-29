package cn.sinjinsong.chat.server.handler.message.impl;

import cn.sinjinsong.chat.server.handler.message.MessageHandler;
import cn.sinjinsong.common.domain.Message;
import cn.sinjinsong.common.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component("MessageHandler.register")
@Slf4j
public class RegisterMessageHandler extends MessageHandler {

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> queue, AtomicInteger onlineUsers) throws InterruptedException {
        System.out.println("register success!!");
    }
}
