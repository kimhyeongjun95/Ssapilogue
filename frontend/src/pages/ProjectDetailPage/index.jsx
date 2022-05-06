import React from "react";
import { Link, useParams } from "react-router-dom";
import detailImage from "../../assets//detailImage.png"
import "./style.scss"
import constructionPic from "../../assets/construction.png"
import projectPeoplePic from "../../assets/proejectPeople.png"
import gitRepo from "../../assets/git.png"
import google from "../../assets/Google.png"
import {Button} from "@mui/material"


const DetailPage = () => {
  const id = useParams().projectId;
  let category = '자율'
  let title = '라이키와 함께 자전거 여행을 떠나보세요!  나의 라이딩 메이트, RIKEY'
  let stack = ['react-native','react','spring','엔진엑스','Unity','Unity']
  let member = ['최강현','정동균','김형준','하현서','김은서']
  let commentCnt = 3
  let comment =[
    {
      "commentId" : 1,
      "content" : "ㅋㅋ 현서님 프로젝트 너무재밌어요 아픙로도 같이해요",
      "nickname" : "최강현",
      "profileImage" : detailImage,
      "createdAt" : "2022-04-25"
    },
    {
      "commentId" : 2,
      "content" : "강현님 웃기고있네요 ㅋㅋ",
      "nickname" : "하현서",
      "profileImage" : detailImage,
      "createdAt" : "2022-04-26"
    }
  ]


  const commentBox = comment.map((item) => {
    return <div className="box-div">
      <div>
        <img className="icon" src={detailImage} alt="profile" />
      </div>
      <div>
        <p>{item.nickname} {item.createdAt}</p>
        <p>{item.content}</p>
        <p>답글달기</p>
      </div>

    </div>
  })


  const stackBox = stack.map((item) => {
    return <Button variant="contained" style={{margin:"0.3%", marginRight:"1%", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
      
    >{item}</Button>
  })

  const memberBox = member.map((item) => {
    return <Button variant="contained" style={{margin : "0.6%", backgroundColor : "#00CAF4", color:'white', fontWeight:'bold'}}
    >{item}
    </Button>
  })
  
  return (
    
    <div>

      <img className="detailImage" src={detailImage} alt="detailImage" />
      <div className="title-div">
        <span className="project-part">{category}</span>
        <h2>{title}</h2>
      </div>
      <div>
        <div className="stack-div">
          <span className="stack">
            <img className="icon" src={constructionPic} alt="conpic" />
            {stackBox}
          </span>


          <span className="option-div">
            <p className="option-category">ReadMe 갱신</p>
            <p className="option-category">수정</p>
            <p className="option-category-red">삭제</p>
          </span>
        </div>

        <div className="member-div">
          <span className="stack">
            <img className="icon" src={projectPeoplePic} alt="projectPeoplePic" />
            {memberBox}
          </span>
        </div>
      </div>

      <div className="git-div">
        <a href="https://www.naver.com" className="link-a">
          <img className="icon" src={gitRepo} alt="gitRepo" />
          Git Repo
        </a>
        <a href="https://www.kakao.com" className="link-a">
          <img className="icon" src={google} alt="google" />
          Demo Site
        </a>
        <Link 
          to={`/project/${id}/report/`}
        >
          <button>리뷰 / 버그 리포트</button>
        </Link>
      </div>

      <div style={{height: "100vh", marginLeft:"25%"}}>
        <p>리드미들갈꺼</p>

      </div>

      <div className="comment-div">
        <p className="comment-p">댓글  <span className="comment-number">{commentCnt}</span></p>
        <div>
          <p><textarea className="comment-box" maxLength={340}></textarea></p>
          <input type="submit" value="Submit" />
        </div>
      </div>

      {commentBox}
    </div>
  )
}

export default DetailPage;