import React,{useState} from "react";
import { Card,CardContent,CardMedia,Typography,CardActionArea } from "@mui/material"
import "./style.scss"
import profilePic from "../../assets/profileDefault.jpg"
import detailImage from "../../assets/detailImage.png"

const ProfilePage = () => {
  const [i,setI] = useState(2)
  const [bmi, setBmi] = useState(2)
  

  const user = {
    "email" : "besthyun@gmail.com",
    "nickname": "최강현[광주_1반_C104]",
    "github": "id",
    "greeting": "최강의 코드를 짭니다.",
    "image": profilePic,
    "userLiked" : 2048,
    "projects": [
      {
        "projectId": 1,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 2,
        "title": "강현키",
        "introduce": "188의 초 톨 미남입니다만!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 3,
        "title": "와이키키",
        "introduce": "최고의 휴양지입니다만...당신들이 뭘 알겠어 가보기나 햇겟어 어이가없어가지고!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      }
      ,{
        "projectId": 4,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 5,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 6,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 7,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring" 
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      }
    ],
    "bookmarkList": [
      {
        "projectId": 1,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 2,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 3,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 4,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 5,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 6,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },
      {
        "projectId": 7,
        "title": "라이키",
        "introduce": "자전거 프로젝트입니다!",
        "category": "자율",
        "techStack": [
          "ReactNative",
          "Spring"
        ],
        "thumbnail": "http://k6c104.p.ssafy.io/images/projectImg/b0cf5863-8ab5-4c4b-8b93-62bc634abb5b_개발짤.jpg",
        "hits": 7,
        "likeCnt": 1,
        "commentCnt": 0
      },

    ]
  }
  const [myproject, setMyproject] = useState(user["projects"].slice(0,3))
  
  const [mybmProject, setMybmProject] = useState(user["bookmarkList"].slice(0,3))

  const myp = myproject.map((item,key) => {
    return <Card style={{width: "24%", margin:"1%"}} sx={{ maxWidth: "35%" }}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image={profilePic}
          alt="green iguana"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {item.title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {item.introduce}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  })

  const bmyp = mybmProject.map((item) => {
    return <Card style={{width: "24%", margin:"1%"}} sx={{ maxWidth: "35%" }}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image={detailImage}
          alt="green iguana"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {item.title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {item.introduce}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  })

  const power = () => {
    
    setMyproject(user["projects"].slice(0,3*i))
    setI(i+1)
    console.log(myproject)
    console.log(mybmProject)
  }

  const bmpower = () => {
    setMybmProject(user["bookmarkList"].slice(0,3*bmi))
    setBmi(bmi+1)
  }
  
  return (
    <div>
      <div className="profile-div">
        <div className="profile-box">
          <div className="img-div">
            <p> 좋아요 : {user.userLiked}개</p>
            <img className="profile-pic" src={profilePic} alt="profilePic" />
          </div>
          <div className="introduce-div">
            <p>이름 : {user.nickname}</p>
            <p>이메일 : {user.email}</p>
            <p>github : {user.github}</p>
            <p>자기소개 : {user.greeting}</p>
          </div>
        </div>
      </div>
      <div className="my-project-div">
        <h2 className="my-post-h">내가 포스팅한 프로젝트</h2>
        <div className="card-div">
          {myp}
        </div>
        {
          myproject.length === user["projects"].length ?
          
            null
            : <button onClick={power}>더보기</button>
        }
      </div>

      <div className="my-project-div">
        <h2 className= "my-post-h">내가 북마크한 프로젝트</h2>
        <div className="card-div">
          {bmyp}
        </div>
        {
          mybmProject.length === user["bookmarList"]?.length ?
          
            null
            : <button onClick={bmpower}>더보기</button>
        }
      </div>
    </div>
  )
}

export default ProfilePage;