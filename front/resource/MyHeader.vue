<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">数据融合工具</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li>
              <a href="javascript:void(0)"><span class="navbar-link" v-text="nickName" v-if="nickName"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" @click="loginModalFlag=true" v-if="!nickName">登录</a>
                <a href="javascript:void(0)" @click="logOut" v-else >退出</a>
            </li>
            <li><a href="javascript:void(0)" role="button" @click="registerFlag=true">注册</a></li>
          </ul>
        </div>
      </div>
      <div class="alert alert-warning alert-dismissible" role="alert" v-show="regSuc">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>注册成功</strong>
      </div>
    </nav>
    <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':loginModalFlag}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">Login in</div>
          <button class="md-close" @click="loginModalFlag=false">Close</button>
        </div>
        <div class="md-content">
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="loginErrorTip">用户名或者密码错误</span>
            </div>
            <ul>
              <li class="regi_form_input">
                <input type="text" tabindex="1" name="loginname" v-model="userName" class="regi_login_input regi_login_input_left" placeholder="User Name" data-type="loginname">
              </li>
              <li class="regi_form_input noMargin">
                <input type="password" tabindex="2"  name="password" v-model="userPwd" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="Password" @keyup.enter="login">
              </li>
            </ul>
          </div>
          <div class="login-wrap">
            <a href="javascript:;" class="btn-login" @click="login">登  录</a>
          </div>
        </div>
      </div>
    </div>
    <div class="md-overlay" v-if="loginModalFlag" @click="loginModalFlag=false"></div>
    <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':registerFlag}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">注册</div>
          <button class="md-close" @click="registerFlag=false">Close</button>
        </div>
        <div class="md-content">
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="regErrorTip">密码错误</span>
            </div>
            <ul>
              <li class="regi_form_input">
                <input type="text" tabindex="1" name="loginname" v-model="regName" class="regi_login_input regi_login_input_left" placeholder="用户名" data-type="loginname">
              </li>
              <li class="regi_form_input noMargin">
                <input type="password" tabindex="2"  name="password" v-model="regPwd1" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="密码（不小于5位）" >
              </li>
              <li class="regi_form_input noMargin">
                <input type="password" tabindex="3"  name="password" v-model="regPwd2" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="确认密码" >
              </li>
            </ul>
          </div>
          <div class="login-wrap">
            <a href="javascript:;" class="btn-login" @click="register">注册</a>
          </div>
        </div>
      </div>
    </div>
    <div class="md-overlay" v-if="loginModalFlag" @click="loginModalFlag=false"></div>
  </div>
</template>
<style>
  .alert-warning{
    position:fixed;
    width:200px;
    height:100px;
    left:auto;
    right:auto;
    top:300px;
  }
</style>
<script>
 // import '../assets/home.css'
 // import '../assets/css/login.css'
  import { mapState } from 'vuex'
  import axios from 'axios'
  export default{
    name:'Header',
    data(){
      return {
        userName: 'admin',
        userPwd: '123456',
        loginModalFlag: false,
        registerFlag: false,
        loginErrorTip: false,
        regErrorTip:false,
        regName: '',
        regPwd1: '',
        regPwd2: '',
        loginSuc:false,
        regSuc:false,
        nickName:null
      }
    },
    computed:{
   //   ...mapState(['moveIn','moveOut'])
    },
    methods:{
//        changeMove(){
//            if(this.moveIn){
//                this.$store.commit("updateMoveIn",false);
//                this.$store.commit("updateMoveOut",true);
//            }else if(!this.moveIn){
//              this.$store.commit("updateMoveIn",true);
//              this.$store.commit("updateMoveOut",false);
//            }
//        },
        login(){
          if(!this.userName || !this.userPwd){
              this.loginErrorTip=true;
              return;
          }
          let param=new URLSearchParams();
          param.append("username",this.userName);
          param.append("password",this.userPwd);
          axios.post("/kjb/ums/login",param).then((response)=>{
              let res=response.data;
              if(res.status==1){
                  this.loginErrorTip=false;
                  this.loginModalFlag=false;
                  this.nickName=this.userName;
                  this.loginSuc=true;
              }else{
                  this.loginErrorTip=true;
              }
          })

        },
        logOut(){
          axios.post("/kjb/ums/logout").then((response)=>{
            let res = response.data;
            if(res.status==1){
              this.nickName="";
            }
          });

        },
      register(){
        if(!this.regName || !this.regPwd1 ||!this.regPwd2||this.regPwd1!=this.regPwd2){
            this.regErrorTip=true;
            return;
        }
        let param=new URLSearchParams();
        param.append("username",this.regName);
        param.append("password",this.regPwd2);
        axios.post("/kjb/ums/register",param).then((response)=>{
          let res=response.data;
          if(res.status==1){
            this.regErrorTip=false;
            this.registerFlag=false;
            this.regSuc=true;
          }else{
            this.regErrorTip=true;
          }
        })

      }
    }
  }
</script>
