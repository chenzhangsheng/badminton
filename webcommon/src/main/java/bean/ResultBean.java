package bean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * Created by ChenZhangsheng on 2017/8/15.
 */
@XStreamAlias("result")
public class ResultBean {
    public static final Integer OK = Integer.valueOf(0);
    public static final Integer CREATEED = Integer.valueOf(0);
    public static final Integer NOT_MODIFY = Integer.valueOf(0);
    public static final Integer UNAUTHORIZED = Integer.valueOf(401);
    public static final Integer NOT_FOUND = Integer.valueOf(404);
    public static final Integer INVALID_REQUST = Integer.valueOf(422);
    public static final Integer SYS_ERROR = Integer.valueOf(500);
    public static final Integer SUCCESS = Integer.valueOf(200);
    public static final ResultBean SUCCESS_OK;
    public static final Integer INVALID_LIVE_STATUS_ERR_CODE;
    public static final Integer ACCESS_JUMP_FAILED_ERR_CODE;
    public static final Integer NO_AVALIABLE_STREAM_ERR_CODE;
    public static final Integer LIVE_CHANNEL_EXHAUSTED_ERR_CODE;
    public static final Integer INVALID_LIVE_CONTROL_ERR_CODE;
    public static final Integer NO_STAREAM_ONCEOK_ERR_CODE;
    public static final Integer ZOOKEEPER_TIMEOUT_ERR_CODE;
    public static final Integer DUPLICATE_RECODE_ERR_CODE;

    @XStreamAlias("data")
    private Object data;
    @XStreamAlias("err")
    private Integer err;
    @XStreamAlias("msg")
    private String msg;

    public ResultBean() {
    }

    public ResultBean(Object data, Integer err) {
        this.data = data;
        this.err = err;
    }

    public ResultBean(Object data, Integer err, String msg) {
        this.data = data;
        this.err = err;
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getErr() {
        return this.err;
    }

    public void setErr(Integer err) {
        this.err = err;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "ResultBean [data=" + this.data + ", err=" + this.err + "]";
    }

    static {
        SUCCESS_OK = new ResultBean((Object)null, OK, "success");
        INVALID_LIVE_STATUS_ERR_CODE = Integer.valueOf(1001);
        ACCESS_JUMP_FAILED_ERR_CODE = Integer.valueOf(1002);
        NO_AVALIABLE_STREAM_ERR_CODE = Integer.valueOf(1003);
        LIVE_CHANNEL_EXHAUSTED_ERR_CODE = Integer.valueOf(1004);
        INVALID_LIVE_CONTROL_ERR_CODE = Integer.valueOf(1005);
        NO_STAREAM_ONCEOK_ERR_CODE = Integer.valueOf(1006);
        ZOOKEEPER_TIMEOUT_ERR_CODE = Integer.valueOf(1);
        DUPLICATE_RECODE_ERR_CODE = Integer.valueOf(1);
    }
}
