import React, { useState } from "react";
import API from "../api/API";
// import axios from "axios";

const SignInPage = () => {

  const [inputs, setInputs] = useState({
    id: '',
    pw: '',
  });
  const { id, pw } = inputs;

  const handleOnChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const login = () => {
    // axios.post("https://meeting.ssafy.com/api/v4/users/login", {
    //   login_id: id,
    //   password: pw,
    // },
    // {
    //   headers: {
    //     "Access-Control-Allow-Origin": "*",
    //   }
    // })
    API.post("/api/v4/users/login", {
      login_id: id,
      password: pw,
    }, 
    )
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      })
    
  }


  return (
    <div>
      아이디<input name="id" onChange={handleOnChange} value={id}/>
      비밀번호<input name="pw" onChange={handleOnChange} type="password" value={pw}/>
      <button onClick={login}>로그인</button>
    </div>
  )
}

export default SignInPage;