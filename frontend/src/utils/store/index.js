import API from "../../api/API"

const store = {
  setToken(token) {
	  API.defaults.headers.common['Authorization'] = `Bearer ${token}`
	  localStorage.setItem("jwt", `Bearer ${token}`)
  },
  getToken() {
    const token = localStorage.getItem("jwt")
	  return API.defaults.headers.common['Authorization'] = `Bearer ${token}`
  },
}

export default store;