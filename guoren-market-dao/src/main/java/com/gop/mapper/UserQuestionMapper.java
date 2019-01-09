package com.gop.mapper;

import com.gop.domain.UserQuestion;

public interface UserQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserQuestion record);

    int insertSelective(UserQuestion record);

    UserQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserQuestion record);

    int updateByPrimaryKey(UserQuestion record);
}