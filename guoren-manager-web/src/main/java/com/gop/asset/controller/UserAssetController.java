package com.gop.asset.controller;

import com.gop.exception.AppException;
import java.util.*;
import java.util.stream.Collectors;

import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.response.AssetDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController("managerUserAsset")
@RequestMapping("/asset")
@Slf4j
// @Api("用户中心")
public class UserAssetController {
    @Autowired
    private CloudApiService cloudApiService;
    @Autowired
    private ConfigAssetService configAssetService;

    // 查询用户资产列表
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/getUserAccounts", method = RequestMethod.GET)
    public List<AssetDto> getUserAccountAssets(@AuthForHeader AuthContext context, @RequestParam("uid") Integer uid) {
        List<String> assetList = configAssetService.getAvailableAssetCode().stream().map(a -> a.getAssetCode())
                                                  .collect(Collectors.toList());
        BrokerAssetAccountRequest request = new BrokerAssetAccountRequest();
        request.setUid((long)uid);
        request.setAssetList(assetList);
        return cloudApiService.getUserAccountAssets(request);

    }
}
