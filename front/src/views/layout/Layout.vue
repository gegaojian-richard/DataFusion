<template>
  <div class="app-wrapper" :class="{hideSidebar:!sidebar.opened}">
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar></navbar>
      <tags-view></tags-view>
      <app-main></app-main>
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
