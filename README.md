# my-itchat
微信网页版封装

使用时只需要实现MessageHandler接口和注入Wechat

启动是Wechat.start()，只是启动了消息监听。

由于想把二维码放在页面，随时登陆，所以不是在启动时扫码登陆。获取二维码是Wechat.getQRCode()，登录是Wechat.login()。
