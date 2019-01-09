package com.gop.notice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

/**
 * Created by Administrator on 2016/10/24.
 */
@RestController
@RequestMapping("/noticeApi")
public class NoticeController {

	@Autowired
	@Qualifier("PublicNoticeServiceImpl")
	PublicNoticeService publicNoticeService;

	@RequestMapping(value = "/noticeCreate", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	public void noticeCreate(@AuthForHeader AuthContext context, @RequestParam("content") String content,
			@RequestParam("nickName") String nickName, @RequestParam("title") String title,
			@RequestParam("topStatus") TopStatus topStatus
			, @RequestParam("createTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTime
			) {
		Integer uid = context.getLoginSession().getUserId();
		
		try {
			PublicNotice publicNotice = new PublicNotice();
			publicNotice.setUid(uid);
			publicNotice.setNickname(nickName);
			publicNotice.setTopStatus(topStatus);
			publicNotice.setContent(content);
			publicNotice.setTitle(title);
//			Date date = new Date(Long.valueOf(createTime));
			publicNotice.setCreateTime(createTime);
			if (topStatus.equals(TopStatus.YES)) {
				publicNotice.setTopTime(new Date());
			} else {
				// 数据库设置为非null,所以初始化设置时间为最小时间
				publicNotice.setTopTime(new Date(0));
			}
			publicNoticeService.createNotice(publicNotice);
		} catch (Exception e) {
			throw new AppException(NoticeCodeConst.CREATE_FAILED);
		}
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	public void noticeTop(@AuthForHeader AuthContext context,@RequestParam(value = "noticeId") Integer noticeId,
			@RequestParam(value = "topStatus") TopStatus topStatus) {
		List<PublicNotice> list = publicNoticeService.queryNoticeByStatus(TopStatus.YES);
		if (0 < list.size() && TopStatus.YES.equals(topStatus)) {
			throw new  AppException(NoticeCodeConst.TOP_OVER_ERROR);
		}
		publicNoticeService.updateNoticeTopStatus(noticeId, topStatus);
	}

	@RequestMapping(value = "/noticeDelete", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	public void noticeDelete(@AuthForHeader AuthContext context,@RequestParam(value = "noticeId") Integer noticeId) {
		PublicNotice publicNotice = publicNoticeService.getNotice(noticeId);
		publicNotice.setStatus(Status.INVALID);
		publicNoticeService.updateNotice(publicNotice);
	}

	// ****************************Lua模块服务代码转换java*************************************
	// 查询notice详细内容
	@RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
	public NoticeVO noticeList(@RequestParam("noticeId") Integer noticeId) {
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
