
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

import Vue from 'vue'
import $ from 'jquery'
import Vuex from 'vuex'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import App from './App'
import router from './router'
import axios from 'axios'
 import global_ from './components/global.vue'
//import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import ElementUI from 'element-ui'
import './assets/scss/index.scss' // global css
import store from './store/index'
import './assets/base.css'
import '@/assets/login.css'
import 'element-ui/lib/theme-chalk/index.css'
import './icons'
import '@/assets/tabel.css'

Vue.prototype.GLOBAL = global_;
axios.defaults.baseURL=global_.BASE_URL;
Vue.prototype.$ajax = axios;
axios.defaults.withCredentials = true;

Vue.use(Vuex);
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App }
})

