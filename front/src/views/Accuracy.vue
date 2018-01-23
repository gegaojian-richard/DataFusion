<template>
  <div>
    <AccuracyBread></AccuracyBread>
  </div>
</template>
<script>
  import axios from 'axios'
  import MyHeader from '../../resource/MyHeader'
  import  NavBar from '../../resource/NavBar'
  import AccuracyBread from './../components/AccuracyBread'
  export default{
    data(){
      return {
      }
    },
    components: {
      MyHeader,
      NavBar,
      AccuracyBread
    },
    methods: {

    }
  }
</script>
