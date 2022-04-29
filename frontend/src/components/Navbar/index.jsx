import { Link } from "react-router-dom"
import './style.scss'
import Default from '../../assets/default.png'
import { useEffect, useState } from "react"

const Navbar = () => {

  const [show, setShow] = useState(false);
  const toggleHandler = () => {
    setShow(!show);
  }

  
  const Login = () => {
    console.log('hi')
  }

  const signInCheck = () => {
    // token 값 있으면 
    console.log("로그아웃")
  }

  const signOut = () => {
    console.log("로그아웃")
  }

  useEffect(() => {
    signInCheck();
  }, [])

  return (
    <nav>
      <Link className="home" to="/">
        <h1>SSapilogue</h1>
      </Link>
      
      <Link className="login" to="/signin">
        <button onClick={Login} >로그인</button>
      </Link>

      <div className="navbar_dropdown">
        
        <div className="navbar-profile-image">
          <img src={Default} alt="" onClick={toggleHandler} />
        </div>

        {show ? 
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
                <button onClick={signOut}>로그아웃</button>
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