import { Link } from "react-router-dom"

const Navbar = () => {

  const Login = () => {
    console.log('hi')
  }

  return (
    <>
      <h1>SSapilogue</h1>
      <Link to="/signin">
        <button onClick={Login} >로그인</button>
      </Link>
    </>
  )
}
export default Navbar;