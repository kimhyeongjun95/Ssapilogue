import React from "react";
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div>
      <h1>여기는 메인화면</h1>
      <Link to="/project/post">
        <button>지금 등록하기</button>
      </Link>
    </div>
  )
}

export default HomePage;