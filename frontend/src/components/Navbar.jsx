import { Outlet } from "react-router-dom";

const Navbar = () => {
  return (
    <>
      <h1>네브바</h1>
      <Outlet /> 
    </>
  )
}
export default Navbar;