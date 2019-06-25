package cn.org.happyhome.nursing.bean;

public class EventBusMessage {
    String[] msg;

    public EventBusMessage(String... message) {
        this.msg = message;
    }
}
