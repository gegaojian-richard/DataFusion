<template>
  <div class="login-container" :style="note">
    <el-form class="login-form" autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left">
      <div class="title-container">
        <h3 class="title">数据整合工具</h3>
      </div>
      <div class="form-window">
        <el-form-item prop="username">
          <!--<span class="svg-container svg-container_login">-->
            <!--<svg-icon icon-class="user" />-->
          <!--</span>-->
          <img class="svg-container svg-container_login" src="/static/login/user.png" alt="">
          <el-input style=" background: transparent;" name="username" type="text" v-model="loginForm.username" autoComplete="on" placeholder="username" />
        </el-form-item>

        <el-form-item prop="password">
          <img class="svg-container svg-container_login" src="/static/login/lock.png" alt="">
          <el-input  style=" background: transparent;" name="password" :type="passwordType" @keyup.enter.native="handleLogin" v-model="loginForm.password" autoComplete="on" placeholder="password" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon icon-class="eye" />
          </span>
        </el-form-item>
        <el-button type="primary" style="width:45%;margin-bottom:10px;margin-top: 14px;height:50px;background-color: #5ACD70;" :loading="loading" @click.native.prevent="handleLogin">登录</el-button>
        <el-button class="thirdparty-button" type="primary"  @click="registerFlag=true">注册</el-button>
      </div>
    </el-form>

    <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':registerFlag}">
      <div class="md-modal-inner">
        <div class="md-top">
          <span class="md-title">注册</span>
          <button class="md-close" @click="registerFlag=false">Close</button>
        </div>
        <div class="md-content">
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="regErrorTip">{{regErrorTip}}</span>
            </div>
            <ul>
              <li class="regi_form_input" style="background: url('/static/login/user.png') 10px center no-repeat;background-size: 18px 20px;">
                <input type="text" tabindex="1" name="loginname" v-model="regName" class="regi_login_input regi_login_input_left" placeholder="用户名" data-type="loginname">
              </li>
              <li class="regi_form_input noMargin" style="background: url('/static/login/lock.png') 10px center no-repeat;background-size: 18px 20px;">
                <input type="password" tabindex="2"  name="password" v-model="regPwd1" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="密码（不小于5位）" >
              </li>
              <li class="regi_form_input noMargin" style="background: url('/static/login/lock.png') 10px center no-repeat;background-size: 18px 20px;">
                <input type="password" tabindex="3"  name="password" v-model="regPwd2" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="确认密码" >
              </li>
            </ul>
          </div>
          <div class="login-wrap">
            <!--<el-button href="javascript:void(0)" class="btn-login" v-on:click="register" style="width:340px;">注册</el-button>-->
            <a href="javascript:void(0)" class="btn-login" @click="register">注册</a>
          </div>
        </div>
      </div>
    </div>
    <div class="md-overlay" v-if="registerFlag" @click="registerFlag=false"></div>
  </div>
</template>
<style>
  .el-input input {
    background: transparent;
    border: 0px;
    -webkit-appearance: none;
    border-radius: 0px;
    padding: 12px 5px 12px 15px;
    color:#bcbcbc;
    height: 47px;
  }
  .el-input input :-webkit-autofill{
    -webkit-box-shadow: 0 0 0px 1000px #2d3a4b inset !important;
    -webkit-text-fill-color: #fff !important;
  }
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
  $bg:#2d3a4b;
  $light_gray:#eee;
  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      background: transparent;
      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        &:-webkit-autofill {
          -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: #fff !important;
        }
      }
    }
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: #fff;
      border-radius: 5px;
      color: #454545;
      margin-bottom: 30px;
    }
  }
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../assets/scss/mixin.scss";
  $bg:#2d3a4b;
  $dark_gray:#889aa4;
  $light_gray:#eee;
  .login-container {
   //@include relative;
    position:relative;
    top:0;
    height: 100vh;
    .login-form {
      position: absolute;
      left: 50%;
      right: 0;
      top: 50%;
      width: 450px;
      margin-top: -175px;
      margin-left: -225px;
      .form-window {
        padding: 35px;
        background-color: #34B2ED;
        border-radius: 3px;
      }
    }
    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }
    .svg-container {
      vertical-align: middle;
      width: 18px;
      height: 20px;
      display: inline-block;
    }
    .title-container {
      position: relative;
      .title {
        font-size: 26px;
        font-weight: 400;
        color: $light_gray;
        margin: 0px auto 20px auto;
        text-align: center;
        font-weight: bold;
      }
      .set-language {
        color: #fff;
        position: absolute;
        top: 5px;
        right: 0px;
      }
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
    .thirdparty-button {
      width: 45%;
      margin-left: 20px;
      height: 50px;
      background-color: #417AED;
    }
    .md-modal {
      overflow: hidden;
      border-radius: 5px;
      width:425px;
    }
    .md-modal .md-modal-inner .md-top{
      width:100%;
      height: 50px;
      line-height: 50px;
      background-color: #266CB4;
      color: #fff;
      .md-title {
        position: absolute;
        top: 0px;
        left: 20px;
        line-height: 50px;
        padding: 0;
        color: #333;
        font-size: 18px;
        font-weight: 400;
        font-style: normal;
        color: #Fff;
      }
    }
    .md-modal .md-modal-inner {
      padding: 0px;
    }
    .md-modal .md-modal-inner .md-content {
      padding: 30px 30px 50px 30px;
      .btn-login {
        height: 50px;
        line-height: 50px;
        border: 2px solid  #5ACD70;
        background: #5ACD70;
      }
    }
  }
</style>
<script>
  import { mapGetters } from 'vuex'
  import axios from 'axios'
export default {
  name: 'login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'admin',
        password: '123456',
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      loading: false,
      showDialog: false,
      registerFlag: false,
      regErrorTip:null,
      regName: null,
      regPwd1: null,
      regPwd2:null,
      note:{
        background: "url("+require("./../../image/loginbg.png")+")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%",
      }
    }

  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('LoginByUsername', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: '/' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    register(){
      if(!this.regName || !this.regPwd1 ||!this.regPwd2||this.regPwd1!=this.regPwd2){
        this.regErrorTip="填写不规范";
        return;
      }else if(this.regPwd1.length<5||this.regPwd1.length>10) {
        this.regErrorTip = "密码长度不在5位至10位之间，请重新填写";
        return;
      }
//      var  param={
//      "username":this.regName,
//      "password":this.regPwd2
//      };
      let param=new URLSearchParams();
      param.append("username",this.regName);
      param.append("password",this.regPwd2);
        axios.post("/kjb/ums/register",param).then((response)=>{
          let res=response.data;
        if(res.status==1){
          this.regErrorTip=null;
          this.registerFlag=false;
          this.regSuc=true;
          this.loginForm.username=this.regName;
          this.loginForm.password=this.regPwd2;
        }else{
          this.regErrorTip=true;
        }
      }).catch(function(e){ this.regErrorTip="该账号已被申请，请重新设置";})
      }
  }
}
</script>


