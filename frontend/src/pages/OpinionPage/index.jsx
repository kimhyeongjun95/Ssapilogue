import React, { useState } from "react";
import { Outlet, Link, useParams } from 'react-router-dom';
import "./style.scss"

const OpinionPage = () => {

  const id = useParams().projectId;
  const [option, setOption] = useState("Review")

  const whichOption = (e) => {
    setOption(e.target.value);
    console.log(e.target.value);
  }

  return (
    <>
      <div className="opinion-center">
        <Link to={`/project/${id}/opinions/review`} className="opinion-link" >
          <button className={option === "Review" ? "option-btn-on option-btn" : "option-btn"} onClick={whichOption} value="Review">Review</button>
        </Link>
        <h1>|</h1>
        <Link to={`/project/${id}/opinions/report`} className="opinion-link" >
          <button className={option === "Report" ? "option-btn-on option-btn" : "option-btn"} onClick={whichOption} value="Report">Report</button>
        </Link>
      </div>
      <Outlet />
    </>
  )
}

export default OpinionPage;