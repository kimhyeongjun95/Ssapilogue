import './style.scss'
import notion from "../../assets/notion.png"
import git from "../../assets/git.png"
const Footer = () => {

  return (
    <><hr className="hr"></hr><footer>
      <div className="content">
        <h5 className="footer-content-1">@ 2022 ProPolice. from</h5> 
        <h5 className="footer-content-2"> SSAFY</h5>
        <h5>Created By 김은서, 김형준, 정동균, 최강현, 하현서</h5>
      </div>
      <div className="image">
        <a href="https://satisfying-starfish-993.notion.site/dd785428616e47d69512de7cf90003c4">
          <img className="icon" src={notion} alt="notion" />
        </a>
        <a href="https://lab.ssafy.com/s06-final/S06P31C104">
          <img className="icon" src={git} alt="git" />
        </a>
      </div>
    </footer></>
  )
}
export default Footer;