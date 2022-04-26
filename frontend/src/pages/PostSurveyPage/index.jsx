import React, { useState } from "react";
import './style.scss'

const PostSurvey = () => {

  const [addOption, setAddOption] = useState('주관식');
  const [surveys, setSurveys] = useState([]); 

  const whichSurvey = (e) => {
    setAddOption(e.target.value);
  }

  const addSubjective = () => {
    setSurveys([...surveys,
      <>
        <input placeholder="질문 제목을 입력해주세요." />
        <input type="text" placeholder="주관식 답변" />
        <br />
      </>
    ]);
  }

  const addMultipleChoice = () => {
    setSurveys([...surveys,
      <div>
        <h3>질문 제목을 입력해주세요.</h3>
        <li>
          <button onClick={addChoice}>객관식 추가</button>
        </li>
      </div>
    ])
  }

  const addChoice = (e) => {
    let asked = document.createElement('input');
    asked.placeholder = "객관식 답변"
    e.target.closest("li").append(asked);
  }

  const addQuestion = () => {
    return addOption === "주관식" ? addSubjective() : addMultipleChoice();
  }

  return (
    <div className="survey">

      <h2>설문조사를 등록해 주세요!</h2>

      <button>기본 폼 가져오기</button>

      {surveys}

      <hr />
      <button onClick={addQuestion}>더하기 버튼</button>

      <button onClick={whichSurvey} value="주관식">주관식</button>
      <button onClick={whichSurvey} value="객관식">객관식</button>

    </div>
  )
}

export default PostSurvey;