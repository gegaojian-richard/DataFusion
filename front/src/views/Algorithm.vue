<template>
  <div>
    <div>
      <span style="font-size: 26px; font-weight: 400;color: #3333;margin: 20px auto 40px auto;text-align: center; font-weight: bold;">算法配置</span><br>
      <div style="text-align: left;margin-left: 100px;margin-top: 20px">
        <div>
          <span>文档路径</span>
          <el-input v-model="position" style="width:200px"></el-input>
        </div>
        <div style="margin-top: 20px">
          <span>算法选择</span>
          <el-select v-model="algorithms"  >
            <el-option label="中文人名识别" value="0"></el-option>
            <el-option label="TextRank关键词抽取" value="1"></el-option>
            <el-option label="TFIDF关键词抽取" value="2"></el-option>
            <el-option label="主题模型主题分布计算" value="3"></el-option>
          </el-select>
        </div>

      </div>
    </div>
    <div style="margin-top: 50px;text-align: left;margin-left: 100px">
      <span style="font-size:20px">{{algorithmsList[algorithms]}}参数配置</span>
      <div style="margin-top: 20px">
        <span>目标数据库</span>
        <el-select v-model="database" @change="chooseDatabase()">
          <el-option
            v-for="item in conns"
            :key="item.displayName"
            :label="item.displayName"
            :value="item.displayName">
          </el-option>
        </el-select>
        <div style="margin-top: 20px">
          <span>目标表</span>
          <el-select v-model="table" >
            <el-option
              v-for="item in tableForChoose"
              :key="item.tableName"
              :label="item.tableName"
              :value="item.tableName">
            </el-option>
          </el-select>
        </div>
        <div style="margin-top: 20px">
          <span v-if="algorithms==1||algorithms==2">TOP-K:</span><el-input  v-model="topK" v-if="algorithms==1||algorithms==2" style="width:200px"></el-input>
          <span v-if="algorithms==3">主题数:</span><el-input v-model="topicNum" v-if="algorithms==3" style="width:200px"></el-input>
        </div>
        </div>
    </div>
    <el-Button @click="submit">提交</el-Button>
  </div>
</template>
<style scoped>
span{
  color: #3333;
  font-weight: bold;
  font-size: 16px;
}
</style>
<script>
  import {mapGetters} from 'vuex'
  import axios from 'axios'
  export default{
      computed:{
        ...mapGetters(['conns']),
      },
      data(){
        return {
            position:"",
          database:"",
          algorithms:null,
          algorithmsList:{"0":"中文人名识别","1":"TextRank关键词抽取","2":"TFIDF关键词抽取","3":"主题模型主题分布计算"},
          table:"",
          tableForChoose:[],
          topK:"",
          topicNum:"",
        }
      },
    beforeMount(){
        this.$store.dispatch('GetConnect');
    },
    methods: {
      chooseDatabase(){
        for (var i = 0; i < this.conns.length; i++) {
          console.log(this.conns[i].displayName)
          if (this.conns[i].displayName == this.database) {
            this.tableForChoose = this.conns[i].tables;
          }
        }
      },
      submit(){
        switch (this.algorithms) {
          case 0:
              var resultforNameRecognition={
                "corpusPath":this.position,
                "tableName":this.table,
                "dataSourceId":this.database,
              }
            axios.post("kjb/nsps/NameRecognition",resultforNameRecognition).then((response)=>{
                  var res=response.data;
                  if(res.status==1){
                    this.$message('任务提交成功，请在任务管理处查看进度');
                  }
            })
                break;
          case 1:
            var resultforTextRank={
              "corpusPath":this.position,
              "topK":this.topK,
              "tableName":this.table,
              "dataSourceId":this.database,
            }
            axios.post("kjb/nsps/TextRank",resultforTextRank).then(response=>{
              var res=response.data;
              if(res.status==1){
                this.$message('任务提交成功，请在任务管理处查看进度');
              }
            })
            break;
          case 2:
            var resultforTFIDF={
              "corpusPath":this.position,
              "topK":this.topK,
              "tableName":this.table,
              "dataSourceId":this.database,
            }
            axios.post("kjb/nsps/TFIDF",resultforTFIDF).then(response=>{
              var res=response.data;
              if(res.status==1){
                this.$message('任务提交成功，请在任务管理处查看进度');
              }
            })
            break;
          case 3:
            var resultforTopicModel={
              "corpusPath":this.position,
              "topicNum":this.topicNum,
              "tableName":this.table,
              "dataSourceId":this.database,
            }
            axios.post("kjb/nsps/TFIDF",resultforTopicModel).then(response=>{
              var res=response.data;
              if(res.status==1){
                this.$message('任务提交成功，请在任务管理处查看进度');
              }
            })
            break;
          default:
              break;
        }
      }
    }

  }
</script>
