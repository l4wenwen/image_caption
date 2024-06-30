import axios from 'axios'
import {ElMessage} from 'element-plus'
import {useRouter} from "vue-router";

const request = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'multipart/form-data',
  },
})

//请求拦截器
request.interceptors.request.use(config => {
  // 请求前使用token
  // const userStore = useUserStore()
  // const token = userStore.userToken
  // if (token) {
  //   config.headers.Authorization = `Bearer ${token}`
  // }
  return config
})

const router = useRouter()
//响应拦截器
request.interceptors.response.use(
  response => {
    return response
  },
  error => {
    let status = error.response.status
    switch (status) {
      case 404:
        ElMessage({type: 'error', message: '请求失败,请检查路径'})
        break
      case 500 | 501 | 502 | 503 | 504 | 505:
        ElMessage({
          type: 'error',
          message: '服务器连接失败',
        })
        break
      default :
        ElMessage({
          type: 'error',
          message: error.response.data,
        })
        break
    }
    return Promise.reject(new Error(error.message))
  },
)

export default request
