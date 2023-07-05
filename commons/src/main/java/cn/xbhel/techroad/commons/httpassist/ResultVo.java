package cn.xbhel.techroad.commons.httpassist;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xbhel
 * @param <T> the data type
 */
@Data
@Accessors(chain = true)
public class ResultVo<T> {

    private static final int SUCCESS = 200;
    private static final int BAD_REQUEST = 400;
    private static final int SERVER_ERROR = 500;
    private static final String SUCCESS_MESSAGE = "operation success";

    /** 状态码 */
    private int code;
    /** 提示消息 */
    private String message;
    /** 数据 */
    private T data;

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<T>()
                .setCode(SUCCESS)
                .setMessage(SUCCESS_MESSAGE)
                .setData(data);
    }

    public static <T> ResultVo<T> badRequest(String message) {
        return new ResultVo<T>()
                .setCode(BAD_REQUEST)
                .setMessage(message);
    }

    public static <T> ResultVo<T> failure(String message) {
        return new ResultVo<T>()
                .setCode(SERVER_ERROR)
                .setMessage(message);
    }
}
