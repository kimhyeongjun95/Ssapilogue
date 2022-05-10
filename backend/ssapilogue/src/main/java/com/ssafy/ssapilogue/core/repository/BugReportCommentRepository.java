package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.BugReportComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugReportCommentRepository extends JpaRepository<BugReportComment, Long> {
    List<BugReportComment> findAllByBugReportIdOrderByIdDesc(Long bugId);
}
