package com.red5.ibaby.pulpit;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-4
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 * 自动录制流文件
 */
public class ApplicationAuto extends MultiThreadedApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(ApplicationAuto.class, "com.red5.ibaby.pulpit.ApplicationAuto");

    public String pulpit_user_notify ;


    @Override
    public boolean connect(IConnection conn, IScope scope, Object[] params) {
        return super.connect(conn, scope, params);
    }


    @Override
    public boolean roomConnect(IConnection conn, Object[] params) {
        log.info("@@@@@@@@@@@@@@@room Connect@@@@@@@@@@@@@@@@@@@@@@@");
        int len = params.length;
        if(len >= 2){
            String userId = (String) params[1];
            conn.setAttribute("userId",userId);
        }
        syncUser(conn,1);
        return super.roomConnect(conn, params);
    }

    @Override
    public void roomDisconnect(IConnection conn) {
        log.info("@@@@@@@@@@@@@@@room Disconnect@@@@@@@@@@@@@@@@@@@@@@@");
        syncUser(conn,0);
        super.roomDisconnect(conn);
    }

    /**
     * 同步用户状态到pulpit
     * @param conn
     */
    private void syncUser(IConnection conn,int state) {

        try{

            String userId = (String) conn.getAttribute("userId");
            if(null == userId || "".equals(userId))
                return;
            String path = conn.getPath();
            PulpitUser user = new PulpitUser(path,userId,state);
            log.info("user Info "+user.toString());
            SyncUserService.pushMessage(pulpit_user_notify, user);

        }catch (Exception ex){
            log.info("notify user exception : "+ex.getMessage());
        }
    }

    public String getPulpit_user_notify() {
        return pulpit_user_notify;
    }

    public void setPulpit_user_notify(String pulpit_user_notify) {
        this.pulpit_user_notify = pulpit_user_notify;
    }


    /**
     * 1 自动录制
     * 2 只允许一个人在线说话，不能多人同时说话，不然录出来的音频文件是混合的
     * 3 手动录制和自动录制只允许一个存在
     * @param stream
     */
    @Override
    public void streamBroadcastStart(IBroadcastStream stream) {
        log.info("pulpit streamBroadcastStart {} pushName is {}",stream.getName(), stream.getPublishedName());
        try {
            //IScope room = this.getScope();
            //String roomName = getFullName(stream.getPublishedName(), room);
            log.info("pulpit {},this room is recording",stream.getPublishedName());
            ClientBroadcastStream clientBroadcastStream = (ClientBroadcastStream)stream;
            clientBroadcastStream.saveAs("pulpit-"+stream.getPublishedName(),true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.streamBroadcastStart(stream);
    }

    /**
     * 停止录制，如果客户端没有流上行到服务器，则自动停止录制，并保存.flv文件
     * @param stream
     */
    @Override
    public void streamBroadcastClose(IBroadcastStream stream) {
        log.info("pulpit streamBroadcastClose {} pushName is {}" ,stream.getName(),stream.getPublishedName());
        super.streamBroadcastClose(stream);
    }

}
