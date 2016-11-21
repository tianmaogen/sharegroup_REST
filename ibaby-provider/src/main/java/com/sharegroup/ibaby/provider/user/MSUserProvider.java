package com.sharegroup.ibaby.provider.user;

import com.sharegroup.ibaby.msrest.MSApiService;
import com.sharegroup.ibaby.msrest.MSApiServiceImpl;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by Administrator on 2016/2/16.
 * 集成MS用户，与.net使用同一个用户数据资源
 */
public class MSUserProvider implements UserProvider {

    private static final Logger Log = LoggerFactory.getLogger(MSUserProvider.class);

    private static final String LOAD_USER =
            "SELECT name, email, creationDate, modificationDate FROM ofUser WHERE username=?";

    private static final boolean IS_READ_ONLY = true;

    private static MSApiService service = new MSApiServiceImpl();


    @Override
    public User loadUser(String username) throws UserNotFoundException {
        if (username.contains("@")) {
            if (!XMPPServer.getInstance().isLocal(new JID(username))) {
                throw new UserNotFoundException("Cannot load user of remote server: " + username);
            }
            username = username.substring(0, username.lastIndexOf("@"));
        }
        if (!"admin".equals(username) && !"ibaby".equals(username)) {
            try {
                User u = service.loadUser(username);
                return u;
            } catch (Exception e) {
                throw new UserNotFoundException(e);
            }
        } else {
            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                con = DbConnectionManager.getConnection();
                pstmt = con.prepareStatement(LOAD_USER);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if (!rs.next()) {
                    throw new UserNotFoundException();
                }
                String name = rs.getString(1);
                String email = rs.getString(2);
                Date creationDate = new Date(Long.parseLong(rs.getString(3).trim()));
                Date modificationDate = new Date(Long.parseLong(rs.getString(4).trim()));

                return new User(username, name, email, creationDate, modificationDate);
            } catch (Exception e) {
                throw new UserNotFoundException(e);
            } finally {
                DbConnectionManager.closeConnection(rs, pstmt, con);
            }
        }
    }

    @Override
    public User createUser(String s, String s1, String s2, String s3) throws UserAlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getUserCount() {
        Log.info("get user count");
        return service.getUserCount();
    }

    @Override
    public Collection<User> getUsers() {
        Collection<String> usernames = getUsernames(0, Integer.MAX_VALUE);
        return new UserCollection(usernames.toArray(new String[usernames.size()]));
    }

    @Override
    public Collection<String> getUsernames() {
        return getUsernames(0, Integer.MAX_VALUE);
    }

    @Override
    public Collection<User> getUsers(int startIndex, int numResults) {
        Collection<String> usernames = getUsernames(startIndex, numResults);
        return new UserCollection(usernames.toArray(new String[usernames.size()]));
    }

    /**
     * 查询用户数据
     *
     * @param startIndex
     * @param numResults
     * @return
     */
    private Collection<String> getUsernames(int startIndex, int numResults) {
        Log.info("get user names  startIndex {" + startIndex + "} , num results {" + numResults + "}");
        List<String> usernames = service.getUsers(startIndex, numResults);
        return usernames;
    }

    @Override
    public void setName(String s, String s1) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEmail(String s, String s1) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCreationDate(String s, Date date) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setModificationDate(String s, Date date) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getSearchFields() throws UnsupportedOperationException {
        return new LinkedHashSet<String>(Arrays.asList("Username", "Name", "Email"));
    }

    @Override
    public Collection<User> findUsers(Set<String> fields, String query) throws UnsupportedOperationException {
        return findUsers(fields, query, 0, Integer.MAX_VALUE);
    }

    @Override
    public Collection<User> findUsers(Set<String> fields, String query, int startIndex,
                                      int numResults) throws UnsupportedOperationException {
        Log.info("find users query:" + query);
        throw new UnsupportedOperationException();
        /*
        List<String> usernames = service.findUsers(fields, startIndex, numResults);

        return new UserCollection(usernames.toArray(new String[usernames.size()]));
        */
    }

    @Override
    public boolean isReadOnly() {
        return IS_READ_ONLY;
    }

    @Override
    public boolean isNameRequired() {
        return false;
    }

    @Override
    public boolean isEmailRequired() {
        return false;
    }
}
