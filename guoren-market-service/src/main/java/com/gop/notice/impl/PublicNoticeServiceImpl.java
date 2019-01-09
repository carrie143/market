package com.gop.notice.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.NoticeCodeConst;
import com.gop.domain.PublicNotice;
import com.gop.domain.enums.Status;
import com.gop.domain.enums.TopStatus;
import com.gop.exception.AppException;
import com.gop.mapper.PublicNoticeMapper;
import com.gop.mode.vo.PageModel;
import com.gop.notice.PublicNoticeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2016/10/24.
 */
@Service("PublicNoticeServiceImpl")
@Slf4j
public class PublicNoticeServiceImpl implements PublicNoticeService {

	@Autowired
	private PublicNoticeMapper publicNoticeMapper;

	@Override
	public void createNotice(PublicNotice publicNotice) {
		List<PublicNotice> list = publicNoticeMapper.queryNoticeByTopStatus(TopStatus.YES);
		if (0 < list.size() && TopStatus.YES.equals(publicNotice.getStatus())) {
			throw new AppException(NoticeCodeConst.TOP_OVER_ERROR);
		}
		try {
			publicNoticeMapper.insertSelective(publicNotice);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.CREATE_FAILED);
		}
	}

	@Override
	public void removeNotice(Integer id) {

		PublicNotice notice = publicNoticeMapper.selectByPrimaryKey(id);
		if (null == notice) {
			throw new AppException(NoticeCodeConst.FIELD_ERROR);
		}

		notice.setStatus(Status.INVALID);
		notice.setTopTime(new Date());
		try {
			publicNoticeMapper.updateByPrimaryKeySelective(notice);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.REMOVE_FAILED);
		}
	}

	@Override
	public void updateNotice(PublicNotice publicNotice) {
		try {
			publicNoticeMapper.updateByPrimaryKeySelective(publicNotice);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.UPDATE_FAILED);
		}
	}

	@Override
	@Transactional
	public void updateNoticeTopStatus(Integer id, TopStatus topStatus) {

		PublicNotice notice = publicNoticeMapper.selectByPrimaryKey(id);
		if (null == notice) {
			throw new AppException(NoticeCodeConst.FIELD_ERROR);
		}

		notice.setTopStatus(topStatus);
		notice.setTopTime(new Date());
		try {
			publicNoticeMapper.updateByPrimaryKeySelective(notice);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.UPDATE_FAILED);
		}

	}

	@Override
	public PublicNotice getNotice(Integer id) {
		PublicNotice notice = publicNoticeMapper.selectByPrimaryKey(id);
		if (null == notice) {
			throw new AppException(NoticeCodeConst.FIELD_ERROR);
		}
		return notice;
	}

	@Override
	public PageModel<PublicNotice> queryNoticePage(Integer pageSize, Integer pageNo, TopStatus topStatus) {
		PageHelper.startPage(pageNo, pageSize);
		if (null == topStatus) {
			PageHelper.orderBy("id desc");
		}
		// PageHelper.orderBy("top_status ,top_time desc,id desc");
		PageHelper.orderBy("top_status,create_time desc");
		PageInfo<PublicNotice> pageInfo = new PageInfo<>(publicNoticeMapper.queryNoticeByTopStatus(topStatus));
		PageModel<PublicNotice> pageModel = new PageModel<>();
		if (null == pageInfo) {
			pageModel.setList(Collections.EMPTY_LIST);
		}
		pageModel.setList(pageInfo.getList());
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(pageInfo.getPageNum());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(pageInfo.getTotal());

		return pageModel;
	}

	@Override
	public List<PublicNotice> queryNoticeByStatus(TopStatus topStatus) {
		return publicNoticeMapper.queryNoticeByTopStatus(topStatus);
	}
}
