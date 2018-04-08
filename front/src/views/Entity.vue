<template>
  <div>
    <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
      <el-tab-pane label="实体管理" name="first" style="height:100%"> <EntityBread></EntityBread></el-tab-pane>
      <el-tab-pane label="事件管理" name="second" style="height:100%"> <EventBread></EventBread></el-tab-pane>
    </el-tabs>
  </div>
</template>
<style>
  .el-tabs__item{
    color:#bfcbd9;
  }
  .el-tabs__content {
    height:calc(100% - 80px);
    padding: 20px;
  }
</style>
<script>
//  import MyHeader from '../../resource/MyHeader'
//  import  NavBar from '../../resource/NavBar'
  import  EntityBread from './../components/EntityBread'
  import  EventBread from './../components/EventBread'
  import axios from 'axios'
  export default{
    data() {
      return {
        activeName2: 'first'
      };
    },

    components: {
//      MyHeader,
//      NavBar,
      EntityBread,
      EventBread
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  }
</script>
