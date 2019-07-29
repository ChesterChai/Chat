package cn.sinjinsong.chat.server;

import cn.sinjinsong.chat.server.dao.IDaoUserDao;
import cn.sinjinsong.chat.server.dao.impl.ImplDaoUserDao;
import cn.sinjinsong.chat.server.domain.DaoUser;
import cn.sinjinsong.chat.server.util.SpringContextUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.Date;

/**
 * Created by SinjinSong on 2017/5/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ChatServerTest {
    private InputStream in;
    private SqlSession session;
    private IDaoUserDao userDao;

    @Test
    public void testSaveUser(){
        DaoUser user = new DaoUser();
        user.setUserName("jam");
        user.setPassword("1234");
        user.setRegisterTime(new Date());
        ImplDaoUserDao userDao = SpringContextUtil.getBean("ImplDaoUserDao");
        userDao.saveUser(user);
    }

    @Test
    public void testSelectUser(){
        ImplDaoUserDao userDao = SpringContextUtil.getBean("ImplDaoUserDao");
        DaoUser users = userDao.findByName("erew");
        System.out.println(users);
    }

}