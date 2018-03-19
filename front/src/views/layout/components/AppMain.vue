<template>
  <section class="app-main" style="height: 100%;">
    <transition name="fade" mode="out-in" style="height:100% ">
      <keep-alive :include="cachedViews" style="height:100%">
        <router-view style="height:100%"></router-view>
      </keep-alive>
    </transition>
  </section>
</template>
<style>

</style>
<script>
  export default {
    name: 'AppMain',
    computed: {
      cachedViews() {
        return this.$store.state.tagsView.cachedViews
      }
      // key() {
      //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
      // }
    }
  }
</script>
