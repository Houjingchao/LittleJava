package rpc.TcpRpc;

/**
 * Created by Hjc on 2017/6/28.
 */
public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public String sayHello(String helloArg) {
        if (helloArg.contains("hello")) {
            return "hello";
        } else {
            return "88";
        }
    }
}
