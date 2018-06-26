<template>
  <div>
    <div class="menu">
      <p class="title">连接信息</p>
      <div style="background: #DFE2EB;">
        <div id="dropdelete" style="display:inline-block;position:relative;">
          <button type="button" class="data-dropdrow" data-toggle="dropdown"  >
            <img src="/static/home/subtraction.png">
            删除
          </button>
          <ul class="dropdown-menu " role="menu" id="datasource" aria-labelledby="data-dropdrow" >
            <li v-for="(item,index) in conRewrite"><a href="#"  role="button"  @click="deleteConnect(item.id)">{{item.displayName}}</a></li>
          </ul>
        </div>
        <div id="dropadd" style="display:inline-block;position:relative;margin-left: 20px;">
          <button type="button" class="data-dropdrow " data-toggle="dropdown" >
            <img src="/static/home/add.png">
            添加
          </button>
          <ul class="dropdown-menu dropdown-menu-right" role="menu" id="datasource_menu" aria-labelledby="data-dropdrow" >
            <li><a href="#"  role="button"  id="mysql-info" @click="addMySql=true">mysql</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#"  role="button"  id="oracle-info" @click="">oracle</a></li>
          </ul>
        </div>
      </div>
      <div class="showtree" >
        <el-tree
          class="filter-tree"
          :data="conRewrite"
          :props="defaultProps"
          default-expand-all
          @node-click="handleNodeClick"
          style="background-color:#1F5FA6;color:#fff;"
        >
        </el-tree>
      </div>
    </div>
    <div class="md-modal modal-msg md-modal-transition" style="width:400px;"  v-bind:class="{'md-show':addMySql}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">添加mysql连接</div>
          <button class="md-close" @click="addMySql=false">Close</button>
        </div>
        <div class="md-content" >
          <div class="confirm-tips">
            <div class="error-wrap">
              <span class="error error-show" v-show="errorTip">信息填写不完整</span>
            </div>
            <div class="alert alert-warning alert-dismissible" role="alert" v-show="createError">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <strong>创建连接失败</strong>
            </div>
            <ul>
              <li class="regi_form_input noMargin">
                <input type="text" tabindex="1"  name="displayName" v-model="displayName" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="连接名">
              </li>
              <li class="regi_form_input">
                <input type="text" tabindex="2" name="dataUrl" v-model="dataUrl" class="regi_login_input regi_login_input_left" placeholder="数据库URL" data-type="loginname">
              </li>
              <li class="regi_form_input noMargin">
                <!--<i class="icon IconPeople"></i>-->
                <img src="/static/login/user.png" alt="">
                <input type="text" tabindex="3"  name="dataUserName" v-model="dataUserName" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="用户名">
              </li>
              <li class="regi_form_input noMargin">
                <img src="/static/login/lock.png" alt="">
                <input type="password" tabindex="4"  name="dataPassword" v-model="dataPassword" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="密码">
              </li>

            </ul>
          </div>
          <div class="login-wrap">
            <a href="javascript:void(0)" class="btn-login" @click="addMysqlConnect">连接</a>
          </div>
        </div>
      </div>
    </div>
    <div class="md-overlay" v-if="addMySql" @click="addMySql=false"></div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss">
  @import "../../assets/scss/mixin";
  .dropdown-menu {
    text-align: center;
    min-width: 120px;
  }
.menu{
  height:100%;
  /*border-left:1px solid #1a1a1a;*/
  /*background-color:#304156 ;*/
  background-color: #1F5FA6;
  color:#bfcbd9;
  font-size: large;
  .title{
    height: 40px;
    text-align: center;
    line-height: 60px;
    font-size: 18px;
    color: #145398;
    background: #DFE2EB;
    font-weight:bold;
  }
}
.regi_form_input img{
  float: left;
  width: 16px;
  height: 18px;
  margin: 12px 0 0 14px;
}

.data-dropdrow{
  border:transparent;
  color: #1E73C5;
  font-size: 14px;
  height:40px;
  line-height: 40px;
}
  .showtree{
    clear: both;
  }
.el-tree-node__content{
  height: 56px;
}
.el-tree-node__content:hover {
  background-color:#4686C4;
}
.el-tree-node:focus>.el-tree-node__content {
  background-color:#4686C4;
}
.dropdown-menu{
  z-index: 1002;
}
  .md-modal {
    overflow: hidden;
    border-radius: 5px;
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

</style>
<script>
  import {mapGetters} from 'vuex'
  import ScrollBar from '@/components/ScrollBar'
  import axios from 'axios'
  export default {
    components: {ScrollBar},
    computed: {
      ...mapGetters(['conns']),
      conRewrite: function () {
        var temp = []
        for (var i = 0; i < this.conns.length; i++) {
          temp[i] = {};
          temp[i].displayName = this.conns[i].displayName
          temp[i].id = this.conns[i].id
          temp[i].tables = [];
          for (var j = 0; j < this.conns[i].tables.length; j++) {
            temp[i].tables[j] = {}
            temp[i].tables[j].displayName = this.conns[i].tables[j].tableName
            temp[i].tables[j].id = this.conns[i].id
          }
        }
        return temp;
      }
    },
    mounted(){
      this.$store.dispatch('GetConnect');
    },
    data() {
      return {
        defaultProps: {
          children: 'tables',
          label: 'displayName'
        },
        isCollapse: false,
        addMySql: false,
        dataUrl: "114.212.84.208:3306/fupin",
        dataUserName: "root",
        dataPassword: "iipconfig",
        displayName:"first",
        createError:false,
        errorTip: false,
      }
    },
    methods: {
      handleNodeClick(data){
        var emitdata = {
          database: data.id,
          table: data.displayName
        }
        if(!data.tables){
          this.$emit('previewtable', emitdata);    //传递给父组件选中的表格
        }
      },
      addMysqlConnect(){
        if(!this.dataUrl||!this.dataUrl||!this.displayName){
          this.errorTip=true;
          return;
        }
        var param = {
          //id:this.mysqlform.conname,
          displayName:this.displayName,
          type:"mysql",
          url:this.dataUrl,
          user:this.dataUserName,
          pwd:this.dataPassword
        };
        axios.post("/kjb/cms/creationDataBase", param
        ).then((response) => {
          var res = response.data;
          if (res.status == 1) {
            this.$store.dispatch('GetConnect')
            this.addMySql = false;
          } else {
            this.createError = true;

          }
        }).catch((e)=>{this.createError=true;})
      },
      deleteConnect(name){
        let param=new URLSearchParams();
        param.append("nick",name);
        axios.post("/kjb/cms/deletionDataBase",param).then((response)=>{
          var res=response.data;
          if(res.status==1){
            this.$store.dispatch('GetConnect')
          }
        })
      }
    }
  }
</script>
