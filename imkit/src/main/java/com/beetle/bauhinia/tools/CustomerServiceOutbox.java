package com.beetle.bauhinia.tools;
import com.beetle.bauhinia.db.CustomerServiceMessageDB;
import com.beetle.bauhinia.db.IMessage;
import com.beetle.im.IMMessage;
import com.beetle.im.IMService;



/**
 * Created by houxh on 16/1/18.
 */
public class CustomerServiceOutbox extends Outbox {

    private static CustomerServiceOutbox instance = new CustomerServiceOutbox();
    public static CustomerServiceOutbox getInstance() {
        return instance;
    }

    @Override
    protected void markMessageFailure(IMessage msg) {
        CustomerServiceMessageDB.getInstance().markMessageFailure(msg.msgLocalID, msg.receiver);
    }

    @Override
    protected void sendImageMessage(IMessage imsg, String url) {
        IMMessage msg = new IMMessage();
        msg.sender = imsg.sender;
        msg.receiver = imsg.receiver;
        msg.content = IMessage.newImage(url).getRaw();
        msg.msgLocalID = imsg.msgLocalID;

        IMService im = IMService.getInstance();
        im.sendCustomerServiceMessage(msg);
    }

    @Override
    protected void sendAudioMessage(IMessage imsg, String url) {
        IMessage.Audio audio = (IMessage.Audio)imsg.content;

        IMMessage msg = new IMMessage();
        msg.sender = imsg.sender;
        msg.receiver = imsg.receiver;
        msg.msgLocalID = imsg.msgLocalID;
        msg.content = IMessage.newAudio(url, audio.duration).getRaw();

        IMService im = IMService.getInstance();
        im.sendCustomerServiceMessage(msg);
    }

}
