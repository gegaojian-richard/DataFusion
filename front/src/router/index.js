import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
import Layout from '@/views/layout/Layout'
import Home from '@/views/Home'
Vue.use(Router)
//同步路由
export const constantRouterMap=[
  {path:'/login',component:_import('login/index'),hidden:true},
  {path:'/404',component:_import('errorPage/401'),hidden:true},
  {path:'/401',component:_import('errorPage/404'),hidden:true},
  {
    path:'',
    component:Layout,
    redirect:'/home',
    children:[{
      path:'home',
      component:Home,
      name:Home,
      meta:{title:'home',icon:'dashboard',noCache:true}
    }]
  },
  {
    path:'/documentation',
    component:Layout,
    redirect:'/documentation/index',
    children:[{
      path:'index',
      component:_import('Documentation'),
      meta:{title:'documentation',icon:'documentation',noCache:true}
    }]
  },
  {
    path:'/connect',
    component:Layout,
    redirect:'/connect/index',
    children:[{
      path:'index',
      component:_import('Connect'),
      name:'Connect',
      meta:{title:'connect',icon:'icon',noCache:true}
    }]
  },
  {
    path:'/entity',
    component:Layout,
    redirect:'/entity/index',
    children:[{
      path:'index',
      component:_import('Entity'),
      name:'Entity',
      meta:{title:'entity',icon:'chart',noCache:true}
    }]
  },
  {
    path:'/fusion',
    component:Layout,
    redirect:'/fusion/index',
    children:[{
      path:'index',
      component:_import('Fusion'),
      name:'Fusion',
      meta:{title:'fusion',icon:'excel',noCache:true}
    }]
  },
  {
    path: '/check',
    component: Layout,
    redirect: 'noredirect',
    name: 'Check',
    meta: {
      title: 'check',
      icon: 'component'
    },
    children: [
      { path: 'complete', component:_import('Complete'), name: 'Complete', meta: { title: 'complete', icon: 'table' }},
      { path: 'insist', component:_import('Insist'), name: 'Insist', meta: { title: 'insist', icon: 'example' }},
      { path: 'accuracy', component:_import('Accuracy') , name: 'Accuracy', meta: { title: 'accuracy', icon: 'form' }}
    ]
  },
]
export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap=[

]

