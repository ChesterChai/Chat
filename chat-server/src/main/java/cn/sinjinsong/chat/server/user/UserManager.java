package cn.sinjinsong.chat.server.user;

import cn.sinjinsong.chat.server.dao.impl.ImplDaoUserDao;
import cn.sinjinsong.chat.server.domain.DaoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SinjinSong on 2017/5/23.
 */
@Component("userManager")
@Slf4j
public class UserManager {
    @Autowired
    private ImplDaoUserDao daoUserDao;

    private Map<String, SocketChannel> nameToChannel;
    private Map<SocketChannel, String> channelToName;

    public UserManager() {
        nameToChannel = new ConcurrentHashMap<>();
        channelToName = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录验证，如果登陆成功加入在线用户
     * @param channel
     * @param username
     * @param password
     * @return
     */
    public synchronized  boolean login(SocketChannel channel, String username, String password) {
        DaoUser daoUser = daoUserDao.findByName(username);
        if (daoUser==null||!password.equals(daoUser.getPassword())) {
            return false;
        }

        if(nameToChannel.containsKey(username)){
            log.info("重复登录，拒绝");
            //重复登录会拒绝第二次登录
            return false;
        }

        nameToChannel.put(username, channel);
        channelToName.put(channel, username);

        return true;
    }
    
    public synchronized void logout(SocketChannel channel) {
        String username = channelToName.get(channel);
        log.info("{}下线", username);
        channelToName.remove(channel);
        nameToChannel.remove(username);
    }
    
    public synchronized SocketChannel getUserChannel(String username) {
        SocketChannel channel = nameToChannel.get(username);
        return channel==null?null:channel;
    }
    
}
