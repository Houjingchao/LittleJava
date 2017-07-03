package rpc.HttpRpc;

import com.sun.xml.internal.fastinfoset.Encoder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Hjc on 2017/7/2.
 */
public class Client {
    public static void main(String[] args) {
        Request request = new Request();
        request.setCommand("HELLO");
        request.setCommandLength(request.getCommand().length());
        request.setEncode(Encoder.UTF_8.getBytes());

        try {
            Socket client = new Socket("127.0.0.1", 4567);
            OutputStream outputStream = client.getOutputStream();
            //发送请求  outputStream-->>request
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
