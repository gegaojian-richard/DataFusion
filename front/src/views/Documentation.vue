<template>
  <el-row>
    <el-col :span="8">123</el-col>
    <el-col :span="8"><FusionBread></FusionBread></el-col>
    <el-col :span="8">789</el-col>
  </el-row>
</template>
<script>
  import axios from 'axios'
  import FusionBread from '../components/fusion/FusionBread'
  export default{
    data(){
      return {
      }
    },
    components: {
      FusionBread
    },
    methods: {

    }
  }
</script>
