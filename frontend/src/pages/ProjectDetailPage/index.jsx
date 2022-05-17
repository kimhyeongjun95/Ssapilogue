import React, {useEffect,useState} from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import detailImage from "../../assets/detailImage.png"
import "./style.scss"
import constructionPic from "../../assets/construction.png"
import projectPeoplePic from "../../assets/proejectPeople.png"
import letsUp from "../../assets/letsUp.svg"
import bookmarkPic from "../../assets/bookmark.svg"
import likePic from "../../assets/thumb.svg"
import alLikePic from "../../assets/thumbColor.svg"
import alBookmark from "../../assets/bookmarkColor.svg"
import deafultProfilePic from  "../../assets/default.png"
import gitRepo from "../../assets/git.png"
import google from "../../assets/Google.png"
import report from "../../assets/report.png"
import API from "../../api/API";
import store from "../../utils/store";
import markdownIt from "markdown-it";
import swal from 'sweetalert';
import Card from "../../components/SmallCard"

//mui  디자인 라이브러리
import Grid from '@mui/material/Grid';
import {Button} from "@mui/material"
//pdf
import { jsPDF } from "jspdf";
import html2canvas from "html2canvas";


const DetailPage = () => {
  const id = useParams().projectId;
  let navigate = useNavigate();
  const token  = store.getToken()
  
  // 사용자 관리
  const [myEmail, setMyEmail] = useState('');
  // 변수관리 hook
  const [category, setCategory] = useState('');
  const [title, setTitle] = useState('');
  const [stack, setStack] = useState([]);
  const [member, setMember] = useState([]);
  const [authormember, setAuthorMember] = useState([]);
  const [commentCnt, setCommentCnt] = useState('');
  const [comment, setComment] = useState([]);
  const [thumbnail, setThumbnail] = useState('');
  const [repo, setRepo] = useState('');
  const [bepo, setBepo] = useState(''); 
  const [readme, setReadme] = useState('');
  const [kai, setKai] = useState(0);
  const [intro, setIntro] = useState('');
  const [isliked, setIsliked] = useState(false);
  const [isbookmarked, setIsbookmarked] = useState(false);
  const [otherProject, setOtherProejct] = useState([]);

  // 댓글기능
  const [indicomment, setIndiComment] = useState('')
  const [commentTrue, setCommentTrue] = useState(false)
  const [startWord, setStartword] = useState(-1)
  const [endWord, setEndword] = useState(-1)
  const [searchData, setSearchData] = useState([]);
  const [isWriter, setIsWriter] = useState(false);


  useEffect(() => {
    let myEmail = ''
    let ptEmail = ''

    async function projectCall() {
      
      const response = await API.get('/api/user', { header: token })
      myEmail = response.data.user.email
      setMyEmail(response.data.user.email)

      const res = await API.get(`/api/project/${id}`)
      setCategory(res.data.project.category)
      setTitle(res.data.project.title)
      setStack(res.data.project.techStack)
      setMember(res.data.project.anonymousMember)
      setCommentCnt(res.data.project.commentCnt)
      setComment(res.data.project.comment)
      setRepo(res.data.project.gitAddress)
      setBepo(res.data.project.deployAddress)
      setReadme(res.data.project.readme)
      setAuthorMember(res.data.project.member)
      setIntro(res.data.project.introduce)
      setThumbnail(res.data.project.thumbnail)
      setIsliked(res.data.project.isLiked)
      setIsbookmarked(res.data.project.isBookmarked)
      setOtherProejct(res.data.project.anotherProjects)
      console.log(res)
      ptEmail = res.data.project.email
      if (myEmail === ptEmail) {
        setIsWriter(true)
      }
    }
    projectCall()

  },[kai,id])
  

  const writeComment = async() => {
    try {
      await API.post(`/api/project-comment/${id}`,{
        content : indicomment,
      })
      setIndiComment('')
      setKai(kai + 1)
    } catch(e) {
      throw e;
    }
  }

  const deleteComment = async(item) => {
    try {
      await API.delete(`/api/project-comment/${item}`)
      setKai(kai + 1)
    } catch(e) {
      throw e;
    }

  }


  const commentBox = comment.map((item) => {
    const regex = /@.*[원|장]/

    let pingping = item.content

    item.content.split(" ").forEach((Citem) => {
      if (Citem.match(regex)) {
        const piopio = Citem.match(regex)[0]
        pingping = pingping.replaceAll(piopio,`<span id="call-red">${piopio}</span>`)
        
      }
      pingping = "<p>" + pingping + "</p>"
    })

    return <div className="box-div">
      <div>
        { (item.profileImage) ?
          <img className="comment-image" src={item.profileImage} alt="profile" />
          :
          <img className="comment-image" src={deafultProfilePic} alt="profile" />
        }
      </div>
      <div className="comment-content-box">
        <div className="comment-nickname-box">
          <div className="comment-nickname">
            {item.nickname}
          </div>
          <div className="comment-created" >
            {item.createdAt}
          </div>
        </div>
        <div className="comment-content" id={item.commentId} dangerouslySetInnerHTML={{__html: pingping}}>
          
        </div>
        { (myEmail === item.email) ?
          <div>
            <p className="project-detail-red" onClick={() => deleteComment(item.commentId)}>삭제하기</p>
          </div>
          :
          null
        }
      </div>

    </div>
  })
  const deleteProject = async() => {
    swal({
      title: "프로젝트 삭제",
      text: "정말 프로젝트를 삭제하시겠습니까?",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          
          swal("프로젝트가 성공적으로 삭제되었습니다.", {
            icon: "success",
          });
          store.getToken();
          API.delete(`/api/project/${id}`)
          navigate('/')
        } else {
          swal("당신의 프로젝트가 살아남았습니다.");
        }
      });
      
  }


  const stackBox = stack.map((item) => {
    return <Button variant="contained" style={{margin:"1px 5px 1px 0", height: "32px", backgroundColor : "#3396F4", color:'white', fontFamily: 'GmarketSansMedium'}}
      
    >{item}</Button>
  })

  const memberBox = member.map((item) => {
    return <Button variant="contained" style={{margin:"1px 5px 1px 0", height: "32px", backgroundColor : "#00CAF4", color:'white', fontFamily: 'GmarketSansMedium'}}
    >{item}
    </Button>
  })
  
  const goUp = () => {
    window.scrollTo(0,0);
  }

  const projectLike = async() => {
    if (isliked) {
      await API.delete(`/api/project/${id}/like`)
      setIsliked(false)
    }else{
      await API.post(`/api/project/${id}/like`)
      setIsliked(true)
    }
  }

  const projectBookmark = async() => {
    
    if (isbookmarked) {
      await API.delete(`/api/project/${id}/bookmark`)
      setIsbookmarked(false)

    }else{
      await API.post(`/api/project/${id}/bookmark`)
      setIsbookmarked(true)
    }
  }


  const onClickSearch = (item) => {
    var changeComment = document.getElementById("commentText").value.replace(document.getElementById("commentText").value.slice(startWord+1,endWord), item + " ")
    document.getElementById("commentText").value = changeComment
    setIndiComment(changeComment)
    allCancel()
  }

  function allCancel() {
    setCommentTrue(false)
    setSearchData([])
  }

  const onChangeComment = (e) => {
    setIndiComment(e.target.value)
    if (commentTrue === true) {

      if (document.getElementById('commentText').selectionStart) {
        setEndword(document.getElementById('commentText').selectionStart)
        if (document.getElementById("commentText").value.slice(startWord+1,endWord)) {
          searchWord(document.getElementById("commentText").value.slice(startWord+1,endWord))
        }
      }
     
    }
    
    
  }


  const checkTag = (event) => {
    if (!commentTrue || indicomment.includes('@')) {
      if (event.key==='@') {
        setCommentTrue(true)
        setStartword(document.getElementById('commentText').selectionStart)
      }
    }
  }

  const receiveReadme = async() => {
    try {
      await API.put(`/api/project/${id}/readme`)
      swal("갱신 완료","Read Me가 성공적으로 갱신되었습니다!","success")
    }catch(e) {
      console.log(e)
    }
  }

  async function searchWord(word) {
    const res  = await API.get(`/api/user-info/search?keyword=${word}`)
    setSearchData(res.data.searchList)
  }

  

  const printDocument = () => {
    html2canvas(document.getElementById("readme"), { 
      loggin : true,
      letterRendering: 1,
      allowTaint: false,
      useCORS: true
    }).then((canvas) => { 
      var doc = new jsPDF('p', 'mm', 'a4'); 
      var imgData = canvas.toDataURL('image/png'); 
      var imgWidth = 210; var pageHeight = 295; 
      var imgHeight = canvas.height * imgWidth / canvas.width; 
      var heightLeft = imgHeight; 
      var position = 0; doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight); 
      heightLeft -= pageHeight; 
      while (heightLeft >= 0) { 
        position = heightLeft - imgHeight; 
        doc.addPage(); 
        doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight); 
        heightLeft -= pageHeight; 
      } 
      doc.save('download.pdf'); 
    });

  }

  // 언급 검색결과
  const searchMap = searchData.map((item) => {
    
    return <div className="search-indi-div">
      <p className="search-p" onClick={() => onClickSearch(item)}>{item}</p>
    </div>
  });
  
  const noneBepo = () => {
    swal("데모 사이트", "배포서버가 준비되어 있지 않습니다.", "error")
  }

  const whichOne = () => {
    swal("리뷰와 버그리포트중 무엇부터 확인하고 싶으세요?", {
      buttons: {
        review: {
          text: "리뷰",
          value: "review",
        },
        bugreport: {
          text: "버그 리포트",
          value: "bugreport",
        },
      },
    })
      .then((value) => {
        switch (value) {
          
        case "bugreport":
          navigate(`/project/${id}/opinions/report`)
          break;
     
        case "review":
          navigate(`/project/${id}/opinions/review`)
          break;
     
        }
      });
  }
  
  return (
    <>
      { (token) ? 
        <div className="project-div">
          { (thumbnail) ?
            <img className="thumbImage" src={thumbnail} alt="detailImage" />
            :
            <img className="detailImage" src={detailImage} alt="detailImage" />
          }

          <div className="project-body-div">
            <div className="title-div">
              <div className="project-part">{category}</div>
              <h2>{title}</h2>
            </div>
            <div className="stack-div">
              <span className="stack">
                <img className="icon" src={constructionPic} alt="conpic" />
                {stackBox}
              </span>
              { (isWriter) ?
                <span className="option-div">
                  <div className="option-category-readme" onClick={receiveReadme}>README 갱신</div>
                  <div className="option-category">
                    <Link 
                      className="to-edit" 
                      to="edit"
                      state={{
                        editTitle : title,
                        editCategory: category,
                        editStack: stack,
                        editMember: member,
                        editRepo : repo,
                        editBepo : bepo,
                        editReadme : readme,
                        edittAuthormember : authormember,
                        editIntro : intro,
                        editThumbnail : thumbnail
                      }}
                    >
                      수정
                    </Link>
                  </div>
                  <div onClick={deleteProject} className="option-category-red">삭제</div>
                </span>
                :
                null
              }
            </div>

            <div className="member-div">
              <span className="stack">
                <img className="icon" src={projectPeoplePic} alt="projectPeoplePic" />
                {memberBox}
              </span>
            </div>

            <div className="git-div">
              <a href={repo} rel="noreferrer" target='_blank' className="link-a">
                <img className="icon" src={gitRepo} alt="gitRepo" />
                <span>
                  Git Repo
                </span>
              </a>
              { (bepo) ?
                <a href={bepo} rel="noreferrer" target='_blank' className="link-a">
                  <img className="icon" src={google} alt="google" />
                  <span>
                    Demo Site
                  </span>
                </a>
                : 
                <span onClick={noneBepo} rel="noreferrer" target='_blank' className="link-a">
                  <img className="icon" src={google} alt="google" />
                  <span>
                    Demo Site
                  </span>
                </span>
              }
              {/* <Link 
                to={`/project/${id}/opinions/review`}
                className="link-a"
              > */}
              <span className="which-one" onClick={whichOne} >
                <img className="icon" src={report} alt="report" />
                <span>리뷰·버그 리포트</span>
                {/* </Link> */}
              </span>
            </div>
            <div>
              <button onClick={printDocument}>Print</button>
            </div>
            <div id="readme" className="readme-div"dangerouslySetInnerHTML={{
              __html: markdownIt().render(readme),
            }}
            ></div>
            <div className="recommend-project">
              <Grid container>
                {otherProject && otherProject.map((search, idx) => (
                  <Grid container item xl={4} md={6} sm={12} key={idx}>
                    <div className="home-card" >
                      <Link to={`/project/${search.projectId}`} className="card-link">
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
            <div className="comment-div">
              
              {/* {indicomment.slice(startWord+1)} */}
              <p className="comment-p">댓글  <span className="comment-number">{commentCnt}</span></p>
              { !(searchData.length === 0) ?
                
                <div className="search-main-div">
                  <p>{searchMap}</p>
                </div>

                : 
                null
              }
              <div>
                <textarea id="commentText" value={indicomment} onKeyPress={checkTag} onChange={onChangeComment} className="comment-box" maxLength={400}></textarea>
                <button className="comment-submit" type="submit" onClick={writeComment}>댓글 작성</button>
              </div>
            </div>
            {commentBox}
            
          </div>
          <div className="project-remote-controll">
            <div className="remote-div">
              <img onClick={goUp} src={letsUp} alt="likePic" />
              <img onClick={projectLike} src={(isliked) ? alLikePic : likePic} alt="likePic" />
              <img onClick={projectBookmark} src={(isbookmarked) ? alBookmark : bookmarkPic} alt="likePic" />
            </div>

          </div>
        </div>
        :
        <div>
          <h1>로그인하라고 이씨</h1>
        </div>
        
      }
    </>
  )
}

export default DetailPage;