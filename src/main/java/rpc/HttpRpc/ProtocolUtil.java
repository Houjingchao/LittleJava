package rpc.HttpRpc;


import com.sun.xml.internal.fastinfoset.Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hjc on 2017/7/2.
 */
public class ProtocolUtil {
    public static Request readRequest(InputStream input) throws IOException {

        //读取编码
        byte[] encodeByte = new byte[1];
        input.read(encodeByte);

        //读取命令长度
        byte[] commandLength = new byte[4];
        input.read(commandLength);
        int commandLen = 0;//需要更新
        //读取命令
        byte[] commandBytes = new byte[commandLen];
        input.read(commandBytes);
        String command = "";
        if (Encoder.UTF_8.getBytes() == encodeByte) {
            command = new String(commandBytes, "UTF-8");
        } else {
            command = new String(commandBytes, "GBK");
        }
        Request request = new Request();
        request.setCommand(command);
        request.setEncode(encodeByte);
        request.setCommandLength(commandLen);
        return request;
    }

    // response -->outputStream
    public static void writeResponse(OutputStream outputStream, Response response) {
        try {
            outputStream.write(response.getEncode());
            //直接write一个int类型会截取低8位，丢弃高24位
            if (Encoder.UTF_8.getBytes() == response.getEncode()) {
                outputStream.write(response.getResponse().getBytes("UTF-8"));
            } else {
                outputStream.write(response.getResponse().getBytes("GBK"));
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
