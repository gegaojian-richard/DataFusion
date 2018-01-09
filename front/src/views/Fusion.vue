<template>
<div>
  <MyHeader></MyHeader>
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar" id="side-conn">
      <SideBar></SideBar>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <FusionBread></FusionBread>
    </div>
  </div>
</div>
</template>
<script>
  import axios from 'axios'
  import MyHeader from './../components/MyHeader'
  import  SideBar from './../components/SideBar'
  import FusionBread from './../components/FusionBread'
  export default{
    data(){
      return {
      }
    },
    components: {
      MyHeader,
      SideBar,
      FusionBread
    },
    methods: {

    }
  }
</script>
