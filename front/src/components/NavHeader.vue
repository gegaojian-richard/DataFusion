<template>
    <header class="header">
        <div class="navbar">
            <div class="navbar-left-container">
              <a href="/">
                <img class="navbar-brand-logo" src="/static/logo.png">
              </a>
            </div>
            <div class="navbar-right-container" style="display: flex;">
              <div class="navbar-menu-container">
                <span class="navbar-link" v-text="nickName" v-if="nickName"></span>
                <a href="javascript:void(0)" class="navbar-link" @click="loginModalFlag=true" v-if="!nickName">Login</a>
                <a href="javascript:void(0)" class="navbar-link" @click="logOut" v-else>Logout</a>
                <div class="navbar-cart-container">
                  <span class="navbar-cart-count" v-text="cartCount" v-if="cartCount"></span>
                  <a class="navbar-link navbar-cart-link" href="/#/cart">
                    <svg class="navbar-cart-logo">
                      <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-cart"></use>
                    </svg>
                  </a>
                </div>
              </div>
            </div>
        </div>
        <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':loginModalFlag}">
          <div class="md-modal-inner">
            <div class="md-top">
              <div class="md-title">Login in</div>
              <button class="md-close" @click="loginModalFlag=false">Close</button>
            </div>
            <div class="md-content">
              <div class="confirm-tips">
                <div class="error-wrap">
                  <span class="error error-show" v-show="errorTip">用户名或者密码错误</span>
                </div>
                <ul>
                  <li class="regi_form_input">
                    <i class="icon IconPeople"></i>
                    <input type="text" tabindex="1" name="loginname" v-model="userName" class="regi_login_input regi_login_input_left" placeholder="User Name" data-type="loginname">
                  </li>
                  <li class="regi_form_input noMargin">
                    <i class="icon IconPwd"></i>
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
    </header>
</template>
<style>
  .header {
    width: 100%;
    background-color: white;
    font-family: "moderat",sans-serif;
    font-size: 16px;
  }
  .navbar {
    display: flex;
    justify-content: space-between;
    align-content: center;
    width: 100%;
    height: 70px;
    max-width: 1280px;
    margin: 0 auto;
    padding: 5px 20px 10px 20px;
  }
  .navbar-left-container {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-left: -20px;
  }
  .navbar-brand-logo {
    /*width: 120px;*/
  }
  .header a, .footer a {
    color: #666;
    text-decoration: none;
  }
  a {
    -webkit-transition: color .3s ease-out;
    transition: color .3s ease-out;
  }
  .navbar-right-container {
    display: none;
    justify-content: flex-start;
    align-items: center;
  }
  .navbar-menu-container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-top: 10px;
  }
  .navbar-link {
    padding-left: 15px;
  }
  .navbar-cart-container {
    position: relative;
  }
  .navbar-cart-count {
    justify-content: center;
    align-items: center;
    position: absolute;
    top: -9px;
    right: -11px;
    width: 20px;
    border-radius: 10px;
    color: white;
    background-color: #eb767d;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
  }
  .navbar-cart-logo {
    width: 25px;
    height: 25px;
    transform: scaleX(-1);
  }
</style>
<script>
    import './../assets/css/login.css'
    export default{
        data(){
            return{
              userName:'admin',
              userPwd:'123456',
              errorTip:false,
              loginModalFlag:false
            }
        },

        /*nickName(){
          return this.$store.state.nickName;
        },
        cartCount(){
          return this.$store.state.cartCount;
        }*/
        mounted(){
            this.checkLogin();
        },
        methods:{
            checkLogin(){

            },
            login(){

            },
            logOut(){

            },
            getCartCount(){

            }
        }
    }
</script>
