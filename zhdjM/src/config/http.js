/**
 * Created by tanxijun on 2017/9/21.
 * axios api https://www.kancloud.cn/yunye/axios/234845
 */
import axios from 'axios'
// import { webServer } from './config'
// axios.defaults.timeout = 5000
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// axios.defaults.baseURL = webServer

//添加响应拦截器
axios.interceptors.response.use(function(response){
    //对响应数据做些事
    let data = response.data
    // 如果用户未登录 移除前端登录标记位
    if(!data.success && (data.message=='mustLogin')){
        sessionStorage.removeItem('userInfo')
        location.reload();
    }

    return response;
},function(error){
    //请求错误时做些事
    return Promise.reject(error);
});

export default axios
