<template>
<div style="height:100%;">
  <fusion-side  @selectentity="selectEntity" style="height:100%;width:180px;position:fixed;overflow: auto"></fusion-side>
  <div style="padding: 20px;margin-left:180px;overflow: visible;">
    <div style="border:1px solid #ccc;padding: 0 20px 50px 20px;background-color:#fff;">
      <el-tabs v-model="activeName2" type="card" @tab-click="handleClick" style="margin-top:20px;">
        <el-tab-pane label="步骤一：数据源配置" name="first">
          <div>
            <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
              <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
              <span>步骤一：数据源配置</span>
            </p>
            <div class="showpanel" style="overflow: auto">
              <div v-for="conn in conRewrite" class="relations">
                <div class='tablecolumn' v-for="table in conn.tables" v-show="table.show">
                  <span style="line-height: 50px; font-size: larger">{{table.id}}--{{table.displayName}}</span>
                  <div style="display:inline" v-for="connect in selectTableProp">
                    <div style="display:inline" v-for="tab in connect.data" >
                      <div class="forselect"
                           v-bind:title="''+table.id+':'+table.displayName"
                           draggable="true"
                           @dragstart='drag($event,connect.id,tab.tableName,column.name)'
                           v-show="connect.id==table.id && tab.tableName==table.displayName && table.show"
                           v-for=" column in tab.colmnuStructures">
                        {{column.name}}-{{column.type}}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <el-button style="margin-top:10px;position:relative;background: #A6CF65;color:#fff;float:right;" type="primary" plain @click="clearChoose">重置</el-button>
            <el-button style="margin-top:10px;position:relative;background-color: #7BC2F8;color:#fff;float:right;margin-right: 20px;" type="primary" plain @click="addFusiondata">添加整合单元</el-button>
          </div>
          <div style="margin-top:50px;">
            <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
              <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
              <span>步骤二：{{selectEntityInfo.displayName}}映射关系配置</span>
            </p>
            <div class="showpanel">
              <div>
                <div  class='relation' @drop='drop($event,item.name)' @dragover='allowDrop($event)'  v-for="item in selectEntityInfo.properties" style="height:50px;border-bottom: 1px dashed #b8b8b8">
                  <span style="line-height: 50px; font-size: larger;margin-right:10px">{{item.name}}--{{item.type}}</span>
                  <span v-if="item.prime==1">（主键）</span>
                </div>
              </div>
            </div>
            <el-button style="margin-top:10px;position:relative;background: #A6CF65;color:#fff;float:right;" type="primary" plain @click="removerelation1">重置</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="步骤二：相交关系" name="third">
          <div>
            <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
              <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
              <span>步骤三：相交关系</span>
            </p>
            <div class="showpanel">
              <div v-for="n in (relations.length-1)" class="xiangjiao">
                <span style="width:100px;">left</span>
                <el-cascader
                  :options="join_units"
                  v-model="relations[n-1].left"
                  @change="handleChange" style="left:0px;">
                </el-cascader>
                <span style="width:100px;">join on </span>
                <span style="width:100px;">right</span>
                <el-cascader
                  :options="join_units"
                  v-model="relations[n-1].right"
                  @change="handleChange">
                </el-cascader>
              </div>
            </div>
            <el-button style="margin-top:10px;position:relative;background-color:#7BC2F8;color:#fff;float: right;" type="primary" plain @click="sendmessage">提交</el-button>
            <el-button style="margin-top:10px;position:relative;background: #A6CF65;color:#fff;float: right;margin-right: 20px;" type="primary" plain @click="removerelation2">重置</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    </div>
    <div>
      <!--连接需要连接的实体-->
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
                  <i class="icon IconPeople"></i>
                  <input type="text" tabindex="3"  name="dataUserName" v-model="dataUserName" class="regi_login_input regi_login_input_left login-input-no input_text" placeholder="用户名">
                </li>
                <li class="regi_form_input noMargin">
                  <i class="icon IconPwd"></i>
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
      <!--选择需要的实体-->
    <div class="md-modal modal-msg md-modal-transition" style="width:550px;height:500px;overflow: auto;" v-bind:class="{'md-show':selectdata}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">数据源配置</div>
          <button class="md-close" @click="selectdata=false">Close</button>
        </div>
        <div class="md-content">
          <div style="margin-bottom: 40px;">
            <el-row>
              <el-col :span="8" >
                <el-tree
                  class="filter-tree tree2"
                  :data="conRewrite"
                  :props="defaultProps"
                  default-expand-all
                  @node-click="handleNodeClick"
                >
                </el-tree>
              </el-col>
              <el-col :span="16">
                <div style="width:300px;border-left:1px solid #b8b8b8;margin-left: 20px;height: 300px;">
                  <div v-for="(tag,index) in conRewrite">
                    <el-tag
                      :key="item.displayName"
                      v-for="item in tag.tables"
                      closable
                      v-show="item.show"
                      :disable-transitions="false"
                      @close="handleClose(item)" style="float: left;">
                      {{item.id}}-{{item.displayName}}
                    </el-tag>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
            <a href="javascript:;" class="btn-login" @click="emitSelect">提交</a>
        </div>
      </div>
    </div>
    <div class="md-overlay" v-if="selectdata || addMySql" @click="selectdata=false"></div>
    </div>
</div>
</template>
<style rel="stylesheet/scss" lang="scss">
  .showpanel {
    height: 200px;
    border: 1px solid #ccc;
    border-radius: 2px;
    margin-top:10px;
    overflow-x:auto;
    overflow-y:auto;
    text-align: left;
    .relation {
      padding-left: 20px;
      color: #333;
      font-size: 14px;
      .tablecolumn{border-bottom: 1px dashed #ccc!important;}
    }
    .relation:nth-child(2n+1) {
      background-color: #F2F7FC;
    }
  }
  .el-tag {
    margin-left: 10px;
    margin-top:10px;
  }
  .el-button{
    padding: 7px 20px;
  }
  .forselect{
    display:inline-block;
    /*width:70px;*/
    height:30px;
    background: #598dff;
    border: 2px solid #c9d8ec;
    color: #665f5f;
    margin-left: 10px;
    margin-top:10px;
    border-radius: 3px;
  }
  /*悬停效果*/
  /*.forselect :hover::after{*/
    /*content: attr(data-title);*/
    /*display: inline-block;*/
    /*padding: 10px 14px;*/
    /*border: 1px solid #ddd;*/
    /*border-radius: 5px;*/
    /*position: absolute;*/
    /*top: -50px;*/
    /*left: -30px;*/
    /*z-index:1000;*/
  /*}*/
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
  .tree2 .el-tree-node__content {
    height: 30px!important;
    line-height: 30px;
  }
  .tree2 .el-tree-node__content:hover {
    background-color:#fff!important;
    color: #4686C4!important;
  }
  .tree2 .el-tree-node:focus>.el-tree-node__content {
    background-color:#fff!important;
    color: #4686C4!important;
  }
  .forselect {
    line-height: 30px;
    background: #E9F3FE;
    border:1px solid #598dff;
    color: #598dff;
    padding: 0 5px;
  }
  .showpanel .xiangjiao{
    height:50px;
    padding: 5px 0px;
    &:nth-child(2n+1){
      background:  #F2F7FC;
    }
    span {
      display: inline-block;
      text-align: center;
      color: #333;
    }
  }
  .el-cascader__label {
    left:0px;
  }
</style>

<script>
  import {mapGetters} from 'vuex'
  import axios from 'axios'
  import fusionSide from '../components/fusion/FusionSide'
  export default{
    data(){
      return {
        activeName2: 'first',
        showtips:false,
        selectdata:false,
        defaultProps: {
          children: 'tables',
          label: 'displayName'
        },
        displayDB:{
          id:null,
          table:"",
        },
        selectTableProp:[],      //选中的数据库表格信息
        selectEntityInfo:{
          displayName:"",
          properties:[]
        }, //选中的目标数据结构
        //   selectTableNum:0,
        relations:[{left:[],right:[]}],
        dom:null,      //选中的tag
        dragitem:{
          connectid:null,
          tablename:null,
          column:null
        },
        s2t:[],
        join_units:[],
        target_table_name:"",
        target_datasource_id:"",
        addMySql:false,
        dataUrl:"",//添加连接地址
        displayName:"",
        dataPassword:"",
        dataUserName:"",
        errorTip:false,
        createError:false,
      }
    },
    components: {
      fusionSide
    },
    mounted(){
      this.getConnect();
    },
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
            temp[i].tables[j].show=false;
          }
        }
        //temp[0].tables[0].show=true;
        return temp;
      }

    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
      addMysqlConnect(){
        if(!this.dataUrl||!this.dataUserName||!this.displayName){
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
            this.$store.dispatch('GetConnect');
            this.addMySql = false;
          } else {
            this.createError = true;
          }
        })
        this.getEntity();
      },
      getEntity(){
        this.$store.dispatch('GetEntity');
      },
      drag:function(event,conn,table,column){
        this.dom = event.currentTarget
        this.dragitem.connectid=conn
        this.dragitem.tablename=table
        this.dragitem.column=column
      },
      drop:function(event,entitycolumn){
        event.preventDefault();
//        console.log(event.target.lastChild.nodeName)
        if(event.target.lastChild.nodeName=="DIV"){
          return;
        }
        event.target.appendChild(this.dom);
        var  pieces2t={
          tfn:null,
          sfn:null
        }
        pieces2t.tfn=entitycolumn
        pieces2t.sfn=this.dragitem.connectid+":"+this.dragitem.tablename+":"+ this.dragitem.column;
        this.s2t.push(pieces2t);
      },
      allowDrop:function(event){
        event.preventDefault();
      },
      addFusiondata(){
        this.selectdata=true;
        $(".tablecolumn").each(function(){
          $(this).children("div:last-child").remove();
        });
      },
      getConnect(){
        this.$store.dispatch('GetConnect')
        this.$store.dispatch('GetEntity')
      },
      handleNodeClick(data){
        if(!data.tables){
          data.show =!data.show;   //传递给父组件选中的表格

        }
      },
      handleClose(tag){
        tag.show=false;
      },
      clearChoose(){
        for(let i=0;i<this.conRewrite.length;i++){
          for(let j=0;j<this.conRewrite[i].tables.length;j++){
            this.conRewrite[i].tables[j].show=false;
          }
        }
        this.selectdata=false;
        this.s2t=[];
        this.relations=[{left:[],right:[]}];
        this.join_units=[];
        $(".relation").each(function(){
          $(this).children("div:last-child").remove();
        });
        $(".tablecolumn").each(function(){
          $(this).children("div:last-child").remove();
        });
      },
      emitSelect(){
        var selectDB=[];
        this.relations=[{left:[],right:[]}];
        this.join_units=[];
        this.selectTableProp=[];
        for(let i=0;i<this.conRewrite.length;i++){
          for(let j=0;j<this.conRewrite[i].tables.length;j++){
            if(this.conRewrite[i].tables[j].show==true){
              //         this.selectTableNum+=1;
              this.relations.push({left:[],right:[]});
//              console.log( this.conRewrite[i].tables[j].id+"+"+this.conRewrite[i].tables[j].displayName)
              this.join_units.push(
                {
                  value:this.conRewrite[i].tables[j].id+":"+this.conRewrite[i].tables[j].displayName,
                  label:this.conRewrite[i].tables[j].id+":"+this.conRewrite[i].tables[j].displayName,
                  children:[],
                }
              );

              if(selectDB.indexOf(this.conRewrite[i].tables[j].id)<0){
                selectDB.push(this.conRewrite[i].tables[j].id);
                // 拿到所有需要的数据库连接信息
              }
            }
          }
        }
        for(let t=0;t<selectDB.length;t++){
          this.descriptDataBase(selectDB[t]);

        }
        this.selectdata=false;
        this.relations.splice(this.relations.length-1,1);
      },
      descriptDataBase(param){
        this.nowConn=param;
        axios.get("/kjb/cms/descriptionDataBase",{
          params:{
            "nick":param
          }
        }).then((response)=>{
          var res=response.data;
          if(res.status==1){
            var jsondata=JSON.parse(res.data);
            var conn={
              id:param,
              data:jsondata.tableStructures
            }
            this.selectTableProp.push(conn)
            for(let i=0;i<this.join_units.length;i++){
              if(this.join_units[i].label.split(":")[0]==param){
//                    console.log(this.join_units[i].label.split("+")[0])
                for( let j=0;j<jsondata.tableStructures.length;j++){
                  if( jsondata.tableStructures[j].tableName==this.join_units[i].label.split(":")[1]){
                    //    console.log(this.join_units[i].label.split("+")[1])
                    for(let t=0;t< jsondata.tableStructures[j].colmnuStructures.length;t++){
                      this.join_units[i].children.push(
                        {
                          value:jsondata.tableStructures[j].colmnuStructures[t].name,
                          label:jsondata.tableStructures[j].colmnuStructures[t].name
                        })
                    }
                  }
                }
              }
            }
          }
        })
      },
      selectEntity(value){
        console.log(value);
        if(value.dbID){
          this.s2t=[];
          this.selectEntityInfo.displayName=value.displayName;
          this.selectEntityInfo.properties=JSON.parse(value.properties);
          this.target_table_name=value.tableName;
          this.target_datasource_id=value.dbID;
        }else{
          this.addMySql=true;
          this.dataUrl=value.dbPosition.split("//")[1];
        }

      },

      removerelation1(){
        $(".relation").each(function(){
          $(this).children("div:last-child").remove();
        });
        $(".tablecolumn").each(function(){
          $(this).children("div:last-child").remove();
        });
        this.emitSelect();
        this.s2t=[];
      },
      removerelation2(){
        this.relations=[{left:[],right:[]}]
      },
      handleChange(){

      },
      sendmessage(){
        var result = {
          s2t: [],
          join_units: [],
          relations: [],
          target_table_name: "",
          target_datasource_id: ""
        }
        console.log(this.selectEntityInfo.properties[0].prime)
        for(let i in this.selectEntityInfo.properties){  //先找到主表
          var x = this.selectEntityInfo.properties[i];
            if(x.prime==1){
                for( let j in this.s2t ){
                    var couple=this.s2t[j];
                    if(couple.tfn===x.name){
                        let sfn=couple.sfn.split(":").slice(0,2).join(":");
                        result.join_units[0]=sfn;
                    }
                }
            }
        }
        result.s2t = this.s2t;
        for (let i = 0; i < this.join_units.length; i++) {
            if(result.join_units.indexOf(this.join_units[i].label)<0){
              result.join_units.push(this.join_units[i].label);
            }

        }
        for (let j = 0; j < this.relations.length; j++) {
          if (this.relations[j].left[0]) {
            var templeft = this.relations[j].left.join(":");
            var tempright = this.relations[j].right.join(":");
//            result.join_units.push(this.relations[j].left[0]);  //按照join的顺序添加表
            var temp = {
              left: templeft,
              right: tempright
            }
            result.relations.push(temp)
          }
        }
//        result.join_units.push(this.relations[this.relations.length - 2].right[0]);

        result.target_table_name = this.target_table_name;
        result.target_datasource_id = this.target_datasource_id;
        // console.log(result)
        axios.post("/kjb/dfs/commitjob", result).then((response) => {
          var res = response.data;
          if (res.status == 1) {
            const h = this.$createElement;
            this.clearChoose();
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, '任务提交成功，请在任务管理处查看进度')
            });
          } else {
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, '任务提交失败')
            });
          }
        }).catch((e) => {
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', {style: 'color: teal'}, '任务提交失败')
          })
        })
      }
    }
  }
</script>

