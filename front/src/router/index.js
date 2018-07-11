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
      meta:{title:'主页',icon:'dashboard',noCache:true}
    }]
  },
  {
    path:'/documentation',
    component:Layout,
    redirect:'/documentation/index',
    children:[{
      path:'index',
      component:_import('Documentation'),
      meta:{title:'文档',icon:'documentation',noCache:true}
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
      meta:{title:'连接管理',icon:'icon',noCache:true}
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
      meta:{title:'实体管理',icon:'chart',noCache:true}
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
      meta:{title:'数据融合',icon:'excel',noCache:true}
    }]
  },
  {
    path:'/task',
    component:Layout,
    redirect:'/task/index',
    children:[{
      path:'index',
      component:_import('Task'),
      name:'Task',
      meta:{title:'任务管理',icon:'dashboard',noCache:true}
    },
      {
        path:'completeresult',
        component:_import('CompleteResult')
      },
      {
        path:'insistresult',
        component:_import('InsistResult')
      },

    ]
  },
  {
    path:'/algorithm',
    component:Layout,
    redirect:'/algorithm/index',
    children:[{
      path:'index',
      component:_import('Algorithm'),
      name:'Algorithm',
      meta:{title:'算法',icon:'clipboard',noCache:true}
    }]
  },
  {
    path: '/check',
    component: Layout,
    redirect: 'noredirect',
    name: 'Check',
    meta: {
      title: '数据治理',
      icon: 'component'
    },
    children: [
      { path: 'complete', component:_import('Complete'), name: 'Complete', meta: { title: '完整性检测', icon: 'table' }},
      { path: 'insist', component:_import('Insist'), name: 'Insist', meta: { title: '一致性检测', icon: 'example' }},
      { path: 'accuracy', component:_import('Accuracy') , name: 'Accuracy', meta: { title: '准确性检测', icon: 'form' }}
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

