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
          <el-button type="primary" @click='viewResult(scope.$index,scope.row)' size="small" icon="el-icon-edit">查看详情</el-button>
        </template>
      </el-table-column>
      </el-table>
    </div>
    <div class="md-modal modal-msg md-modal-transition"  v-bind:class="{'md-show':showDetail}">
      <div class="md-modal-inner">
        <div class="md-top">
          <div class="md-title">完整性检查结果</div>
          <button class="md-close" @click="showDetail=false">Close</button>
        </div>
        <div class="md-content" >
            <div style="margin-top:15px">
              <div align="center">
                <el-table
                  :data="resultData"
                  height="200"
                  style="margin:5px auto;">
                  <el-table-column :label="key" v-for="(value,key) in resultData[0]"
                                   width="120">
                    <template slot-scope="scope">
                      <span class="blockspan" v-if="editingRow!=scope.$index" @click="handleEdit(scope.$index)">{{resultData[scope.$index][key]}}</span>
                      <el-input class="smallinput" v-if="editingRow==scope.$index" v-model="resultData[scope.$index][key]"></el-input>
                    </template>
                  </el-table-column>
                </el-table>
                <el-pagination
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="10"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="totalCount">
                </el-pagination>
              </div>
             </div>
          <a href="javascript:;" class="btn-login" @click="sendAllData">确定</a>
          <!--<el-Button @click="sendAllData" style="margin-top: 15px;margin-left: 400px;">确定</el-Button>-->
        </div>
        </div>
      </div>
      <div class="md-overlay" v-if="showDetail" @click="showDetail=false"></div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  .smallinput >.el-input__inner{
    height:20px;
  }
  .edit-input {
    padding-right: 100px;
  }
  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
  .el-table td, .el-table th.is-leaf {
    background: #6C89B1;
    color: #fff;
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
  .el-pagination {
    margin-bottom: 20px;
    margin-top: 20px;
  }
</style>
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
        this.getData();
      },
      viewResult(index, row){
        this.showDetail = true;
        this.nowEditJob = row.jobID;
        this.nowUserId=row.userID;
        this.getData();
      },
      getData(){
        var redisParam = {
          "key": this.nowUserId+"-"+this.nowEditJob,
          "start": this.currentPage,
          "end": this.currentPage + 10
        }
        axios.post("/kjb/tvs/redisData", redisParam).then
        ((response) => {
          var res = response.data;
          if (res.status == 1) {
            this.resultData = JSON.parse(res.data).items;
          }
        })
      }
    }
    }
</script>



