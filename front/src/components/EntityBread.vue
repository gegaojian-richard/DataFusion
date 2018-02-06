<template>
  <div class="entity-event">
    <div class="entity">
      <div class="entity-title">
        <h2 class="entity-title-h2"><span>实体库</span></h2>
        <button type="button" class="btn btn-small" aria-label="Left Align" @click="getEntity" id="load">
          <span class="glyphicon glyphicon-repeat" aria-hidden="true">刷新</span>
        </button>
        <button type="button" class="btn btn-small" aria-label="Left Align" @click="showAdd" id="add">
          <span class="glyphicon glyphicon-plus" aria-hidden="true">添加</span>
        </button>
      </div>
      <div class="entity-item">
        <div class="entity-item-head">
          <ul>
            <li>实体名</li>
            <li>数据库地址</li>
            <li>表名</li>
            <li>属性</li>
            <li>管理</li>
          </ul>
        </div>
        <ul class="entity-item-list">
          <li v-for="(item,index) in entityLi">
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
              <a href="javascript:void(0);" v-on:click="startEdit(index)" >编辑</a>
              <a href="javascript:void(0);" v-on:click="deleteEntity(index)">删除</a>
            </div>
          </li>
        </ul>
      </div>
      <div>
        <div class="md-modal modal-msg md-modal-transition" style="width:500px" v-bind:class="{'md-show':addShow}">
          <div class="md-modal-inner">
            <div class="md-top">
              <button class="md-close" @click="addShow=false">Close</button>
            </div>
            <div class="md-content">
              <div class="confirm-tips">
                <div class="input-group">
                  <span  style="width:50px"> 实体名：</span>
                  <input v-model="addOne.displayName" type="text" class="inputEntity" >
                </div>
                <div class="input-group">
                  <span style="width:50px"> 数据库：</span>
                  <el-select  v-model="addOne.dbPosition" style="height:8px;width:200px">
                    <el-option v-for="item in conns" :key="item.displayName" :value="item.id" :label="item.displayName"></el-option>
                  </el-select>
                </div>
                <div class="input-group">
                  <span style="width:50px"> 表格名: </span>
                  <input v-model="addOne.tableName" type="text" class="inputEntity">
                </div>
                <div class="input-group">
                  <span style="margin-top:30px;" display="inline-block">具体信息</span><br>
                  <span  role="button" @click="addProperty" style="margin-left: 200px;border: 1px solid #b8b8b8"> <i class="el-icon-circle-plus">增加属性</i> </span>
                  <el-table
                    :data="addOnepro"
                    border
                    height="200"
                    style="width: 100%">
                    <el-table-column
                      align="center"
                      label="字段名"
                      width="100">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.name }}</span>
                        <span v-if="editingRow==scope.$index" class="cell-edit-input"><el-input
                          v-model="scope.row.name"></el-input></span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="类型"
                      width="100">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.type }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.type"  width="40px">
                          <el-option v-for="item in optionsType" :key="item" :value="item" :label="item"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="主键"
                      width="100px">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.prime }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.prime"  width="40px">
                          <el-option v-for="item in optionsPrim" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作"
                      width="100px">
                      <template slot-scope="scope">
                         <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleEdit(scope.$index)">  <i
                                  class="el-icon-edit"></i> </span>
                        <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleDelete(scope.$index)">  <i class="el-icon-delete"></i> </span>
                        <span v-if="editingRow==scope.$index" class="cell-icon" @click="handleSave(scope.$index)">  <i
                          class="el-icon-document"></i> </span>
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
      <div>
        <div class="md-modal modal-msg md-modal-transition" style="width:500px" v-bind:class="{'md-show':editShow}">
          <div class="md-modal-inner">
            <div class="md-top">
              <button class="md-close" @click="editShow=false">Close</button>
            </div>
            <div class="md-content">
              <div class="confirm-tips">
                <div class="input-group">
                  <span  style="width:50px"> 实体名：</span>
                  <input v-model="editArr.displayName" type="text" class="inputEntity" >
                </div>
                <div class="input-group">
                  <span style="width:50px"> 数据库：</span>
                  <el-select  v-model="editArr.dbPosition"  >
                    <el-option v-for="item in conns.displayName" :key="item" :value="item" :label="item"></el-option>
                  </el-select>
                </div>
                <div class="input-group">
                  <span style="width:50px"> 表格名: </span>
                  <input v-model="editArr.tableName" type="text" class="inputEntity">
                </div>
                <div class="input-group">
                  <span style="margin-top:30px;" display="inline-block">具体信息</span><br>
                  <span  role="button" @click="editAddProperty" style="margin-left: 200px;border: 1px solid #b8b8b8"> <i class="el-icon-circle-plus">增加属性</i> </span>
                  <el-table
                    :data="editArr.properties"
                    border
                    height="200"
                    style="width: 100%">
                    <el-table-column
                      align="center"
                      label="字段名"
                      width="100">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.name }}</span>
                        <span v-if="editingRow==scope.$index" class="cell-edit-input"><el-input
                          v-model="scope.row.name"></el-input></span>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="类型"
                      width="100">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.type }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.type"  width="40px">
                          <el-option v-for="item in optionsType" :key="item" :value="item" :label="item"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="主键"
                      width="100px">
                      <template slot-scope="scope">
                        <span v-if="editingRow!=scope.$index">{{ scope.row.prime }}</span>
                        <el-select v-if="editingRow==scope.$index" v-model="scope.row.prime"  width="40px">
                          <el-option v-for="item in optionsPrim" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作"
                      width="100px">
                      <template slot-scope="scope">
                         <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleEdit(scope.$index)">  <i
                           class="el-icon-edit"></i> </span>
                        <span v-if="editingRow!=scope.$index" class="cell-icon" @click="handleDelete(scope.$index)">  <i class="el-icon-delete"></i> </span>
                        <span v-if="editingRow==scope.$index" class="cell-icon" @click="handleSave(scope.$index)">  <i
                          class="el-icon-document"></i> </span>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div class="btn-wrap">
                <a href="javascript:;" class="btn-login" @click="sureEdit">提交</a>
              </div>
            </div>
          </div>
        </div>
        <div class="md-overlay" v-if="editShow" @click="editShow=false"></div>
      </div>
    </div>
  </div>
</template>
<style>
  .el-input input{
    color:#909399;
  }
  .el-input__inner{
    height:30px;
    margin-top: 5px;
  }
  .inputEntity{
    height:50px;
    margin-left:0px;
    width:200px;
    margin-top: 10px;
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
        optionsPrim:[{'label':'Y','value':'1'},{'label':'N',"value":'0'}],
        optionsType:['INT','FLOAT','TEXT','VARCHAR'],
        editingRow: null,
        editArr:{'displayName':'','dbPosition':'','tableName':'','entityType':'','properties':[]}
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
        this.eventLi = val.filter(function (item) {
          return item.entityType == 1;
        })
      },
      nowEditCol:function(val) {
        if (!(val<0)) {
          this.editArr.id = this.entityLi[val].id ;
          this.editArr.displayName = this.entityLi[val].displayName;
          this.editArr.dbPosition = this.entityLi[val].dbPosition ;
          this.editArr.tableName = this.entityLi[val].tableName;
          this.editArr.entityType = this.entityLi[val].entityType;
          this.editArr.properties = JSON.parse(this.entityLi[val].properties);
        }else{
          this.editArr={'id':'','displayName':'','dbPosition':'','tableName':'','entityType':'','properties':[]}
        }
      }
    },
    methods: {
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
      handleSave: function (index) {
        this.editingRow = null;
      },
      startEdit(index) {
        this.nowEditCol = index;
        this.editShow=true;
      },
      cancelEdit() {
        this.nowEditCol = -1;
      },
      sureEdit() {      //确定之后先是删除一条之后再插入一条
        var id;
        id=this.entityLi[this.nowEditCol].id;
        axios.get("kjb/entity/delete", {
          params: {
            "entityId": id
          }
        }).then((response) => {
          let res = response.data;
          if (res.status == 1) {
              var insertE={
                'displayName': this.editArr.displayName,
                'dbPosition': this.editArr.dbPosition,
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
        axios.get("kjb/entity/delete", {
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
          'entityType':1,
          'properties':''
        }
      },
      getEntity(){
        this.$store.dispatch('GetEntity');
      },
      insertEntity(params){
        axios.post("kjb/entity/create", params).then(
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


