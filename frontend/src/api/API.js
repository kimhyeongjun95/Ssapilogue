import axios from 'axios';

const API = axios.create({
  headers: {
    "Access-Control-Allow-Origin": "*",
  },
});
  
export default API;