import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Test from '@/components/Test'
import MyHeader from '@/components/MyHeader'
import Entity from '@/views/Entity'
import Connect from '@/views/Connect'
import Fusion from '@/views/Fusion'
import Accuracy from '@/views/Accuracy'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name:'Connect',
      component:Connect
    },
    {
      path:"/connect",
      name:'Connect',
      component:Connect
    },
    {
      path:'/fusion',
      name:'Fusion',
      component:Fusion
    },
    {
      path:'/entity',
      name:'Entity',
      component:Entity
    },
    {
      path:'/accuracy',
      name:'Accuracy',
      component:Accuracy
    }
  ]
})
