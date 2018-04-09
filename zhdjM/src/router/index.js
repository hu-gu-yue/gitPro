import Vue from 'vue'
import Router from 'vue-router'
import InfiniteScroll from 'vue-infinite-scroll';
Vue.use(InfiniteScroll);

Vue.use(Router)

let rooterArray = [
  business
]

const routes = [
    {
        path: '/index',
        name: 'index',
        title: '智慧党建',
        component: function (resolve) {
            require(['../views/index.vue'], resolve)
        }
    },
    {
        path: '/404',
        name: 'notFount',
        title: '404',
        component: function (resolve) {
            require(['../views/common/404.vue'], resolve)
        }
    },
    {   path: '*',
        redirect: '/404'
    }
]

rooterArray.forEach(function(value, index, array) {
  value.forEach(function(route, index, array) {
    routes.push(route)
  })
})

const router = new Router({
  mode: 'hash',
  routes: routes
})

export default router
