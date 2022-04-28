import React, { useState } from "react";
import './style.scss';
import trash from '../../assets/trashDelete.png';
import cross from '../../assets/crossDelete.png';
import MultipleQuestions from "../../components/Survey/MultipleQuestion";

const PostSurvey = () => {

  const [option, setOption] = useState('주관식');
  const [inputs, setInputs] = useState([])

  const whichSurvey = (e) => {
    setOption(e.target.value);
  }

  const addSurvey = () => {
    return option === "주관식" ? addSubjective() : addMultipleChoice();
  }

  const deleteSurvey = (e, idx) => {
    console.log(idx);
    const values = [...inputs];
    values.splice(idx, 1);
    setInputs(values);
  }

  const addSubjective = () => {
    setInputs([...inputs, { title:'', type: "주관식" }])
  }

  const addMultipleChoice = () => {
    setInputs([...inputs, { title: '', type: "객관식", surveyOptions: [], }])
  }

  // const addChoice = (e) => {
  //   let box = document.createElement('div');
  //   let toAsk = document.createElement('input');
  //   let deleteBtn = document.createElement('img')
  //   setInputs([...inputs, { surveyOptions: '' }]);
  //   toAsk.placeholder = "객관식 답변";
  //   toAsk.name = "surveyOptions"
  //   toAsk.value = inputs.surveyOptions;
  //   deleteBtn.src = cross;
  //   deleteBtn.className = "delete";
  //   deleteBtn.addEventListener("click", (e) => {
  //     deleteSurvey(e);
  //   })
  //   box.appendChild(deleteBtn);
  //   box.appendChild(toAsk);
  //   e.target.closest("li").append(box);
  // }
  

  const tracker = () => {
    console.log(inputs);
  }

  const handleInput = (e, idx) => {
    const { name, value } = e.target;
    const list = [...inputs];
    list[idx][name] = value;
    setInputs(list);
    console.log(inputs);
  }

  const choiceHandleInput = (e, idx, optIdx) => {
    const { name, value } = e.target;
    const list = [...inputs];
    list[idx][name][optIdx] = value;
    setInputs(list);
    console.log(inputs);
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
              <input
                placeholder = "객관식 답변" 
                name="surveyOptions"
                value={input.surveyOptions[0]}
                onChange={e => choiceHandleInput(e, idx, 0)}
              />
              <input
                placeholder = "객관식 답변" 
                name="surveyOptions"
                value={input.surveyOptions[1]}
                onChange={e => choiceHandleInput(e, idx, 1)}
              />
              <input
                placeholder = "객관식 답변" 
                name="surveyOptions"
                value={input.surveyOptions[2]}
                onChange={e => choiceHandleInput(e, idx, 2)}
              />
              <input
                placeholder = "객관식 답변" 
                name="surveyOptions"
                value={input.surveyOptions[3]}
                onChange={e => choiceHandleInput(e, idx, 3)}
              />
              <input
                placeholder = "객관식 답변" 
                name="surveyOptions"
                value={input.surveyOptions[4]}
                onChange={e => choiceHandleInput(e, idx, 4)}
              />
              <img className="delete" src={cross} alt="cross" onClick={deleteSurvey} />
            </>
          }
        </div>
      ))}

      <hr />
      <button onClick={addSurvey}>더하기 버튼</button>

      <button className={option === "주관식" ? "btn-on" : null} onClick={whichSurvey} value="주관식">주관식</button>
      <button className={option === "객관식" ? "btn-on" : null} onClick={whichSurvey} value="객관식">객관식</button>

    </div>
  )
}

export default PostSurvey;
// div 삭제되면 inputs도 tracking 해야함.