import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Test from '@/components/Test'
import MyHeader from '@/components/MyHeader'
import Entity from '@/views/Entity'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path:"/test",
      name:'Test',
      component:Test
    },
    {
      path:'/myheader',
      name:'MyHeader',
      component:MyHeader
    },
    {
      path:'/Entity',
      name:'Entity',
      component:Entity
    }
  ]
})
