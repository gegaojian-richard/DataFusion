<template>
  <div>
    <MyHeader></MyHeader>
    <div class="row">
      <div class="col-sm-3 col-md-2 sidebar" id="side-conn">
        <SideBar></SideBar>
      </div>
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <div class="entity-event">
          <div class="entity">
            <div class="entity-title">
              <h2 class="entity-title-h2"><span>实体库</span></h2>
            </div>
            <div class="entity-item">
              <div class="entity-item-head">
                <ul>
                  <li>实体名</li>
                  <li>数据库地址</li>
                  <li>表名</li>
                  <li>管理</li>
                </ul>
              </div>
              <ul class="entity-item-list">
                <li>
                  <div class="entity-tab-1">
                    people
                  </div>
                  <div class="entity-tab-2">
                    people
                  </div>
                  <div class="entity-tab-3">
                    localhost:3306/kjb
                  </div>
                  <div class="entity-tab-4">
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="event">
            <div class="event-title">
              <h2 class="event-title-h2"><span>事件库</span></h2>
            </div>
            <div class="entity-item">
              <div class="entity-item-head">
                <ul>
                  <li>事件名</li>
                  <li>数据库地址</li>
                  <li>表名</li>
                  <li>管理</li>
                </ul>
              </div>
              <ul class="entity-item-list">
                <li>
                  <div class="entity-tab-1">
                    people
                  </div>
                  <div class="entity-tab-2">
                    people
                  </div>
                  <div class="entity-tab-3">
                    localhost:3306/kjb
                  </div>
                  <div class="entity-tab-4">
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
  .entity-item{
    display:table;
    width:100%;
  }
  .entity-item-head{
    display:table-header-group;
    width:100%;
  }

  .entity-item-head ul{
    display: table-row;
    width: 100%;
  }
  .entity-item-head li{
    display: table-cell;
    height: 40px;
    line-height: 40px;
    background: #605F5F;
    color: #fff;
    text-align: center;
    text-transform: uppercase;
    font-family: "moderat", sans-serif;
    letter-spacing: .25em;
  }
  .entity-item-head li{
    padding: 0 10px;
  }
  .entity-item-list {
    display: table-row-group; }
  .entity-item-list > li {
    position: relative;
    display: table-row;
    padding: 32px 0;
    background: #fff; }
  .entity-item-list > li > div {
    position: relative;
    display: table-cell;
    text-align: center;
    vertical-align: top;
    border-bottom: 1px solid #e9e9e9;
    height: 100%; }

</style>
<script>
  import MyHeader from './../components/MyHeader'
  import  SideBar from './../components/SideBar'
  export default{
      data(){
          return{

          }
      },
    components:{
          MyHeader,
          SideBar
    },
    methods:{

    }
  }
</script>
