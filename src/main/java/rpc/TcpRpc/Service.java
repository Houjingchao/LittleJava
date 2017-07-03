package rpc.TcpRpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Hjc on 2017/6/30.
 */
public class Service {
    public static void main(String[] args) throws ClassNotFoundException {
        HashMap services = new HashMap();
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket socket = serverSocket.accept();
            while (true) {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String interfaceName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?>[] params = (Class<?>[]) inputStream.readObject();
                Object[] arguments = (Object[]) inputStream.readObject();

                //调用
                Class serviceInterfaceClass = Class.forName(interfaceName);//获取服务实现的对象
                //
                Object service = services.get(interfaceName);
                Method method = serviceInterfaceClass.getMethod(methodName, params);//获得调用的方法
                Object object = method.invoke(service, arguments);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(object);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
