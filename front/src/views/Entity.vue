<template>
  <div>
    <MyHeader></MyHeader>
    <el-row >
      <el-col :span="4">
        <NavBar></NavBar>
      </el-col>
      </el-col>
      <el-col :span="20">
        <EntityBread></EntityBread>
        <EventBread></EventBread>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import MyHeader from './../components/MyHeader'
  import  NavBar from '../../resource/NavBar'
  import  EntityBread from './../components/EntityBread'
  import  EventBread from './../components/EventBread'
  import axios from 'axios'
  export default{
    data(){
      return {}
    },
    components: {
      MyHeader,
      NavBar,
      EntityBread,
      EventBread
    },
  }
</script>
