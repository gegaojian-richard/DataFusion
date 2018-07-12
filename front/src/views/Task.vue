<template>
  <div class="app-container calendar-list-container" >
    <!--<connect-info  @previewtable="previewTable" class="sidecontainer" style="height:100%;float:left;width:180px"></connect-info>-->
    <div style="border:1px solid #bfcbd9;padding: 0px 20px;">
      <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
        <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
        <span>任务管理</span>
      </p>
      <el-table :data="list" v-loading.body="listLoading" border fit highlight-current-row style="margin-top:20px;">
        <el-table-column align="center" label="任务ID" width="80">
          <template slot-scope="scope">
            <span>{{scope.row.jobID}}</span>
          </template>
        </el-table-column>

        <el-table-column width="180px" align="center" label="任务类型">
          <template slot-scope="scope">
            <span>{{scope.row.jobType}}</span>
          </template>
        </el-table-column>

        <el-table-column class-name="status-col" label="任务状态" width="110">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status ">{{scope.row.status}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column align="center" label="Actions" >
        <template slot-scope="scope">
          <router-link :to="{path:'/task/completeresult',query:{nowUserId:scope.row.userID,nowEditJob:scope.row.jobID}}" v-show="scope.row.jobType === '完整性检查'" >查看详情</router-link>
          <router-link :to="{path:'/task/insistresult',query:{nowUserId:scope.row.userID,nowEditJob:scope.row.jobID}}" v-show="scope.row.jobType === '一致性检查'" >查看详情</router-link>
        </template>
      </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
//  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  export default {
    data() {
      return {
        editingRow:null,
        list:[],
        showDetail:false,
        listLoading: true,
        resultData:[],
        currentPage:0,
        totalCount:10,
        nowEditJob:null,
        userID:null,
      }
    },
   created(){
     var  param={}
     this.listLoading = false;
     axios.post("/kjb/tvs/privateTasks"
     ).then((response) => {
       var res = response.data;
       if (res.status == 1) {
        this.list=JSON.parse(res.data);
       } else {
       }
     })
   },
    methods: {
       handleEdit(index){
           this.editingRow =index;
       },
       sendAllData(){

       },
      handleCurrentChange(val){
        this.currentPage = val;
        this.getData(val);
      },
//      viewResult(index, row){
//        this.showDetail = true;
//        this.nowEditJob = row.jobID;
//        this.nowUserId=row.userID;
//        this.getNum();
//      },
//      getNum(){
//        var redisParam = {
//          "key": this.nowUserId+"-"+this.nowEditJob,
//          "start": this.currentPage,
//          "end": this.currentPage + 10
//        }
//        axios.post("/kjb/tvs/redisLen", redisParam).then
//        ((response) => {
//          var res = response.data;
//          if (res.status == 1) {
//            this.totalCount = parseInt(res.data);
//            this.getData();
//          }
//        })
//      },
//      getData(val){
//        var redisParam = {
//          "key": this.nowUserId+"-"+this.nowEditJob,
//          "start": this.currentPage*10,
//          "end": this.currentPage*10+10
//        }
//        axios.post("/kjb/tvs/redisData", redisParam).then
//        ((response) => {
//          var res = response.data;
//          if (res.status == 1) {
//            this.resultData = JSON.parse(res.data).items;
//          }
//        })
//      }
    }
    }
</script>



