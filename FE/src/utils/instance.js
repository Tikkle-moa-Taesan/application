import axios from 'axios'

const BASE_BE_URL = import.meta.env.VITE_BASE_BE_URL

const instance = axios.create({
  baseURL: BASE_BE_URL,
  withCredentials: true,
})

export default instance
