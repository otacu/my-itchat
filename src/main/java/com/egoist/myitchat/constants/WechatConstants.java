package com.egoist.myitchat.constants;

public final class WechatConstants {
    private WechatConstants() {
        
    }

    public static final String WECHAT_URL_ENTRY ="https://wx.qq.com/";
    public static final String WECHAT_URL_LOGIN_BASE ="https://login.weixin.qq.com";
    public static final String WECHAT_URL_PATH_BASE ="/cgi-bin/mmwebwx-bin";
    public static final String WECHAT_URL_UUID=WECHAT_URL_LOGIN_BASE+"/jslogin?appid=wx782c26e4c19acffb&fun=new&lang=zh_CN&_=%s";
    public static final String WECHAT_URL_QRCODE=WECHAT_URL_LOGIN_BASE+"/qrcode";
    public static final String WECHAT_URL_STATUS_NOTIFY="%s"+WECHAT_URL_PATH_BASE+"/webwxstatusnotify";
    public static final String WECHAT_URL_STATREPORT=WECHAT_URL_ENTRY+WECHAT_URL_PATH_BASE+"/webwxstatreport?fun=new";
    public static final String WECHAT_URL_LOGIN=WECHAT_URL_LOGIN_BASE+WECHAT_URL_PATH_BASE+"/login?loginicon=true&uuid=%s&tip=0&r=%s&_=%s";
    public static final String WECHAT_URL_INIT="%s"+WECHAT_URL_PATH_BASE+"/webwxinit?r=%s";
    public static final String WECHAT_URL_SYNC_CHECK="%s"+WECHAT_URL_PATH_BASE+"/synccheck";
    public static final String WECHAT_URL_SYNC="%s"+WECHAT_URL_PATH_BASE+"/webwxsync?sid=%s&skey=%s";
    public static final String WECHAT_URL_GET_CONTACT = "%s"+WECHAT_URL_PATH_BASE+"/webwxgetcontact?r=%s&seq=%s&skey=%s";
    public static final String WECHAT_URL_SEND_MSG="%s"+WECHAT_URL_PATH_BASE+"/webwxsendmsg";
    public static final String WECHAT_URL_UPLOAD_MEDIA="%s"+WECHAT_URL_PATH_BASE+"/webwxuploadmedia?f=json";
    public static final String WECHAT_URL_GET_MSG_IMG="%s"+WECHAT_URL_PATH_BASE+"/webwxgetmsgimg?&MsgID=%s&skey=%s";
    public static final String WECHAT_URL_GET_VOICE="%s"+WECHAT_URL_PATH_BASE+"/webwxgetvoice";
    public static final String WECHAT_URL_GET_VIDEO="%s"+WECHAT_URL_PATH_BASE+"/webwxgetvideo";
    public static final String WECHAT_URL_PUSH_LOGIN="%s"+WECHAT_URL_PATH_BASE+"/webwxpushloginurl";
    public static final String WECHAT_URL_LOGOUT="%s"+WECHAT_URL_PATH_BASE+"/webwxlogout?redirect=1&type=1&skey=%s";
    public static final String WECHAT_URL_BATCH_GET_CONTACT="%s"+WECHAT_URL_PATH_BASE+"/webwxbatchgetcontact?type=ex&r=%s";
    public static final String WECHAT_URL_OP_LOG="%s"+WECHAT_URL_PATH_BASE+"/webwxoplog";
    public static final String WECHAT_URL_VERIFY_USER="%s"+WECHAT_URL_PATH_BASE+"/webwxverifyuser";
    public static final String WECHAT_URL_GET_MEDIA="%s"+WECHAT_URL_PATH_BASE+"/webwxgetmedia";
    public static final String WECHAT_URL_CREATE_CHATROOM="%s"+WECHAT_URL_PATH_BASE+"/webwxcreatechatroom?r=%s";
    public static final String WECHAT_URL_DELETE_CHATROOM_MEMBER="%s"+WECHAT_URL_PATH_BASE+"/webwxupdatechatroom?fun=delmember";
    public static final String WECHAT_URL_ADD_CHATROOM_MEMBER="%s"+WECHAT_URL_PATH_BASE+"/webwxupdatechatroom?fun=addmember";
    public static final String BROWSER_DEFAULT_USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
}
