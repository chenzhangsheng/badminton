package exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ChenZhangsheng on 2017/8/15.
 */
public class PlatformRequestRuntimeException extends PlatformRuntimeException {
    private static final long serialVersionUID = 4777011887086274817L;
    private final String reason;
    private final int err;
    private final HttpStatus httpStatus;

    public PlatformRequestRuntimeException(String msg, int err, HttpStatus httpStatus) {
        this(msg, "", err, httpStatus);
    }

    public PlatformRequestRuntimeException(String msg, int err, HttpStatus httpStatus, Throwable cause) {
        this(msg, "", err, httpStatus, cause);
    }

    public PlatformRequestRuntimeException(String msg, String reason, int err, HttpStatus httpStatus) {
        super(msg);
        this.reason = reason;
        this.err = err;
        this.httpStatus = httpStatus;
    }

    public PlatformRequestRuntimeException(String msg, String reason, int err, HttpStatus httpStatus, Throwable cause) {
        super(msg, cause);
        this.reason = reason;
        this.err = err;
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return this.reason;
    }

    public int getErr() {
        return this.err;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
