import API from "../../api/API"

const store = {
  setToken(token) {
    if (token === "") {
      localStorage.setItem("jwt", "");
      delete API.defaults.headers.common['Authorization'];
      return;
    }
    if (token === "logout") {
      localStorage.setItem("jwt", "");
      delete API.defaults.headers.common['Authorization'];
      window.location.replace("/")
      return;
    }
	  API.defaults.headers.common['Authorization'] = `Bearer ${token}`;
	  localStorage.setItem("jwt", `Bearer ${token}`);
  },

  getToken() {
    const token = localStorage.getItem("jwt");
    if (token) {
      API.defaults.headers.common['Authorization'] = `${token}`;
      return true;
    }
    delete API.defaults.headers.common['Authorization']
    return false;
  },

  setImage(url) {
    if (url === "") {
      localStorage.setItem("userPic", 'None');
      return;
    }
    localStorage.setItem("userPic", url);  
  },

  getImage() {
    return localStorage.getItem("userPic");
  }

}

export default store;