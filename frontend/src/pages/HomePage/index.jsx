import React, { useState } from "react";
import { Link } from 'react-router-dom';
import API from "../../api/API";
import Select from "../../components/Select"

const HomePage = () => {
  
  const [searchResult, setSerachResult] = useState('');
  const [option, setOption] = React.useState('');

  const getProfile = () => {
    API.get("api/user")
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      })
  }

  const handleOption = (e) => {
    setOption(e.target.value);
    console.log(option);
  }

  // 엔터 검색은 실제 프로젝트 보여주기
  // 자동 완성은 드롭다운
  const titleEnterSearch = async (e, value) => {
    const res = await API.get(`/api/project//search?keyword=${value}`)
    setSerachResult(res.data.projectList);
    e.target.value = "";
    console.log("제목 엔터검색")
  }
  const titleAutoSearch = async (value) => {
    const response = await API.get(`/api/project/search/title?keyword=${value}`)
    setSerachResult(response.data.searchList);
    console.log("제목 자동검색")
  }

  const search = async(e) => {
    const value = e.target.value;
    try {
      if (option === "제목") {
        if (e.key === "Enter") {
          titleEnterSearch(e, value);
          return;
        }
        titleAutoSearch(value);
        return;
      }

      // if (option === "기술스택") {
        
      // }
    } catch (e) {
      throw e;
    }
  }



  // 기술스택 검색 자동완성
  // 1. 기술스택 검색 => 프로젝트 제목 나옴
  // 2. 기술스택 이름의 자동완성

  // 실제 검색(엔터 or 클릭)
  // 1. 엔터 : 기술스택 검색 => 실제 프로젝트 카드 나옴 (react, react native 중복 O)
  // 2. 클릭 : 기술스택 클릭 => 이 기술과 관련된 것만 (react, react native 중복 X)

  // 

  return (
    <>

      <h1>여기는 메인화면</h1>


      <Link to="/project/post">
        <button>지금 등록하기</button>
      </Link>

      <button onClick={getProfile}>프로필 조회</button>

      <br />
      <Select onChange={handleOption} option={option} />
      <input type="text" onChange={e => search(e)} onKeyPress={(e) => search(e)} />
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