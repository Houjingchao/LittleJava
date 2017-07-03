package rpc.HttpRpc;

/**
 * Created by Hjc on 2017/7/2.
 */
public class Response {
    private byte[] encode;
    private int responseLength;
    private String response;

    public byte[] getEncode() {
        return encode;
    }

    public void setEncode(byte[] encode) {
        this.encode = encode;
    }

    public int getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(int responseLength) {
        this.responseLength = responseLength;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
