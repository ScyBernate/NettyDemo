package client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.TimeUnit;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.JsonObject;

public class ws_Client {

    public static WebSocketClient client;

    private String requestId;

    public void connect(int i) throws InterruptedException {
        try {
            //115.231.73.55   xixiqueen.club
            client = new WebSocketClient(new URI("ws://localhost:7120/"), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake arg0) {
                    System.out.println("打开链接");
                }

                @Override
                public void onMessage(String arg0) {
                    System.out.println("服务器推送消息" + arg0);
                }

                @Override
                public void onError(Exception arg0) {
                    arg0.printStackTrace();
                    System.out.println("发生错误已关闭");
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {
                    System.out.println("连接已关闭");
                }


            };
            client.connect();
            while (!client.getReadyState().equals(READYSTATE.OPEN)) {
                System.out.print("...");
                TimeUnit.SECONDS.sleep(1);
            }
            //首次连接 发送心跳包
            //heatBeat();
            System.out.println("ws第一条消息");
            //TimeUnit.MILLISECONDS.sleep(500);

            //发送消息
			/*obj.addProperty("requestId",requestId);
			obj.addProperty("serviceId", 1002);
			obj.addProperty("name", name);
			obj.addProperty("message", name + "发的测试消息");
			client.send(obj.toString());
			*/
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("连接服务器失败");
        }
    }

    //发送心跳
    public void heatBeat() {

    }

    //下线
    public void offLine() {
        try {
            TimeUnit.SECONDS.sleep(1);
            JsonObject obj = new JsonObject();
            // {"requestId":uuid, "serviceId":1001, "name":name}
            client.close();
        } catch (NotYetConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws URISyntaxException, NotYetConnectedException, UnsupportedEncodingException, IOException, InterruptedException {
        ws_Client client = new ws_Client();
        try {
            client.connect(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


       /* ws_Client client = new ws_Client();
        try {
            client.connect(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }


    public static void send(byte[] bytes) {
        client.send(bytes);
    }
}
