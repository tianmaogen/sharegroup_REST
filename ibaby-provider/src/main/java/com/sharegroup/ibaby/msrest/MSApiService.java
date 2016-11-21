package com.sharegroup.ibaby.msrest;

import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserNotFoundException;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/2/16.
 */
public interface MSApiService {

    /**
     * 获取用户密码
     * @param userName 用户ID
     * @return 用户密码（密文）
     */
     String getPassword(String userName) throws UserNotFoundException;


    /**
     * 获取用户信息
     * @param username 用户ID
     * @return
     * String username, String name, String email, Date creationDate, Date modificationDate
     */
    User loadUser(String username) throws UserNotFoundException;

    /**
     * 查询用户数量
     *@return  返回所有的用户数量
     */
    int getUserCount();


    /**
     * 获取数据
     * @return 分页数据 username
     */
    List<String> getUsers(int startIndex, int numResults);

    /**
     * 模糊查询
     * @param fields 可查询字段为
     *          Username、Name、Email
     * @param startIndex
     * @param numResults
     * @return username
     */
    List<String> findUsers(Set<String> fields,int startIndex, int numResults);



}
