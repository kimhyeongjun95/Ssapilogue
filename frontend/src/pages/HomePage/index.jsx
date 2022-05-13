import React, { useState } from "react";
import { Link } from 'react-router-dom';
import API from "../../api/API";
import Select from "../../components/Select"
import Card from "../../components/Card"
import Slide1 from "../../assets/Slide1.png"
import Slide2 from "../../assets/Slide2.png"
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import "./style.scss"

const HomePage = () => {
  
  const [searchResult, setSearchResult] = useState('');
  const [dropResult, setDropResult] = useState('');
  const [techSearchResult, setTechSearchResult] = useState('');
  const [option, setOption] = useState('제목');

  const settings = {
    dots: true,
    infinite: true,
    autoplay: true,
    speed: 550,
    autoplaySpeed: 2000,
    slidesToShow: 1,
    slidesToScroll: 1,
  };  

  const handleOption = (e) => {
    setOption(e.target.value);
  }

  const titleEnterSearch = async (e, value) => {
    const response = await API.get(`/api/project//search?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    e.target.value = "";
  }

  const titleAutoSearch = async (value) => {
    const response = await API.get(`/api/project/search/title?keyword=${value}`);
    setDropResult(response.data.searchList);
  }

  const techProjectAutoSearch = async (value) => {
    const response = await API.get(`/api/tech-stack/search/title?keyword=${value}`);
    setDropResult(response.data.searchList);
  }

  const techStackAutoSearch = async (value) => {
    const response = await API.get(`/api/tech-stack/search/specific?keyword=${value}`);
    setTechSearchResult(response.data.searchList);
  }

  const techProjectEnterSearch = async (e, value) => {
    const response = await API.get(`/api/tech-stack/search/project?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    setTechSearchResult('');
    e.target.value = "";
  }

  const techStackClickSearch = async (e) => {
    const value = e.target.innerText;
    const response = await API.get(`/api/tech-stack//search/specific/project?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    setTechSearchResult('');
  }

  const search = async(e) => {
    const value = e.target.value;
    try {
      if (option === "제목") {
        setTechSearchResult('');
        if (e.key === "Enter") {
          titleEnterSearch(e, value);
          return;
        }
        titleAutoSearch(value);
        return;
      }

      if (option === "기술스택") {
        if (e.key === "Enter") {
          techProjectEnterSearch(e, value);
          return;
        }
        techProjectAutoSearch(value);
        techStackAutoSearch(value);
        return;
      }
    } catch (e) {
      throw e;
    }
  }

  return (
    <>

      <Slider {...settings}>
        <img className="slide-image" src={Slide1} alt="slide1" />
        <img className="slide-image" src={Slide2} alt="slide2" />
      </Slider>

      <Link to="/project/post" className="post-project-btn">
        <button>지금 등록하기</button>
      </Link>

      <br />
      <Select onChange={handleOption} option={option} />
      <input type="text" onChange={e => search(e)} onKeyPress={(e) => search(e)} />
      <br />
      <h3>프로젝트 미리보기 검색 결과</h3>
      {dropResult && dropResult.map((search, idx) => (
        <div key={idx}>
          {search.title}
        </div>
      ))}
      <h3>기술 검색 결과</h3>
      {techSearchResult && techSearchResult.map((tech, idx) => (
        <div key={idx}>
          <h6 onClick={techStackClickSearch}>
            {tech}
          </h6>
        </div>
      ))}

      <h3>프로젝트 최종 검색 결과</h3>
      <div className="cards-grid">
        {searchResult && searchResult.map((search, idx) => (
          <div key={idx}>
            <Card
              title={search.title} 
              content={search.introduce}
              category={search.category}
              likeCnt={search.likeCnt}
              viewCnt={search.hits}
              commentCnt={search.commentCnt}
              techStack={search.techStack}
              thumbnail={search.thumbnail}
            />
          </div>
        ))}
      </div>
    </>
  )
}

export default HomePage;