<template>
  <div>
    <el-row>
      <el-col span="6">
        <connect-info  @previewtable="previewTable" style="height:490px;width:180px"></connect-info>
      </el-col>
      <el-col span="18">
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import axios from 'axios'
  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  export default{
    data(){
      return {
        previewData:[]         //请求的表格数据
      }
    },
    components: {
      connectInfo,
    },
    methods: {
      previewTable(emitdata){
        axios.get("/kjb/cms/preview", {
            params: {
              "display": emitdata.database,
              "table": emitdata.table
            }
          }
        ).then((response) => {
          var res = response.data;
          if (res.status == 1) {
            var receive = JSON.parse(res.data);
            this.previewData = receive.items;
          }
        })
      }

    }
  }
</script>
