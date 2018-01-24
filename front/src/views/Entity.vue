<template>
  <div>
        <EntityBread></EntityBread>
        <EventBread></EventBread>
  </div>
</template>
<script>
  import MyHeader from '../../resource/MyHeader'
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
