package exception;

/**
 * Created by ChenZhangsheng on 2017/8/15.
 */
public class PlatformRuntimeException extends RuntimeException{
    private static final long serialVersionUID = 4777011887086274817L;
    private String reason;

    public PlatformRuntimeException(String msg) {
        this(msg, "");
    }

    public PlatformRuntimeException(String msg, Throwable cause) {
        this(msg, "", cause);
    }

    public PlatformRuntimeException(String msg, String reason) {
        super(msg);
        this.reason = reason;
    }

    public PlatformRuntimeException(String msg, String reason, Throwable cause) {
        super(msg, cause);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
}
