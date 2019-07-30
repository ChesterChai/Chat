package cn.sinjinsong.chat.server.dao.impl;

import cn.sinjinsong.chat.server.dao.IDaoUserDao;
import cn.sinjinsong.chat.server.domain.DaoUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;

@Component("ImplDaoUserDao")
public class ImplDaoUserDao implements IDaoUserDao {
    private InputStream in;
    private SqlSession session;
    private IDaoUserDao userDao;

    @PostConstruct
    public void init() throws Exception{
        //读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        factory.getConfiguration().addMapper(IDaoUserDao.class);
        //使用工厂生产SqlSession对象
        session = factory.openSession(true);
        //使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(IDaoUserDao.class);
    }

    @PreDestroy
    public void destroy() throws Exception{
        //释放资源
        session.close();
        in.close();
    }

    @Override
    public DaoUser findByName(String userName) {
        return userDao.findByName(userName);
    }

    @Override
    public int saveUser(DaoUser user) {
        return userDao.saveUser(user);
    }
}
