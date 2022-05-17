import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import API from "../../api/API";
import SelectTitleStack from "../../components/Select/TitleStack.jsx"
import SelectType from '../../components/Select/Type.jsx'
import Grid from '@mui/material/Grid';
import { Chip } from "@mui/material"
import Card from "../../components/Card"
import Slide1 from "../../assets/Slide1.png"
import Slide2 from "../../assets/Slide2.png"
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import "./style.scss"

const HomePage = () => {
  
  const [searchResult, setSearchResult] = useState([]);
  const [entireResult, setEntireResult] = useState([]);
  const [dropResult, setDropResult] = useState('');
  const [techSearchResult, setTechSearchResult] = useState('');

  const [searchOption, setSearchOption] = useState("제목");
  const [typeOption, setTypeOption] = useState("");

  const settings = {
    dots: true,
    infinite: true,
    autoplay: true,
    speed: 550,
    arrows: false,
    autoplaySpeed: 2000,
    slidesToShow: 1,
    slidesToScroll: 1,
  };  

  const handleSearchOption = (e) => {
    setSearchOption(e.target.value);
  }

  const handleTypeOption = (e) => {
    setTypeOption(e.target.value);
  }

  const handlePopular = (e) => {
    const value = e.target.innerText;

    if (value === "인기순") {
      const result = entireResult.sort((a, b) => b.likeCnt - a.likeCnt)
      setSearchOption(result);
      return;
    }
    
    if (value === "최신순") {
      setSearchResult(entireResult)
      return;
    }
  }

  const titleEnterSearch = async (e, value) => {
    const response = await API.get(`/api/project//search?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    e.target.value = "";
    setEntireResult(response.data.projectList);
    typeFilter();
  }

  const titleAutoSearch = async (value) => {
    const response = await API.get(`/api/project/search/title?keyword=${value}`);
    setDropResult(response.data.searchList);
    setEntireResult(response.data.searchList);
    typeFilter();
  }

  const techProjectAutoSearch = async (value) => {
    const response = await API.get(`/api/tech-stack/search/title?keyword=${value}`);
    setDropResult(response.data.searchList);
    setEntireResult(response.data.searchList);
    typeFilter();
  }

  const techStackAutoSearch = async (value) => {
    const response = await API.get(`/api/tech-stack/search/specific?keyword=${value}`);
    setTechSearchResult(response.data.searchList);
    setEntireResult(response.data.searchList);
    typeFilter();
  }

  const techProjectEnterSearch = async (e, value) => {
    const response = await API.get(`/api/tech-stack/search/project?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    setTechSearchResult('');
    e.target.value = "";
    setEntireResult(response.data.projectList);
    typeFilter();
  }

  const techStackClickSearch = async (e) => {
    const value = e.target.innerText;
    const response = await API.get(`/api/tech-stack/search/specific/project?keyword=${value}`);
    setSearchResult(response.data.projectList);
    setDropResult('');
    setTechSearchResult('');
    setEntireResult(response.data.projectList);
    typeFilter();
  }

  const initialSearch = async () => {
    const response = await API.get('api/project')
    setSearchResult(response.data.projectList)
    setEntireResult(response.data.projectList);
    typeFilter();
  }

  const search = async(e) => {
    const value = e.target.value;
    try {
      if (searchOption === "제목") {
        setTechSearchResult('');
        if (e.key === "Enter") {
          titleEnterSearch(e, value);
          return;
        }
        titleAutoSearch(value);
        return;
      }

      if (searchOption === "기술스택") {
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

  const typeFilter = () => {
    if (typeOption === "전체") {
      setSearchResult(entireResult);
    }

    if (typeOption === "공통") {
      const result = entireResult.filter(search => search.category === "공통")
      setSearchResult(result);
    }

    if (typeOption === "특화") {
      const result = entireResult.filter(search => search.category === "특화")
      setSearchResult(result);
    }

    if (typeOption === "자율") {
      const result = entireResult.filter(search => search.category === "자율")
      setSearchResult(result);
    }
  }

  useEffect(() => {
    initialSearch();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    typeFilter();
  }, [typeOption])

  return (
    <>

      <Slider {...settings}>
        <img className="slide-image" src={Slide1} alt="slide1" />
        <img className="slide-image" src={Slide2} alt="slide2" />
      </Slider>

      <div className="home-body">     
        <Link to="/project/post" >
          <button className="post-project-btn">
            지금 등록하기
          </button>
        </Link>

        <div className="home-search">
          <SelectTitleStack defaultValue="" onChange={handleSearchOption} option={searchOption} />
          <div style={{ width : "100%" }}>
            <input className="home-search-input" placeholder="🔍 검색" type="text" onChange={e => search(e)} onKeyPress={(e) => search(e)} />
            { (dropResult.length || techSearchResult.length) ?
              <div className="home-search-main">
                <div className="home-search-label">프로젝트</div>
                <div className="home-search-title">

                  {dropResult && dropResult.map((search, idx) => (
                    <div style={{ marginBottom : "5px", fontWeight : 'bold', color : '#484848' }} key={idx}>
                      <Link to={`project/${search.projectId}`} className="card-link">
                        {search.title}
                      </Link>
                    </div>
                  ))}

                </div>
                <div className="home-search-label">스킬태그</div>
                <div className="home-search-tech">

                  {techSearchResult && techSearchResult.map((tech, idx) => (
                    <span key={idx}>
                      <Chip
                        onClick={techStackClickSearch}
                        style={{ height : "24px", margin : "5px 3px", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
                        label={tech} 
                      />
                    </span>
                  ))}

                </div>
              </div>
              :
              null
            }
          </div>
        </div>

        <div className="home-body-title">
          우리 모두의 프로젝트
        </div>

        <div className="home-body-content">
          다른 개발자들의 프로젝트를 한번 볼까요?
        </div>
        
        <div className="home-card-option">
          <div className="home-card-sort">
            <div onClick={handlePopular}>최신순</div>
            <div onClick={handlePopular}>인기순</div>
          </div>
          <SelectType defaultValue="" onChange={handleTypeOption} option={typeOption}  />
        </div>

        <div className="cards-grid">
          <Grid container>
            {searchResult && searchResult.map((search, idx) => (
              <Grid item xl={4} md={6} sm={12} key={idx}>
                <div className="home-card" >
                  <Link to={`project/${search.projectId}`} className="card-link">
                    <Card
                      title={search.title} 
                      content={search.introduce}
                      category={search.category}
                      likeCnt={search.likeCnt}
                      viewCnt={search.hits}
                      commentCnt={search.commentCnt}
                      techStack={search.techStack}
                      thumbnail={search.thumbnail}
                      bookmark={search.isBookmarked}
                    />  
                  </Link>
                </div>
              </Grid>
            ))}
          </Grid>
          
        </div>
      </div>
    </>
  )
}

export default HomePage;