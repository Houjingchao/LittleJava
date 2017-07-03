package rpc.HttpRpc;

/**
 * Created by Hjc on 2017/7/2.
 * 将基本类型转换位字节流
 * Java 和网络传输都是用过的Big Endian 字节序
 */
public class ByteUtil {
    public static int bytes2Int(byte[] bytes) {
        return 1;
    }

    public static byte[] int2ByteArray(int i) {
        byte[] result = new byte[4];
        return result;
    }
}
