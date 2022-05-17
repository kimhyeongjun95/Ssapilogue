import React, { useEffect, useState } from "react";
import API from "../../api/API";
import store from "../../utils/store";
import profilePic from "../../assets/profileDefault.jpg"
import "./style.scss"
import { useNavigate } from "react-router-dom";

const ChangeInfoPage = () => {

  const [inputs, setInputs] = useState({
    github: '',
    greeting: '',
  });
  const [email, setEmail] = useState('');
  const [image, setImage] = useState('');
  const navigate = useNavigate();
  const { github, greeting } = inputs;

  const getInfo = async () => {
    store.getToken();
    const response = await API.get("/api/user")
    setInputs(response.data.user);
    setEmail(response.data.user.nickName);
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
      github: github,
      greeting: greeting,
      image: image,
    })
    navigate('/profile')
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
          <h5 className="change-img-h5">프로필 사진을 변경하려면 사진을 클릭하세요.</h5>
        </div>

        <div className="change-input-box">
          <p className="change-input-name">GITHUB</p>
          <input className="change-input" name="github" onChange={e => handleOnChange(e)} value={github}/>
        </div>

        <div className="change-input-box">
          <p className="change-input-name">자기소개</p>
          <input className="change-input" style={{height: "100px"}} name="greeting" onChange={e => handleOnChange(e)} value={greeting}/>
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