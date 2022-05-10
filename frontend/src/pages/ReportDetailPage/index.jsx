import React, {useEffect, useState} from "react";
import { useParams, useNavigate } from "react-router-dom";
import detailImage from "../../assets//detailImage.png"
import "./style.scss"
import API from "../../api/API";
import moment from 'moment';
import defaultpic from '../../assets/default.png'
import markdownIt from "markdown-it";
import { Button } from "@mui/material";

const ReportDetailPage = () => {
  const reportId = useParams().reportId
  const projectId = useParams().projectId

  let navigate = useNavigate()
  //상태관리 hook

  const [commentCnt, setCommentCnt] = useState('');
  const [comment, setComment] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [writer, setWriter] = useState('');
  const [profilepic, setProfilepic] = useState('');
  const [createAt, setCreateAt] = useState('');
  const [kai, setKai] = useState(0);

  useEffect(async()=> {
    const res = await API.get(`/api/bug/detail/${reportId}`)
    setCommentCnt(res.data.bugReport.commentCnt)
    setComment(res.data.bugReport.comments)
    setTitle(res.data.bugReport.title)
    setContent(res.data.bugReport.content)
    setWriter(res.data.bugReport.nickname)
    setProfilepic(res.data.bugReport.profileImage)
    setCreateAt(moment(res.data.bugReport.createAt).format('YYYY년 MM월 DD일'))
    console.log(res.data.bugReport)
  },[kai])

  const deleteReport = async() => {
    const res = await API.delete(`/api/bug/${reportId}`)
    navigate(`/project/${projectId}/opinions/report`)
    console.log(res)
  }

  const postComment = async() => {
    let commentText = document.getElementById('commentText').value;
    const res = await API.post(`/api/bug-comment/${reportId}`,{
      content : commentText
    })
    document.getElementById('commentText').value = ''
    setKai(kai + 1)
  }

  const deleteComment = async(bugCoId) => {
    const res = await API.delete(`/api/bug-comment/${bugCoId}`)
    console.log(res)
    setKai(kai + 1)
  }
  const goEdit = () => {
    console.log(content)
    navigate("edit", {state: {content :content ,title : title}});
  }
    
  const commentBox = comment.map((item) => {
    return <div className="report-detail-box-div">
      <div>
        <img className="report-detail-icon" src={detailImage} alt="profile" />
      </div>
      <div>
        {item.nickname} {item.createdAt}
        <p>{item.content}</p>
        <div>
          <p className="report-detail-red" onClick={() => deleteComment(item.bugCoId)}>삭제하기</p>
        </div>
        
      </div>

    </div>
  })

  return (
    <div className="report-detail-main">
      <div className="report-detail-main-div">
        <div className="report-detail-nav">
          <h1>{title}</h1>
          <div className="pow">
            <p onClick={goEdit}>수정</p>
            <p className="red" onClick={deleteReport}>삭제</p>
          </div>
        </div>
        <hr />
        <div className="report-hr-div">
          <p> 작성일  {createAt}</p>
        </div>
        <div className="report-detail-writer-div">
          <img src={defaultpic} />
          <p>{writer}</p>
        </div>
        <div className="report-detail-content-div" dangerouslySetInnerHTML={{
          __html: markdownIt().render(content),
        }}
        ></div>
      </div>
      <div className="report-detail-comment-div">
        <p className="comment-p">댓글  <span className="comment-number">{commentCnt}</span></p>
        <div>
          <textarea id="commentText" className="comment-box" maxLength={400}></textarea>
          <button className="comment-submit" type="submit" onClick={postComment}>댓글 작성</button>
        </div>
      </div>
      {commentBox}
      
    </div>
  )
}

export default ReportDetailPage;