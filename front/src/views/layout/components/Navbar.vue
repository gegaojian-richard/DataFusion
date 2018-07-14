<template>
  <el-menu class="navbar" mode="horizontal" style="color:#fff;">
    <hamburger   class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>

    <breadcrumb class="breadcrumb-container"></breadcrumb>
    <a class="navbar-brand" href="/">数据整合工具</a>
    <a href="/#/login/" style="float:right;margin-right: 250px;" v-show="!this.name">登录</a>
    <a v-show="this.name">{{this.name}}</a>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger,
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'avatar',
      'name'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
  },
  logOut(){
    axios.post("/kjb/ums/logout").then((response)=>{
      let res = response.data;
      if(res.status==1){
        this.nickName="";
      }
    });
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 70px;
  line-height: 70px;
  border-radius: 0px !important;
  background: url('/static/home/bg.png') no-repeat;
  background-size: 100% 100%;
  color: #fff;
  margin-left: -1px;
  margin-top: -1px;
  .hamburger-container {
    height: 70px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 70px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
  .navbar-brand {
    height: 70px;
    line-height: 70px;
    padding: 0px 0px 0px 15px;
    font-size: 22px;
  }
}
a:hover {
  color: #fff;
}
</style>
