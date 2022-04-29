import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import './style.scss';
import trash from '../../assets/trashDelete.png';
import cross from '../../assets/crossDelete.png';
// import API from '../../assets/API';

const PostSurvey = () => {

  const [option, setOption] = useState('주관식');
  const [inputs, setInputs] = useState([])

  const { title } = useLocation();

  const whichSurvey = (e) => {
    setOption(e.target.value);
  }

  const addSurvey = () => {
    return option === "주관식" ? addSubjective() : addMultipleChoice();
  }

  const deleteSurvey = (idx) => {
    const values = [...inputs];
    values.splice(idx, 1);
    setInputs(values);
  }

  const deleteChoice = (e, idx, optIdx) => {
    e.target.closest("li").remove();
    const list = [...inputs]
    list[idx]["surveyOptions"][optIdx] = '';
    setInputs(list);
  }

  const addSubjective = () => {
    setInputs([...inputs, { title:'', type: "주관식" }])
  }

  const addMultipleChoice = () => {
    setInputs([...inputs, { title: '', type: "객관식", surveyOptions: [], count: 0 }])
  }

  const addChoice = (e, idx) => {
    const list = [...inputs]
    list[idx]["count"] += 1;
    const count = list[idx]["count"];
    list[idx].surveyOptions[count] = '';
    let ask = document.createElement("input");
    ask.value = list[idx].surveyOptions[count];
    ask.placeholder = "객관식 답변";
    ask.name = "surveyOptions";
    ask.addEventListener("keydown", (e) => {
      choiceHandleInput(e, idx, count);
    })
    let deleteBtn = document.createElement('img')
    deleteBtn.src = cross;
    deleteBtn.className = "delete";
    deleteBtn.addEventListener("click", (e) => {
      deleteChoice(e, idx, count);
    })
    let cover = document.createElement("li");
    cover.appendChild(ask);
    cover.append(deleteBtn);
    e.target.closest("div").appendChild(cover);
  }

  const tracker = () => {
    console.log(inputs);
  }

  const handleInput = (e, idx) => {
    const { name, value } = e.target;
    const list = [...inputs];
    list[idx][name] = value;
    setInputs(list);
  }

  const choiceHandleInput = (e, idx, optIdx) => {
    const { name, value } = e.target;
    const list = [...inputs];
    list[idx][name][optIdx] = value;
    setInputs(list);
  }

  const submitSurvey = () => {
    console.log(title);
  }

  return (
    <div className="survey">

      <h2>설문조사를 등록해 주세요!</h2>
      <button onClick={tracker}>기본 폼 가져오기</button>

      {inputs.map((input, idx) => (
        <div key={idx}>
          <input 
            name="title"
            value={input.title}
            placeholder="질문 제목을 입력해주세요." 
            onChange={e => handleInput(e, idx)}
          />
          <img src={trash} onClick={e => deleteSurvey(e, idx)} alt="trash" />

          {input.type === "주관식" ?
            <></> 
            : 
            <>
              <div className="choice-input">
                <button onClick={e => addChoice(e, idx)}>추가</button>

                <li>
                  <input
                    placeholder="객관식 답변" 
                    name="surveyOptions"
                    value={input.surveyOptions[0]}
                    onChange={e => choiceHandleInput(e, idx, 0)}
                  />
                </li>

              </div>
              <img className="delete" src={cross} alt="cross" onClick={deleteSurvey} />
            </>
          }
        </div>
      ))}

      <hr />
      <button onClick={addSurvey}>더하기 버튼</button>

      <button className={option === "주관식" ? "btn-on" : null} onClick={whichSurvey} value="주관식">주관식</button>
      <button className={option === "객관식" ? "btn-on" : null} onClick={whichSurvey} value="객관식">객관식</button>

      <button onClick={submitSurvey}>완료</button>

    </div>
  )
}

export default PostSurvey;
// div 삭제되면 inputs도 tracking 해야함.