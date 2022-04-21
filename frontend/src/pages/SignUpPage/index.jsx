import React, { useState } from "react";

const SignUpPage = () => {
  
  const [inputs, setInputs] = useState({
    nickName: '',
    greetings: '',
    github: '',
  });
  const { nickName, greetings, github } = inputs;

  const handleOnChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const signUp = () => {
    console.log(nickName, greetings, github);
  }

  return (
    <>
      <h1>회원가입 페이지!</h1>

      닉네임<input name="nickName" onChange={handleOnChange} value={nickName}/>
      소개말<input name="greetings" onChange={handleOnChange} value={greetings}/>
      Github<input name="github" onChange={handleOnChange} value={github}/>
      <button onClick={signUp}>회원가입</button>
    </>
  )
}

export default SignUpPage;