<template>
  <div class="connect">
    <connect-info  @previewtable="previewTable" class="sidecontainer" style="height:490px;float:left;width:180px"></connect-info>
      <div class="show">
        <div>
          <h2>数据源</h2>
          <!--<button type="button" class="btn btn-small" aria-label="Left Align" @click="getConnect" id="load">-->
            <!--<span class="glyphicon glyphicon-repeat" aria-hidden="true">刷新</span>-->
          <!--</button>-->
        </div>
        </div>
        <div class="tablecontent">
          <el-table
            :data="previewData"
            height="450"
            style="width:600px;margin:5px auto;">
            <el-table-column :label="key" v-for="(value,key) in previewData[0]"
                             width="120">
              <template slot-scope="scope">
                {{previewData[scope.$index][key]}}
              </template>
            </el-table-column>
          </el-table>
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
    height: 100%;
    width: 100%;
  }
</style>
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
  .show{
    margin-left: 150px;
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
      getConnect(){
        this.$store.dispatch('GetConnect')
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
