//package com.gop.recharge.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.gop.recharge.service.RechargeOrderService;
//import com.gop.recharge.service.impl.SzfChargeService;
//import com.gop.user.facade.UserFacade;
//import com.gop.util.DES;
//import com.gop.web.base.annotation.AuthForHeader;
//import com.gop.web.base.auth.AuthContext;
//import com.gop.web.base.auth.annotation.Strategy;
//import com.gop.web.base.auth.annotation.Strategys;
//
//@RestController
//@RequestMapping("/recharge")
//@Slf4j
//public class RechargeController {
//
//    @Autowired
//    private SzfChargeService szfChargeService;
//
//    @Autowired
//    private RechargeOrderService rechargeOrderService;
//
//    @Autowired
//    private UserFacade userFacade;
//
//    @Value("${szfcharge.privateKey}")
//    private String privateKey;
//
//    @Value("${szfcharge.merId}")
//    private String targetMerId;
//
//    @Value("${szfcharge.desKey}")
//    private String desKey;
//
//    @Value("${szfcharge.postUrl}")
//    private String postUrl;
//
//    @Value("${szfcharge.returnUrl}")
//    private String returnUrl;
//
//    // usc 准备金账户
//    @Value("${uscwallet.userId}")
//    private String uscwalletUserId;
//
//    private String defaultAsset = "USC";
//    private String defaultPayChannel = "SZF";
//
//    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
//    @RequestMapping(value = "/doRecharge", method = { RequestMethod.GET, RequestMethod.POST })
//    @ResponseBody
//    public JSONObject doRecharge(@AuthForHeader AuthContext context,
//            @RequestParam(value = "cardTypeCombine") Integer cardTypeCombine,
//            @RequestParam(value = "payMoney") Long payMoney, @RequestParam(value = "cardNum") String cardNum,
//            @RequestParam(value = "cardPwd") String cardPwd) throws Exception {
//        JSONObject jsonResult = new JSONObject();
//        Integer uid = context.getLoginSession().getUserId();
//        String cardInfo = payMoney + "@" + cardNum + "@" + cardPwd;
//        payMoney = payMoney * 100;
//        String code = null;
//        code = this.rechargeOrderService.doRecharge(Integer.valueOf(this.uscwalletUserId), uid, payMoney,
//            this.defaultAsset, this.defaultPayChannel, cardTypeCombine, this.targetMerId, this.privateKey, this.desKey,
//            this.postUrl, cardNum, DES.encode(cardInfo, this.desKey),this.returnUrl);
//        jsonResult.put("code", code);
//        return jsonResult;
//    }
//
//    @RequestMapping(value = "/doSzfChargeCardCallback", method = { RequestMethod.GET, RequestMethod.POST })
//    @ResponseBody
//    public String doSzfCallback(HttpServletRequest request, @RequestParam(value = "payMoney") long payMoney,
//            @RequestParam(value = "merId") String merId, @RequestParam(value = "orderId") String orderId,
//            @RequestParam(value = "payResult") int payResult,
//            @RequestParam(value = "privateField") String privateField,
//            @RequestParam(value = "md5String") String md5String, @RequestParam(value = "errcode") String errcode)
//                    throws Exception {
//        String cardMoney = request.getParameter("cardMoney");
//        long cardMoneyL = cardMoney != null ? Long.valueOf(cardMoney) : 0;
//        if (!this.targetMerId.equals(merId)) {
//            return "404";
//        }
//        this.rechargeOrderService.doCallback(Integer.valueOf(this.uscwalletUserId), merId, payMoney, cardMoneyL,
//            orderId, payResult, privateField, this.privateKey, md5String);
//
//        return "200";
//    }
//
//}
