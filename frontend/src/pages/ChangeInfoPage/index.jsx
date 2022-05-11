import React, { useEffect, useState } from "react";
import API from "../../api/API";
import store from "../../utils/store";
import profilePic from "../../assets/profileDefault.jpg"
import "./style.scss"
import { height } from "@mui/system";

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
      <div className="change-info">
        <div style={{marginBottom: "10vh"}}>
          <label for="file-input">
            <div className="profile-circle">
              <img className="profile-img" src={profilePic} alt="profilePic" />
            </div>
          </label>
          <input id="file-input" type="file" style={{display: "none"}} onChange={uploadImage} />
          <h5>프로필 사진을 변경하려면 사진을 클릭하세요</h5>
        </div>

        <div style={{display:"flex", flexDirection:"row", marginBottom: "4vh"}}>
          <p style={{marginRight: "40px"}}>GITHUB</p>
          <input style={{width: "320px"}} name="github" onChange={e => handleOnChange(e)} value={github}/>
        </div>

        <div style={{display:"flex", flexDirection:"row", marginBottom: "4vh"}}>
          <p style={{marginRight: "40px"}}>자기소개</p>
          <input style={{width: "320px", height: "100px"}} name="greeting" onChange={e => handleOnChange(e)} value={greeting}/>
        </div>

        <div className="change-btn">
          <button className="btn-red" style={{marginRight: "20px"}} onClick={withDraw}>회원탈퇴</button>
          <button className="btn-blue" onClick={changeInfo}>변경하기</button>
        </div>
      </div>
    </>
  )
}

export default ChangeInfoPage;