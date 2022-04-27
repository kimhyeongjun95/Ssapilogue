import React from "react";
import { Link } from 'react-router-dom';
import API from "../../api/API";

const HomePage = () => {
  
  const getProfile = () => {
    API.get("api/user")
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      })
  }

  return (
    <div>
      <h1>여기는 메인화면</h1>
      <Link to="/project/post">
        <button>지금 등록하기</button>
      </Link>
      <button onClick={getProfile}>프로필 조회</button>
    </div>
  )
}

export default HomePage;