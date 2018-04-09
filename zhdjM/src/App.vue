<template>
    <div id="app">
        <loading v-model="isLoading"></loading>
        <transition name="router-slid" mode="out-in">
            <router-view></router-view>
        </transition>

    <left-menu :itemMap="itemMap"></left-menu>
    </div>
</template>

<script>
    import {Loading} from 'vux'
    import {mapState} from 'vuex'

    export default {
        name: 'app',
        components: {
            Loading
        },
        computed: {
            ...mapState({
                isLoading: state => state.vux.isLoading
            })
        },
        data (){
          return {

          }
        },
        mounted(){

        }
    }
</script>

<style lang="less">
    @import '~vux/src/styles/reset.less';
    @import './assets/css/qylycss.css';
    @import '~swiper/dist/css/swiper.css';
    body {
        background-color: #f1f1f1;
    }

    .router-slid-enter-active, .router-slid-leave-active {
        transition: all .4s;
    }

    .router-slid-enter, .router-slid-leave-active {
        transform: translate3d(2rem, 0, 0);
        opacity: 0;
    }
</style>
