import Vue from 'vue'
import Vuex from 'vuex'
import tagsView from './modules/tagsView'
import getters from './getters'
import errorLog from './modules/errorLog'
import permission from './modules/permission'
import app from './modules/app'
import user from './modules/user'
Vue.use(Vuex)
const store=new Vuex.Store({
  modules: {
    tagsView,
    errorLog,
    app,
    permission,
    user
  },
  getters
})
export default store
