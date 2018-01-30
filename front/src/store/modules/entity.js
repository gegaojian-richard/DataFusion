import axios from 'axios'
const  entity= {
  state: {
    entityevent: [],
  },
  mutations: {
    SET_ENTITYS: (state,data) => {
      state.entityevent = data;
    }
  },
  actions: {
    GetEntity({commit}){
      return new Promise((resolve, reject) => {
        axios.get("kjb/entity/show").then((response) => {
          let res = response.data;
          if (res.status == 1) {
            var jsondata = JSON.parse(res.data);
            commit('SET_ENTITYS', jsondata);
            resolve()
          } else {
            reject();
          }
        }).catch(error=> {
          reject(error)
        })
    })
    }
  }
}
export default entity

