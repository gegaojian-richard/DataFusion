<template>
  <div class="entity-event">
    <div class="entity">
      <div class="entity-title">
        <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
          <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
          <span>实体库</span>
        </p>

      </div>
      <div class="entity-item">
        <div class="entity-item-head">
          <ul>
            <li style="width:25%;">实体名</li>
            <li>数据库地址</li>
            <li>表名</li>
            <li>属性</li>
            <li>管理</li>
          </ul>
        </div>
        <ul class="entity-item-list" style="height: 678px;overflow-y: auto;">
          <li v-for="(item,index) in entityLi" v-bind:class="{blue:item.dbID}">
            <div class="entity-tab">
              {{item.displayName}}
            </div>
            <div class="entity-tab">
              {{item.dbPosition}}
            </div>
            <div class="entity-tab">
              {{item.tableName}}
            </div>
            <div class="entity-tab">
              ... ...
            </div>
            <div class="entity-tab">
              <a href="javascript:void(0);" v-on:click="startEdit(index)" v-if="item.dbID" >编辑</a>
              <a href="javascript:void(0);" v-on:click="deleteEntity(index)" v-if="item.dbID">删除</a>
              <a href="javascript:void(0)"  v-if="!item.dbID" v-on:click="connectDB(item.dbPosition)" style="color: #2bc4e2">连接</a>
            </div>
          </li>
        </ul>
      </div>
      <div style="height: 40px;margin-top:30px;">
        <button style="background: #A5CE64;color:#fff;line-height: 23px;float:right;" type="button" class="btn btn-small" aria-label="Left Align" @click="getEntity" id="load">
          <span class="glyphicon glyphicon-repeat" style="top:0px;" aria-hidden="true">刷新</span>
        </button>
        <button style="background: #82C4FB;color:#fff;line-height: 23px;float:right;" type="button" class="btn btn-small" aria-label="Left Align" @click="showAdd" id="add">
          <span class="glyphicon glyphicon-plus" style="top:0px;" aria-hidden="true">添加</span>
        </button>
      </div>
      <!--添加连接的遮罩层-->
      <div class="md-modal modal-msg md-modal-transition" style="width:400px"  v-bind:class="{'md-show':addMySql}">
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
      <!--添加一条实体的遮罩层-->
      <div>
        <div class="md-modal modal-msg md-modal-transition" style="width:500px" v-bind:class="{'md-show':addShow}">
          <div class="md-modal-inner">
            <div class="md-top">
              <div class="md-title">添加实体</div>
              <button class="md-close" @click="addShow=false">Close</button>
            </div>
            <div class="md-content">
              <div class="confirm-tips">
                <div class="input-group">
                  <span  style="width:50px"> 实体名:</span>
                  <input v-model="addOne.displayName" type="text" class="inputEntity" >
                </div>
                <div class="input-group">
                  <span style="width:50px"> 数据库:</span>
                  <el-select  v-model="addOne.dbPosition" style="height:8px;width:380px;">
                    <el-option v-for="item in conns" :key="item.displayName" :value="item.id" :label="item.displayName"></el-option>
                  </el-select>
                </div>
                <div class="input-group">
                  <span style="width:50px"> 表格名:</span>
                  <input v-model="addOne.tableName" type="text" class="inputEntity">
                </div>
                <div class="input-group">
                  <div style="height: 40px;line-height: 40px;">
                    <span style="height: 40px;line-height: 40px;display: inline-block" >具体信息</span>
                    <span  role="button" @click="addProperty" style="padding: 0 5px;color:#fff;margin-left: 250px;display: inline-block;height: 40px;background: #7CC1FC;"> <i class="el-icon-circle-plus">增加属性</i> </span>
                  </div>
                  <el-table
                    :data="addOnepro"
                    class="entity-table"
                    border
                    style="width: 100%;margin-top:20px;">
                    <el-table-column
                      align="center"
                      label="字段名"
                      width="100">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ scope.row.name }}</span>
                        <span v-if="editingRow==scope.$index" class="cell-edit-input"><el-input
                          v-model="scope.row.name" style="border:1px solid #ccc !important;
    border-radius: 3px !important;" ></el-input></span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="类型"
                      width="100">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ typetoShow[scope.row.type] }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.type" >
                          <el-option v-for="item in optionsType" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="主键"
                      width="100px">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ primetoShow[scope.row.prime] }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.prime" >
                          <el-option v-for="item in optionsPrim" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作"
                      width="100px">
                      <template slot-scope="scope">
                         <!--<span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleEdit(scope.$index)">  <i-->
                                  <!--class="el-icon-edit"></i> </span>-->
                        <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleDelete(scope.$index)">  <i class="el-icon-delete"></i> </span>
                        <!--<span v-if="editingRow==scope.$index" class="cell-icon" @click="handleSave(scope.$index)">  <i-->
                          <!--class="el-icon-document"></i> </span>-->
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div class="btn-wrap">
                <a href="javascript:;" class="btn-login" @click="addEntity">提交</a>
              </div>
            </div>
          </div>
        </div>
        <div class="md-overlay" v-if="addShow" @click="addShow=false"></div>
      </div>
      <!--修改一条实体的遮罩层-->
      <div>
        <div class="md-modal modal-msg md-modal-transition" style="width:500px" v-bind:class="{'md-show':editShow}">
          <div class="md-modal-inner">
            <div class="md-top">
              <button class="md-close" @click="editShow=false">Close</button>
            </div>
            <div class="md-content">
              <div class="confirm-tips">
                <div class="input-group">
                  <span  style="style:inline-block;width:55px"> 实体名:</span>
                  <input v-model="editArr.displayName" type="text" class="inputEntity" >
                </div>
                <div class="input-group">
                  <span style="style:inline-block;width:55px"> 数据库:</span>
                  <!--<el-select  v-model="editArr.dbPosition"  style="width:380px;">-->
                    <!--<el-option v-for="item in conns.displayName" :key="item" :value="item" :label="item"></el-option>-->
                  <!--</el-select>-->
                  <input v-model="editArr.dbPosition" disabled="true" type="text" class="inputEntity">
                </div>
                <div class="input-group">
                  <span style="style:inline-block;width:55px"> 表格名:</span>
                  <input v-model="editArr.tableName" type="text" class="inputEntity">
                </div>
                <div class="input-group">
                  <div style="height: 40px;line-height: 40px;">
                    <span style="height: 40px;line-height: 40px;display: inline-block" >具体信息</span>
                    <span  role="button" @click="addProperty" style="padding: 0 5px;color:#fff;margin-left: 250px;display: inline-block;height: 40px;background: #7CC1FC;"> <i class="el-icon-circle-plus">增加属性</i> </span>
                  </div>
                  <el-table
                    :data="editArr.properties"
                    class="entity-table"
                    border
                    style="width: 100%;">
                    <el-table-column
                      align="center"
                      label="字段名"
                      width="100">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ scope.row.name }}</span>
                        <span v-if="editingRow==scope.$index" class="cell-edit-input"><el-input
                          v-model="scope.row.name" style="color:#1f2d3d;border:1px solid #ccc !important;
    border-radius: 3px !important; "></el-input></span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="类型"
                      width="100">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ typetoShow[scope.row.type]}}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.type" >
                          <el-option v-for="item in optionsType" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="主键"
                      width="100px">
                      <template slot-scope="scope">
                        <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{ primetoShow[scope.row.prime ]}}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.prime" >
                          <el-option v-for="item in optionsPrim" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作"
                      width="100px">
                      <template slot-scope="scope">
                         <!--<span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleEdit(scope.$index)">  <i-->
                           <!--class="el-icon-edit"></i> </span>-->
                        <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleDelete(scope.$index)">  <i class="el-icon-delete"></i> </span>
                        <!--<span v-if="editingRow==scope.$index" class="cell-icon" @click="handleSave(scope.$index)">  <i-->
                          <!--class="el-icon-document"></i> </span>-->
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div class="btn-wrap">
                <a href="javascript:;" class="btn-login" @click="sureEdit(editArr.dbID)">提交</a>
              </div>
            </div>
          </div>
        </div>
        <div class="md-overlay" v-if="editShow || addShow || addMySql" @click="editShow=false"></div>
      </div>
    </div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
   /*.el-input__inner{*/
     /*border:1px solid #ccc !important;*/
     /*border-radius: 3px !important;*/
  /*}*/
  /*.el-select{*/
    /*height:30px;*/
  /*}*/
  .entity-table{
    height:300px !important;
    overflow: auto;
  }
  .entity-event {
    height: 100%;
    border:1px solid #bfcbd9;
    padding: 0 20px;
  }
  .blockspan{
    display:inline-block;
    height:30px;
    width:100px;
    line-height: 30px;
  }
  .entity-item-list {
    li:nth-child(2n) {
      background-color: #F2F7FD;
    }
  }
  .entity-item-list > li > div{
    border-bottom: 1px dashed #ccc!important;
  }
  .entity-item-list > .blue{
    color: #2bc4e2;
  }
  .el-input input{
    color: #0c0709;
  }
  .el-input__inner{
    height:30px;
    margin-top: 5px;

  }
  .inputEntity{
    height:50px;
    margin-left:0px;
    margin-top: 5px;
    margin-bottom: 5px;
    border-radius: 5px;
  }
  .entity-tab{
    /*width:100%;*/
    height:32px;
    line-height:32px;
    position:relative;
  }
  .entity-tab input{
    position:absolute;
    top:0;
    left:0;
    width:100%;
    color:#999;
    /*padding-left:10px;*/
    -webkit-box-sizing:border-box;
    box-sizing:border-box;
    height:30px;
    border:1px solid #787070;
    -webkit-animation:borderAn 2s infinite;
    animation:borderAn 2s infinite;
  }
  /*.entity-tab input{*/
  /*height:30px;*/
  /*border:1px solid #787070;*/
  /*-webkit-animation:borderAn 2s infinite;*/
  /*animation:borderAn 2s infinite;*/
  /*}*/
  #load,#add,#addproperty{
    float:right;
    border: 1px solid #ccc;
    color: #0c0709;
    background-color: #ccc ;
    font-size: 12px;
    padding:0 5px;
    height:30px;
    line-height: 30px;
    margin:5px 10px 5px 20px;
  }
  .entity-item{
    display:table;
    width:100%;
    color: #605F5F;
    border:1px solid #bfcbd9;
    margin-top:20px;
  }
  .entity-item-head{
    display:table-header-group;
    width:100%;
    color:#fff;
    background: #6C89B1;

  }

  .entity-item-head ul{
    display: table-row;
    width: 100%;
  }
  .entity-item-head li{
    display: table-cell;
    background: #6C89B1!important;
    height: 40px;
    line-height: 40px;
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
  .md-modal {
    overflow:auto;
    border-radius: 5px;
    height:500px !important;
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
  .inputEntity {
    height: 40px;
    margin-left: 0px;
    width: 380px;
    margin-top: 5px;
    margin-bottom: 5px;
    border-radius: 5px;
  }
  .input-group {
    margin-bottom: 10px;
    height: 40px;
    color: #605F5F!important;
  }
  .el-select>.el-input {
    width: 380px!important;
    margin-bottom: 10px;
  }

</style>
<script>
  import axios from 'axios'
  import {mapGetters} from 'vuex'
  export default{
    data(){
      return{
      //  entityEvent:[],
        entityLi:[],
        eventLi:[],
        editStatus:false,
        nowEditCol:-1,
        addOnepro:[{"name":'',"type":'',"prime":''}],
        addOne:{'displayName':'','dbPosition':'','tableName':'','entityType':'','properties':''},
        editShow:false,
        addShow:false,
        primetoShow:{"1":"Y","0":"N"},
        typetoShow:{"0":"短文本","1":"长文本","2":"整数","3":"小数","4":"日期","5":"日期时间"},
        optionsPrim:[{'label':'Y','value':'1'},{'label':'N',"value":'0'}],
        optionsType:[{"label":"短文本","value":"0"},{"label":"长文本","value":"1"},{"label":"整数","value":"2"},{"label":"小数","value":"3"},
          {"label":"日期","value":"4"},{"label":"日期时间","value":"5"}],
        editingRow: null,
        editArr:{'displayName':'','dbPosition':'','tableName':'','entityType':'','dbID':'','properties':[]},
        addMySql:false ,//添加连接
        dataUrl:"",//添加连接地址
        displayName:"",
        dataPassword:"",
        dataUserName:"",
        errorTip:false,
        createError:false,
      }
    },
    computed: {
      ...mapGetters(['entityevent','conns']),
    },
    mounted(){
      this.getEntity();
    },
    watch:{
      entityevent:function(val) {
        this.entityLi = val.filter(function (item) {
          return item.entityType == 0;
        });
//        this.eventLi = val.filter(function (item) {
//          return item.entityType == 1;
//        })
      },
      nowEditCol:function(val) {
        if (!(val<0)) {
          this.editArr.id = this.entityLi[val].id ;
          this.editArr.displayName = this.entityLi[val].displayName;
          this.editArr.dbPosition = this.entityLi[val].dbPosition ;
          this.editArr.tableName = this.entityLi[val].tableName;
          this.editArr.entityType = this.entityLi[val].entityType;
          this.editArr.dbID=this.entityLi[val].dbID;
          this.editArr.properties = JSON.parse(this.entityLi[val].properties);
        }else{
          this.editArr={'id':'','displayName':'','dbPosition':'','tableName':'','entityType':'','properties':[]}
        }
      }
    },
    methods: {
        //连接数据库
      connectDB:function (db) {
          this.addMySql=true;
          if(db.indexOf("//")>0){
            this.dataUrl=db.split("//")[1];
          }else{
             this.dataUrl=db ;
          }

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
            this.$store.dispatch('GetConnect')
            this.addMySql = false;
            this.getEntity();
          } else {
            this.createError = true;
          }
        })

      },
        //弹出addEntity框
      showAdd:function(){
        this.addShow=true;
      },
      //输入实体属性
      handleEdit: function (index) {
        //遍历数组改变editeFlag
        this.editingRow = index;
      },
      handleDelete:function(index){
        this.addOnepro.splice(index,1);

      },
      //增加一条实体属性
      addProperty:function(){
        var newpiece={
          'name':'',
          'type':'',
          'prime':''
        };
        this.addOnepro.push(newpiece);
      },
      editAddProperty:function(){
        var newpiece={
          'name':'',
          'type':'',
          'prime':''
        };
        this.editArr.properties.push(newpiece);
      },
      //实体属性保存
//      handleSave: function (index) {
//        this.editingRow = null;
//      },
      startEdit(index) {
        this.nowEditCol = index;
        this.editShow=true;
      },
      cancelEdit() {
        this.nowEditCol = -1;
      },
      sureEdit(dbID) {      //确定之后先是删除一条之后再插入一条
        var id;
        id=this.entityLi[this.nowEditCol].id;
        axios.get("/kjb/entity/delete", {
          params: {
            "entityId": id
          }
        }).then((response) => {
          let res = response.data;
          if (res.status == 1) {
              var insertE={
                'displayName': this.editArr.displayName,
                'dbPosition': dbID,
                'tableName':this.editArr.tableName,
                'entityType': this.editArr.entityType,
                 'properties': JSON.stringify(this.editArr.properties)
              };
            this.insertEntity(insertE);
          }
        })
        this.editShow=false;
      },
      deleteEntity(index){
        var id;
        id=this.entityLi[index].id;
        axios.get("/kjb/entity/delete", {
          params: {
            "entityId": id
          }
        }).then((response) => {
          let res = response.data;
          if (res.status == 1) {
            this.getEntity();   //删除一条时候刷新
          }
        })
      },
      addEntity(){
        for(var i=0;i<this.addOnepro.length;i++){
            if(this.addOnepro[i].name==null){
                this.addOnepro.splice(i,1);
            }
        }
        var addOne={
          'displayName':this.addOne.displayName,
          'dbPosition':this.addOne.dbPosition,
          'tableName':this.addOne.tableName,
          'entityType':0, //0表示实体，1表示事件
          'properties':JSON.stringify(this.addOnepro)
        };
        this.insertEntity(addOne);
        this.addShow=false;
      },
      resetAddOnepro:function(){
         this.addOnepro=[{"name":'',"type":'',"prime":''}];
      },
      resetEntity:function(){
        this.addOne={
          'displayName':'',
          'dbPosition':'',
          'tableName':'',
          'entityType':0,
          'properties':''
        }
      },
      getEntity(){
        this.$store.dispatch('GetEntity');
      },
      insertEntity(params){
        axios.post("/kjb/entity/create", params).then(
          (response) => {
            let res = response.data;
            if (res.status == 1) {
              this.nowEditCol = -1;
              this.getEntity();
              this.resetEntity();
              this.resetAddOnepro();
            }
          }
        );
      },
    }
  }
</script>


