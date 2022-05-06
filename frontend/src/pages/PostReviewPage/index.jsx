import React, { useState, useEffect } from "react";
import API from "../../api/API";
import { useParams } from "react-router-dom";

const PostReviewPage = () => {
  const id = useParams().projectId;
  const [reviews, setReviews] = useState([]);

  const getSurvey = async () => {
    try {
      const result = await API.get(`/api/survey/${id}`);
      console.log(result);
      setReviews(result.data.surveyList);
    } catch (e) {
      throw e;
    }
  }

  const check = () => {
    console.log(reviews);
  }

  useEffect(() => {
    getSurvey();
  }, [])

  // 보내야하는 양식
  // [
  //   (객관식)
  //   {
  //     "content": null,
  //     "surveyId": "62675cd8f7cfeb0c48c5770b",
  //     "surveyOptionId": "62675cd8f7cfeb0c48c5770b"
  //   },
  //   (주관식)
  //   {
  //     "content": "매우 유용했습니다.",
  //     "surveyId": "62675cd8f7cfeb0c48c5770b",
  //     "surveyOptionId": null
  //   }
  // ]

  return (
    <>
      <h1>리뷰 등록 페이지!</h1>
      <button onClick={check}>확인</button>
      {reviews.map((review, idx) => (
        <div key={idx}>
          {review.surveyType === "주관식" ?
            <>
              <h2>
                {review.title}
                <input type="text" name="" id="" />
              </h2>
              {console.log(review.title)}
            </>
            :
            <>
              {review.title}
              {review.surveyOptions.map((option, idx) => (
                <>
                  <div>
                    {option.content}
                  </div>
                </>
              ))}
            </>
          } 
        </div>
      ))}
    </>
  )
}

export default PostReviewPage;