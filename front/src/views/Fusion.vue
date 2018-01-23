<template>
<div>
      <FusionBread></FusionBread>
  </div>
</div>
</template>
<script>
  import axios from 'axios'
  import MyHeader from '../../resource/MyHeader'
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
