<template>
  <div style="margin-top: 50px">
    <el-carousel :interval="5000" arrow="always" height="600">
      <el-carousel-item v-for="(img,index) in imgList" :key="index">
        <img :src="img.url" style="width:500px;height:300px">
        <!--<h3>{{img.text}}</h3>-->
      </el-carousel-item>
    </el-carousel>
    <!--<img src="./../image/document1.png" style="clear: both;width:500px;height:400px">-->
  </div>
</template>
<script>
  import axios from 'axios';
  import d1 from './../image/document1.png'
  import d2 from './../image/document2.png'
  import d3 from  './../image/document3.png'
  export default{
    data(){
      return {
          imgList:[
            {url:d1},
            {url:d2},
            {url:d3},
          ]
      }
    },
    components: {
    },
    methods: {

    }
  }
</script>
