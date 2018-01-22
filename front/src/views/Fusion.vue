<template>
<div>
  <MyHeader></MyHeader>
  <el-row >
    <el-col :span="4">
      <NavBar></NavBar>
    </el-col>
    </el-col>
    <el-col :span="20">
      <FusionBread></FusionBread>
    </el-col>
  </el-row>
  </div>
</div>
</template>
<script>
  import axios from 'axios'
  import MyHeader from './../components/MyHeader'
  import  NavBar from '../../resource/NavBar'
  import FusionBread from './../components/FusionBread'
  export default{
    data(){
      return {
      }
    },
    components: {
      MyHeader,
      NavBar,
      FusionBread
    },
    methods: {

    }
  }
</script>
