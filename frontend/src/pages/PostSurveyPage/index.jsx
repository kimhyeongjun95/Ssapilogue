import React, { useState } from "react";
import './style.scss';
import trash from '../../assets/trashDelete.png';
import cross from '../../assets/crossDelete.png';

const PostSurvey = () => {

  const [option, setOption] = useState('주관식');
  const [surveys, setSurveys] = useState([]); 

  const whichSurvey = (e) => {
    setOption(e.target.value);
  }

  const addSruvey = () => {
    return option === "주관식" ? addSubjective() : addMultipleChoice();
  }

  const deleteSurvey = (e) => {
    let question = e.target.closest("div");
    question.remove();
  }

  const addSubjective = () => {
    setSurveys([...surveys,
      <div data-category-name="sub">
        <img src={trash} onClick={deleteSurvey} alt="trash" />
        <input placeholder="질문 제목을 입력해주세요."  />
        <input type="text" placeholder="주관식 답변" />
        <br />
      </div>
    ]);
  }

  const addMultipleChoice = () => {
    setSurveys([...surveys,
      <div>
        <img src={trash} onClick={deleteSurvey} alt="trash" />
        <li>
          <input className="multiple-question" placeholder="질문 제목을 입력해주세요." />
          <button onClick={addChoice}>객관식 추가</button>
        </li>
      </div>
    ])
  }

  const addChoice = (e) => {
    let box = document.createElement('div');
    let asked = document.createElement('input');
    let deleteBtn = document.createElement('img')
    asked.placeholder = "객관식 답변";
    deleteBtn.src = cross;
    deleteBtn.className = "delete";
    deleteBtn.addEventListener("click", (e) => {
      deleteSurvey(e);
    })
    box.appendChild(deleteBtn);
    box.appendChild(asked);
    e.target.closest("li").append(box);
  }

  const tracker = () => {
    console.log(surveys);
  }

  return (
    <div className="survey">

      <h2>설문조사를 등록해 주세요!</h2>
      <button onClick={tracker}>기본 폼 가져오기</button>

      {surveys}

      <hr />
      <button onClick={addSruvey}>더하기 버튼</button>

      <button className={option === "주관식" ? "btn-on" : null} onClick={whichSurvey} value="주관식">주관식</button>
      <button className={option === "객관식" ? "btn-on" : null} onClick={whichSurvey} value="객관식">객관식</button>

    </div>
  )
}

export default PostSurvey;