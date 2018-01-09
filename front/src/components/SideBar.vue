<template>
  <div>
    <div class="sidebar" id="side-conn" v-bind:class="{'movein':moveIn ,'moveout':moveOut}" >
      <div id="drop" style="position: relative">
        <button type="button" class="btn btn-small " data-toggle="dropdown"  id="data-dropdrow">
          <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
          添加数据源
        </button>
        <ul class="dropdown-menu dropdown-menu-right" role="menu" id="datasource_menu" aria-labelledby="data-dropdrow" >
          <li><a href="#"  role="button"  id="mysql-info" @click="addMySql=true">mysql</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="#"  role="button"  id="oracle-info" @click="">oracle</a></li>
        </ul>
      </div>
      <div class="conn-detail">
        <dl class="user-conn-detail">
          <dt><span style="width:40px;display:inline-block">序号</span><span style="width:70px;display:inline-block">连接名</span></dt>
          <dd v-for="(value,index) in conns" style="height:50px">
            <div style="position: relative" class="dropdown">
              <span style="width:40px;display:inline-block">{{index}}</span>
              <a class="dropdown-toggle" data-toggle="dropdown" href="#"  @click="descriptDataBase(value.name)" style="width:70px;display:inline-block">{{value.name}}</a>
              <span class="glyphicon glyphicon-remove" style="width:20px;display:inline-block" @click=" deleteConnect(value.name)"></span>
              <ul class="dropdown-menu  dropdown-menu-right" role="menu" id="conn_menu">
                <li  v-for="(value,index) in databaseDetail">{{value.tablename}}</li>
              </ul>
            </div>
          </dd>
        </dl>
      </div>
    </div>
    <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':addMySql}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">添加mysql连接</div>
          <button class="md-close" @click="addMySql=false">Close</button>
        </div>
        <div class="md-content">
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="errorTip">信息填写不完整</span>
            </div>
            <div class="alert alert-warning alert-dismissible" role="alert" v-show="createError">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <strong>创建连接失败</strong>
            </div>
            <ul>
              <li class="regi_form_input">
                <input type="text" tabindex="1" name="dataUrl" v-model="dataUrl" class="regi_login_input regi_login_input_left" placeholder="数据库URL" data-type="loginname">
              </li>
              <li class="regi_form_input noMargin">
                <i class="icon IconPeople"></i>
                <input type="text" tabindex="2"  name="dataUserName" v-model="dataUserName" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="用户名">
              </li>
              <li class="regi_form_input noMargin">
                <i class="icon IconPwd"></i>
                <input type="password" tabindex="3"  name="dataPassword" v-model="dataPassword" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="密码">
              </li>
              <li class="regi_form_input noMargin">
                <input type="text" tabindex="4"  name="displayName" v-model="displayName" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="连接名">
              </li>
            </ul>
          </div>
          <div class="login-wrap">
            <a href="javascript:void(0)" class="btn-login" @click="addMysqlConnect">连接</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
  .sidebar {
    display: none;
  }
  @media (min-width: 768px) {
    .sidebar {
      position: fixed;
      top: 51px;
      bottom: 0;
      left: -220px;
      z-index: 100;
      display: block;
      padding: 20px;
      overflow-x: hidden;
      overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
      background-color: #f5f5f5;
      border-right: 1px solid #eee;
      transition: transform 1s;
      -moz-transition: -moz-transform 1s;
      -webkit-transition: -webkit-transform 1s;
      -o-transition: -o-transform 1s;
    }

    .moveout {
      transform: translateX(200px);
      -moz-transform: translateX(200px);
      -webkit-transform: translateX(200px);
      -o-transform: translateX(200px);
    }

    .movein {
      transform: translateX(-200px);
      -moz-transform: translateX(-200px);
      -webkit-transform: translateX(-200px);
      -o-transform: translateX(-200px);
    }
    #datasource_menu{
      margin-right: 10px;
      min-width: 120px;
    }
    #data-dropdrow{
      border: 1px solid #ccc;
      color: #0c0709;
      background-color: #ccc ;
      font-size: 12px;
      padding:0 5px;
      height:40px;
      line-height: 40px;
      margin:5px 10px 5px 20px;
    }
    #datasource_menu{
      float:right;
    }
    .alert-warning{
      position:fixed;
      width:200px;
      height:100px;
      left:auto;
      right:auto;
      top:300px;
    }
    #conn_menu{
      float:right;
      margin-right: 10px;
      min-width: 120px;
    }

    #conn_menu li{
      text-align:center;
      border-bottom:1px solid #3333 ;
    }
    #conn_menu li:last-child{
      border-bottom:none;
    }
  }

</style>
<script>
  import {mapState} from 'vuex'
  import axios from 'axios'
  export default{
    data(){
      return {
        addMySql: false,
        dataUrl: "localhost:3306/kjb",
        dataUserName: "root",
        dataPassword: "tangsy",
        displayName:"first",
        errorTip: false,
        createError:false,
        conns:[],
        databaseDetail:[],
        tableDetail:[]
      }
    },
    mounted(){
        this.getConnect();
    },
    computed: {
      ...mapState(['moveIn', 'moveOut'])
    },
    methods: {
        addMysqlConnect(){
            if(!this.dataUrl||!this.dataPassword||!this.dataUrl||!this.displayName){
                this.errorTip=true;
                return;
            }
            var param={
              id:this.displayName,
              displayName:this.displayName,
              type:"mysql",
              url:this.dataUrl,
              user:this.dataUserName,
              pwd:this.dataPassword

          };
          axios.post("/kjb/cms/creationDataBase", param
          ).then((response)=>{
              var res=response.data;
              if(res.status==1){
                 this.getConnect();
                 this.addMySql=false;
              }else{
                  this.createError=true;

              }
          })

        },
        deleteConnect(name){
          let param=new URLSearchParams();
          param.append("nick",name);
          axios.post("/kjb/cms/deletionDataBase",param).then((response)=>{
              var res=response.data;
              if(res.status==1){
                  this.getConnect();
              }
          })
        },
        getConnect(){
          axios.get("/kjb/cms/currentDataBase").then((response)=>{
            var res=response.data;
            if(res.status==1){
                var jsonConns=JSON.parse(res.data);
                this.conns=jsonConns.databases;
            }else{
              this.createError=true;
            }

          })


        },
        descriptDataBase(param){
          axios.get("/kjb/cms/descriptionDataBase",{
              params:{
                  "nick":param
              }
          }).then((response)=>{
              var res=response.data;
              if(res.status==1){
                var jsondata=JSON.parse(res.data);
                this.databaseDetail=jsondata.tableStructures;
              }
          })

      },
        descriptTable(){
          axios.get("/kjb/cms/preview",{

          }).then((response)=>{

          })

      }
    }
  }
</script>
