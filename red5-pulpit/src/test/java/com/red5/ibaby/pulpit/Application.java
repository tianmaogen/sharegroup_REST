package com.red5.ibaby.pulpit;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-4
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 * 手动录制流文件
 */
public class Application extends MultiThreadedApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(Application.class, "com.red5.ibaby.pulpit.Application");


    private Map<String,IScope> scopeMap = new HashMap<String, IScope>();

    /**
     * 获取指定的房间(IScope)
     * @param fullName
     *         roomPath+roomName
     * @return
     */
    private IScope getRoom(String fullName){
        log.info("pulpit get  room ");
        return scopeMap.get(fullName);
    }

    /**
     * 创建IScope 并存储所有的IScope
     *      定义存储的Iscope名：roomPath+roomName
     *      防止pulpit/Doctor/class1 与 pulpit/Mother/class1 重名导致roomName(IScope)覆盖
     * @param room
     * @return
     */
    @Override
    public boolean roomStart(IScope room) {
        log.info("pulpit  {} roomStart",room);
        String fullName =  room.getPath()+room.getName();
        String roomName = fullName.replace("/","");
        log.info("pulpit roomStart fullName is {}",roomName);
        scopeMap.put(roomName, room);
        log.info("pulpit current count room is {}",scopeMap.size());
        return super.roomStart(room);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 结束IScope
     * @param room
     */
    @Override
    public void roomStop(IScope room) {
        log.info("pulpit  {} roomStop",room);
        String fullName =  room.getPath()+room.getName();
        String roomName = fullName.replace("/","");
        log.info("pulpit roomStop fullName is {}", roomName);
        scopeMap.remove(roomName);
        super.roomStop(room);
    }

    /**
     * 流文件开始录制
     * @param streamName  要录制的流的文件名
     * @param roomName     要录制的流的房间IScope
     */
    public void startRecording(String streamName,String roomName){
        log.info("pulpit  app {} - {} start recoding",roomName,streamName);
        IScope room =  this.getRoom(roomName);
        if(room == null){
            log.info("pulpit startRecording room {} is null",roomName);
            return;
        }
        log.info("pulpit recording room is {}",room);
        ClientBroadcastStream stream = (ClientBroadcastStream)this.getBroadcastStream(room, streamName);
        if(stream == null){
            log.info("pulpit startRecording stream {} is null",roomName);
            return;
        }
        log.info("pulpit recording stream is {}", stream);
        try {
            StringBuffer sbf =  new StringBuffer(roomName);
            sbf.append("-").append(streamName);
            stream.saveAs(sbf.toString(),true);
        } catch (IOException e) {
            log.error("pulpit savAs exception is : "+e.getMessage());
        }
    }

    /**
     * 停止录制音频文件
     * @param streamName   流文件名
     * @param roomName    流所在的房间
     */
    public void stopRecording(String streamName,String roomName){

        log.info("pulpit  app {} stop recoding",roomName);
        IScope room =  this.getRoom(roomName);
        if(room == null){
            log.info("pulpit stopRecording room {} is null",roomName);
            return;
        }
        //房间中可同时存在多个流文件 ，只能停止指定的流
        ClientBroadcastStream stream = (ClientBroadcastStream)this.getBroadcastStream(room,streamName);
        if(stream == null){
            log.info("pulpit stopRecording stream {} is null",roomName);
            return;
        }
        stream.stopRecording();

    }

}
