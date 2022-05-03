import React, { useState } from "react";
import API from "../../api/API";
import { useNavigate } from 'react-router-dom';
import store from "../../utils/store";

const SignInPage = () => {

  const [inputs, setInputs] = useState({
    id: '',
    pw: '',
  });
  const { id, pw } = inputs;
  const navigate = useNavigate();

  const handleOnChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const signIn = async () => {
    try {
      const result = await API.post("/api/v4/users/login", {login_id: id,password: pw,})
      console.log(result);
      const res = await API.post("/api/user/login", {email:result.data.email, password:pw, userId:result.data.id})
      const direct = res.data.status;
      if (direct === "NO USER") {
        navigate("/signup", {state: {email: result.data.email, pw: pw, userId: result.data.id }});
        return;
      } 
      if (direct === "SUCCESS") {
        const token = res.data.token;
        store.setToken(token);
        window.location.replace("/")
        return;
      }
    } catch (e) {
      console.log(e);
      throw e;
    }
  }

  return (
    <div>
      아이디<input name="id" onChange={handleOnChange} value={id}/>
      비밀번호<input name="pw" onChange={handleOnChange} type="password" value={pw}/>
      <button onClick={signIn}>로그인</button>
    </div>
  )
}

export default SignInPage;