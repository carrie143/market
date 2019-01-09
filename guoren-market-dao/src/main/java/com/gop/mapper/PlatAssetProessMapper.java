package com.gop.mapper;

import com.gop.domain.PlatAssetProcess;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/5/28.
 */
public interface PlatAssetProessMapper {
    List<PlatAssetProcess> selectList(@Param("assetCode") String assetCode, @Param("endDate") Date endDate);
    int selectCount( @Param("date") Date date);
    void insertBatch(List<PlatAssetProcess> platAssetProesses);
    void updateStatus(@Param("date") Date date);
}
