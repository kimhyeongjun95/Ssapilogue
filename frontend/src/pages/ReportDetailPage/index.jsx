import React, {useEffect, useState} from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./style.scss"
import API from "../../api/API";
import moment from 'moment';

const ReportDetailPage = () => {
  const reportId = useParams().reportId
  const projectId = useParams().projectId
  //상태관리 hook

  const [commentCnt, setCommentCnt] = useState('');
  const [comment, setComment] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [writer, setWriter] = useState('');
  const [profilepic, setProfilepic] = useState('');
  const [createAt, setCreateAt] = useState('');

  useEffect(async()=> {
    const res = await API.get(`/api/bug/detail/${reportId}`)
    setCommentCnt(res.data.bugReport.commentCnt)
    setComment(res.data.bugReport.comments)
    setTitle(res.data.bugReport.title)
    setContent(res.data.bugReport.content)
    setWriter(res.data.bugReport.writer)
    setProfilepic(res.data.bugReport.profileImage)
    setCreateAt(moment(res.data.bugReport.createAt).format('YYYY년 MM월 DD일 HH시'))
    console.log(res.data.bugReport)
  },[])
  return (
    <>
      <div className="report-main-div">
        <div className="report-nav">
          <h1>{title}</h1>
          <p></p>
        </div>
        
        {reportId}
        {projectId}
        {createAt}
      </div>
      
    </>
  )
}

export default ReportDetailPage;