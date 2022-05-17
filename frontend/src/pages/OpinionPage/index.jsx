import React, { useState } from "react";
import { Outlet, Link, useParams } from 'react-router-dom';
import "./style.scss"

const OpinionPage = () => {

  const id = useParams().projectId;
  const a = window.location.href.split('/')
  const [option, setOption] = useState(a[a.length-1])

  const whichOption = (e) => {
    setOption(e.target.value);
    console.log(e.target.value);
  }

  return (
    <>
      <Link to={`/project/${id}`} style={{color: "black"}}>
        <p className="back-project">프로젝트로 돌아가기</p>
      </Link>
      <div className="opinion-center">
        <Link to={`/project/${id}/opinions/review`} className="opinion-link" >
          <button className={option === "review" ? "option-btn-on option-btn" : "option-btn"} onClick={whichOption} value="review">Review</button>
        </Link>
        <h1>|</h1>
        <Link to={`/project/${id}/opinions/report`} className="opinion-link" >
          <button className={option === "report" ? "option-btn-on option-btn" : "option-btn"} onClick={whichOption} value="report">Report</button>
        </Link>
      </div>
      <Outlet />
    </>
  )
}

export default OpinionPage;