import React, {useState, useRef} from "react";
import Question from "../../components/Input/question"
import API from "../../api/API";
import { Editor } from '@toast-ui/react-editor';
import {Button} from "@mui/material"
import "./style.scss"
import store from "../../utils/store";
import { useNavigate, useParams } from "react-router-dom";


const PostReviewPage = () => {
  const id = useParams().projectId; 

  const editorRef = React.createRef();
  const [val, setVal] = useState('')
  const [markdown, setMarkdown] = useState('')

  const onChangeIntroFunction = () => {
    const marktext = editorRef.current.getInstance().getMarkdown()
    console.log(marktext);
    setMarkdown(marktext)
  };

  const postReport = async() => {
    if (!val) {
      return alert('제목을 입력해주세요')
    }else{
      if (!markdown) {
        return alert('마크다운을 입력해주세요!!')
      }
    }
    try{
      store.getToken()
      const postReport = await API.post(`/api/bug/${id}`, {
        title: val,
        content : markdown
      })
      console.log(postReport)
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
          onChange={onChangeIntroFunction}
          ref={editorRef}
        />
      </div>
      <Button type="submit" style={{marginTop:"10%"}} onClick={postReport} variant="contained">등록</Button>
        
      {/* </form> */}
        
    </div>
      
    
  )
}

export default PostReviewPage;