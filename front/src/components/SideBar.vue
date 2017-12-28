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
    </div>
    <div class="md-modal modal-msg md-modal-transition" v-bind:class="{'md-show':addMySql}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">Login in</div>
          <button class="md-close" @click="addMySql=false">Close</button>
        </div>
        <div class="md-content">
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="errorTip">信息错误</span>
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
            </ul>
          </div>
          <div class="login-wrap">
            <a href="javascript:;" class="btn-login" @click="addConnect">连接</a>
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
  }

</style>
<script>
  import {mapState} from 'vuex'
  export default{
    data(){
      return {
        addMySql: false,
        dataUrl: "",
        dataUserName: "",
        dataPassword: "",
        errorTip: false
      }
    },
    computed: {
      ...mapState(['moveIn', 'moveOut'])
    },
    methods: {
        addConnect(){

        }
    }
  }
</script>
