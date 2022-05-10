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
  const [writecomment, setWriteComment] = useState('');
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

  const onChangeComment = (e) => {
    setWriteComment(e.target.value)
  }

  const postComment = async() => {
    const res = await API.post(`/api/bug-comment/${reportId}`,{
      content : writecomment
    })
    setWriteComment('')
    setKai(kai + 1)
  }

  const commentBox = comment.map((item) => {
    return <div className="box-div">
      <div>
        <img className="icon" src={detailImage} alt="profile" />
      </div>
      <div>
        <p>{item.nickname} {item.createdAt}</p>
        <p>{item.content}</p>
        <div>
          <p className="red">삭제하기</p>
        </div>
        
      </div>

    </div>
  })

  return (
    <div className="main">
      <div className="report-main-div">
        <div className="report-nav">
          <h1>{title}</h1>
          <div className="pow">
            <p>수정</p>
            <p className="red" onClick={deleteReport}>삭제</p>
          </div>
        </div>
        <hr />
        <div className="hr-div">
          <p> 작성일  {createAt}</p>
        </div>
        <div className="writer-div">
          <img src={defaultpic} />
          <p>{writer}</p>
        </div>
        <div className="content-div" dangerouslySetInnerHTML={{
          __html: markdownIt().render(content),
        }}
        ></div>
      </div>
      <div className="comment-div">
        <p className="comment-p">댓글  <span className="comment-number">{commentCnt}</span></p>
        <div>
          <textarea className="comment-box" maxLength={400} value={writecomment} onChange={onChangeComment}></textarea>
          <Button variant="contained" onClick={postComment}>작성하기</Button>
        </div>
      </div>
      {commentBox}
      
    </div>
  )
}

export default ReportDetailPage;