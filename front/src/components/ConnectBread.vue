<template>
<div class="connect">
  <h2>数据源</h2>
  <button type="button" class="btn btn-small" aria-label="Left Align" @click="getConnect" id="load">
    <span class="glyphicon glyphicon-repeat" aria-hidden="true">刷新</span>
  </button>
  <div style="margin-top: 30px; height:60px; border-bottom: 1px solid #b8b8b8">
        <el-dropdown  v-for="(item,index) in conns" style="margin-bottom: 10px;margin-left:40px" trigger="click">
          <el-button type="primary"  @click="descriptDataBase(item.name)">
            {{item.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown" style="margin-top: -50px;" >
            <el-dropdown-item v-for="(value,index) in databaseDetail"><span v-on:click="descriptTable(index)">{{value.tablename}}</span></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
  </div>
  <div style="margin-top: 30px">
    <el-row :gutter="20">
      <el-col :span="6"  style="border-right: 1px solid #b8b8b8">
        <el-table
          :data="tableDetail"
          style="width:200px"
           height="400">
          <el-table-column
            prop="columnName"
            label="属性"
            width="100">
          </el-table-column>
          <el-table-column
            prop="columnType"
            label="类型"
            width="100">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="18"><div class="grid-content bg-purple">
        <el-table
          :data="previewData"
          height="400"
          border
          style="width:100%">
          <el-table-column :label="key" v-for="(value,key) in previewData[0]"
            width="120">
            <template slot-scope="scope">
              {{previewData[scope.$index][key]}}
            </template>
          </el-table-column>
        </el-table>
      </div>
      </el-col>
    </el-row>
  </div>
</div>
</template>
<style>
  #load{
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
</style>
<script>
  import axios from 'axios'
  export default{
    data(){
      return {
        addMySql: false,
        dataUrl: "localhost:3306/dataset1",
        dataUserName: "root",
        dataPassword: "tangsy",
        displayName:"china-regions",
        errorTip: false,
        createError:false,
        conns:[],
        databaseDetail:[],
        tableDetail:[],
        connectInfo:[],
        nowConn:"",
        previewData:[]
      }
    },
    mounted(){
      this.getConnect();
    },
    methods: {
      descriptTable(index){
            this.tableDetail=this.databaseDetail[index].colmnuStructures;
            var name=this.databaseDetail[index].tablename;
            this.previewTable(name);
        },
      addMysqlConnect(){
        if(!this.dataUrl||!this.dataPassword||!this.dataUrl||!this.displayName){
          this.errorTip=true;
          return;
        }
        var param={
          id:this.displayName,
          displayName:this.displayName,
          type:"mysql",
          url:this.dataUrl,
          user:this.dataUserName,
          pwd:this.dataPassword

        };
        axios.post("/kjb/cms/creationDataBase", param
        ).then((response)=>{
          var res=response.data;
          if(res.status==1){
            this.getConnect();
            this.addMySql=false;
          }else{
            this.createError=true;

          }
        })

      },
      deleteConnect(name){
        let param=new URLSearchParams();
        param.append("nick",name);
        axios.post("/kjb/cms/deletionDataBase",param).then((response)=>{
          var res=response.data;
          if(res.status==1){
            this.getConnect();
          }
        })
      },
      getConnect(){
        axios.get("/kjb/cms/currentDataBase").then((response)=>{
          var res=response.data;
          if(res.status==1){
            var jsonConns=JSON.parse(res.data);
            this.conns=jsonConns.databases;
          }else{
            this.createError=true;
          }

        })


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
            this.databaseDetail= jsondata.tableStructures;
          }
        })

      },
      previewTable(nick){
        axios.get("/kjb/cms/preview",{
            params:{
            "display":this.nowConn,
            "table":nick
          }}
          ).then((response)=>{
            var res=response.data;
            if(res.status==1){
                var receive=JSON.parse(res.data);
                this.previewData=receive.items;
            }

        })

      }
    }
  }
</script>
