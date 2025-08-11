package home.hmvueblog.web.api.service;

import home.hmvueblog.domain.common.enums.StatusCode;
import home.hmvueblog.domain.common.enums.YNCode;
import home.hmvueblog.domain.notice.NoticeRepository;
import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeFormDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import home.hmvueblog.domain.notice.entity.Notice;
import home.hmvueblog.domain.post.PostRepository;
import home.hmvueblog.domain.post.dto.PostDetailDto;
import home.hmvueblog.domain.post.dto.PostSearchDto;
import home.hmvueblog.domain.postcomment.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 목록 조회
     * @param condition
     * @return
     */
    public Page<?> searchNoticeList(NoticeSearchDto condition){
        return noticeRepository.searchNoticeList(condition);
    }

    /**
     * 공지사항 상세 조회
     * @param condition
     * @return
     */
    public NoticeDetailDto searchNoticeDetail(NoticeSearchDto condition){
        return noticeRepository.searchNoticeDetail(condition);
    }

    /**
     * 공지사항 등록
     * @param noticeForm
     * @retur
     */
    @Transactional
    public Notice saveNotice(NoticeFormDto noticeForm) {

        Notice notice = noticeForm.toEntity();
        return noticeRepository.save(notice);
    }

    /**
     * 공지사항 수정
     * @param noticeForm
     * @return
     */
    @Transactional
    public Notice updateNotice(String id, NoticeFormDto noticeForm) {

        Notice notice = noticeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

        notice.updateNotice(
                noticeForm.getTitle(),
                noticeForm.getContent(),
                StatusCode.ACTIVE,
                null
        );

        return notice;
    }

    /**
     * 공지사항 삭제
     * @param id
     */
    public void deleteNotice(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }
}
