import React, { useEffect, useState } from "react";
import API from "../../api/API";
import store from "../../utils/store";

const ChangeInfoPage = () => {

  const [inputs, setInputs] = useState({
    github: '',
    greeting: '',
  });
  const [nickName, setNickName] = useState('');
  const [email, setEmail] = useState('');
  const [image, setImage] = useState('');
  const { github, greeting } = inputs;

  const getInfo = async () => {
    store.getToken();
    const response = await API.get("/api/user")
    setInputs(response.data.user);
    setEmail(response.data.user.nickName);
    setNickName(response.data.user.email);
  }

  const handleOnChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const uploadImage = async (e) => {
    store.getToken();
    const formData = new FormData();
    formData.append('file', e.target.files[0]);
    const response = await API.post('/api/user/image', formData);
    setImage(response.data.imageUrl);
  }

  const changeInfo = async() => {
    await API.put("/api/user", {
      email: email,
      nickname: nickName,
      github: github,
      greeting: greeting,
      image: image,
    })
  }
  
  const withDraw = () => {
    store.getToken();
    API.delete("api/user");
    store.setToken("logout");
    return;
  }

  useEffect(() => {
    getInfo();
  }, [])
  
  return (
    <>
      <h1>회원정보 변경 페이지!</h1>

      <input type="file" onChange={uploadImage} />
      GITHUB <input name="github" onChange={e => handleOnChange(e)} value={github}/>
      자기소개 <input name="greeting" onChange={e => handleOnChange(e)} value={greeting}/>
      <button onClick={changeInfo}>변경하기</button>
      <button onClick={withDraw}>회원탈퇴</button>
    </>
  )
}

export default ChangeInfoPage;