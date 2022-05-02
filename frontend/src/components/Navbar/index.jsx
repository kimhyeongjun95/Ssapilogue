import { Link } from "react-router-dom"
import './style.scss'
import Default from '../../assets/default.png'
import { useEffect, useState } from "react"
import store from "../../utils/store"

const Navbar = () => {

  const [authorized, setAuthorized] = useState(false);
  const [dropDown, setDropDown] = useState(false);
  const toggleHandler = () => {
    setDropDown(!dropDown);
  }

  const signInCheck = () => {
    store.getToken() ? setAuthorized(true) : setAuthorized(false);
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
            <button>로그인</button>
          </Link>
        }

        {dropDown ? 
          <div className="navbar_dropdown_content">

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <button>프로필</button>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_myprofile">
                <button>정보수정</button>
              </div>
            </div>

            <div className="navbar_dropdown_content_element">
              <div className="navbar_logout">
                <button>로그아웃</button>
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