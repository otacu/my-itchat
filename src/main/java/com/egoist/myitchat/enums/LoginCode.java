package com.egoist.myitchat.enums;

public enum LoginCode {

    /**
     * 成功
     */
    SUCCESS("200","登录成功"),
    /**
     * 在手机上点击确认
     */
    AWAIT_CONFIRMATION("201","请在手机上点击确认"),
    /**
     * 二维码超时
     */
    EXPIRED("400","二维码超时，请刷新二维码"),
    /**
     * 等待扫描二维码
     */
    AWAIT_SCANNING("408", "等待扫描二维码");

    private final String code;

    private final String description;

    LoginCode(String code,String description) {
        this.code = code;
        this.description=description;
    }

    public String getCode() {
        return code;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    public static String getDescriptionByCode(String code){
        String desc = null;
        for(LoginCode item : LoginCode.values()){
            if (item.getCode().equals(code)){
                desc = item.getDescription();
                break;
            }
        }
        return desc;
    }
}
