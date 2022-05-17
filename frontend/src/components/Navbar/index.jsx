import { Link } from "react-router-dom"
import { useEffect, useState } from "react"
import store from "../../utils/store"
import { useNavigate } from "react-router-dom"
import './style.scss'
import Default from '../../assets/default.png'
import Logo from "../../assets/logo.png"

const Navbar = () => {

  const [authorized, setAuthorized] = useState(false);
  const [dropDown, setDropDown] = useState(false);
  const [userPic, setUserPic] = useState('')
  const navigate = useNavigate();

  const toggleHandler = () => {
    setDropDown(!dropDown);
  }

  const signInCheck = () => {
    store.getToken() ? setAuthorized(true) : setAuthorized(false);
  }

  const signOut = () => {
    store.setImage("");
    store.setToken("logout");
  }

  const goProfile = () => {
    navigate("/profile", {state : { username : ""}});
    setDropDown(!dropDown);
  }

  const goChangeInfo = () => {
    navigate("/changeinfo");
    setDropDown(!dropDown);
  }

  useEffect(() => {
    signInCheck();
    setUserPic(store.setImage('userPic'))
  }, [])

  return (
    <nav>
      <Link className="home" to="/">
        <img src={Logo} alt="logo" className="logo" />
      </Link>
      

      <div className="navbar_dropdown">
        {authorized ?
          <div className="navbar-profile-image">
            { (userPic) ?
              <img src={userPic} alt="userPic" onClick={toggleHandler} className="person-image" />
              :
              <img src={Default} alt="defaultPic" onClick={toggleHandler} className="person-image" />
            }
          </div>
          :
          <Link className="login" to="/signin">
            <p className="login_text">로그인</p>
          </Link>
        }

        {dropDown ? 
          <div className="navbar_dropdown_content">

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <button className="navbar_dropdown_button" onClick={goProfile}>프로필</button>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <button className="navbar_dropdown_button" onClick={goChangeInfo}>정보수정</button>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_logout">
                <button className="navbar_dropdown_button" onClick={signOut}>로그아웃</button>
              </div>   
            </div>
            
          </div>
          :
          <></>
        }

      </div>
    </nav>
  )
}
export default Navbar;