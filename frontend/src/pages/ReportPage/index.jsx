import React from "react";
import Undo from "../../assets/undo.png"
import edit from "../../assets/Edit-alt.png"
import "./style.scss"

const ReportPage = () => {

  const response = {
    "bugList": [
      {
        "bugId": 1,
        "nickname": "하현서[광주_1반_C104]팀장",
        "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
        "title": "여기 클릭이 안돼요!!",
        "content": "형준이가 클릭이 안돼요!!",
        "is_solved": true,
        "createAt": "2022-04-28 18:10"
      },
      {
        "bugId": 1,
        "nickname": "하현서[광주_1반_C104]팀장",
        "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
        "title": "여기 클릭이 안돼요!!",
        "content": "형준이가 클릭이 안돼요!!",
        "is_solved": false,
        "createAt": "2022-04-28 18:10"
      },
      {
        "bugId": 1,
        "nickname": "하현서[광주_1반_C104]팀장",
        "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
        "title": "여기 클릭이 안돼요!!",
        "content": "형준이가 클릭이 안돼요!!",
        "is_solved": true,
        "createAt": "2022-04-28 18:10"
      },{
        "bugId": 1,
        "nickname": "하현서[광주_1반_C104]팀장",
        "profileImage": "https://j6ssafy.c104.com/images/xxxxx",
        "title": "여기 클릭이 안돼요!!",
        "content": "형준이가 클릭이 안돼요!!",
        "is_solved": false,
        "createAt": "2022-04-28 18:10"
      } 
    ],
    "status": "SUCCESS"
  }

  const bugBox = response["bugList"].map((item) => {
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
    
    const sico = (e) => {
      if (e.target.checked === true){
        e.target.checked = false
      }else{
        console.log("폴스")
      }
    }
    return <div className={bgdiv}>
      <div className="menu-solved">
        <input type="checkbox" defaultChecked={pio} size="big"></input>
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
        <img className="write-pic" src={edit} alt="write" />
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
        </div>
      </div>
    </>
   
  )
}

export default ReportPage;