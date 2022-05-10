import './style.scss'
import notion from "../../assets/notion.png"
import git from "../../assets/git.png"
const Footer = () => {

  return (
    <><hr className="hr"></hr><footer>
      <div className="title">
        <h6>SSapilogue</h6>
      </div>
      <div className="content">
        <h6>@ 2022 ProPolice. from SSAFY</h6>
        <h6>Created By 김은서, 김형준, 정동균, 최강현, 하현서</h6>
      </div>
      <div className="image">
        <img className="notion" src={notion} alt="notion" />
        <img className="notion" src={git} alt="git" />
      </div>
    </footer></>
  )
}
export default Footer;