package home.hmvueblog.web.api.controller;

import home.hmvueblog.domain.notice.NoticeRepository;
import home.hmvueblog.domain.notice.dto.NoticeDetailDto;
import home.hmvueblog.domain.notice.dto.NoticeFormDto;
import home.hmvueblog.domain.notice.dto.NoticeSearchDto;
import home.hmvueblog.domain.notice.entity.Notice;
import home.hmvueblog.web.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hunmuk
 */
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 목록 조회
     *
     * @param condition
     * @return
     */
    @GetMapping("/api/notice")
    @ResponseBody
    public ResponseEntity<?> getNotice(NoticeSearchDto condition) {

        Page<?> noticeList = noticeService.searchNoticeList(condition);
        return ResponseEntity.ok(noticeList);
    }

    /**
     * 공지사항 등록
     *
     * @param form
     * @return
     */
    @PostMapping("/api/notice")
    @ResponseBody
    public ResponseEntity<?> createNotice(@RequestBody @Validated NoticeFormDto form) {

        Notice notice = noticeService.saveNotice(form);
        return ResponseEntity.ok(notice.getId());
    }

    /**
     * 공지사항 상세 조회
     *
     * @param id
     * @param condition
     * @return
     */
    @GetMapping("/api/notice/{id}")
    @ResponseBody
    public ResponseEntity<?> getNoticeDetail(@PathVariable("id") String id, NoticeSearchDto condition) {

        condition.setId(Long.valueOf(id));
        NoticeDetailDto noticeDetail = noticeService.searchNoticeDetail(condition);
        return ResponseEntity.ok(noticeDetail);
    }

    /**
     * 공지사항 수정
     *
     * @param id
     * @param form
     * @return
     */
    @PutMapping("/api/notice/{id}")
    @ResponseBody
    public ResponseEntity<?> updateNotice(@PathVariable("id") String id, @RequestBody NoticeFormDto form) {

        Notice notice = noticeService.updateNotice(id, form);
        return ResponseEntity.ok(notice.getId());
    }

    /**
     * 공지사항 삭제
     *
     * @param id
     * @return
     */

    @DeleteMapping("/api/notice/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteNotice(@PathVariable("id") String id) {
        noticeService.deleteNotice(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }
}
