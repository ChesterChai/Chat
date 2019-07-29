package cn.sinjinsong.chat.server.handler.message.impl;

import cn.sinjinsong.chat.server.dao.impl.ImplDaoUserDao;
import cn.sinjinsong.chat.server.domain.DaoUser;
import cn.sinjinsong.chat.server.handler.message.MessageHandler;
import cn.sinjinsong.chat.server.property.PromptMsgProperty;
import cn.sinjinsong.common.domain.Message;
import cn.sinjinsong.common.domain.MessageHeader;
import cn.sinjinsong.common.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component("MessageHandler.register")
@Slf4j
public class RegisterMessageHandler extends MessageHandler {
    @Autowired
    private ImplDaoUserDao daoUserDao;

    @Override
    public void handle(Message message, Selector server, SelectionKey client, BlockingQueue<Task> queue, AtomicInteger onlineUsers) throws InterruptedException {
        MessageHeader header = message.getHeader();
        String username = header.getSender();
        String password = new String(message.getBody(), PromptMsgProperty.charset);
        DaoUser daoUser = DaoUser.builder().userName(username)
                .password(password).registerTime(new Date()).build();
        daoUserDao.saveUser(daoUser);
    }
}
