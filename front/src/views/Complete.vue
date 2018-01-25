<template>
  <div>
    <connect-info  @previewtable="previewTable" style="height:490px;float:left;width:180px"></connect-info>
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
