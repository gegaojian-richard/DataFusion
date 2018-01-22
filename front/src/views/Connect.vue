<template>
  <div>
    <ConnectBread></ConnectBread>
  </div>
</template>
<script>
  import axios from 'axios'
  import MyHeader from './../components/MyHeader'
  import  NavBar from '../../resource/NavBar'
  import ConnectBread from './../components/ConnectBread'
  export default{
    data(){
      return {
      }
    },
    components: {
      MyHeader,
      NavBar,
      ConnectBread
    },
    methods: {

    }
  }
</script>
