package com.gop.mapper;


import com.gop.domain.UserStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/5/21.
 */
public interface UserStatisticsMapper {
    List<UserStatistics> selectUserStatistics();

    void insertStatistics(UserStatistics userStatistics);

    int countUserStatistics(@Param("createDate") Date createDate);

    int updateStatus(@Param("createDate") Date createDate);
}
