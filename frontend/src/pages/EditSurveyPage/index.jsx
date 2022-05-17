import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import API from "../../api/API";
import store from "../../utils/store";
import './style.scss';
import trash from '../../assets/trashDelete.png';
import cross from '../../assets/crossDelete.png';
import plus from '../../assets/plus.png';
import swal from 'sweetalert'

const EditSurvey = () => {

  const id = useParams().projectId;
  const [option, setOption] = useState('주관식');
  const [inputs, setInputs] = useState([]);
  const [toDelete, setToDelete] = useState([]);
  const navigate = useNavigate();

  
  const whichSurvey = (e) => {
    setOption(e.target.value);
  }

  const addSurvey = () => {
    return option === "주관식" ? addSubjective() : addMultipleChoice();
  }

  const deleteSurvey = (idx) => {
    const list = [...inputs];
    const temp = list.splice(idx, 1);
    setToDelete([...toDelete, temp[0].surveyId]);
    setInputs(list);
  }

  const deleteChoice = (e, idx, optIdx) => {
    e.target.closest("li").remove();
    const list = [...inputs]
    list[idx]["surveyOptions"][optIdx] = '';
    setInputs(list);
  }

  const addSubjective = () => {
    setInputs([...inputs, { title:'', surveyType: "주관식", surveyId: null }])
  }

  const addMultipleChoice = () => {
    setInputs([...inputs, { title: '', surveyType: "객관식", surveyOptions: [], surveyId: null, count: 0 }])
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
    ask.className = "objective-answer";
    ask.addEventListener("input", (e) => {
      choiceHandleInput(e, idx, count);
    })

    let deleteBtn = document.createElement('img')
    deleteBtn.src = cross;
    deleteBtn.className = "delete";
    deleteBtn.addEventListener("click", (e) => {
      deleteChoice(e, idx, count);
    })

    let cover = document.createElement("li");
    cover.className = "answer-box";
    cover.appendChild(ask);
    cover.append(deleteBtn);
    e.target.closest("div").appendChild(cover);
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

  const refiningData = () => {
    for (let i = 0; i < inputs.length; i++) {
      delete inputs[i]['default'];
      delete inputs[i]['answer'];
    }
  }
  
  const bringSurvey = async (id) => {
    try {
      const response = await API.get(`/api/survey/edit/${id}`);
      console.log(response);
      const value = response.data.surveyList
      for (let i = 0; i < value.length; i++) {
        value[i]['default'] = true;
      }
      setInputs(value)
    } catch(e) {
      throw e
    } 
  }

  const checkRequired = () => {
    for (let i = 0; i < inputs.length; i++) {
      if (inputs[i].surveyType === "주관식") {
        if (inputs[i].title.length === 0) {
          return false
        }
      }

      if (inputs[i].surveyType === "객관식") {
        for (let j = 0; j < inputs[i].surveyOptions.length; j++) {
          if (inputs[i].surveyOptions[j].length === 0) {
            return false
          }
        }
      }
    }
    return true;
  }

  const submit = async () => {
    console.log(inputs);
    try {
      refiningData();
      if (!checkRequired()) {
        swal("빈칸을 모두 채워주세요!", "빈칸을 모두 채우고 다시 한번 확인해주세요.","error")
      }
      store.getToken();
      await API.post(`/api/survey/${id}`, {
        createSurveyReqDtos: inputs
      }) 
      if (toDelete.length > 0) {
        await API.delete(`/api/survey`, {
          deletedSurveys: toDelete
        })
      }
      navigate(`/project/${id}`)
      return;
    } catch (e) {
      throw e;
    }
  }

  useEffect(() => {
    bringSurvey(id)
  }, [id])


  return (

    <div className="post-box">

      <div className="title-highlight" style={{marginBottom: "5vh",  fontFamily: 'GmarketSansMedium'}}>
        <h1>설문조사를 등록해 주세요!</h1>
      </div>

      {inputs.map((input, idx) => (
        <div className="survey-box" key={idx}>
          {input.default === true && (
            <input 
              className="title-box"
              name="title"
              value={input.title}
              placeholder="질문 제목을 입력해주세요." 
              onChange={e => handleInput(e, idx)}
              disabled
            />
          )}
          {input.default !== true && (
            <input 
              className="title-box"
              name="title"
              value={input.title}
              placeholder="질문 제목을 입력해주세요." 
              onChange={e => handleInput(e, idx)}
            />
          )}
          <img className="trash" src={trash} onClick={() => deleteSurvey(idx)} alt="trash" />

          {input.surveyType === "주관식" ?
            <>
              <input type="text" className="objective-answer" placeholder="주관식 답변" disabled />
            </> 
            : 
            <>
              <div className="choice-input">

                <li className="answer-box">
                  {input.default === true && input.surveyOptions.map((answer, idx) => (
                    <div>
                      <input
                        className="objective-answer"
                        placeholder="객관식 답변" 
                        name="surveyOptions"
                        value={answer}
                        onChange={e => choiceHandleInput(e, idx, 0)}
                        disabled
                      />
                    </div>
                  ))}
                  {input.default !== true && (
                    <div>
                      <input
                        className="objective-answer"
                        placeholder="객관식 답변" 
                        name="surveyOptions"
                        value={input.surveyOptions[0]}
                        onChange={e => choiceHandleInput(e, idx, 0)}
                      />
                      <img className="option-plus" src={plus} onClick={e => addChoice(e, idx)} alt="choice-plus" />
                    </div>
                  )}
                </li>
              </div>
            </>
          }
        </div>
      ))}

      <img className="plus" src={plus} onClick={addSurvey} alt="plus-survey" />

      <div className="survey-type">
        <button className={option === "주관식" ? "btn-blue" : "btn-white"} onClick={whichSurvey} value="주관식">주관식</button>
        <button className={option === "객관식" ? "btn-blue" : "btn-white"} onClick={whichSurvey} value="객관식">객관식</button>
      </div>

      <div style={{display:"flex",flexDirection:"row", marginTop:"5vh",marginBottom:"5vh"}}>
        <button className="btn-white btn-large" style={{marginRight: "3vw"}}>취소</button>
        <button className="btn-blue btn-large" onClick={submit}>등록</button>
      </div>

    </div>

  )
}

export default EditSurvey;