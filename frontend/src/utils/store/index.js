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
	  API.defaults.headers.common['Authorization'] = `${token}`;
    if (token) {
      return true;
    }
    return false;
  },
}

export default store;