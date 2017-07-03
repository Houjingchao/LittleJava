package rpc.TcpRpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Hjc on 2017/6/28.
 */
public class Consumer {
    public static void main(String[] args) {
        //接口名称
        String interfaceName = SayHelloService.class.getName();
        try {
            Method method = SayHelloService.class.getMethod("sayHello", String.class);
            Object[] arguments = {"hello"};
            Socket socket = new Socket("127.0.0.1", 1234);
            // send
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(interfaceName);
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(arguments);
            // receive
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object object = inputStream.readObject();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
