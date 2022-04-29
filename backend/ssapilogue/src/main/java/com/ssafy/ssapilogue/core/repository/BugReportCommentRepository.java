package com.ssafy.ssapilogue.core.repository;

import com.ssafy.ssapilogue.core.domain.BugReportComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugReportCommentRepository extends JpaRepository<BugReportComment, Long> {
}
