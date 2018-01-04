<template>
  <div>
    <MyHeader></MyHeader>
    <div class="row">
      <div class="col-sm-3 col-md-2 sidebar" id="side-conn">
        <SideBar></SideBar>
      </div>
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <div class="entity-event">
          <div class="entity">
            <div class="entity-title">
              <h2 class="entity-title-h2"><span>实体库</span></h2>
              <button type="button" class="btn btn-small" aria-label="Left Align" @click="getEntity" id="load">
                <span class="glyphicon glyphicon-repeat" aria-hidden="true">刷新</span>
              </button>
              <button type="button" class="btn btn-small" aria-label="Left Align" @click="addEntity" id="add">
                <span class="glyphicon glyphicon-plus" aria-hidden="true">添加</span>
              </button>
            </div>
            <div class="entity-item">
              <div class="entity-item-head">
                <ul>
                  <li>实体名</li>
                  <li>数据库地址</li>
                  <li>表名</li>
                  <li>属性</li>
                  <li>管理</li>
                </ul>
              </div>
              <ul class="entity-item-list">
                <li v-for="item in entityLi">
                  <div class="entity-tab">
                   {{item.displayName}}
                    <input v-model="editArr.displayName" type="text" v-if="item.displayName==nowEditCol">
                  </div>
                  <div class="entity-tab">
                    {{item.dbPosition}}
                    <input v-model="editArr.dbPosition" type="text" v-if="item.displayName==nowEditCol">
                  </div>
                  <div class="entity-tab">
                    {{item.tableName}}
                    <input v-model="editArr.tableName" type="text" v-if="item.displayName==nowEditCol">
                  </div>
                  <div class="entity-tab">
                    {{item.properties}}
                    <input v-model="editArr.properties" type="text" v-if="item.stuId==nowEditCol">
                  </div>
                  <div class="entity-tab">
                    <a href="javascript:void(0);" v-on:click="startEdit(item.displayName)" v-if="item.displayName!=nowEditCol">编辑</a>
                    <a href="javascript:void(0);" v-on:click="cancelEdit" v-if="item.displayName==nowEditCol">取消</a>
                    <a href="javascript:void(0);" v-on:click="sureEdit(item.displayName)" v-if="item.displayName==nowEditCol">确认</a>
                    <a href="javascript:void(0);" v-on:click="deleteEntity(item.displayName)">删除</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="event">
            <div class="event-title">
              <h2 class="event-title-h2"><span>事件库</span></h2>
            </div>
            <div class="entity-item">
              <div class="entity-item-head">
                <ul>
                  <li>事件名</li>
                  <li>数据库地址</li>
                  <li>表名</li>
                  <li>管理</li>
                </ul>
              </div>
              <ul class="entity-item-list">
                <li>
                  <div class="entity-tab-1">
                    people
                  </div>
                  <div class="entity-tab-2">
                    people
                  </div>
                  <div class="entity-tab-3">
                    localhost:3306/kjb
                  </div>
                  <div class="entity-tab-4">
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
  .entity-tab input{
    height:30px;
    border:1px solid #787070;
    -webkit-animation:borderAn 2s infinite;
    animation:borderAn 2s infinite;
  }
  #load{
    float:right;
    border: 1px solid #ccc;
    color: #0c0709;
    background-color: #ccc ;
    font-size: 12px;
    padding:0 5px;
    height:30px;
    line-height: 30px;
    margin:5px 10px 5px 20px;
  }
  #add{
    float:right;
    border: 1px solid #ccc;
    color: #0c0709;
    background-color: #ccc ;
    font-size: 12px;
    padding:0 5px;
    height:30px;
    line-height: 30px;
    margin:5px 10px 5px 20px;
  }
  .entity-item{
    display:table;
    width:100%;
  }
  .entity-item-head{
    display:table-header-group;
    width:100%;
  }

  .entity-item-head ul{
    display: table-row;
    width: 100%;
  }
  .entity-item-head li{
    display: table-cell;
    height: 40px;
    line-height: 40px;
    background: #605F5F;
    color: #fff;
    text-align: center;
    text-transform: uppercase;
    font-family: "moderat", sans-serif;
    letter-spacing: .25em;
  }
  .entity-item-head li{
    padding: 0 10px;
  }
  .entity-item-list {
    display: table-row-group; }
  .entity-item-list > li {
    position: relative;
    display: table-row;
    padding: 32px 0;
    background: #fff; }
  .entity-item-list > li > div {
    position: relative;
    display: table-cell;
    text-align: center;
    vertical-align: top;
    border-bottom: 1px solid #e9e9e9;
    height: 100%; }

</style>
<script>
  import MyHeader from './../components/MyHeader'
  import  SideBar from './../components/SideBar'
  import axios from 'axios'
  export default{
    data(){
      return{
        entityEvent:[],
        entityLi:[],
        eventLi:[],
        editStatus:false,
        nowEditCol:-1,
        addOne:{'displayName':'','dbPosition':'','tableName':'','entityType':'','properties':''},
      }
    },
    mounted(){
      this.getEntity();
    },
    components:{
      MyHeader,
      SideBar
    },
    computed:{
        //存储当前编辑的对象
      editArr:function(){
          var edit0={};
          for(var i=0,len=this.entityLi.length;i<len;i++){
              if(this.nowEditCol===this.entityLi[i]['displayName']){
                  edit0=this.entityLi[i];
                  break;
              }
          }
          return{
            'id':edit0.id,
            'displayName':edit0.displayName,
            'dbPosition':edit0.dbPosition,
            'entityType':edit0.entityType,
            'properties':edit0.properties
          }

      }
    },
    watch:{
        entityEvent:function(val) {
          this.entityLi = val.filter(function (item) {
            return item.entityType == 0;
          });
          this.eventLi = val.filter(function (item) {
            return item.entityType == 1;
          })
        }
    },
    methods: {
      startEdit(name) {
        this.nowEditCol = name;
      },
      cancelEdit() {
        this.nowEditCol = -1;
      },
      sureEdit(name) {
        for (var i = 0, len = this.entityLi.length; i < len; i++) {
          if (name === this.entityLi[i]['displayName']) {
            this.entityLi.splice(i, 1, this.editArr);
            break;
          }
        }
        this.nowEditCol = -1;
      },
      deleteEntity(name){
        for(var i=0,len=this.entityLi.length;i<len;i++){
          if(name=== this.entityLi[i]['displayName'] ){
            this.entityLi.splice(i,1);
            break;
          }
        }
      },
      addEntity(){
        var addOne={
          'displayName':this.addOne.displayName,
          'dbPosition':this.addOne.dbPosition,
          'entityType':this.addOne.entityType,
          'properties':this.addOne.properties
        };

        this.entityEvent.push(addOne);
        this.resetEntity();
      },
      resetEntity:function(){
        this.addOne={
          'displayName':'',
          'dbPosition':'',
          'tableName':'',
          'entityType':'',
          'properties':'',

        }
      },
      getEntity(){
        axios.get("kjb/entity/show").then((response) => {
          let res = response.data;
          if (res.status == 1) {
              this.entityEvent=JSON.parse(res.data);
          }
        })
      },

      deleteEntity2(id){
        axios.get("kjb/entity/delete", {
          params: {
            "entityId": id
          }
        }).then((response) => {
          let res = response.data;
          if (res.status == 1) {

          }
        })

      },
      insertEntity2(){
        axios.post("kjb/entity/insert", params).then(
          (response) => {
            let res = response.data;
            if (res.status == 1) {

            }
          }
        )
      },
      updateEntity2(){
        axios.get("kjb/entity/update", {
          params: param
        }).then((response) => {
            let res = response.data;
            if (res.status == 1) {

            }
          }
        )
      }
    }
  }
</script>
