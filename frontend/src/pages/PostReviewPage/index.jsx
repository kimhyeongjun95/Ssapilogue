import React, { useState, useEffect } from "react";
import API from "../../api/API";
import { useParams } from "react-router-dom";

const PostReviewPage = () => {
  const id = useParams().projectId;
  const [reviews, setReviews] = useState([]);
  const [response, setResponse] = useState([]);
  
  const check = () => {
    console.log(reviews);
    console.log(response);
    // responesFrame();
  }

  // const handleInput = (e, idx) => {
  //   const { name, value } = e.target;
  //   const list = [...response];
  //   list[idx][name] = value;
  //   setResponse(list);
  // }

  // const responesFrame = () => {
  //   console.log(reviews.length);
  // }

  const getSurvey = async () => {
    try {
      const result = await API.get(`/api/survey/${id}`);
      console.log(result);
      setReviews(result.data.surveyList);
    } catch (e) {
      throw e;
    }
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
              </h2>
              <input type="text" name="" id="" value={review.answer} />
            </>
            :
            <>
              {review.title}
              {review.surveyOptions.map((option, ix) => (
                <div key={ix}>
                  {option.content}
                  <input type="radio" name={review.surveyId} value={option.surveyOptionId}/>
                </div>
              ))}
            </>
          } 
        </div>
      ))}
    </>
  )
}

export default PostReviewPage;