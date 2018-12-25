package com.egoist.myitchat.pojo;

import java.util.Objects;

public class FuncResult {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 目标数据data
     */
    private Object data;

    /**
     * OK状态码：200
     */
    private static final int OK_STATUS = 200;

    /**
     * OK
     */
    private static final String OK_MSG = "OK";

    /**
     * @Fields serialVersionUID : 自动生成serialVersionUID
     */
    private static final long serialVersionUID = -1991985523438870491L;


    /**
     * 创建FuncResult实例对象
     *
     * @param data 目标数据
     * @return 返回FuncResult实例对象
     */
    public static FuncResult ok(Object data) {
        return new FuncResult(data);
    }

    /**
     * 创建FuncResult实例对象
     *
     * @return 返回FuncResult实例对象
     */
    public static FuncResult ok() {
        return new FuncResult(null);
    }

    /**
     * 创建FuncResult实例对象，无参数构造方法
     */
    public FuncResult() {

    }

    /**
     * 创建 FuncResult 实例对象
     *
     * @param status 状态码
     * @param msg    提示信息
     * @param data   数据
     */
    public FuncResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 创建 FuncResult 实例对象，状态是200
     *
     * @param data 数据
     */
    public FuncResult(Object data) {
        this.status = OK_STATUS;
        this.msg = OK_MSG;
        this.data = data;
    }

    /**
     * 获取状态码
     *
     * @return 返回状态码
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态码
     *
     * @param status 状态码
     * @return 返回FuncResult实例对象
     */
    public FuncResult setStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * 获取提示信息
     *
     * @return 返回提示信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置提示信息
     *
     * @param msg 提示信息
     * @return 返回FuncResult实例对象
     */
    public FuncResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 获取目标数据
     *
     * @return 返回目标数据
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 目标数据
     * @return 返回FuncResult实例对象
     */
    public FuncResult setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 判断FuncResult的结果是否成功
     *
     * @param result FuncResult
     * @return status==200：true or false
     */
    public static Boolean isOk(FuncResult result) {
        if (Objects.isNull(result)) {
            return Boolean.FALSE;
        }
        return result.getStatus() == OK_STATUS ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * toString
     *
     * @return 返回字符串
     */
    @Override
    public String toString() {
        return "FuncResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
