import React, { useState } from "react";
import API from "../../api/API";
import { useNavigate } from 'react-router-dom';

// const store = {
//   setLocalStorage({ key, val }) {
//     localStorage.setItem(`${key}`, JSON.stringify(val));
//   },
//   getLocalStorage(key) {
//      return JSON.parse(localStorage.getItem(`${key}`));
//   },
// };

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

  const signIn = () => {
    API.post("/api/v4/users/login", {login_id: id,password: pw,})
      .then((result) => {
        console.log(result);
        API.post("/api/user/login", {email:result.data.email, password:pw, userId:result.data.id})
          .then((res) => {
            const direct = res.data.status;
            if (direct === "NO USER") {
              navigate("/signup", {state: {email: result.data.email, pw: pw, userId: result.data.id }});
              return;
            }
            console.log("로그인 성공");
          })
      })
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