<template>
  <div class="app-container calendar-list-container" >
    <el-table :data="list" v-loading.body="listLoading" border fit highlight-current-row style="width:500px">

      <el-table-column align="center" label="任务ID" width="80">
        <template slot-scope="scope">
          <span>{{scope.row.dataSourceId}}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="任务类型">
        <template slot-scope="scope">
          <span>{{scope.row.jobType}}</span>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" label="任务状态" width="110">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{scope.row.status}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="Actions" >
      <template slot-scope="scope">
        <el-button type="primary" @click='view(scope.row.jobType)' size="small" icon="el-icon-edit">查看详情</el-button>
      </template>
    </el-table-column>

    </el-table>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        list:[]
        ,
        listLoading: true,
      }
    },
   created(){
     var  param={}
     this.listLoading = true
     axios.post("/kjb/tvs/privateTasks"
     ).then((response) => {
       var res = response.data;
       if (res.status == 1) {
         this.listLoading = false
        this.list=JSON.parse(res.data);
       } else {
       }
     })
   },
    methods: {
//      getList() {
//
////        fetchList(this.listQuery).then(response => {
////          const items = response.data.items
////          this.list = items.map(v => {
////            this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
////
////            v.originalTitle = v.title //  will be used when user click the cancel botton
////
////            return v
////          })
//
////        })
//      },
//      cancelEdit(row) {
//        row.title = row.originalTitle
//        row.edit = false
//        this.$message({
//          message: 'The title has been restored to the original value',
//          type: 'warning'
//        })
//      },
//      confirmEdit(row) {
//        row.edit = false
//        row.originalTitle = row.title
//        this.$message({
//          message: 'The title has been edited',
//          type: 'success'
//        })
//      }
    }
  }
</script>

<style scoped>
  .edit-input {
    padding-right: 100px;
  }
  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
</style>

