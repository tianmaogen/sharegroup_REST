package org.jivesoftware.openfire.plugin.ibaby.module;

import org.dom4j.Element;
import org.xmpp.packet.Packet;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-9
 * Time: 下午2:40
 * To change this template use File | Settings | File Templates.
 */
public class ReplyMessage extends Packet {


    private Integer Code;

    private Boolean Success;

    private String Msg;

    private String Created;


    /**
     * Constructs a new Message.
     */
    public ReplyMessage() {
        this.element = docFactory.createDocument().addElement("message");
    }

    /**
     * Constructs a new Message using an existing Element. This is useful
     * for parsing incoming message Elements into Message objects.
     *
     * @param element the message Element.
     */
    public ReplyMessage(Element element) {
        super(element);
    }

    /**
     * Constructs a new Message using an existing Element. This is useful
     * for parsing incoming message Elements into Message objects. Stringprep validation
     * on the TO address can be disabled. The FROM address will not be validated since the
     * server is the one that sets that value.
     *
     * @param element the message Element.
     * @param skipValidation true if stringprep should not be applied to the TO address.
     */
    public ReplyMessage(Element element, boolean skipValidation) {
        super(element, skipValidation);
    }

    /**
     * Constructs a new Message that is a copy of an existing Message.
     *
     * @param message the message packet.
     * @see #createCopy()
     */
    private ReplyMessage(ReplyMessage message) {
        Element elementCopy = message.element.createCopy();
        docFactory.createDocument().add(elementCopy);
        this.element = elementCopy;
        // Copy cached JIDs (for performance reasons)
        this.toJID = message.toJID;
        this.fromJID = message.fromJID;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    @Override
    public ReplyMessage createCopy() {
        return new ReplyMessage(this);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("{");
        str.append("\"Code\":"+this.Code);
        str.append(",\"Success\":"+this.Success);
        str.append(",\"Created\":\""+this.Created+"\"");
        str.append(",\"Msg\":\""+this.Msg+"\"");
        str.append("}");
        return str.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
