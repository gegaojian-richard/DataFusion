<template>
  <div class="sidebar">
    <div class="entityinfo">
      <dl>
        <dt><span style="width:100px;display:inline-block">实体库</span></dt>
        <dd v-for="(value,index) in entityLi" @click="emitChoose(value)">
         <a  v-bind:class="{blue:value.dbID}" >{{value.displayName}}</a>
        </dd>
      </dl>
    </div>
    <div class="eventinfo" style="margin-top: 50px">
      <dl>
        <dt><span style="width:100px;display:inline-block">事件库</span></dt>
        <dd v-for="(value,index) in eventLi">
          <a @click="emitChoose(value)">{{value.displayName}}</a>
        </dd>
      </dl>
    </div>
  </div>
</template>
<style>
  .blue{
    color: #2bc4e2;
  }
  .sidebar{
    height:100%;
    /*border-left:2px solid #bfcbd9;*/
    background-color: #1F5FA6;
    color:#fff;
    font-size: large;
    overflow: auto;
  }
  .entityinfo dd {
    height: 50px;
    line-height: 50px;
  }
  .entityinfo dd:hover {
    background: #4686C4;
  }
  .entityinfo dd a:hover {
    color:inherit;
  }
  .entityinfo dl dt, .eventinfo dl dt {
    height: 60px;
    text-align: center;
    font-size: 18px;
    color: #145398;
    line-height: 60px;
    background: #DFE2EB;
    font-weight: bold;
  }
</style>
<script>
  import axios from 'axios'
  import {mapGetters} from 'vuex'
  export default{
      data(){
          return{
            entityLi:[],
            eventLi:[],

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
      emitChoose(value){
        this.$emit('selectentity', value);
      }
      }
  }
</script>
