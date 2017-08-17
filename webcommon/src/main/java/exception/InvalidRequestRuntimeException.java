package exception;

import bean.ResultBean;
import org.springframework.http.HttpStatus;

/**
 * Created by ChenZhangsheng on 2017/8/15.
 */
public class InvalidRequestRuntimeException extends PlatformRequestRuntimeException {
    private static final long serialVersionUID = 3568395377136183710L;

    public InvalidRequestRuntimeException(String msg) {
        super(msg, ResultBean.INVALID_REQUST.intValue(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public InvalidRequestRuntimeException(String msg, Throwable cause) {
        super(msg, ResultBean.INVALID_REQUST.intValue(), HttpStatus.UNPROCESSABLE_ENTITY, cause);
    }

    public InvalidRequestRuntimeException(String msg, String reason) {
        super(msg, reason, ResultBean.INVALID_REQUST.intValue(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public InvalidRequestRuntimeException(String msg, String reason, Throwable cause) {
        super(msg, reason, ResultBean.INVALID_REQUST.intValue(), HttpStatus.UNPROCESSABLE_ENTITY, cause);
    }

    public InvalidRequestRuntimeException(String msg, int err, HttpStatus httpStatus) {
        super(msg, err, httpStatus);
    }

    public InvalidRequestRuntimeException(String msg, int err, HttpStatus httpStatus, Throwable cause) {
        super(msg, err, httpStatus, cause);
    }

    public InvalidRequestRuntimeException(String msg, String reason, int err, HttpStatus httpStatus) {
        super(msg, reason, err, httpStatus);
    }

    public InvalidRequestRuntimeException(String msg, String reason, int err, HttpStatus httpStatus, Throwable cause) {
        super(msg, reason, err, httpStatus, cause);
    }
}
