import React, {useState, useRef} from 'react';
import Question from "../../components/Input/question"
import TextField from "@mui/material/TextField";
import {InputLabel,MenuItem,FormControl, Button, Chip} from "@mui/material"
import Select from "@mui/material/Select";
import API from '../../api/API';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { Link, useNavigate } from 'react-router-dom';
import swal from 'sweetalert';
import "./style.scss"

const PostProjectPage = () => {

  const navigate = useNavigate()
  // 상태관리
  const [title, setTitle] = useState('')
  const [bepo, setBepo] = useState('') 
  const [repo, setRepo] = useState('')
  const [various, setVarious] = useState('');
  const [intro, setIntro] = useState('')
  const [thumbnail, setThumnail] = useState('')
  const [thumbnailUrl, setThumnailUrl] = useState('')
  const [markdown, setMarkdown] = useState('')
  const [readmeCheck, setReadmeCheck] = useState('1');
  const [searchData, setSearchData] = useState([]);
  const [msearchData, setmSearchData] = useState([]);
  const editorRef = React.createRef();

  // 기술스택 //
  const [hashbox, setHashbox] = useState([])
  const [hashtag, setHashtag] = useState('')
  // 프로젝트 맴버 //
  const [phashbox, setpHashbox] = useState([])
  const [phashtag, setpHashtag] = useState('')

  // 라벨링
  const plusHashtag = (e) => {
    if (e.key === "Enter") {
      if (hashtag) {
        setHashbox([...hashbox, hashtag])
        setHashtag('')

        setSearchData([])
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
        setmSearchData([])
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

  const onChangeIntroFunction = () => {
    const marktext = editorRef.current.getInstance().getMarkdown()
    setMarkdown(marktext)
  };

  // 사진 전달 인풋
  const imageInput = useRef();
 
  const onCickImageUpload = () => {
    imageInput.current.click();
  };
 
  const onChange = async(event) => {
    const imageFile = event.target.files[0];
    const imageUrl = URL.createObjectURL(imageFile);
    setThumnailUrl(imageUrl)
    const formData = new FormData();

    formData.append('file', imageFile);
    
    const res = await API.post('/api/project/image', formData);
    setThumnail(res.data.imageUrl)
 
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
  

  
  const hashType = (InputTitle,Plcaehorder, inputBox,inputValue,inputSetValue,inputSetbox,hamsu,inputId,sD,setSD) => {
    const handleChange = async(event) => {
      inputSetValue(event.target.value);
      if (inputId === "기술스택") {
        const type_value = document.getElementById(inputId).value
        if (type_value) {
          const res = await API.get(`/api/tech-stack/search/specific?keyword=${type_value}`)
          setSD(res.data.searchList)
        }
      }else{
        const type_value = document.getElementById(inputId).value
        if (type_value) {
          const res = await API.get(`/api/user-info/search/?keyword=${type_value}`)
          setSD(res.data.searchList)
        }
      }
    };

    
    const onClickSearch = (item) => {
      setSD([])
      inputSetValue(item)
      inputSetbox([...inputBox, item])
      inputSetValue('')
    }

    const searchMap = sD.map((item) => {
    
      return <div className="pp-search-indi-div">
        <p className="search-p" onClick={() => onClickSearch(item)}>{item}</p>
      </div>
    });

    

    
    return <div style={{width: "40%"}}>
      <p style={{marginBottom : 0}}> {InputTitle} </p>
      
      <TextField
        type="text"
        size = "small"
        id={inputId}
        className={inputId}
        style={{width:"100%"}}
        value={inputValue}
        onChange={handleChange}
        onKeyPress={hamsu}
        placeholder={Plcaehorder}
      />
      { (sD.length) ?
        <div className="pp-search-indi-all-div">
          {searchMap}
        </div>
        :
        null
      }
      <div style={{width:"100%" ,display:"flex",flexDirection:"row", alignItems :"center",flexWrap: "wrap"}}>
        {alHash(inputBox,inputSetbox)}
      
        
      </div>
    </div>
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
        <MenuItem value={"공통"}>공통</MenuItem>
        <MenuItem value={"특화"}>특화</MenuItem>
        <MenuItem value={"자율"}>자율</MenuItem>
        <MenuItem value={"토이"}>토이</MenuItem>
      </Select>
    </FormControl>
  }

  const mkChange = (e) => {
    setReadmeCheck(e.target.value)
  }

  
  const toSurvey = () => {
    let party = [title, various, repo, hashbox,phashbox,intro]
    let flag = false
    let party_name = ["프로젝트이름","분류", "Git repo", "기술스택", "프로젝트 멤버","프로젝트 소개"]
    for (let party_index = 0; party_index < party.length; party_index++ ) {
      if (party[party_index].length === 0)
      {
        if ( party_index === 1) {
          flag = true
          swal("미입력", `${party_name[party_index]} 이(가) 입력되지 않았습니다.`, "error");
          break
        }else {
          document.getElementsByClassName(`${party_name[party_index]}`)[0].querySelector('input').focus()
          flag = true
          swal("미입력", `${party_name[party_index]} 이(가) 입력되지 않았습니다.`, "error");
          break
        }
      }
  
    }
    if (!flag) {
      navigate('/project/survey',{
        state : {
          title: title,
          intro: intro,
          various: various,
          phashbox: phashbox,
          hashbox: hashbox,
          bepo: bepo,
          repo: repo,
          thumbnail: thumbnail,
          readmeCheck: readmeCheck,
          markdown: markdown
        }
      })
    }
    
   
  }




  return (
    <>
      <div style={{display:"flex",flexDirection:"column", justifyContent : "center", alignItems :"center"}}>

        <h2 style={{width:"40%", textAlign:"center"}}>프로젝트를 등록해주세요</h2>
        <p style={{width:"40%", color: "#909090", fontSize: 12,marginBottom:"5vh"}}> * 는 필수항목입니다.</p>
        <Question InputTitle="* 프로젝트 이름" inputValue={title} inputSetValue={setTitle} pilsu="1" inputId="프로젝트이름"/>
        {chooseType()}
        <Question InputTitle="배포주소" inputValue={bepo} inputSetValue={setBepo} />
        <Question InputTitle="* Git Repo" inputValue={repo} inputSetValue={setRepo} pilsu="1" inputId="Git repo"/>
        {hashType("* 기술스택","기술스택을 입력후 엔터를 눌러주세요.",hashbox,hashtag,setHashtag,setHashbox,plusHashtag,"기술스택",searchData,setSearchData)}
        {hashType("* 프로젝트 멤버","프로젝트 멤버를 입력후 엔터를 눌러주세요.",phashbox,phashtag,setpHashtag,setpHashbox,PplusHashtag,"프로젝트 멤버",msearchData,setmSearchData)}

        <input type="file" style={{ display: "none" }} onChange={onChange} ref={imageInput} accept="img/*" />
        <button style={uploadButton} onClick={onCickImageUpload}>썸네일 업로드</button>
        { (thumbnail) ?
          <div style={{marginTop:"2vh"}}>
            <img src={thumbnailUrl} style={{height:"20vh",width:"35vh"}} alt="thumbnail" />
          </div>
          :
          null
        }
        <div>
          <input type="radio" checked={readmeCheck === "1"} name="theme" value={"1"} onChange={mkChange} />직접 입력하기
          <input type="radio" checked={readmeCheck === "0"} name="theme" value={"0"} onChange={mkChange}/>github에서 가져오기
        </div>
        <Question InputTitle="* 소개" inputValue={intro} inputSetValue={setIntro} pilsu="1" inputId="프로젝트 소개"/>
        { (readmeCheck === "1") ?
          <div style={{marginTop:"2%",width:"40%"}}>
            <Editor
              initialEditType="markdown"
              height="40vh"
              placeholder='마크다운을 붙여주세요.'
              onChange={onChangeIntroFunction}
              ref={editorRef}
            /> 
          </div>
          : null
        }

        <div style={{display:"flex",flexDirection:"row", marginTop:"5vh",marginBottom:"5vh"}}>
          <Link to="/" style={{ textDecoration: 'none' }}>
            <Button size="large" style={{marginRight:"3vw"}} variant="outlined"> 취소 </Button>
          </Link>
          <Button onClick={toSurvey} size="large" variant="contained"> 다음단계 </Button>
          {/* </Link> */}
        </div>

       
      </div>
    </>
  )
}

export default PostProjectPage;