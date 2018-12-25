package com.egoist.myitchat.service;

import com.egoist.myitchat.enums.LoginCode;
import com.egoist.myitchat.enums.StatusNotifyCode;
import com.egoist.myitchat.exception.WechatException;
import com.egoist.myitchat.pojo.FuncResult;
import com.egoist.myitchat.pojo.request.BaseRequest;
import com.egoist.myitchat.pojo.response.*;
import com.egoist.myitchat.pojo.shared.ChatRoomDescription;
import com.egoist.myitchat.pojo.shared.Token;
import com.egoist.myitchat.utils.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WechatHttpServiceInternal wechatHttpServiceInternal;

    private int qrRefreshTimes = 0;

    /**
     * 获取二维码
     */
    public FuncResult getQRCode() {
        try {
            //0 entry
            wechatHttpServiceInternal.open(qrRefreshTimes);
            logger.info("[0] entry completed");
            //1 uuid
            String uuid = wechatHttpServiceInternal.getUUID();
            cacheService.setUuid(uuid);
            logger.info("[1] uuid completed");
            //2 qr
            FuncResult result = wechatHttpServiceInternal.getQR(uuid);
            if (!FuncResult.isOk(result)) {
                return new FuncResult(400, "获取二维码异常【2】", null);
            }
            logger.info("[2] qrcode completed");
            //3 statreport
            wechatHttpServiceInternal.statReport();
            logger.info("[3] statReport completed");
            return result;
        } catch (Exception e) {
            return new FuncResult(400, "获取二维码异常【1】", null);
        }
    }

    public FuncResult login() {
        try {
            // 如果已登录，直接返回成功
            if (cacheService.isAlive()) {
                return new FuncResult(200, "已登录，不需要登录", null);
            }
            //4 login
            LoginResult loginResponse = wechatHttpServiceInternal.login(cacheService.getUuid());
            if (!LoginCode.SUCCESS.getCode().equals(loginResponse.getCode())) {
                return new FuncResult(400, LoginCode.getDescriptionByCode(loginResponse.getCode()), null);
            }
            if (loginResponse.getHostUrl() == null) {
                return new FuncResult(400, "hostUrl can't be found", null);
            }
            if (loginResponse.getRedirectUrl() == null) {
                return new FuncResult(400, "redirectUrl can't be found", null);
            }
            cacheService.setHostUrl(loginResponse.getHostUrl());
            if (loginResponse.getHostUrl().equals("https://wechat.com")) {
                cacheService.setSyncUrl("https://webpush.web.wechat.com");
                cacheService.setFileUrl("https://file.web.wechat.com");
            } else {
                cacheService.setSyncUrl(loginResponse.getHostUrl().replaceFirst("^https://", "https://webpush."));
                cacheService.setFileUrl(loginResponse.getHostUrl().replaceFirst("^https://", "https://file."));
            }

            logger.info("[4] login completed");
            //5 redirect login
            Token token = wechatHttpServiceInternal.openNewloginpage(loginResponse.getRedirectUrl());
            if (token.getRet() == 0) {
                cacheService.setPassTicket(token.getPass_ticket());
                cacheService.setsKey(token.getSkey());
                cacheService.setSid(token.getWxsid());
                cacheService.setUin(token.getWxuin());
                BaseRequest baseRequest = new BaseRequest();
                baseRequest.setUin(cacheService.getUin());
                baseRequest.setSid(cacheService.getSid());
                baseRequest.setSkey(cacheService.getsKey());
                cacheService.setBaseRequest(baseRequest);
            } else {
                throw new WechatException("token ret = " + token.getRet());
            }
            logger.info("[5] redirect login completed");
            //6 redirect
            wechatHttpServiceInternal.redirect(cacheService.getHostUrl());
            logger.info("[6] redirect completed");
            //7 init
            InitResponse initResponse = wechatHttpServiceInternal.init(cacheService.getHostUrl(), cacheService.getBaseRequest());
            WechatUtils.checkBaseResponse(initResponse);
            cacheService.setSyncKey(initResponse.getSyncKey());
            cacheService.setOwner(initResponse.getUser());
            logger.info("[7] init completed");
            //8 status notify
            StatusNotifyResponse statusNotifyResponse =
                    wechatHttpServiceInternal.statusNotify(cacheService.getHostUrl(),
                            cacheService.getBaseRequest(),
                            cacheService.getOwner().getUserName(), StatusNotifyCode.INITED.getCode());
            WechatUtils.checkBaseResponse(statusNotifyResponse);
            logger.info("[8] status notify completed");
            //9 get contact
            long seq = 0;
            do {
                GetContactResponse getContactResponse = wechatHttpServiceInternal.getContact(cacheService.getHostUrl(), cacheService.getBaseRequest().getSkey(), seq);
                WechatUtils.checkBaseResponse(getContactResponse);
                logger.info("[*] getContactResponse seq = " + getContactResponse.getSeq());
                logger.info("[*] getContactResponse memberCount = " + getContactResponse.getMemberCount());
                seq = getContactResponse.getSeq();
                cacheService.getIndividuals().addAll(getContactResponse.getMemberList().stream().filter(WechatUtils::isIndividual).collect(Collectors.toSet()));
                cacheService.getMediaPlatforms().addAll(getContactResponse.getMemberList().stream().filter(WechatUtils::isMediaPlatform).collect(Collectors.toSet()));
            } while (seq > 0);
            logger.info("[9] get contact completed");
            //10 batch get contact
            ChatRoomDescription[] chatRoomDescriptions = initResponse.getContactList().stream()
                    .filter(x -> x != null && WechatUtils.isChatRoom(x))
                    .map(x -> {
                        ChatRoomDescription description = new ChatRoomDescription();
                        description.setUserName(x.getUserName());
                        return description;
                    })
                    .toArray(ChatRoomDescription[]::new);
            if (chatRoomDescriptions.length > 0) {
                BatchGetContactResponse batchGetContactResponse = wechatHttpServiceInternal.batchGetContact(
                        cacheService.getHostUrl(),
                        cacheService.getBaseRequest(),
                        chatRoomDescriptions);
                WechatUtils.checkBaseResponse(batchGetContactResponse);
                logger.info("[*] batchGetContactResponse count = " + batchGetContactResponse.getCount());
                cacheService.getChatRooms().addAll(batchGetContactResponse.getContactList());
            }
            logger.info("[10] batch get contact completed");
            cacheService.setAlive(true);
            cacheService.setLastNormalRetcodeTime(System.currentTimeMillis());
            logger.info("[*] login process completed");
            logger.info("开启微信状态检测线程");
            startCheckLoginStatusThread();
            return new FuncResult(200, "登录成功", null);
        } catch (Exception ex) {
            return new FuncResult(400, "登录异常", null);
        }
    }

    private void startCheckLoginStatusThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (cacheService.isAlive()) {
                    long t1 = System.currentTimeMillis(); // 秒为单位
                    if (t1 - cacheService.getLastNormalRetcodeTime() > 60 * 1000) { // 相差超过60秒，判为离线
                        cacheService.setAlive(false);
                        logger.info("微信已离线");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(10 * 1000);
                    } catch (Exception ex) {
                        logger.error("sleep error:" + ex.toString());
                    }
                }
            }
        }).start();
    }
}
