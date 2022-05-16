package com.ssafy.ssapilogue.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("FindReviewResDto")
public class FindReviewResDto {

    @ApiModelProperty(value = "설문조사 문항 번호", example = "1")
    private Integer index;

    @ApiModelProperty(value = "설문조사 제목", example = "싸피로그가 유용했나요?")
    private String surveyTitle;

    @ApiModelProperty(value = "설문조사 타입", example = "객관식")
    private String surveyType;

    @ApiModelProperty(value = "총 리뷰 개수", example = "20")
    private Integer totalCount;

    @ApiModelProperty(value = "객관식 문항 리뷰")
    private List<FindObjectiveReviewResDto> objectiveReviews;

    @ApiModelProperty(value = "주관식 문항 리뷰")
    private List<FindSubjectiveReviewResDto> subjectiveReviews;
}
