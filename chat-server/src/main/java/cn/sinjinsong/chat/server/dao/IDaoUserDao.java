package cn.sinjinsong.chat.server.dao;

import cn.sinjinsong.chat.server.domain.DaoUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;


public interface IDaoUserDao {
    /**
     * 根据用户名和密码查找用户
     * @param user
     * @return
     */
    @Select("select * from users where userName = #{userName}")
    DaoUser findByName(String userName);

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into users(userName,password,registerTime) values(#{userName},#{password},#{registerTime})")
    int saveUser(DaoUser user);

    /**
     *
     * @return
     */
    @Select("select * from users")
    Set<DaoUser> allUser();
}
