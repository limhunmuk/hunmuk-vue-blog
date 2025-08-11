package home.hmvueblog.domain.notice;

import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import org.springframework.data.domain.Page;

public interface NoticeRepositoryCustom {

    Page<NoticeDetailDto> searchNoticeList(NoticeSearchDto condition);

    NoticeDetailDto searchNoticeDetail(NoticeSearchDto condition);
}
