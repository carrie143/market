package com.gop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.gop.authentication.service.UserIdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 暴露外部接口
 */
@RestController("exposeAPI")
@RequestMapping("/expose/user")
@Slf4j
public class UserExposeController {
    private static String format = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    public UserIdentificationService userIdentificationService;

    public void main(String[] args) {

    }

    /**
     * 获取该时间段通过kyc的用户
     *
     * @param strTime
     * @param endTime
     * @return
     * @note 数据库有过期时间字段，不知道是否有用到!
     */
    @RequestMapping(value = "/getAddUser", method = RequestMethod.GET)
    public List getAddUser(@RequestParam(value = "strTime") long strTime,
                           @RequestParam(value = "endTime") long endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(new Date(strTime * 1000));
        String end = sdf.format(new Date(endTime * 1000));
        Map map = new HashMap();
        map.put("strDay", str);
        map.put("endDay", end);
        map.put("orderBy", "uid");
        map.put("order", "asc");
        map.put("strRow", 0);
        map.put("endRow", 100000);
        return userIdentificationService.getAddUser(map);
    }

    @RequestMapping(value = "/getUserInvite", method = RequestMethod.GET)
    public int getUserInvite(@RequestParam(value = "uid") int uid) {
        return userIdentificationService.getUserInvite(uid);
    }
}
