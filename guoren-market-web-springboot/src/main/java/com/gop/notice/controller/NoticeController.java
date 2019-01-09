package com.gop.notice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.NoticeCodeConst;
import com.gop.config.CommonConstants;
import com.gop.domain.PublicNotice;
import com.gop.domain.enums.Status;
import com.gop.domain.enums.TopStatus;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.notice.PublicNoticeService;
import com.gop.notice.vo.NoticeVO;

/**
 * Created by Administrator on 2016/10/24.
 */
@RestController
@RequestMapping("/noticeApi")
public class NoticeController {

	@Autowired
	@Qualifier("PublicNoticeServiceImpl")
	PublicNoticeService publicNoticeService;

	// 查询notice详细内容
	@RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
	public NoticeVO noticeList(@RequestParam(value = "noticeId") Integer noticeId) {
		try {
			Preconditions.checkNotNull(noticeId);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.FIELD_ERROR);
		}
		// publicNoticeService.updateNoticeTopStatus(noticeId, topStatus);
		PublicNotice notice = publicNoticeService.getNotice(noticeId);
		NoticeVO vo = new NoticeVO();
		vo.setId(notice.getId());
		vo.setContent(notice.getContent());
		vo.setCreateTime(notice.getCreateTime());
		vo.setNickname(notice.getNickname());
		vo.setStatus(notice.getStatus());
		vo.setUid(notice.getUid());
		vo.setTopTime(notice.getTopTime());
		vo.setTopStatus(notice.getTopStatus());
		vo.setContent(notice.getContent());
		return vo;
	}

	// notice分页加高级查询
	@RequestMapping(value = "/noticeList", method = RequestMethod.GET)
	public PageModel<PublicNotice> noticeDetail(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "topStatus", required = false) TopStatus topStatus) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 1;
		}
		pageSize = CommonConstants.getPageSize(pageSize);
		pageNo = CommonConstants.getPageNo(pageNo);
		PageModel<PublicNotice> model = publicNoticeService.queryNoticePage(pageSize, pageNo, topStatus);
		return model;
	}
	// ****************************************************************************

}
