# java-wechat-h5

1、阅读微信参数查看及配置.docx文档进行配置

2、微信支付流程
1）获取code
2）通过code获取openid
3）调用统一下单接口
4）通过微信JSAPI中的getBrandWCPayRequest调用支付
5）支付回调修改订单状态

3、文档目录介绍
util/*				存放所有用到的工具方法
controll/*			控制器类

4、业务流程
举例：
支付页面地址： payUrl(http://www.aa.com/b/pay.action)
1）要跳转到支付页面时，如果是微信浏览器直接跳转到"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + URLEncoder.encode(payUrl) + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect"
系统会自动跳转到payUrl并且返回一个参数code(http://www.aa.com/b/pay.action?code=aaa)

后续步骤直接通过代码了解
controll/payController

代码使用spring-mvc实现
采用servlet同理

如有疑问可参考官方文档说明
微信官方文档地址
获取code文档地址：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN（搜索：[第一步：用户同意授权，获取code]）
获取openid文档：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN（搜索[第二步：通过code获取access_token]）
统一下单地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
微信内H5调起支付：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6

程序值放了关键性代码，但足够详细

最后祝各位对接顺利\(^o^)/~
















              