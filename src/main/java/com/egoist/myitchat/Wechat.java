package com.egoist.myitchat;

import com.egoist.myitchat.pojo.FuncResult;
import com.egoist.myitchat.service.LoginService;
import com.egoist.myitchat.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Wechat {
    @Autowired
    private LoginService loginService;
    @Autowired
    private SyncService syncService;

    private static final Logger logger = LoggerFactory.getLogger(Wechat.class);

    public FuncResult getQRCode() {
        System.setProperty("jsse.enableSNIExtension", "false");
        return loginService.getQRCode();
    }

    public FuncResult login() {
        return loginService.login();
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("start listen");
                syncService.listen();
            }
        }).start();
    }

}