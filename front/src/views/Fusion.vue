<template>
<div style="height:100%">
  <fusion-side style="height:490px; width:180px"></fusion-side>
  <FusionBread></FusionBread>
</div>
</template>
<script>
  import axios from 'axios'
  import FusionBread from '../components/fusion/FusionBread'
  import fusionSide from '../components/fusion/FusionSide'
  export default{
    data(){
      return {
      }
    },
    components: {
      FusionBread,
      fusionSide
    },
    methods: {

    }
  }
</script>
