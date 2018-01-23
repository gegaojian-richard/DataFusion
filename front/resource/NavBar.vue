<template>
  <el-row class="tac" >
    <el-col  style="height:1000px; background-color:#545c64;margin-right:10px;float:right">
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item index="1">
          <i class="el-icon-menu"></i>
          <span slot="title"><a href="/#/connect">数据源管理</a></span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-setting"></i>
          <span slot="title"><a href="/#/entity">实体管理</a></span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-setting"></i>
          <span slot="title"><a href="/#/Fusion">数据整合</a></span>
        </el-menu-item>
        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>数据治理</span>
          </template>
          <el-menu-item index="4-1">一致性</el-menu-item>
          <el-menu-item index="4-2">完整性</el-menu-item>
          <el-menu-item index="4-3"><a href="/#/accuracy">准确性</a></el-menu-item>
          <el-menu-item index="4-4">及时性</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-col>
  </el-row>

</template>
<script>
  export default {
      data(){
          return {
            routes:[
              {path: '/d', name: '查阅', component: About},
        {path: '/admin', name: '系统管理', component: MainView},
        {path: '/docadmin', name: '档案维护', component: LayoutMain}
      ]
          }
      },
    methods: {
    }
  }
</script>


