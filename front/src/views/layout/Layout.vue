<template>
  <div class="app-wrapper" :class="{hideSidebar:!sidebar.opened}">
    <sidebar class="sidebar-container" ></sidebar>
    <div class="main-container">
      <navbar class="nav-container"></navbar>
      <tags-view class="tag-container"></tags-view>
      <app-main class="app-main"></app-main>
    </div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../assets/scss/mixin";
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    .sidebar-container {
      background-color: #1E73C4;
      z-index: 199!important;
    }
  }
  .nav-container{
    position:fixed;
    width:100%;
    top:0px;
    z-index: 199!important;
  }
  .tag-container{
    position:fixed;
    width:100%;
    top:60px;
    height:50px;
    z-index: 199!important;
  }
  .app-main{
    padding-top: 95px;

  }
  </style>
<script>
 import Navbar from './components/Navbar.vue'
 import TagsView from './components/TagsView.vue'
 import Sidebar from './components/SideBar/index.vue'
 import  AppMain from './components/AppMain.vue'

 export default {
    components: {
      Navbar,
      TagsView,
      Sidebar,
      AppMain
    },
  computed: {
  sidebar() {
    return this.$store.state.app.sidebar
  }
  }
  }
</script>
