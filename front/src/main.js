
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

import Vue from 'vue'
import $ from 'jquery'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import App from './App'
import router from './router'
import Vuex from 'vuex'
import axios from 'axios'
import './assets/css/base.css'
import global_ from './components/Global.vue'
Vue.prototype.GLOBAL = global_;
axios.defaults.baseURL=global_.BASE_URL;
Vue.prototype.$ajax = axios;
axios.defaults.withCredentials = true;
Vue.use(Vuex);
Vue.config.productionTip = false
const store=new Vuex.Store({
  state:{
    nickName:'',
    moveIn:false,
    moveOut:false,
  },
  mutations:{
    updateMoveIn(state,moveIn){
      state.moveIn=moveIn;
    },
    updateMoveOut(state,moveOut){
      state.moveOut=moveOut;
    },
    updateNickName(state,nickName){
      state.nickName=nickName;
    }
  }
});

new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App }
})

