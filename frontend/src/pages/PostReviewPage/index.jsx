import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import API from "../../api/API";
import store from "../../utils/store";
import './style.scss';

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
      <div className="post-box">

        <div style={{marginBottom: "3vh"}}>
          <h1>리뷰를 작성해 주세요!</h1>
        </div>

        {reviews.map((review, idx) => (
          <div className="survey-box" key={idx}>
            {review.surveyType === "주관식" ?
              <>
                <div className="title-box" style={{marginBottom: "3vh"}}>{review.title}</div>
                <input 
                  className="objective-answer"
                  placeholder="주관식 답변"
                  type="text" 
                  name="answer" 
                  value={review.answer} 
                  onChange={e => handleInput(e, idx)} 
                  style={{fontFamily:'GmarketSansMedium'}}
                />
              </>
              :
              <>
                <div className="title-box" style={{marginBottom: "3vh"}}>{review.title}</div>
                {review.surveyOptions.map((option, optIdx) => (
                  <div key={optIdx}>
                    <input 
                      style={{marginRight: "10px", marginBottom: "1vh"}}
                      type="radio"
                      name={idx} 
                      value={option.surveyOptionId} 
                      onChange={e => choiceHandleInput(e, idx)} 
                    />
                    {option.content}
                  </div>
                ))}
              </>
            } 
          </div>
        ))}
        <div style={{display:"flex",flexDirection:"row", marginTop:"5vh",marginBottom:"5vh"}}>
          <button className="btn-white btn-large" style={{marginRight: "3vw"}}>취소</button>
          <button className="btn-blue btn-large" onClick={submit}>등록</button>
        </div>
      </div>
    </>
  )
}

export default PostReviewPage;