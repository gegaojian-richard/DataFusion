<template>
  <div class="menu-wrapper">
    <template v-for="(item, index) in routes" v-if="!item.hidden&&item.children">

      <router-link v-if="(item.children.length===1 && !item.children[0].children )|| !item.children[1].name" :to="item.path+'/'+item.children[0].path" :key="item.children[0].name">
        <el-menu-item  style="text-align:left;" :index="item.path+'/'+item.children[0].path" :class="{'submenu-title-noDropdown':!isNest}">
          <!--<svg-icon v-if="item.children[0].meta&&item.children[0].meta.icon" :icon-class="item.children[0].meta.icon"></svg-icon>-->
          <img :src="'/static/home/' + (index - 2) + '.png'" alt="">
          <span v-if="item.children[0].meta&&item.children[0].meta.title">{{item.children[0].meta.title}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu style="text-align:left;" v-else :index="item.name||item.path" :key="item.name">
        <template slot="title">
          <!--<svg-icon  v-if="item.meta&&item.meta.icon" :icon-class="item.meta.icon"></svg-icon>-->
          <img src="/static/home/8.png" alt="">
          <span v-if="item.meta&&item.meta.title">{{item.meta.title}}</span>
        </template>

        <template v-for="child in item.children" v-if="!child.hidden">
          <sidebar-item :is-nest="true" class="nest-menu" v-if="child.children&&child.children.length>0" :routes="[child]" :key="child.path"></sidebar-item>

          <router-link v-else :to="item.path+'/'+child.path" :key="child.name">
            <el-menu-item  style=" text-align:left;" :index="item.path+'/'+child.path">
              <svg-icon  v-if="child.meta&&child.meta.icon" :icon-class="child.meta.icon"></svg-icon>
              <span v-if="child.meta&&child.meta.title">{{child.meta.title}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>

    </template>
  </div>
</template>
<style>
  .el-submenu__title i {
    color: #fff;
  }
</style>


<script>

export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    },
    isNest: {
      type: Boolean,
      default: false
    }
  },
  methods: {
  }
}
</script>

