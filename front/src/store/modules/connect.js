import axios from 'axios'

const  connect= {
  state: {
    conns: []
  },
  mutations: {
    SET_CONNECT: (state,data) => {
      state.conns = data;
    }
  },
  actions: {
    GetConnect({commit}){
      return new Promise((resolve, reject) => {
        axios.get("/kjb/cms/currentDataBase").then((response) => {
          var res = response.data;
          if (res.status == 1) {
            var jsonConns = JSON.parse(res.data);
            commit('SET_CONNECT', jsonConns);
            resolve()
          } else {
            reject(error)
          }
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}
export default connect

