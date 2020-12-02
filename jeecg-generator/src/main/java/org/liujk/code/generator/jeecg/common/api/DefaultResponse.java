package org.liujk.code.generator.jeecg.common.api;

import lombok.Data;

@Data
public class DefaultResponse<T> extends BaseResponse {

    private T data;

    /**
     * 构造一个成功的返回对象
     */
    public DefaultResponse () {
        super ();
    }

    /**
     * 构造一个成功的返回对象
     *
     * @param data
     */
    public DefaultResponse (T data) {
        super ();
        setData (data);
    }

    public DefaultResponse (ResultCodeable resultCode) {
        super (resultCode);
    }

    public DefaultResponse (ResultCodeable resultCode, T data) {
        super (resultCode);
        setData (data);
    }

    public DefaultResponse (String resultCode, String resultMessage) {
        super (resultCode, resultMessage);
    }

    public DefaultResponse (String resultCode, String resultMessage, T data) {
        super (resultCode, resultMessage);
        setData (data);
    }

    /**
     * 建议使用构造函数
     *
     * @param data
     * @param <T>
     *
     * @return
     * @see DefaultResponse#DefaultResponse(Object)
     */
    public static <T> DefaultResponse success (T data) {
        return new DefaultResponse<T> (data);
    }

    public static DefaultResponse success () {
        return new DefaultResponse ();
    }

    public static DefaultResponse newResponse (String code, String msg) {
        return new DefaultResponse (code, msg);
    }

    public static DefaultResponse newResponse (ResultCodeable resultCode) {
        return new DefaultResponse (resultCode);
    }

    @Override
    public DefaultResponse<T> setToSuccess () {
        super.setToSuccess ();
        return this;
    }

    @Override
    public DefaultResponse<T> setStatus (ResultCodeable resultCode) {
        super.setStatus (resultCode);
        return this;
    }

    @Override
    public DefaultResponse<T> setStatus (String resultCode, String resultMessage) {
        super.setStatus (resultCode, resultMessage);
        return this;
    }

    @Override
    public <RESPONSE extends BaseResponse> DefaultResponse<T> copyResult (RESPONSE response) {
        super.copyResult (response);
        return this;
    }

    public T getData () {
        return data;
    }

    public DefaultResponse<T> setData (T data) {
        this.data = data;
        return this;
    }
}
