import { Link } from "react-router-dom"
import './style.scss'

const Navbar = () => {

  const Login = () => {
    console.log('hi')
  }

  return (
    <nav>
      <h1>SSapilogue</h1>
      <Link className="login" to="/signin">
        <button onClick={Login} >로그인</button>
      </Link>
    </nav>
  )
}
export default Navbar;