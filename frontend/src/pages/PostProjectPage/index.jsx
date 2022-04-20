import React, {useState, useRef} from 'react';
import TextField from "@mui/material/TextField";
import {InputLabel,MenuItem,FormControl, Button, Chip} from "@mui/material"
import Select from "@mui/material/Select";

import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

const PostProjectPage = () => {
  // 상태관리
  const [title, setTitle] = useState('')
  const [bepo, setBepo] = useState('') 
  const [repo, setRepo] = useState('')
  const [various, setVarious] = useState('');
  const [intro, setIntro] = useState('')
  const [thumbnail, setThumnail] = useState('')
  const [thumbnailUrl, setThumnailUrl] = useState('')
 
  // 기술스택 //
  const [hashbox, setHashbox] = useState([])
  const [hashtag, setHashtag] = useState('')
  // 프로젝트 맴버 //
  const [phashbox, setpHashbox] = useState([])
  const [phashtag, setpHashtag] = useState('')

  // 라벨링
  const onChangeHashtag = (event) => {
    setHashtag(event.target.value)
  }
  
  const plusHashtag = (e) => {
    if (e.key === "Enter") {
      if (hashtag) {
 
        setHashbox([...hashbox, hashtag])
 
        setHashtag('')
        console.log(hashbox)
      } else {
        alert("입력값이 없습니다.")
      }
    } 
  }
   
  const PplusHashtag = (e) => {
    if (e.key === "Enter") {
      if (phashtag) {
 
        setpHashbox([...phashbox, phashtag])
 
        setpHashtag('')
        console.log(phashbox)
      }else {
        alert("입력값이 없습니다.")
      }
    } 
  }
   
 
  const hashDelete = (index,inputBox,inputSetbox) => () => {
    inputBox.splice(index,1);
    inputSetbox([...inputBox])
  }
 
  const alHash = (inputBox,inputSetbox) => inputBox.map( (item,index) => {
 
    return <Chip style={{ margin : "1%", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
      label={item}
      onDelete={hashDelete(index,inputBox,inputSetbox)} 
    />
  })
  //
 
  // 사지 전달 인풋
  const imageInput = useRef();
 
  const onCickImageUpload = () => {
    imageInput.current.click();
  };
 
  const onChange = (event) => {
    const imageFile = event.target.files[0];
    const imageUrl = URL.createObjectURL(imageFile);
    setThumnailUrl(imageUrl)
    setThumnail(imageFile)
    console.log(thumbnail)
    console.log(thumbnailUrl)
 
  };
  ///
 
  //// 마크다운 에디터
 
  ///
  // css
  const uploadButton = {
    width: "40%",
    marginTop : "2%",
    padding: 8 ,
    backgroundColor: "#3396F4",
    borderRadius: 5,

    color: "white",
    cursor: "pointer",
  }
 
  //
   
  const hashType = (InputTitle,inputBox,inputValue,inputSetValue,inputSetbox,hamsu) => {
    const handleChange = (event) => {
      inputSetValue(event.target.value);
    };
    return <div style={{width: "40%"}}>
      <p style={{marginBottom : 0}}> {InputTitle} </p>
      <TextField
        type="text"
        size = "small"
        style={{width:"100%"}}
        value={inputValue}
        onChange={handleChange}
        onKeyPress={hamsu}
        placeholder={InputTitle}
      />
      <div style={{width:"100%" ,display:"flex",flexDirection:"row", alignItems :"center",flexWrap: "wrap"}}>
        {alHash(inputBox,inputSetbox)}
      
        
      </div>
    </div>
  }
 
 
 
 
  const questionType = (InputTitle,inputValue ,inputSetValue, pilsu) => {
    const handleChange = (event) => {
      inputSetValue(event.target.value);
    };
    if (pilsu){
      return <div style={{width: "40%"}}>
        <div>
          <p style={{marginBottom : 0}}> {InputTitle} </p>
          <TextField
            style={{width:"100%"}}
            size = "small"
            value={inputValue}
            onChange={handleChange}
            required
            id="outlined-basic"
            variant="outlined"
          />
        </div>
      </div>
    }else{
      return <div style={{width: "40%"}}>
        <div>
          <p style={{marginBottom : 0}}> {InputTitle} </p>
          <TextField
            style={{width:"100%"}}
            size = "small"
            value={inputValue}
            onChange={handleChange}
            id="outlined-basic"
            variant="outlined"
          />
        </div>
      </div>

    }
  }
  const chooseType = () => {
    const handleChange = (event) => {
      setVarious(event.target.value);
    };
    return  <FormControl style={{marginTop:"2vh",width:"40%"}} size="small">
      <InputLabel id="demo-select-small">* 분류</InputLabel>
      <Select
        labelId="demo-select-small"
        id="demo-select-small"
        value={various}
        label="* 분류"
        onChange={handleChange}
      >
        <MenuItem value={"Common"}>공통</MenuItem>
        <MenuItem value={"Special"}>특화</MenuItem>
        <MenuItem value={"Free"}>자율</MenuItem>
        <MenuItem value={"Toy"}>토이</MenuItem>
      </Select>
    </FormControl>
  }

  return (
    <>
      <div style={{display:"flex",flexDirection:"column", justifyContent : "center", alignItems :"center"}}>

        <h2 style={{width:"40%", textAlign:"center"}}>프로젝트를 등록해주세요</h2>
        <a style={{width:"40%", color: "#909090", fontSize: 12,marginBottom:"5vh"}}> * 는 필수항목입니다.</a>
        {questionType("* 프로젝트 이름",title,setTitle,1)}
        {chooseType()}
        {questionType("배포주소", bepo, setBepo)}
        {questionType("*Git Repo", repo, setRepo)}
        {hashType("* 기술스택",hashbox,hashtag,setHashtag,setHashbox,plusHashtag)}
        {hashType("* 프로젝트 멤버", phashbox,phashtag,setpHashtag,setpHashbox,PplusHashtag)}

        <input type="file" style={{ display: "none" }} onChange={onChange} ref={imageInput} accept="img/*" />
        <button style={uploadButton} onClick={onCickImageUpload}>썸네일 업로드</button>
        { (thumbnail) ?
          <div style={{marginTop:"2vh"}}>
            <img src={thumbnailUrl} style={{height:"20vh",width:"35vh"}} />
          </div>
          :
          null
        }
        {questionType("*소개", intro, setIntro,1)}
        <div style={{marginTop:"2%",width:"40%"}}>
          <Editor
            height="40vh"
            placeholder='마크다운을 붙여주세요.'

          />
        </div>

        <div style={{display:"flex",flexDirection:"row", marginTop:"5vh",marginBottom:"5vh"}}>
          <Button size="large" style={{marginRight:"3vw"}} variant="outlined"> 취소 </Button>
          <Button size="large" variant="contained"> 다음단계 </Button>
        </div>

       
      </div>
    </>
  )
}

export default PostProjectPage;