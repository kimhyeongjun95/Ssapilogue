import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import API from "../../api/API";
import store from "../../utils/store";
import "./style.scss"

const SignUpPage = () => {
  
  const [inputs, setInputs] = useState({
    nickName: '',
    greetings: '',
    github: '',
  });
  const { nickName, greetings, github } = inputs;
  const locations = useLocation().state;
  const { email, pw, userId } = locations;

  const handleOnChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const signUp = () => {
    API.post("api/user", {
      email: email,
      password: pw,
      userId: userId,
      nickName: nickName,
      github: github,
      greeting: greetings,
    })
      .then((res) => {
        if (res.data.message === "success") {
          const token = res.data.token;
          store.setToken(token);
          window.location.replace("/");
          return;
        }
      })
      .catch((err) => {
        throw err;
      });
  }

  return (
    <div className="box">
      <h2>반가워요!</h2>
      <p className="p">*는 필수항목입니다.</p>

      <p className="subject">닉네임</p>
      <input className="input" name="nickName" onChange={handleOnChange} value={nickName}/>
      <p className="subject">소개말</p>
      <input className="input" name="greetings" onChange={handleOnChange} value={greetings}/>
      <p className="subject">Github</p>
      <input className="input" name="github" onChange={handleOnChange} value={github}/>
      <button className="button" onClick={signUp}>회원가입</button>
      
    </div>
  )
}

export default SignUpPage;