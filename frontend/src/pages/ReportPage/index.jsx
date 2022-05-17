import React, {useState,useEffect} from "react";
import edit from "../../assets/Edit-alt.png"
import "./style.scss"
import { Link, useNavigate, useParams } from "react-router-dom";
import API from "../../api/API";
import store from "../../utils/store";
import moment from 'moment';

const ReportPage = () => {
  const [board, setBoard] = useState([0,0,0]);
  const [bugList,setBugList] = useState([]);
  const [projectOwner, setProjectOwner] = useState(false)
  const id = useParams().projectId;
  const token = store.getToken()
  let navigate = useNavigate();

  useEffect( () => {
    async function bugrecall() {
      let myEmail = ''
      let ptEmail = ''

      const res = await API.get(`/api/bug/${id}`);
      setBoard([res.data.bugList["totalCount"],res.data.bugList["solvedCount"],res.data.bugList["unsolvedCount"]])
      setBugList(res.data.bugList['bugReports'])
      // 프로젝트와 내가 같은 사용자인지 비교
      const response = await API.get('/api/user', { header: token })
      myEmail = response.data.user.email
      const prores = await API.get(`/api/project/${id}`)
      ptEmail = prores.data.project.email
      if (myEmail === ptEmail) {
        setProjectOwner(true)
      }
    }
    bugrecall()
    return
  },[id])

  const inputClick = (item) => {
    async function isSolve() {
      store.getToken()
      const res = await API.post(`/api/bug/solved/${item}`)
      let [a1,a2,a3] = board
      var bgChangeDiv = document.getElementById(`${item}`)
      if (res.data.isSolved) { 
        bgChangeDiv.className = "black-item-div"
        setBoard([a1,a2+1,a3-1])
      }else{
        bgChangeDiv.className = "white-item-div"
        setBoard([a1,a2-1,a3+1])
      }
    }
    isSolve()
  }
  const bugClick = (item) => {
    navigate(`${item}`)
  }
  const bugBox = bugList.map((item,index) => {
    let bgdiv = "white-item-div"
    let pio = true
    if (item.isSolved){
      bgdiv = "black-item-div"
      pio = true
    }else{
      bgdiv = "white-item-div"
      pio = false
    }
    

    return <div className={bgdiv} id={item.bugId}>
      <div className="menu-solved">
        { (projectOwner) ?
          <input type="checkbox" defaultChecked={pio} onClick={() => inputClick(item.bugId)} size="big"></input>
          : <input type="checkbox" disabled="disabled" defaultChecked={pio} onClick={() => inputClick(item.bugId)} size="big"></input>
        }
      </div>
      <p className="menu-title" onClick={() => bugClick(item.bugId)}>{item.title}</p>
      <p className="menu-date">{moment(item.createAt).format('YYYY년 MM월 DD일')}</p>
      <p className="menu-writer">{item.nickname}</p>
     
    </div>
  })



  return (
    <>
      <div className="report-nav-div">
        <div className="nav-div"/>
      </div>

      <div className="solve-div">
        <div>
          <p>전체</p>
          <h2 className="solve-h2">{board[0]}</h2>
        </div>
        <div>
          <p>해결</p>
          <h2 className="solve-h2">{board[1]}</h2>
        </div>
        <div>
          <p>미해결</p>
          <h2 className="solve-h2">{board[2]}</h2>
        </div>
      </div>
      <br />
      <div className="wirte-pic-div">
        <Link 
          to={'post'}
        >
          <img className="write-pic" src={edit} alt="write" />
        </Link>
      </div>
      <div className="report-box-div">
        <div className="report-menu-div">
          <p className="menu-solved">해결</p>
          <p className="menu-title2">제목</p>
          <p className="menu-title3">날짜</p>
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