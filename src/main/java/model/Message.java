package model;

/**
 * @ClassName Message
 * @Description TODO
 * @Author User
 * @DATE 2019/9/24 16:54
 * @Version 1.0
 **/
public class Message {

    private int mageicNumber = 0x1000;

    private byte bigVersion = 1;

    private byte middleVersion = 0;

    private byte smallVersion = 0;

    private byte messageType = 1;

    private String body;

    public int getMageicNumber() {
        return mageicNumber;
    }

    public void setMageicNumber(int mageicNumber) {
        this.mageicNumber = mageicNumber;
    }

    public byte getBigVersion() {
        return bigVersion;
    }

    public void setBigVersion(byte bigVersion) {
        this.bigVersion = bigVersion;
    }

    public byte getMiddleVersion() {
        return middleVersion;
    }

    public void setMiddleVersion(byte middleVersion) {
        this.middleVersion = middleVersion;
    }

    public byte getSmallVersion() {
        return smallVersion;
    }

    public void setSmallVersion(byte smallVersion) {
        this.smallVersion = smallVersion;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mageicNumber=" + mageicNumber +
                ", bigVersion=" + bigVersion +
                ", middleVersion=" + middleVersion +
                ", smallVersion=" + smallVersion +
                ", messageType=" + messageType +
                ", body='" + body + '\'' +
                '}';
    }
}
