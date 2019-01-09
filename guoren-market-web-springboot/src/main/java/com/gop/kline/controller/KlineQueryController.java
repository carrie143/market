package com.gop.kline.controller;

import com.gop.api.cloud.request.KlineQueryReq;
import com.gop.api.cloud.response.KlineQueryPages;
import com.gop.api.cloud.service.CloudApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lxa on 2018/1/30.
 *
 * @author lixianan
 */
@Slf4j
@RestController
@RequestMapping("/kline-query")
public class KlineQueryController {

  @Autowired
  private CloudApiService cloudApiService;

  @GetMapping("/pages")
  public KlineQueryPages getKlinePages(KlineQueryReq req) {
    req.setNanoTime(System.nanoTime());
    return cloudApiService.klineQueryPages(req);
  }



}
