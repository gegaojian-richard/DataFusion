<template>
  <div class="connect">
    <connect-info  @previewtable="previewTable" class="sidecontainer" style="height:500px;float:left;width:180px"></connect-info>
    <div  style="margin-left: 180px;padding: 20px;height: 100%;">
      <div style="height: 100%;;border:1px solid #bfcbd9;padding: 0px 20px;">
        <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
          <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
          <span>数据源</span>
        </p>
        <el-table
          :data="previewData"
          height="calc(100% - 100px)"
          style="margin:5px auto;background-color:#fff">

          <el-table-column :label="key" v-for="(value,key) in previewData[0]"
                           width="120" style="background-color: #103251;">
            <template slot-scope="scope">
              {{previewData[scope.$index][key]}}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  @import "./../assets/scss/mixin";
  .connect {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
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
//    mounted(){
//      this.getConnect();
//    },
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
//      getConnect(){
//        this.$store.dispatch('GetConnect')
//      },
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
