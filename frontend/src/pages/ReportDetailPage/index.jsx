import React, {useEffect, useState} from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./style.scss"
import API from "../../api/API";
import moment from 'moment';
import defaultpic from '../../assets/default.png'
import markdownIt from "markdown-it";

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

  // 댓글기능
  const [indicomment, setIndiComment] = useState('')
  const [commentTrue, setCommentTrue] = useState(false)
  const [startWord, setStartword] = useState(-1)
  const [endWord, setEndword] = useState(-1)
  const [searchData, setSearchData] = useState([]);

  useEffect(() => {
    async function startReportDetail() {
      const res = await API.get(`/api/bug/detail/${reportId}`)
      setCommentCnt(res.data.bugReport.commentCnt)
      setComment(res.data.bugReport.comments)
      setTitle(res.data.bugReport.title)
      setContent(res.data.bugReport.content)
      setWriter(res.data.bugReport.nickname)
      setProfilepic(res.data.bugReport.profileImage)
      setCreateAt(moment(res.data.bugReport.createAt).format('YYYY년 MM월 DD일'))
      console.log(res.data.bugReport)
    }
    startReportDetail()
  },[kai,reportId])

  const deleteReport = async() => {
    const res = await API.delete(`/api/bug/${reportId}`)
    navigate(`/project/${projectId}/opinions/report`)
    console.log(res)
  }

  const postComment = async() => {
    let commentText = document.getElementById('commentText').value;
    await API.post(`/api/bug-comment/${reportId}`,{
      content : commentText
    })
    document.getElementById('commentText').value = ''
    setIndiComment('')
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
    const regex = /@.*[원|장]/
    // console.log(item.content.match(regex))
    // console.log(item.content.match(regex))
    let pingping = item.content
    let papa = []
    item.content.split(" ").map((Citem) => {
      if (Citem.match(regex)) {
        const piopio = Citem.match(regex)[0]
        // console.log(item.match(regex)[0])
        pingping = pingping.replaceAll(piopio,`<span id="call-red">${piopio}</span>`)
        
      }
      pingping = "<p>" + pingping + "</p>"
      console.log(pingping)
    })
    console.log(pingping)
    return <div className="report-detail-box-div">
      <div>
        <img className="report-detail-icon" src={(item.profileImage) ? item.profileImage : defaultpic} alt="profile" />
      </div>
      <div>
        {item.nickname} {item.createdAt}
        <div className="comment-content" id={item.commentId} dangerouslySetInnerHTML={{__html: pingping}}>
          
        </div>
        <div>
          <p className="report-detail-red" onClick={() => deleteComment(item.bugCoId)}>삭제하기</p>
        </div>
        
      </div>

    </div>
  })

  const checkTag = (event) => {
    if (!commentTrue || indicomment.includes('@')) {
      if (event.key=='@') {
        console.log('언급시작')
        setCommentTrue(true)

        setStartword(document.getElementById('commentText').selectionStart)
        console.log(startWord)
        

      }
    }
  }

  async function searchWord(word) {
    console.log(document.getElementById("commentText").value.slice(startWord+1,endWord))
    console.log("검색",word)
    const res  = await API.get(`/api/user-info/search?keyword=${word}`)
    setSearchData(res.data.searchList)
    const date = new Date();
    console.log(date)
    console.log("왜이게..?",document.getElementById("commentText").value.slice(startWord+1,endWord))
    console.log(res)
  }

  const onChangeComment = (e) => {
    setIndiComment(e.target.value)
    if (commentTrue === true) {
      console.log('트루는통과')
      if (document.getElementById('commentText').selectionStart) {
        setEndword(document.getElementById('commentText').selectionStart)
        if (document.getElementById("commentText").value.slice(startWord+1,endWord)) {
          console.log('들어왓음')
          console.log(document.getElementById("commentText").value.slice(startWord+1,endWord))
          searchWord(document.getElementById("commentText").value.slice(startWord+1,endWord))
        }
      }
    }
  }
  const searchMap = searchData.map((item) => {
    
    return <div className="search-indi-div">
      <p className="search-p" onClick={() => onClickSearch(item)}>{item}</p>
    </div>
  });
  const onClickSearch = (item) => {
    var changeComment = document.getElementById("commentText").value.replace(document.getElementById("commentText").value.slice(startWord+1,endWord), item + " ")
    document.getElementById("commentText").value = changeComment
    setIndiComment(changeComment)
    allCancel()
  }

  function allCancel() {
    setCommentTrue(false)
    setSearchData([])
  }

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
          <img src={(profilepic) ? profilepic : defaultpic} alt="writerProfilePic" />
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
          { !(searchData.length === 0) ?
            
            <div className="search-main-div">
              <p>{searchMap}</p>
            </div>

            : 
            null
          }
          <textarea id="commentText" value={indicomment} className="comment-box" maxLength={400} onKeyPress={checkTag} onChange={onChangeComment}></textarea>
          <button className="comment-submit" type="submit" onClick={postComment}>댓글 작성</button>
        </div>
      </div>
      {commentBox}
      
    </div>
  )
}

export default ReportDetailPage;