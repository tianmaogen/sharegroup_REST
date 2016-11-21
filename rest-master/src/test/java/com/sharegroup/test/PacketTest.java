package com.sharegroup.test;


/**
 * Created by Administrator on 2015/12/22.
 */
public class PacketTest {

   /* public static void main(String [] args){
        String message  = "<message id=\"75d673dc-4e76-4150-851a-33d157246c10\" to=\"aa9321a091e24d4c8d0659bd3b12fc6c@192.168.1.222\" from=\"cb5f71963d2e4738a6b9f503feb96d62@192.168.1.222/Android_866568027340891\" type=\"chat\">\n" +
                "  <subject>message</subject>\n" +
                "  <body>{\"ChatType\":\"3\",\"ChatMainId\":\"647e5651616341b2b98510145b9c6a4f\",\"ClientMsgId\":\"75d673dc-4e76-4150-851a-33d157246c10\",\"ObjectType\":\"1\",\"ObjectId\":\"cb5f71963d2e4738a6b9f503feb96d62\",\"ContentType\":\"10\",\"Content\":\"hhhjcjjcjdj\",\"Created\":\"2015-12-22 15:29:49\",\"NotReadCount\":\"0\"}</body>\n" +
                "  <thread>NSDB89</thread>\n" +
                "  <delay xmlns=\"urn:xmpp:delay\" from=\"192.168.1.222\" stamp=\"2015-12-22T07:29:49.425Z\"></delay>\n" +
                "  <x xmlns=\"jabber:x:delay\" from=\"192.168.1.222\" stamp=\"20151222T07:29:49\"></x>\n" +
                "  <delay xmlns=\"urn:xmpp:delay\" from=\"192.168.1.222\" stamp=\"2015-12-22T07:40:13.127Z\"></delay>\n" +
                "  <x xmlns=\"jabber:x:delay\" from=\"192.168.1.222\" stamp=\"20151222T07:40:13\"></x>\n" +
                "  <delay xmlns=\"urn:xmpp:delay\" from=\"192.168.1.222\" stamp=\"2015-12-22T07:48:37.485Z\"></delay>\n" +
                "  <x xmlns=\"jabber:x:delay\" from=\"192.168.1.222\" stamp=\"20151222T07:48:37\"></x>\n" +
                "  <delay xmlns=\"urn:xmpp:delay\" from=\"192.168.1.222\" stamp=\"2015-12-22T07:51:58.130Z\"/>\n" +
                "  <x xmlns=\"jabber:x:delay\" from=\"192.168.1.222\" stamp=\"20151222T07:51:58\"/>\n" +
                "</message>\n";


        Gson g = new Gson();
        Message packet = new Message();
       Element delayElement= packet.addChildElement("delay","urn:xmpp:delay");
        Element delayElement1= packet.addChildElement("delay","urn:xmpp:delay");
        delayElement.addAttribute("from","192.168.1.222");
        delayElement1.addAttribute("stamp","20151222T07:48:37");
        System.out.println(packet);
//        dinfo.setFrom("from");
//        DelayInfo d= new DelayInfo(dinfo);
//        PacketExtension extension = dinfo;
//        packet.addExtension(extension);

//        System.out.println(packet);

        PacketExtension extension =  packet.getExtension("delay", "urn:xmpp:delay");
//        System.out.println(g.toJson(extension));
        System.out.println(extension == null);
//        System.out.println(extension.getElement().getName());

        //PacketExtension exception =  packet.getExtension("delay", "urn:xmpp:delay");


    }
*/
}
