package com.sharegroup.rest.packet;

import com.sharegroup.rest.bean.AuthenticationToken;
import org.jivesoftware.openfire.plugin.rest.entity.MUCRoomEntities;
import org.jivesoftware.openfire.plugin.rest.entity.MUCRoomEntity;
import org.jivesoftware.openfire.plugin.rest.entity.ParticipantEntities;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
public class MucApiClient {

	/** The rest client. */
    private RestClient restClient;


    /**
     * Instantiates a new rest api client.
     *
     * @param url
     *            the url
     * @param port
     *            the port
     * @param authenticationToken
     *            the authentication token
     */
    public MucApiClient(String url, int port, AuthenticationToken authenticationToken) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        restClient = new RestClient.RestClientBuilder(url + ":" + port).authenticationToken(authenticationToken)
                .connectionTimeout(5000).build();
    }

    /**
     * Gets the rest client.
     *
     * @return the rest client
     */
    public RestClient getRestClient() {
        return restClient;
    }


    /**
     * Gets the chat rooms.
     *
     * @return the chat rooms
     */
    public MUCRoomEntities getChatRooms() {
        return restClient.get("chatrooms", MUCRoomEntities.class, new HashMap<String, String>());
    }

    /**
     * Gets the chat rooms.
     *
     * @param queryParams
     *            the query params
     * @return the chat rooms
     */
    public MUCRoomEntities getChatRooms(Map<String, String> queryParams) {
        return restClient.get("chatrooms", MUCRoomEntities.class, queryParams);
    }

    /**
     * Gets the chat room.
     *
     * @param roomName
     *            the room name
     * @return the chat room
     */
    public MUCRoomEntity getChatRoom(String roomName) {
        return restClient.get("chatrooms/" + roomName, MUCRoomEntity.class, new HashMap<String, String>());
    }

    /**
     * Creates the chat room.
     *
     * @param chatRoom
     *            the chat room
     * @return the response
     */
    public Response createChatRoom(MUCRoomEntity chatRoom) {
        return restClient.post("chatrooms", chatRoom, new HashMap<String, String>());
    }

    /**
     * Update chat room.
     *
     * @param chatRoom
     *            the chat room
     * @return the response
     */
    public Response updateChatRoom(MUCRoomEntity chatRoom) {
        return restClient.put("chatrooms/" + chatRoom.getRoomName(), chatRoom, new HashMap<String, String>());
    }

    /**
     * Delete chat room.
     *
     * @param roomName
     *            the room name
     * @return the response
     */
    public Response deleteChatRoom(String roomName) {
        return restClient.delete("chatrooms/" + roomName, new HashMap<String, String>());
    }

    /**
     * Gets the chat room participants.
     *
     * @param roomName
     *            the room name
     * @return the chat room participants
     */
    public ParticipantEntities getChatRoomParticipants(String roomName) {
        return restClient.get("chatrooms/" + roomName + "/participants", ParticipantEntities.class,
                new HashMap<String, String>());
    }

    /**
     * Adds the owner.
     *
     * @param roomName
     *            the room name
     * @param jid
     *            the jid
     * @return the response
     */
    public Response addOwner(String roomName, String jid) {
        return restClient.post("chatrooms/" + roomName + "/owners/" + jid, null, new HashMap<String, String>());
    }

    /**
     * Adds the admin.
     *
     * @param roomName
     *            the room name
     * @param jid
     *            the jid
     * @return the response
     */
    public Response addAdmin(String roomName, String jid) {
        return restClient.post("chatrooms/" + roomName + "/admins/" + jid, null, new HashMap<String, String>());
    }

    /**
     * Adds the member.
     *
     * @param roomName
     *            the room name
     * @param jid
     *            the jid
     * @return the response
     */
    public Response addMember(String roomName, String jid) {
        return restClient.post("chatrooms/" + roomName + "/members/" + jid, null, new HashMap<String, String>());
    }

    /**
     * Adds the outcast.
     *
     * @param roomName
     *            the room name
     * @param jid
     *            the jid
     * @return the response
     */
    public Response addOutcast(String roomName, String jid) {
        return restClient.post("chatrooms/" + roomName + "/outcasts/" + jid, null, new HashMap<String, String>());
    }

}
