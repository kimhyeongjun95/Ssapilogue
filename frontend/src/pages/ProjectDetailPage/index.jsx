import React, {useEffect,useState} from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import detailImage from "../../assets/detailImage.png"
import "./style.scss"
import constructionPic from "../../assets/construction.png"
import projectPeoplePic from "../../assets/proejectPeople.png"
import gitRepo from "../../assets/git.png"
import google from "../../assets/Google.png"
import report from "../../assets/report.png"
import {Button} from "@mui/material"
import API from "../../api/API";
import store from "../../utils/store";
import markdownIt from "markdown-it";


const DetailPage = () => {
  const id = useParams().projectId;
  let navigate = useNavigate();
  const [category, setCategory] = useState('');
  const [title, setTitle] = useState('');
  const [stack, setStack] = useState([]);
  const [member, setMember] = useState([]);
  const [authormember, setAuthorMember] = useState([]);
  const [commentCnt, setCommentCnt] = useState('');
  const [comment, setComment] = useState([]);
  const [thumbnail, setThumbnail] = useState('');
  const [repo, setRepo] = useState('');
  const [bepo, setBepo] = useState(''); 
  const [readme, setReadme] = useState('');
  const [kai, setKai] = useState(0);
  const [intro, setIntro] = useState('');
  
  useEffect(() => {
    async function projectCall() {
      const res = await API.get(`/api/project/${id}`)
      setCategory(res.data.project.category)
      setTitle(res.data.project.title)
      setStack(res.data.project.techStack)
      setMember(res.data.project.anonymousMember)
      setCommentCnt(res.data.project.commentCnt)
      setComment(res.data.project.comment)
      setRepo(res.data.project.gitAddress)
      setBepo(res.data.project.deployAddress)
      setReadme(res.data.project.readme)
      setAuthorMember(res.data.project.member)
      setIntro(res.data.project.introduce)
      setThumbnail(res.data.project.thumbnail)
      console.log(res.data.project)
    }
    projectCall()
  },[kai,id])
  // let category = '자율'
  // let title = '라이키와 함께 자전거 여행을 떠나보세요!  나의 라이딩 메이트, RIKEY'
  // let stack = ['react-native','react','spring','엔진엑스','Unity','Unity']
  // let member = ['최강현','정동균','김형준','하현서','김은서']
  // let commentCnt = 3
  // let comment =[
  //   {
  //     "commentId" : 1,
  //     "content" : "ㅋㅋ 현서님 프로젝트 너무재밌어요 아픙로도 같이해요",
  //     "nickname" : "최강현",
  //     "profileImage" : detailImage,
  //     "createdAt" : "2022-04-25"
  //   },
  //   {
  //     "commentId" : 2,
  //     "content" : "강현님 웃기고있네요 ㅋㅋ",
  //     "nickname" : "하현서",
  //     "profileImage" : detailImage,
  //     "createdAt" : "2022-04-26"
  //   }
  // ]
  const writeComment = async() => {
    try {
      var commentText = document.getElementById('commentText').value;
      await API.post(`/api/project-comment/${id}`,{
        content : commentText,
      })
      document.getElementById('commentText').value = ''
      setKai(kai + 1)
    } catch(e) {
      throw e;
    }
  }

  const deleteComment = async(item) => {
    try {
      await API.delete(`/api/project-comment/${item}`)

      setKai(kai + 1)
    } catch(e) {
      throw e;
    }

  }

  const commentBox = comment.map((item) => {
    return <div className="box-div">
      <div>
        <img className="comment-image" src={detailImage} alt="profile" />
      </div>
      <div className="comment-content-box">
        <div className="comment-nickname-box">
          <div className="comment-nickname">
            {item.nickname}
          </div>
          <div className="comment-created">
            {item.createdAt}
          </div>
        </div>
        <div className="comment-content">
          <p>{item.content}</p>
        </div>
        <div>
          <p className="project-detail-red" onClick={() => deleteComment(item.commentId)}>삭제하기</p>
        </div>
      </div>

    </div>
  })
  const deleteProject = async() => {

    store.getToken();
    await API.delete(`/api/project/${id}`)
    navigate('/')
  }


  const stackBox = stack.map((item) => {
    return <Button variant="contained" style={{margin:"1px 5px 1px 0", height: "32px", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
      
    >{item}</Button>
  })

  const memberBox = member.map((item) => {
    return <Button variant="contained" style={{margin:"1px 5px 1px 0", height: "32px", backgroundColor : "#00CAF4", color:'white', fontWeight:'bold'}}
    >{item}
    </Button>
  })

  
  return (
    
    <div className="project-div">
      <img className="detailImage" src={detailImage} alt="detailImage" />
      <div className="project-body-div">
        <div className="title-div">
          <div className="project-part">{category}</div>
          <h2>{title}</h2>
        </div>
        <div className="stack-div">
          <span className="stack">
            <img className="icon" src={constructionPic} alt="conpic" />
            {stackBox}
          </span>

          <span className="option-div">
            <div className="option-category">ReadMe 갱신</div>
            <div className="option-category">
              <Link 
                className="to-edit" 
                to="edit"
                state={{
                  editTitle : title,
                  editCategory: category,
                  editStack: stack,
                  editMember: member,
                  editRepo : repo,
                  editBepo : bepo,
                  editReadme : readme,
                  edittAuthormember : authormember,
                  editIntro : intro,
                  editThumbnail : thumbnail
                }}
              >
                수정
              </Link>
            </div>
            <div onClick={deleteProject} className="option-category-red">삭제</div>
          </span>
        </div>

        <div className="member-div">
          <span className="stack">
            <img className="icon" src={projectPeoplePic} alt="projectPeoplePic" />
            {memberBox}
          </span>
        </div>

        <div className="git-div">
          <a href="https://www.naver.com" rel="noreferrer" target='_blank' className="link-a">
            <img className="icon" src={gitRepo} alt="gitRepo" />
            <span>
              Git Repo
            </span>
          </a>
          <a href="https://www.kakao.com" rel="noreferrer" target='_blank' className="link-a">
            <img className="icon" src={google} alt="google" />
            <span>
              Demo Site
            </span>
          </a>
          <Link 
            to={`/project/${id}/opinions/review`}
            className="link-a"
          >
            <img className="icon" src={report} alt="report" />
            리뷰·버그 리포트
          </Link>
        </div>

        <div className="readme-div"dangerouslySetInnerHTML={{
          __html: markdownIt().render(readme),
        }}
        ></div>

        <div className="comment-div">
          <p className="comment-p">댓글  <span className="comment-number">{commentCnt}</span></p>
          <div>
            <textarea id="commentText" className="comment-box" maxLength={400}></textarea>
            <button className="comment-submit" type="submit" onClick={writeComment}>댓글 작성</button>
          </div>
        </div>

        {commentBox}
      </div>
    </div>
  )
}

export default DetailPage;