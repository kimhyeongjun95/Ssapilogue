import { Link } from "react-router-dom"
import { useEffect, useState } from "react"
import store from "../../utils/store"
import { useNavigate } from "react-router-dom"
import './style.scss'
import Default from '../../assets/default.png'

const Navbar = () => {

  const [authorized, setAuthorized] = useState(false);
  const [dropDown, setDropDown] = useState(false);
  const navigate = useNavigate();

  const toggleHandler = () => {
    setDropDown(!dropDown);
  }

  const signInCheck = () => {
    store.getToken() ? setAuthorized(true) : setAuthorized(false);
  }

  const signOut = () => {
    store.setToken("logout");
  }

  const goProfile = () => {
    navigate("/profile");
    setDropDown(!dropDown);
  }

  const goChangeInfo = () => {
    navigate("/changeinfo");
    setDropDown(!dropDown);
  }

  useEffect(() => {
    signInCheck();
  }, [])

  return (
    <nav>
      <Link className="home" to="/">
        <h1>SSapilogue</h1>
      </Link>
      

      <div className="navbar_dropdown">
        {authorized ?
          <div className="navbar-profile-image">
            <img src={Default} alt="" onClick={toggleHandler} />
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