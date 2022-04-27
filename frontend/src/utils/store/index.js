import API from "../../api/API"

const store = {
  setToken(token) {
	  API.defaults.headers.common['Authorization'] = `Bearer ${token}`
	  localStorage.setItem("jwt", `Bearer ${token}`)
  },
  getToken() {
	  return localStorage.getItem("jwt")
  },
}

export default store;