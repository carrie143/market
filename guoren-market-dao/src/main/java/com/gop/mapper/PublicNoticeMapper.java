package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.PublicNotice;
import com.gop.domain.enums.TopStatus;

public interface PublicNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublicNotice record);

    int insertSelective(PublicNotice record);

    PublicNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublicNotice record);

    int updateByPrimaryKey(PublicNotice record);

	int updatePublicNoticeTopStatus(@Param("id")Integer id, @Param("topStatus")TopStatus topStatus);

	List<PublicNotice> queryNoticeByTopStatus(@Param("topStatus")TopStatus topStatus);
}