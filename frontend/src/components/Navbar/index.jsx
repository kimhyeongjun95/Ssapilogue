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
    navigate("/profile")
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
            <p className="p">로그인</p>
          </Link>
        }

        {dropDown ? 
          <div className="navbar_dropdown_content">

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <a onClick={goProfile}>프로필</a>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <a>정보수정</a>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_logout">
                <a onClick={signOut}>로그아웃</a>
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