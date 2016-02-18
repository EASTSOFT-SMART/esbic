package com.eastsoft.android.esbic.util;

/**
 * Created by Mr Wang on 2016/2/5.
 */
public class MessageUtil {
    private String messageContent;
    private String date;
    private int ImageId;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    private int stateId;
}
