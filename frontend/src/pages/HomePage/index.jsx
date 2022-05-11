import React, { useState } from "react";
import { Link } from 'react-router-dom';
import API from "../../api/API";

const HomePage = () => {
  
  const [searchResult, setSerachResult] = useState('');

  const getProfile = () => {
    API.get("api/user")
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      })
  }

  const search = async(e) => {
    const value = e.target.value;
    const response = await API.get(`/api/project/search/title?keyword=${value}`)
    setSerachResult(response.data.searchList);
  }

  const enterSearch = async (e) => {
    const value = e.target.value;
    if (e.key === "Enter") {
      const res = await API.get(`/api/project//search?keyword=${value}`)
      setSerachResult(res.data.projectList);
      e.target.value = "";
      return;
    }
  }

  return (
    <>

      <h1>여기는 메인화면</h1>


      <Link to="/project/post">
        <button>지금 등록하기</button>
      </Link>

      <button onClick={getProfile}>프로필 조회</button>

      <br />
      <input type="text" onChange={e => search(e)} onKeyPress={enterSearch} />
      <br />
      {searchResult && searchResult.map((search) => (
        <>
          {search.title}
        </>
      ))}

    </>
  )
}

export default HomePage;