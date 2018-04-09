// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import FastClick from 'fastclick'
import App from './App'
import router from './router/index'
import $ from 'jquery'
import { dateFormat,LoadingPlugin  } from 'vux'
import { imageRootPath,platformNo } from './config/config'

import  { ConfirmPlugin, AlertPlugin, ToastPlugin } from 'vux'
Vue.use(ConfirmPlugin)
Vue.use(AlertPlugin)
Vue.use(ToastPlugin)
Vue.use(LoadingPlugin)
// 引入mockjs
// require('./mock/mock.js')

/* loading setting start*/
Vue.use(Vuex)
const store = new Vuex.Store({}) // 这里你可能已经有其他 module

store.registerModule('vux', { // 名字自己定义
    state: {
        isLoading: false,
    },
    mutations: {
        updateLoadingStatus (state, payload) {
            state.isLoading = payload.isLoading
        }
    }
})

router.beforeEach(function (to, from, next) {
    store.commit('updateLoadingStatus', {isLoading: true})

    if (to.matched.some(record => record.meta.requiresAuth)) {
        let strObj = sessionStorage.getItem('userInfo');
        if (!strObj) {
            next({
                path: '/index',
                query: {redirect: to.fullPath}//把要跳转的地址作为参数传到下一步
            })
        }else{
            next();
        }
    }else{
        next();
    }

    let path = to.matched[0].path

    let item = router.options.routes.filter((ele) => {
        let pathTemp = ele.path
        return path == pathTemp
    })

    if(item.length>0){
        let title = item[0].title==undefined?'云山露营':item[0].title
        document.title = title
    } else{
        document.title = '云山露营'
    }

})
/* loading setting end*/

router.afterEach(function (to) {
    store.commit('updateLoadingStatus', {isLoading: false})
})

Vue.filter('dateFormat', function (time,type) {
    let date = '';
    if(type == 'YMD'){
        date = dateFormat(time, 'YYYY-MM-DD');
    } else if (type == 'HMS'){
        date = dateFormat(time, 'HH:mm:ss');
    } else if (type == 'HM'){
        date = dateFormat(time, 'YYYY-MM-DD HH:mm');
    } else if (type == 'MD'){
        date = dateFormat(time, 'MM-DD');
    } else {
        date = dateFormat(time, 'YYYY-MM-DD HH:mm:ss');
    }
    return date;
})


/**
 * 图片路径过滤器
 * type 资源类型 以后台的typeName为准
 * id 营地id 商户id 营地管理员id
 *
 */
Vue.filter('imgPath', function(value,type,id) {
    // console.log("value:",value)
    if(!value){
        return
    }

    // base64的格式和绝对路径不做处理
    if(value.substring(0,4)=="data"||
        value.substring(0,4)=="http"){
        return value;
    }

    let imgPath = ''
    if(type=="stay"||
        type=="food"||
        type=="play"||
        type=="special"||
        type=="businessman") {//住宿 美食 玩乐 特产 商户
        imgPath = imageRootPath + 'lyxxh/' + platformNo + '/businessman/' + id + value
    }else if(type=="scenicSpot"||
        type=="destination") {//景点 目的地
        imgPath = imageRootPath + 'lyxxh/' + platformNo +  '/destination/' + id + value
    }
    return imgPath
})


FastClick.attach(document.body)

Vue.config.productionTip = false

//added by xiaohan for rem below
//按照750 (设计图所标尺寸)/100 = rem
$(window).on('resize', function () {
    document.documentElement.style.fontSize = ($(document.documentElement).width() / 750) * 100 + 'px';
})
document.documentElement.style.fontSize = ($(document.documentElement).width() / 750) * 100 + 'px';
//added by xiaohan for rem above


/* eslint-disable no-new */
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app-box')
