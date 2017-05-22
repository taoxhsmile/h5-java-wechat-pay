
@Controller
@RequestMapping(value = "/pay")
public class payController extends BaseController {

    @RequestMapping("/submitPay.shtml")
    //code 微信返回的code
    //out_trade_no 此处指订单ID
    //total_fee 订单需要支付的金额
    //appid、secret 参考文档
    //notify_url 支付成功之后 微信会进行异步回调的地址
    public String submitPayWx( ModelMap resultMap, String code, String out_trade_no) {
        String nonce_str = PayUtil.getRandomStringByLength(16);//生成随机数，可直接用系统提供的方法
        String spbill_create_ip = Util.getIpAdd();//用户端ip,这里随意输入的
        String trade_type = "JSAPI";
        String openid = "";
        String prepay_id = "";
			/*
			 * 1、静默登录
			 * 2、获取openid
			 * 3、调用统一下单接口
			 * */
        HttpRequest http = new HttpRequest();
        //静默登录获取openid
        String openIdInfo = http.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", "appid=" + appid + "&secret=" + secret +"&code=" + code + "&grant_type=authorization_code");
        //openIdInfo json字符串
        JSONObject json = JSONObject.parseObject(openIdInfo);
        openid = json.getString("openid");
        HashMap<String, Object> map = new HashMap();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("device_info", "WEB");
        map.put("nonce_str", nonce_str);
        map.put("body", "购买金币");//订单标题
        map.put("out_trade_no", out_trade_no);//订单ID
        map.put("total_fee", total_fee);//订单需要支付的金额
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("trade_type", trade_type);
        map.put("notify_url", notify_url);//notify_url 支付成功之后 微信会进行异步回调的地址
        map.put("openid", openid);
        String sign = PayUtil.getSign(map);//参数加密  该方法key的需要根据你当前公众号的key进行修改
        map.put("sign", sign);
        //String content = XMLParser.getXMLFromMap(map);
        http = new HttpRequest();
        //调用统一下单接口
        String PostResult = http.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", map);
        Map<String, Object> cbMap = XMLParser.getMapFromXML(PostResult);
        if (cbMap.get("return_code").equals("SUCCESS") && cbMap.get("result_code").equals("SUCCESS")) {
            prepay_id = cbMap.get("prepay_id") + "";//这就是预支付id
            String timeStamp = String.valueOf(DateUtil.getNow().getTime() / 1000);
            map = new HashMap<String, Object>();
            map.put("appId", appid);
            map.put("nonceStr", nonce_str);
            map.put("package", "prepay_id=" + prepay_id);
            map.put("signType", "MD5");
            map.put("timeStamp", timeStamp);
            sign = PayUtil.getSign(map);//参数加密

            String jsStr = "";
            jsStr += "function onBridgeReady(){"
                    + "WeixinJSBridge.invoke("
                    + "'getBrandWCPayRequest', {"
                    +       "\"appId\" : \"" + appid + "\",   "    //公众号名称，由商户传入
                    +       "\"timeStamp\":\"" + timeStamp + "\",  "        //时间戳，自1970年以来的秒数
                    +       "\"nonceStr\" : \"" + nonce_str + "\"," //随机串
                    +       "\"package\" : \"prepay_id=" + prepay_id + "\",      "
                    +       "\"signType\" : \"MD5\",       "  //微信签名方式:
                    +       "\"paySign\" : \"" + sign + "\" " //微信签名
                    +    "},"
                    +    "function(res){"
                    +    "  if(res.err_msg == \"get_brand_wcpay_request:ok\" ) {"

                    + "}   "   // 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                    +    "window.location.href='" + returnUrl + "';"
                    +    "}"
                    + ");"
                    + "}"
                    + "function paySubmit(){"
                    + "if (typeof WeixinJSBridge == \"undefined\"){"
                    +   "if( document.addEventListener ){"
                    +   "       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);"
                    +   "   }else if (document.attachEvent){"
                    +   "       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); "
                    +   "       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);"
                    +   "   }"
                    +   "}else{"
                    +   "   onBridgeReady();"
                    +   "}"
                    + "}";
            resultMap.put("jsStr", jsStr);
            //最后将js直接返回给前台 进行调用 给按钮增加paySubmit方法
        }
        return "submitPayWx";
    }


}