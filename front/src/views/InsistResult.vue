<template>
  <div>
    <div>
      <h2>一致性结果</h2>
      <div  class="showtable" align="center" style="width:100%;margin:0px 5px 20px 5px;border: 1px solid #ccc;color: #333;overflow:auto;height:300px;">
        <table class="imagetable quarter-div_table" >
          <thead>
          <tr>
            <th>选择</th>
            <th  style=" text-algin:center;color:#fff;" v-for="(key,item) in resultData[0]" >{{item}}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for = "(item,index_outer) in resultData">
            <td>
              <el-radio style="margin-left: 20px" v-model="selectResult[index_outer]" label="1">主表</el-radio>
              <el-radio style="margin-left: 20px" v-model="selectResult[index_outer]" label="0">从表</el-radio>
            </td>
            <td v-for ="(it,index_inner) in item">
              <span class="blockspan" v-if="editingRow!=index_outer" @click="handleEdit(index_outer)">{{it}}</span>
              <el-input class="smallinput" v-if="editingRow==index_outer" v-model="resultData[index_outer][index_inner]"></el-input>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <el-pagination
        small
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="10"
        layout="total, prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    </div>
    <el-button>提交</el-button>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  .imagetable {
    width:100%;
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    height:300px;
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

  .el-pagination {
    margin-bottom: 20px;
    margin-top: 20px;
    width:300px !important;
  }
</style>
<script>
  import axios from 'axios';
  export default{
    mounted(){
      this.$store.dispatch('GetConnect');
    },
    data(){
      return{
        resultData:[],
        currentPage:0,
        totalCount:10,
        editingRow:null,
        selectValue:[],
        selectResult:[]
      }
    },
    created(){
      console.log(this.$route.query.nowUserId);
      var redisParam = {
        "key": this.$route.query.nowUserId+"-"+this.$route.query.nowEditJob,
        "start": this.currentPage,
        "end": this.currentPage + 10
      }
      axios.post("/kjb/tvs/redisLen", redisParam).then
      ((response) => {
        var res = response.data;
        if (res.status == 1) {
          this.totalCount = parseInt(res.data);
          this.getData();
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
      getData(val){
        var redisParam = {
          "key": this.$route.query.nowUserId+"-"+this.$route.query.nowEditJob,
          "start": this.currentPage*10,
          "end": this.currentPage*10+10
        }
        axios.post("/kjb/tvs/redisData", redisParam).then
        ((response) => {
          var res = response.data;
          if (res.status == 1) {
            this.resultData = JSON.parse(res.data).items;
            console.log(this.resultData);
            for(var i=0;i<this.resultData.length;i++){
                this.selectResult.push("1");
            }
          }
        })
      },
      sortAnswer(){

      }
    }
  }

</script>

