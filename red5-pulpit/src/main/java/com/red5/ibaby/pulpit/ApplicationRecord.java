package com.red5.ibaby.pulpit;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-4
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 * 自动录制流文件
 */
public class ApplicationRecord extends MultiThreadedApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(ApplicationRecord.class, "com.red5.ibaby.pulpit.ApplicationRecord");

    @Override
    public boolean connect(IConnection conn, IScope scope, Object[] params) {
        return super.connect(conn, scope, params);
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
