import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import API from "../../api/API";
import store from "../../utils/store";

const PostReviewPage = () => {
  const id = useParams().projectId;
  const [reviews, setReviews] = useState([]);

  const handleInput = (e, idx) => {
    const { name, value } = e.target;
    const list = [...reviews];
    list[idx][name] = value;
    setReviews(list);
  }

  const choiceHandleInput = (e, idx) => {
    const { value } = e.target;
    const list = [...reviews];
    list[idx]["answer"] = value;
    setReviews(list);
  }

  const getSurvey = async (id) => {
    try {
      const result = await API.get(`/api/survey/${id}`);
      setReviews(result.data.surveyList);
    } catch (e) {
      throw e;
    }
  }

  const submit = async () => {
    try {
      store.getToken()
      const response = await API.post(`/api/review`, {reviews:reviews})
      console.log(response);
    } catch (e) {
      throw e;
    }
  }
  
  useEffect(() => {
    getSurvey(id);
  }, [id])

  return (
    <>
      <h1>리뷰 등록 페이지!</h1>
      {reviews.map((review, idx) => (
        <div key={idx}>
          {review.surveyType === "주관식" ?
            <>
              <h2>
                {review.title}
              </h2>
              <input type="text" name="answer" value={review.answer} onChange={e => handleInput(e, idx)} />
            </>
            :
            <>
              {review.title}
              {review.surveyOptions.map((option, optIdx) => (
                <div key={optIdx}>
                  {option.content}
                  <input type="radio" name={idx} value={option.surveyOptionId} onChange={e => choiceHandleInput(e, idx)} />
                </div>
              ))}
            </>
          } 
        </div>
      ))}
      <button onClick={submit}>리뷰 작성 완료</button>

    </>
  )
}

export default PostReviewPage;