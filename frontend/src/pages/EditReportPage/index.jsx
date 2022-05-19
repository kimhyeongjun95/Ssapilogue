import React, {useState} from "react";
import Question from "../../components/Input/question"
import API from "../../api/API";
import { Editor } from '@toast-ui/react-editor';
import {Button} from "@mui/material"
import "./style.scss"
import { useNavigate, useParams,useLocation } from "react-router-dom";
import swal from 'sweetalert';

const EditReportPage = () => {
  const projectid = useParams().projectId; 
  const reportId = useParams().reportId;
  let navigate = useNavigate();
  const locations = useLocation().state;
  const { content,title } = locations;
  const editorRef = React.createRef();
  const [val, setVal] = useState(title)
  const [markdown, setMarkdown] = useState(content)

  const onChangeIntroFunction = () => {
    const marktext = editorRef.current.getInstance().getMarkdown()
    setMarkdown(marktext)
  };

  const editReport = async() => {
    if (!val) {
      return swal("미입력", "제목을 입력해주세요.", "error")
    }else{
      if (!markdown) {
        return swal("미입력", "마크다운을 입력해주세요.", "error")
      }
    }
    try{
      const editReport = await API.put(`/api/bug/${reportId}`, { // eslint-disable-line no-unused-vars
        title: val,
        content : markdown
      })
      navigate(`/project/${projectid}/opinions/report/${reportId}`)
    } catch (e) {
      throw e;
    }
  }

  return (
    <div className="main-div">
      <h1>버그 리포트 등록 페이지!</h1>
      {/* <form className="form-style" onSubmit={() => postReport()}> */}
      <Question InputTitle="제목" inputValue={val} inputSetValue={setVal} pilsu="1"/>
      <div className="editor-div">
        <Editor 
          placeholder='리포트를 작성해주세요.'
          initialValue={content}
          onChange={onChangeIntroFunction}
          ref={editorRef}
        />
      </div>
      <Button type="submit" style={{marginTop:"10%"}} onClick={editReport} variant="contained">등록</Button>
        
      {/* </form> */}
        
    </div>
      
    
  )
}
export default EditReportPage