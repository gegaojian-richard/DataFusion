<template>
  <div class="sidebar">
    <div class="entityinfo">
      <dl>
        <dt><span style="width:100px;display:inline-block">实体库</span></dt>
        <dd v-for="(value,index) in entityLi">
         <a>{{value.displayName}}</a>
        </dd>
      </dl>
    </div>
    <div class="eventinfo" style="margin-top: 50px">
      <dl>
        <dt><span style="width:100px;display:inline-block">事件库</span></dt>
        <dd v-for="(value,index) in eventLi">
          <a>{{value.displayName}}</a>
        </dd>
      </dl>
    </div>
  </div>
</template>
<style>
  .sidebar{
    height:100%;
    border-left:1px solid #1a1a1a;
    background-color: #103251;
    color:#bfcbd9;
    padding-top:5px;
    font-size: large;
  }
</style>
<script>
  import axios from 'axios'
  import {mapGetters} from 'vuex'
  export default{
      data(){
          return{
            entityLi:[],
            eventLi:[]
          }
      },
    computed: {
      ...mapGetters(['entityevent']),
    },
    mounted(){
      this.getEntity();
    },
    watch: {
      entityevent: function (val) {
        this.entityLi = val.filter(function (item) {
          return item.entityType == 0;
        });
        this.eventLi = val.filter(function (item) {
          return item.entityType == 1;
        })
      }
    },
    methods: {
        getEntity(){
          this.$store.dispatch('GetEntity');
        },
      }
  }
</script>
