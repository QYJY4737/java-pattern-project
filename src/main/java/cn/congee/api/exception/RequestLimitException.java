package cn.congee.api.exception;

/**
 * @Author: yang
 * @Date: 2020-12-14 12:06
 */
public class RequestLimitException extends BaseException{

    public RequestLimitException() {
    }

    public RequestLimitException(Integer code, String message) {
        super(code, message);
    }

    public RequestLimitException(IExceptionMsg exception) {
        super(exception);
    }

    public RequestLimitException(Exception e) {
        super(e);
    }

    public RequestLimitException(String errMessage) {
        super(errMessage);
    }
}
