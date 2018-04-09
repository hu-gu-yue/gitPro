/**
 * Created by tanxijun on 2017/10/12.
 */
import axios from '../config/http'
let base = '/yc/api/v1/lyxxh';

export const logout = params => { return axios.post(`${base}/user/logout`, params).then( response => { return response.data })};
export const favorite = params => { return axios.post(`${base}/favorite`, params).then( response => { return response.data })};
export const like = params => { return axios.post(`${base}/like`, params).then( response => { return response.data })};
