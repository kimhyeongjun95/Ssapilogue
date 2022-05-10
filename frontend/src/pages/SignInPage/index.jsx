import React, { useState } from "react";
import API from "../../api/API";
import { useNavigate } from 'react-router-dom';
import store from "../../utils/store";
import "./style.scss"
import mattermost from "../../assets/mattermost.png"

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
      store.setToken("");
      const result = await API.post("/api/v4/users/login", {login_id: id,password: pw,})
      const res = await API.post("/api/user/login", { email:result.data.email, password:pw, userId:result.data.id })
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
      throw e;
    }
  }

  return (
    <div className="box">
      <img className="mattermost" src={mattermost} alt="mattermost" />
      <p className="id">아이디</p>
      <input className="input" name="id" onChange={handleOnChange} value={id}/>
      <p className="password">비밀번호</p>
      <input className="input" name="pw" onChange={handleOnChange} type="password" value={pw}/>
      <button className="button" onClick={signIn}>로그인</button>
    </div>
  )
}

export default SignInPage;