<template>
  <div class="connect">
    <connect-info  @previewtable="previewTable" class="sidecontainer"   style="height:100%;width:180px;position:fixed;overflow: auto;z-index:2000;"></connect-info>
    <div style="padding: 20px;margin-left:180px;overflow: visible;">
      <div style="border:1px solid #bfcbd9;padding: 0px 20px;">
        <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
          <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
          <span>数据源</span>
        </p>
        <div class="showtable" style="width:100%;margin-top: 50px;border: 1px solid #ccc;color: #333;overflow:auto;height:350px;">
          <table class="imagetable quarter-div_table">
            <thead>
            <tr>
              <th  style=" text-algin:center;color:#fff;" v-for="(key,item) in previewData[0]">{{item}}</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for = "item in previewData">
              <td v-for ="it in  item">{{it}}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "./../assets/scss/mixin";
  .connect {
    @include clearfix;
    position: relative;
    width: 100%;
  }
  .showtable{
    margin-top: 40px;
    position:relative;
    overflow: auto;
    background-color: #ffffff;
    tbody {
      height: 600px;
      overflow-x: auto;
      overflow-y: auto;
    }
  }

  .imagetable {
    width:100%;
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
  }
  .imagetable th {
    font-weight:bold;
    /*background-color: #103251;*/
    color:#bfcbd9;
    font-size:0.95em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    border:none;
    min-width: 100px;
  }
  .imagetable td {
    font-size:0.95em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
  }
  .imagetable thead {
    background-color: #6787B0;
  }
  .imagetable tbody tr {
    border-bottom: 1px dashed #ccc;
    height: 50px;
    &:nth-child(2n) {
      background-color: #F2F7FD;
    }
  }
</style>
<style>
  .sidecontainer{
    overflow: auto;
  }
</style>
<script>
  import axios from 'axios'
  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  export default{
    components:{connectInfo},
    data(){
      return {
        addMySql: false,
        dataUrl: "114.212.84.208:3306/fupin",
        dataUserName: "root",
        dataPassword: "iipconfig",
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
//    mounted(){
//      this.getConnect();
//    },
    methods: {
      descriptTable(index){
        this.tableDetail=this.databaseDetail[index].colmnuStructures;
        var name=this.databaseDetail[index].tablename;
        this.previewTable(name);
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
      previewTable(emitdata){
        this.previewData=[];
        axios.get("/kjb/cms/preview",{
          params:{
            "display":emitdata.database,
            "table":emitdata.table
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
