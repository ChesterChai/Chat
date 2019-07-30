package cn.sinjinsong.chat.server.handler.message.impl;

import cn.sinjinsong.chat.server.dao.impl.ImplDaoUserDao;
import cn.sinjinsong.chat.server.domain.DaoUser;
import cn.sinjinsong.chat.server.handler.message.MessageHandler;
import cn.sinjinsong.chat.server.property.PromptMsgProperty;
import cn.sinjinsong.common.domain.*;
import cn.sinjinsong.common.enumeration.ResponseCode;
import cn.sinjinsong.common.enumeration.ResponseType;
import cn.sinjinsong.common.util.ProtoStuffUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
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
        SocketChannel clientChannel = (SocketChannel) client.channel();
        MessageHeader header = message.getHeader();
        String username = header.getSender();
        String password = new String(message.getBody(), PromptMsgProperty.charset);
        DaoUser daoUser = DaoUser.builder().userName(username)
                .password(password).registerTime(new Date()).build();


        try {
            if(daoUserDao.findByName(username) != null){
                byte[] response = ProtoStuffUtil.serialize(
                        new Response(
                                ResponseHeader.builder()
                                        .type(ResponseType.PROMPT)
                                        .sender(message.getHeader().getSender())
                                        .timestamp(message.getHeader().getTimestamp())
                                        .responseCode(ResponseCode.REGISTER_FAILURE.getCode()).build(),
                                String.format(PromptMsgProperty.NAME_EXISTS).getBytes(PromptMsgProperty.charset)));
                clientChannel.write(ByteBuffer.wrap(response));
            }else{
                daoUserDao.saveUser(daoUser);
                byte[] response = ProtoStuffUtil.serialize(
                        new Response(
                                ResponseHeader.builder()
                                        .type(ResponseType.PROMPT)
                                        .sender(message.getHeader().getSender())
                                        .timestamp(message.getHeader().getTimestamp())
                                        .responseCode(ResponseCode.REGISTER_SUCCESS.getCode()).build(),
                                String.format(PromptMsgProperty.REGISTER_SUCCESS).getBytes(PromptMsgProperty.charset)));
                clientChannel.write(ByteBuffer.wrap(response));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
