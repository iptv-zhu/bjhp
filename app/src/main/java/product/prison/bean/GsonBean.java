package product.prison.bean;

/**
 * Created by zhu on 2017/9/18.
 */

public class GsonBean<T> {
    private String code;

    private T data;

    private String msg;

    private String errorInfo;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

}
