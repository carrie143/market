package com.gop.notice;

import java.util.List;

import com.gop.domain.PublicNotice;
import com.gop.domain.enums.TopStatus;
import com.gop.mode.vo.PageModel;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface PublicNoticeService {

    public void createNotice(PublicNotice publicNotice);

    public void removeNotice(Integer id);

    public void updateNotice(PublicNotice publicNotice);

    public void updateNoticeTopStatus(Integer id, TopStatus topStatus);

    public PublicNotice getNotice(Integer id);

	public PageModel<PublicNotice> queryNoticePage(Integer pageSize, Integer pageNo, TopStatus topStatus);

	public List<PublicNotice> queryNoticeByStatus(TopStatus topStatus);

}
