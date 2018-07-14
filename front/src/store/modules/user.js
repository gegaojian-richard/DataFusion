import  axios from 'axios'
const user = {
  state: {
    name: '',
    roles: 'admin',
  },

  mutations: {
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        let param=new URLSearchParams();
        param.append("username",userInfo.username.trim());
        param.append("password",userInfo.password.trim());
        axios.post("/kjb/ums/login",param).then(response => {
          const data = response.data;
          if(data.status==1){
            commit('SET_NAME', "已登录");
            resolve()
          }else{
            reject(error)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        axios.post("/kjb/ums/logout").then((response)=>{
          let res = response.data;
          if(res.status==1){
           commit('SET_NAME','')
            resolve()
          }else{
            reject(error)
          }
        });
      }).catch(error => {
          reject(error)
        })
    },
  }
}

export default user
