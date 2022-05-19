import React, {useState, useEffect, useRef} from 'react';
import Question from "../../components/Input/question"
import TextField from "@mui/material/TextField";
import {MenuItem,FormControl, Chip} from "@mui/material"
import Select from "@mui/material/Select";
import API from '../../api/API';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { Link, useNavigate, useLocation} from 'react-router-dom';
import store from "../../utils/store";
import swal from 'sweetalert';
import "./style.scss"

const PostProjectPage = () => {
  const locations = useLocation().state;
  const { btitle, bintro, bvarious, bphashbox, bhashbox, bbepo, brepo, bthumbnail, breadmeCheck, bmarkdown } = locations
  
  const navigate = useNavigate()
  // 상태관리
  const [title, setTitle] = useState(btitle)
  const [bepo, setBepo] = useState(bbepo) 
  const [repo, setRepo] = useState(brepo)
  const [various, setVarious] = useState(bvarious);
  const [intro, setIntro] = useState(bintro)
  const [thumbnail, setThumnail] = useState(bthumbnail)
  const [thumbnailUrl, setThumnailUrl] = useState(bthumbnail)
  const [markdown, setMarkdown] = useState(bmarkdown)
  const [readmeCheck, setReadmeCheck] = useState(breadmeCheck);
  const [searchData, setSearchData] = useState([]);
  const [msearchData, setmSearchData] = useState([]);
  const editorRef = React.createRef();

  
  // 기술스택 //
  const [hashbox, setHashbox] = useState(bhashbox)
  const [hashtag, setHashtag] = useState('')
  // 프로젝트 맴버 //
  const [phashbox, setpHashbox] = useState(bphashbox)
  const [phashtag, setpHashtag] = useState('')


  useEffect(() => {
    const token = store.getToken()
    if (!token) {
      swal("권한 없음", "로그인을 마친 회원만 이용 가능합니다.","info")
      navigate('/')
    }
    console.log(document.getElementsByClassName('pp-main-div')[0].querySelector('div'))
    document.getElementsByClassName('pp-main-div')[0].querySelector('div').focus()
  },[])

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
 
    return <Chip style={{ margin : "1%", backgroundColor : "#3396F4", color:'white', fontFamily: 'GmarketSansMedium'}}
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
    border: 0,

    color: "white",
    cursor: "pointer",
    fontFamily: "GmarketSansMedium"
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
        }else{
          setSD([])
        }
      }else{
        const type_value = document.getElementById(inputId).value
        if (type_value) {
          const res = await API.get(`/api/user-info/search/?keyword=${type_value}`)
          setSD(res.data.searchList)
        }else{
          setSD([])
        }
      }
    };

    
    const onClickSearch = (item) => {
      setSD([])
      if (inputBox.includes(item)) {
        swal("잘못된 입력","이미 등록하셨어요!", "error")
      }else{
        inputSetbox([...inputBox, item])
      }
      inputSetValue('')
    }

    const searchMap = sD.map((item) => {
      
      return <div className="pp-search-indi-div">
        <p className="search-p" onClick={() => onClickSearch(item)}>{item}</p>
      </div>
    });
    
    return <div style={{width: "40%"}}>
      <p className="pp-searchmap-p"> {InputTitle} </p>
      
      <TextField
        type="text"
        size = "small"
        id={inputId}
        className={inputId}
        style={{width:"100%", fontFamily:"GmarketSansMedium"}}
        value={inputValue}
        onChange={handleChange}
        onKeyPress={hamsu}
        placeholder={Plcaehorder}
        inputProps={{ style: {fontFamily: 'GmarketSansMedium'}}}
      />
      { (sD.length && inputValue) ?
        <div className="pp-search-indi-all-div">
          {searchMap}
        </div>
        :
        null
      }
      <div className="pp-searchmap-hash-div">
        {alHash(inputBox,inputSetbox)}
      </div>
    </div>
  }
    
    

    

  const chooseType = () => {
    const handleChange = (event) => {
      setVarious(event.target.value);
    };
    return  <FormControl style={{width:"40%"}} size="small">
      <p style={{ fontFamily : "GmarketSansMedium"}}>* 분류</p>
      <Select
        labelId="demo-select-small"
        id="demo-select-small"
        value={various}
        onChange={handleChange}
        displayEmpty
      >
        <MenuItem disalbed value="">
          <em style={{ fontFamily : "GmarketSansLight", fontStyle: "normal"}}>선택해주세요.</em>
        </MenuItem>
        <MenuItem style={{ fontFamily : "GmarketSansLight"}} value={"관통"}>관통</MenuItem>
        <MenuItem style={{ fontFamily : "GmarketSansLight"}} value={"공통"}>공통</MenuItem>
        <MenuItem style={{ fontFamily : "GmarketSansLight"}} value={"특화"}>특화</MenuItem>
        <MenuItem style={{ fontFamily : "GmarketSansLight"}} value={"자율"}>자율</MenuItem>
        <MenuItem style={{ fontFamily : "GmarketSansLight"}} value={"토이"}>토이</MenuItem>
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
      <div className="pp-main-div">

        <div className="title-highlight">
          <h1>프로젝트를 등록해 주세요!</h1>
        </div>

        <p className="pp-required-p"> * 는 필수항목입니다.</p>
        
        <Question InputTitle="* 프로젝트 이름" inputValue={title} inputSetValue={setTitle} pilsu="1" inputId="프로젝트이름"/>
        {chooseType()}
        <Question InputTitle="배포주소" inputValue={bepo} inputSetValue={setBepo} />
        <Question InputTitle="* Git Repo" inputValue={repo} inputSetValue={setRepo} pilsu="1" inputId="Git repo"/>
        {hashType("* 기술스택","기술스택을 입력후 엔터를 눌러주세요.",hashbox,hashtag,setHashtag,setHashbox,plusHashtag,"기술스택",searchData,setSearchData)}
        {hashType("* 프로젝트 멤버","프로젝트 멤버를 입력후 엔터를 눌러주세요.",phashbox,phashtag,setpHashtag,setpHashbox,PplusHashtag,"프로젝트 멤버",msearchData,setmSearchData)}

        <input type="file" style={{ display: "none" }} onChange={onChange} ref={imageInput} accept="img/*" />
        <Question InputTitle="* 소개" inputValue={intro} inputSetValue={setIntro} pilsu="1" inputId="프로젝트 소개" placeholder="메인 페이지에 보여줄 한 줄 소개를 작성해주세요."/>
        <button style={uploadButton} onClick={onCickImageUpload}>썸네일 업로드</button>
        { (thumbnail) ?
          <div className="pp-thumbnail-div">
            <img src={thumbnailUrl} className="pp-thumbnail-image" alt="thumbnail" />
          </div>
          :
          null
        }
        <div style={{marginTop:0}} className="pp-readme-div">
          <p style={{ fontFamily:"GmarketSansMedium", fontSize: "14px"}}>README</p>
          <input type="radio" checked={readmeCheck === "1"} name="theme" value={"1"} onChange={mkChange} />
          <p className="radio-p">직접 입력하기</p> 
          <input type="radio" checked={readmeCheck === "0"} name="theme" value={"0"} onChange={mkChange}/>
          <p className="radio-p">git에서 가져오기</p>
        </div>
        { (readmeCheck === "1") ?
          <div className="pp-readme-check-div">
            <Editor
              initialEditType="markdown"
              height="40vh"
              initialValue={markdown}
              placeholder='마크다운을 붙여주세요.'
              onChange={onChangeIntroFunction}
              ref={editorRef}
              style={{ fontFamily: "GmarketSansLight"}}
            /> 
          </div>
          : null
        }
        

        <div className="pp-next-button-div">
          {/* <ThemeProvider theme={theme}>
            <Link to="/" style={{ textDecoration: 'none' }}>
              <Button color="primary" className="next-button" size="large" style={{marginRight:"3vw", fontFamily: 'GmarketSansMedium'}} variant="outlined"> 취소 </Button>
            </Link>
            <Button color="primary" className="next-button" onClick={toSurvey} size="large" style={{ fontFamily: 'GmarketSansMedium'}} variant="contained"> 다음단계 </Button>
          </ThemeProvider> */}
          <Link to="/" style={{ textDecoration: 'none' }}>
            <button className="btn-white btn-large" style={{marginRight: "3vw"}}>취소</button>
          </Link>
          <button className="btn-blue btn-large" onClick={toSurvey}>다음 단계</button>
        </div>

       
      </div>
    </>
  )
}

export default PostProjectPage;
