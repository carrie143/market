package com.gop;

import com.google.common.base.Optional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.ApiConstants;
import com.gop.api.cloud.ApiRequest;
import com.gop.api.cloud.ApiResponse;
import com.gop.api.cloud.client.ApiCallProcessor;
import com.gop.api.cloud.client.HttpConstants;
import com.gop.api.cloud.client.HttpLayerException;
import com.gop.api.cloud.common.ApiErrorCode;
import com.gop.api.cloud.config.ApiConfig;
import com.gop.api.cloud.enums.SettleType;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.request.BrokerAssetRequest;
import com.gop.api.cloud.request.BrokerSymbolFee;
import com.gop.api.cloud.request.BrokerWithdrawRequest;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.request.CreateUserReq;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.request.KlineQueryReq;
import com.gop.api.cloud.request.MatchOrderReq;
import com.gop.api.cloud.request.ModifyWhitelistReq;
import com.gop.api.cloud.request.SettleQueryRequest;
import com.gop.api.cloud.request.SymbolFeeAddReq;
import com.gop.api.cloud.request.SymbolQueryReq;
import com.gop.api.cloud.request.SymbolScaleReq;
import com.gop.api.cloud.request.TradeOrderQueryReq;
import com.gop.api.cloud.request.UnVerifiedCountRequest;
import com.gop.api.cloud.request.WithdrawConfirmRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.BrokerAssetDto;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import com.gop.api.cloud.response.DepositDetailDto;
import com.gop.api.cloud.response.KlineQueryPages;
import com.gop.api.cloud.response.SettleRecordDto;
import com.gop.api.cloud.response.SymbolData;
import com.gop.api.cloud.response.SymbolScale;
import com.gop.api.cloud.response.TradeOrderDto;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.response.TransferInAddressDto;
import com.gop.api.cloud.response.UserData;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.MatchCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.service.BrokerAssetOperDetailService;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.coin.transfer.service.WithdrawCoinAddressService;
import com.gop.common.Confirm;
import com.gop.common.ExportExcelService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.ConfigEmail;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.EmailLog;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.domain.enums.ConfigEmailStatus;
import com.gop.domain.enums.ConfigSymbolType;
import com.gop.domain.enums.CurrencyType;
import com.gop.domain.enums.SymbolStatus;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.exception.AppException;
import com.gop.mapper.dto.WithdrawAddress;
import com.gop.match.service.ConfigSymbolService;
import com.gop.match.service.SymbolService;
import com.gop.mode.vo.PageModel;
import com.gop.settlement.controller.dto.WithdrawAmountLimitDto;
import com.gop.sms.dto.EmailDto;
import com.gop.sms.service.ConfigEmailService;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserService;
import com.gop.util.IDGenerateUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import io.netty.util.internal.MathUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wuyanjie on 2018/4/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GuorenManagerWebSpringbootApplication.class})
@Slf4j
public class CloudApiTest {
  @Autowired
  private ConfigSymbolService configSymbolService;
  @Autowired
  private ConfigAssetService configAssetService;

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private UserService userService;

  @Autowired
  private SymbolService symbolService;
  @Autowired
  private BrokerAssetOperDetailService brokerAssetOperDetailService;


  @Autowired
  private ExportExcelService exportExcelService;

  @Autowired
  private CloudApiService cloudApiService;
  @Autowired
  private ConfigAssetProfileService configAssetProfileService;
  @Autowired
  private WithdrawCoinAddressService withdrawCoinAddressService;
  @Test
  public void getWithdrawLimit(){
    String assetCode = "USDT";
    BigDecimal cloudFee =BigDecimal.ZERO;
    if (assetCode != null){
      ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
      AssetRequest request = new AssetRequest();
      request.setAssetCode(assetCode);
      request.setUid(Long.valueOf("0"));
      List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
      cloudFee = list.size()>0?list.stream().findAny().get().getWithdrawFee():BigDecimal.ZERO;

      if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN) || list.size() == 0) {
        throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
      }
    }
    // 可提现数量
    AssetRequest assetRequest = new AssetRequest();
    assetRequest.setAssetCode(assetCode);
    assetRequest.setUid(Long.valueOf("0")); // 券商传0
    BigDecimal balance = cloudApiService.queryBalance(assetRequest);

//        String withdrawMin = configAssetProfileService.getStringValue(assetCode, ConfigAssetType.WITHDRAWMIN);

    List<WithdrawAddress> addresses =  withdrawCoinAddressService.getWithdrawAddressBroker(assetCode);
    WithdrawAmountLimitDto dto = new WithdrawAmountLimitDto();
    dto.setAddress(addresses.size()<=0?null:addresses.get(0).getAddress());
    dto.setAssetCode(assetCode);
    dto.setBalance(balance);
    dto.setCloudFee(cloudFee.toString());
    System.out.println(dto);
  }
  @Test
  public void getUserTradeOrder() throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    TradeOrderQueryReq request = new TradeOrderQueryReq();
    request.setBeginTime(null);
    request.setEndTime(null);
    request.setPriceAsset("ETH");
    request.setTradeAsset("ACT");
    request.setUid(null);
    request.setStatus(null);
    request.setOrderType(null);
    request.setTradeFlag(null);
    request.setPageNo(1);
    request.setPageSize(10);
    PageInfo<TradeOrderDto> pageInfo = cloudApiService.queryUserOrderRecord(request);
    System.out.println(JSONArray.toJSONString(pageInfo.getList()));
  }
  @Test
  public void getSettleRecord() throws ParseException {
    SettleQueryRequest request = new SettleQueryRequest();
    request.setAssetCode(null);
    request.setType(SettleType.BROKER_FEE);
    request.setPageNo(1);
    request.setPageSize(10);
    PageInfo<SettleRecordDto> pageInfo =  cloudApiService.getSettleRecord(request);
    PageModel<SettleRecordDto> pageModel = new PageModel<>();
    pageModel.setPageNo(pageInfo.getPageNum());
    pageModel.setPageSize(pageInfo.getSize());
    pageModel.setPageNum(pageInfo.getPages());
    pageModel.setTotal(pageInfo.getTotal());
    pageModel.setList(pageInfo.getList());
    System.out.println(JSONArray.toJSONString(pageModel.getList()));
  }
  @Test
  public void getUserAccountAssets() throws ParseException {
    List<String> assetList = configAssetService.getAvailableAssetCode().stream().map(a -> a.getAssetCode())
                                               .collect(Collectors.toList());
    BrokerAssetAccountRequest request = new BrokerAssetAccountRequest();
    request.setAssetList(assetList);
    System.out.println(JSONArray.toJSONString(cloudApiService.getUserAccountAssets(request)));
  }
  @Test
  public void getConfigAssetList() throws ParseException {
    AssetRequest request = new AssetRequest();
    request.setAssetCode("KNC");
    List<BrokerConfigAssetDto> cloudAssetList = cloudApiService.getConfigAssetList(request);
    if(cloudAssetList == null || cloudAssetList.size()<=0){
      throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
    }
    Map<String,String> map = new HashMap<>();
    cloudAssetList.forEach(asset -> map.put(asset.getAssetCode(), String.valueOf(asset.getWithdrawFee())));

    List<ConfigAssetProfile> localList = configAssetProfileService.selectAll();
    List<String> assetList = new ArrayList<>();
    localList.stream().filter(asset ->!assetList.contains(asset.getAssetCode())).forEach(asset ->assetList.add(asset.getAssetCode()));

    for (int i=0;i<assetList.size();i++){
      if (map.containsKey(assetList.get(i))){
        ConfigAssetProfile configAssetProfile = new ConfigAssetProfile();
        configAssetProfile.setAssetCode(assetList.get(i));
        configAssetProfile.setProfileKey(ConfigAssetType.CLOUDFEE);
        configAssetProfile.setProfileValue(map.get(assetList.get(i)));
        localList.add(configAssetProfile);
      }
    }
    System.out.println(JSONArray.toJSONString(localList));
  }
  @Test
  public void withdrawCoinDetail_manager() throws ParseException {
    WithdrawQueryRequest request = new WithdrawQueryRequest();
    request.setPageNo(1);
    request.setPageSize(10);
    request.setStatus(null);
    request.setAssetCode("ACT");
    request.setAddress(null);
    request.setEndDate(null);
    request.setStartDate(null);
    request.setUid(Long.valueOf("10001"));
    PageInfo<WithdrawCoinDetailDto> pageInfo = cloudApiService.withdrawCoinDetail(request);
    System.out.println(JSONArray.toJSONString(pageInfo.getList()));
  }
  @Test
  public void queryBalance() throws ParseException {
    // 可提现数量
    AssetRequest request = new AssetRequest();
    request.setAssetCode("BTC");
    List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
    list.stream().filter(asset -> asset.getAssetCode().equals("BTC")).forEach(asset -> {
      AssetRequest a = new AssetRequest();
      a.setAssetCode(asset.getAssetCode());
      a.setUid(Long.valueOf("0")); // 券商传0
      System.out.println(a.getAssetCode()+" = "+cloudApiService.queryBalance(a));
    });
  }
  @Test
  public void withdrawConfirm() throws ParseException {
    WithdrawConfirmRequest request = new WithdrawConfirmRequest();
    request.setConfirm(Confirm.REFUSE);
    request.setRefuseMs("tasetdddda");
    cloudApiService.withdrawConfirm(request);

    String a = "{\"address\":\"btcaddress\",\"amount\":1,\"assetCode\":\"BTC\",\"message\":\"券商转出记录\"}123";

    System.out.println(DigestUtils.sha256Hex(a).toUpperCase());
  }
  @Test
  public void queryBrokerFinance() throws ParseException {
    BrokerAssetRequest brokerAssetRequest = new BrokerAssetRequest();
    brokerAssetRequest.setNanoTime(System.nanoTime());
    List<BrokerAssetDto> brokerAssetList = cloudApiService.queryBrokerFinance(brokerAssetRequest).getList();
    System.out.println(JSONArray.toJSONString(brokerAssetList));
  }
  @Test
  public void depositeCoinDetail_manager() throws ParseException {
    DepositQueryRequest request = new DepositQueryRequest();
    request.setUid(Long.valueOf("0"));
    request.setStartDate("2018-07-11 11:11:11");
    request.setEndDate(null);
    request.setAddress(null);
    request.setAssetCode("BTC");
    request.setPageNo(1);
    request.setPageSize(10);
    request.setStatus(null);

    PageInfo<DepositDetailDto> pageInfo = cloudApiService.depositeCoinDetail(request);
    System.out.println(JSONArray.toJSONString(pageInfo));
  }
  @Test
  public void getSymbols() throws ParseException {
    SymbolQueryReq request = new SymbolQueryReq();
    request.setNanoTime(System.nanoTime());
    String[] coins = new String[2];
    coins[0] = "AAA";
    coins[1] = "BTC";
    request.setCoin(coins);
    List<SymbolData> symbolList = cloudApiService.getSymbols(request);
    System.out.println(JSONArray.toJSONString(symbolList));
  }

  @Test
  public void getSymbolScale(){
    List<ConfigSymbol> configSymbols = symbolService.getConfigSymbols();
    Map map = new HashMap<String,String>();
    configSymbols.forEach(v -> {
                            map.put("priceAsset",v.getPriceAsset());
                            map.put("tradeAsset",v.getTradeAsset());}
                         );
    List<Map<String,String>> list = new ArrayList<>();
//    list.add(map);
    SymbolScaleReq request = new SymbolScaleReq(System.nanoTime(),list);
    List<SymbolScale> symbolList = cloudApiService.getSymbolScale(request);
    System.out.println(JSONArray.toJSONString(symbolList));
  }
  @Test
  public void countWithdrawByStatus() throws ParseException {
    UnVerifiedCountRequest request = new UnVerifiedCountRequest();
    request.setStatus(WithdrawCoinOrderStatus.WAIT);
    request.setAssetCode("ACT");
    request.setUid(Long.valueOf("10001"));
    Map<String,Integer> waitCounts = cloudApiService.countWithdrawByStatus(request);
    System.out.println(waitCounts);
  }
  public static void main(String[] args) {
    UUID uid = UUID.randomUUID();
    System.out.println(uid.toString().replace("-", ""));
    UUID U2 = UUID.randomUUID();
    System.out.println(U2.toString().replace("-", ""));
  }


  @Test
  public void createUserTest(){
    CreateUserReq req = new CreateUserReq();
    req.setNanoTime(System.nanoTime());
    req.setBrokerUid(1234554l);
    req.setPhone("18211034025");
    req.setEmail("zhangjingang@new4g.com");
    req.setName("zhang");
    req.setGender("MALE");
    UserData userData = cloudApiService.activateNewUser(req);
    System.out.println(userData.getUid());

  }
  @Test
  public void brokerWithdraw(){
    BrokerWithdrawRequest request = new BrokerWithdrawRequest();
    request.setAssetCode("USDT");
    request.setAddress("1KNPxwHyaUun8ECkMyrmEDrMA8GizYAWX6");
    request.setAmount(BigDecimal.valueOf(0.4));
    request.setClientOrderNo(IDGenerateUtil.generateClientOrderNo());
    cloudApiService.brokerWithdraw(request);
  }
  @Test
  public void addWhiteList(){
    ModifyWhitelistReq request = new ModifyWhitelistReq();
    request.setBrokerUid(Long.valueOf("100001"));
    request.setNanoTime(System.nanoTime());
    request.setWhiteList(true);
    cloudApiService.updateWhiteList(request);
  }
  @Test
  public void getBrokerAssetList(){
    AssetRequest request = new AssetRequest();
    List<BrokerConfigAssetDto> configAssetList = cloudApiService.getConfigAssetList(request);
    System.out.println(configAssetList.size());

  }
  @Test
  public void withdrawCoinDetail(){
    WithdrawQueryRequest request =  new WithdrawQueryRequest();
    request.setClientOrderNo("3408581340715199");
    PageInfo<WithdrawCoinDetailDto> withdrawCoinDetailDtoPageInfo = cloudApiService
        .withdrawCoinDetail(request);
    System.out.println(withdrawCoinDetailDtoPageInfo.getList().size());

  }

  @Test
  public void depositeCoinDetail(){
    DepositQueryRequest request =  new DepositQueryRequest();
    request.setUid(1234554l);
    PageInfo<DepositDetailDto> depositDetailDtoPageInfo = cloudApiService
        .depositeCoinDetail(request);
    System.out.println(depositDetailDtoPageInfo.getList().size());

  }

  @Test
  public void apiErrorCode() throws AppException, HttpLayerException {
    BrokerWithdrawRequest request = new BrokerWithdrawRequest();
    request.setAssetCode("USDT");
    request.setAddress("1KNPxwHyaUun8ECkMyrmEDrMA8GizYAWX6");
    request.setAmount(BigDecimal.valueOf(0.4));
    request.setClientOrderNo(IDGenerateUtil.generateClientOrderNo());
    String url = "http://192.168.168.126:8080/api/v1/" + ApiConstants.WITHDRAW_BROKER;
    ApiConfig apiConfigLocal = new ApiConfig();
    apiConfigLocal.setAccesskey("100001");
    apiConfigLocal.setSecretkey("0000000000000000");
    apiConfigLocal.setUrl(url);
    ApiCallProcessor<Void> apiCallProcessor = new ApiCallProcessor<>(apiConfigLocal,
                                                                     new TypeReference<ApiResponse<Void>>() {
                                                                     });

    apiCallProcessor.call(new ApiRequest(request), HttpConstants.REQ_METHOD_POST, null);

  }

  /**地址生成这部分需要调试完成后重新发布一版*/
  @Test
  public void testInAddressQuery(){
    AssetRequest request = new AssetRequest();
    request.setUid(1l);
    request.setAssetCode("BTC");
    List<TransferInAddressDto> transferInAddressDtos = cloudApiService
        .transferInAddressQuery(request);
    System.out.println(transferInAddressDtos.size());
  }

  @Test
  public void testMatchOrder(){
    String address;
    List<WithdrawAddress> withdrawAddressList = withdrawCoinAddressService.getWithdrawAddressBroker("ACT");
    if (withdrawAddressList ==null || withdrawAddressList.size()<=0){
      address = StringUtils.EMPTY;
    }else {
      address = withdrawAddressList.stream().findAny().get().getAddress();
    }
    System.out.println(address);
  }

  @Test
  public void testKlineQuery(){
    /**{"endTime":1530957264,"kline":"15m","nanoTime":448955092265075,"pageNum":0,"pageSize":300,"startTime":1530952764,"symbol":"BTC_ACT"}
     */
    KlineQueryReq req = new KlineQueryReq();
    req.setPageNum(1);
    req.setPageSize(300);
    req.setSymbol("BTC_ACT");
    req.setStartTime(1530952764);
    req.setEndTime(1530957264);
    req.setNanoTime(448955092265075l);
    req.setKline("15m");
    KlineQueryPages pages = cloudApiService.klineQueryPages(req);
    System.out.println(JSON.toJSONString(pages));
  }
  @Autowired
  private ConfigEmailService configEmailService;
  @Value("${spring.mail.username}")
  private String user;
  @Value("${spring.mail.password}")
  private String passWord;
  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private String port;
  @Value("${spring.mail.properties.mail.smtp.auth}")
  private boolean auth;
  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private boolean starttlsEnable;
  @Value("${spring.mail.properties.mail.smtp.starttls.required}")
  private boolean starttlsRequired;
  @Value("${spring.mail.properties.mail.smtp.timeout}")
  private Integer timeOut;
  @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
  private String sslFactory;
  @Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
  private boolean fallback;
  @Value("${mail.personal}")
  private String personal;
  @Test
  public void sendMailTest(){
    List<String> to = new ArrayList<>();
    to.add("fuyao@new4g.cn");
    EmailDto dto = new EmailDto();
    dto.setToUser(to);
    dto.setSubject("aaaa");
    dto.setText("change to english. baidu alibaba");
    sendSimpleMail(dto);
  }

  // TODO: 2018/7/18   下一版本优化
  private boolean sendSimpleMail(EmailDto emailDto) {
    if (emailDto ==null || emailDto.getToUser()==null){
      log.error("邮箱地址:{}发送给邮件异常|",emailDto);
      return false;
    }
    String fromUser = Optional.fromNullable(emailDto.getFromUser()).or(user);
    String[] toUsers = emailDto.getToUser().toArray(new String[emailDto.getToUser().size()]);

    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.auth", Optional.fromNullable(auth).or(true));
    javaMailProperties.put("mail.smtp.starttls.enable", Optional.fromNullable(starttlsEnable).or(true));
    javaMailProperties.put("mail.smtp.starttls.required", Optional.fromNullable(starttlsRequired).or(true));
    javaMailProperties.put("mail.smtp.timeout", Optional.fromNullable(timeOut).or(15000));
    javaMailProperties.put("mail.smtp.port", Optional.fromNullable(port).or("465"));
    javaMailProperties.put("mail.smtp.socketFactory.class", Optional.fromNullable(sslFactory).or("javax.net.ssl.SSLSocketFactory"));
    javaMailProperties.put("mail.smtp.socketFactory.fallback", Optional.fromNullable(fallback).or(false));

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    ConfigEmail emailConfig = configEmailService.getEmailByMinSendCountAndStatus(ConfigEmailStatus.LISTED);
    mailSender.setJavaMailProperties(javaMailProperties);
    mailSender.setHost(Optional.fromNullable(emailConfig).isPresent()?emailConfig.getMailHost():host);
    mailSender.setUsername(Optional.fromNullable(emailConfig).isPresent()?emailConfig.getMailUsername():user);
    mailSender.setPassword(Optional.fromNullable(emailConfig).isPresent()?emailConfig.getMailPassword():passWord);
    mailSender.setDefaultEncoding("UTF-8");
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(Optional.fromNullable(mailSender.getUsername()).or(fromUser),Optional.fromNullable(personal).or("MasterDax"));
      helper.setTo(toUsers);
      if (emailDto.getCcUser() != null && emailDto.getCcUser().length > 0) {
        helper.setCc(emailDto.getCcUser());
      }
      helper.setSubject(emailDto.getSubject());
      helper.setText(emailDto.getText(),true);
      mailSender.send(mimeMessage);
      if (emailConfig!=null) {
        configEmailService.updateEmailCount(emailConfig.getId());
      }
      return true;
    } catch (Exception e) {
      log.error("邮箱地址:{}发送给{}的邮件异常|",emailDto.getFromUser(), emailDto.getToUser(),e);
    }
    return false;
  }
}
