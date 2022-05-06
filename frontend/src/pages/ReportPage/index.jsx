import React, {useState,useEffect} from "react";
import Undo from "../../assets/undo.png"
import edit from "../../assets/Edit-alt.png"
import "./style.scss"
import { Link, useParams } from "react-router-dom";
import API from "../../api/API";

const ReportPage = () => {
  const [board, setBoard] = useState([0,0,0]);
  const [bugList,setBugList] = useState([]);
  const [test, setTest] = useState(0);
  const id = useParams().projectId;
  useEffect( () => {
    async function bugrecall() {
      const res = await API.get(`/api/bug/${id}`);
      console.log(res.data)
      console.log(board)
      console.log("테스트",test)
      console.log(res.data.bugList["bugReports"])
      setTest(res.data.bugList["totalCount"])
      setBoard([res.data.bugList["totalCount"],res.data.bugList["solvedCount"],res.data.bugList["unsolvedCount"]])
      setBugList(res.data.bugList['bugReports'])
      console.log(board)
    }
    bugrecall()
    return
  },[])
  // const response = {
  //   "bugList": [
  //     {
  //       "bugId": 1,
  //       "nickname": "하현서[광주_1반_C104]팀장",
  //       "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
  //       "title": "여기 클릭이 안돼요!!",
  //       "content": "형준이가 클릭이 안돼요!!",
  //       "is_solved": true,
  //       "createAt": "2022-04-28 18:10"
  //     },
  //     {
  //       "bugId": 1,
  //       "nickname": "하현서[광주_1반_C104]팀장",
  //       "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
  //       "title": "여기 클릭이 안돼요!!",
  //       "content": "형준이가 클릭이 안돼요!!",
  //       "is_solved": false,
  //       "createAt": "2022-04-28 18:10"
  //     },
  //     {
  //       "bugId": 1,
  //       "nickname": "하현서[광주_1반_C104]팀장",
  //       "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
  //       "title": "여기 클릭이 안돼요!!",
  //       "content": "형준이가 클릭이 안돼요!!",
  //       "is_solved": true,
  //       "createAt": "2022-04-28 18:10"
  //     },{
  //       "bugId": 1,
  //       "nickname": "하현서[광주_1반_C104]팀장",
  //       "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
  //       "title": "여기 클릭이 안돼요!!",
  //       "content": "형준이가 클릭이 안돼요!!",
  //       "is_solved": false,
  //       "createAt": "2022-04-28 18:10"
  //     } 
  //   ],
  //   "status": "SUCCESS"
  // }

  const bugBox = bugList.map((item) => {
    let bgdiv = "white-item-div"
    let pio = true
    if (item.is_solved){
      bgdiv = "black-item-div"
      pio = true
      console.log(bgdiv)
    }else{
      bgdiv = "white-item-div"
      pio = false
      console.log(bgdiv)
    }
    

    return <div className={bgdiv}>
      <div className="menu-solved">
        <input type="checkbox" defaultChecked={pio} onClick={() => inputclick(item.bugId)} size="big"></input>
      </div>
      <p className="menu-title">{item.title}</p>
      <p className="menu-date">{item.createAt}</p>
      <p className="menu-writer">{item.nickname}</p>
     
    </div>
  })

  return (
    <>
      <div className="report-nav-div">
        <div className="back-div">
          <img className="undo-pic" src={Undo} alt="Undo"/>
          프로젝트로 돌아가기
        </div>
        <div className="choose-div">
          <h1> review </h1>
          <div></div>
          <h1> bug report</h1>
        </div>
        <div className="nav-div"/>
      </div>

      <div className="solve-div">
        <div>
          <p>전체</p>
          <h2 className="solve-h2">26</h2>
        </div>
        <div>
          <p>해결</p>
          <h2 className="solve-h2">18</h2>
        </div>
        <div>
          <p>미해결</p>
          <h2 className="solve-h2">8</h2>
        </div>
      </div>
      <br />
      <div className="wirte-pic-div">
        <Link 
          to={`/project/${id}/report/post`}
        >
          <img className="write-pic" src={edit} alt="write" />
        </Link>
      </div>
      <div className="report-box-div">
        <div className="report-menu-div">
          <p className="menu-solved">해결</p>
          <p className="menu-title">제목</p>
          <p className="menu-date">날짜</p>
          <p>제보자</p>
        </div>
        <div>
          {bugBox}
          호호
          {board}
        </div>
      </div>
    </>
   
  )
}

export default ReportPage;