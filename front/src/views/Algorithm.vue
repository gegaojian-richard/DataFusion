<template>
  <div style="padding: 20px;height: 100%;">
    <div style="padding: 0px 0px 20px 20px;heborder:1px solid #bfcbd9">
      <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
        <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
        <span style="color:#698EC3;">算法配置</span>
      </p>
      <div style="text-align: left;margin-left: 50px;margin-top: 20px;display: flex">
        <div>
          <span>文档路径</span>
          <input v-model="position" style="width:200px;margin-left: 20px;border-radius: 3px;border: 1px solid #ccc;height: 47px;padding-left: 20px;font-size: 16px;color:#333;" >
        </div>
        <div style="margin-left: 100px">
          <span>算法选择</span>
          <el-select v-model="algorithms" style="margin-left: 20px;width:200px;" >
            <el-option label="中文人名识别" value="0"></el-option>
            <el-option label="TextRank关键词抽取" value="1"></el-option>
            <el-option label="TFIDF关键词抽取" value="2"></el-option>
            <el-option label="主题模型主题分布计算" value="3"></el-option>
          </el-select>
        </div>
      </div>
    <div style="margin-top: 20px">
      <p style="height: 50px;text-align: left;line-height: 60px;color:#000;font-size: 16px;">
        <span style="color:#000;">参数配置</span>
      </p>
      <div style="text-align: left;margin-left: 40px;margin-top: 20px;display: flex">
        <div>
          <span>目标数据库</span>
          <el-select v-model="database" @change="chooseDatabase()" style="margin-left: 15px;width:200px;">
            <el-option
              v-for="item in conns"
              :key="item.displayName"
              :label="item.displayName"
              :value="item.displayName">
            </el-option>
          </el-select>
        </div>
        <div style="margin-left: 120px">
          <span>目标表</span>
          <el-select v-model="table" style="margin-left: 15px;width:200px;">
            <el-option
              v-for="item in tableForChoose"
              :key="item.tableName"
              :label="item.tableName"
              :value="item.tableName">
            </el-option>
          </el-select>
        </div>
      </div>
      <div style="margin-top: 20px">
        <span v-if="algorithms==1||algorithms==2">TOP-K:</span><el-input  v-model="topK" v-if="algorithms==1||algorithms==2" style="width:200px"></el-input>
        <span v-if="algorithms==3">主题数:</span><el-input v-model="topicNum" v-if="algorithms==3" style="width:200px"></el-input>
      </div>
    </div>
    <el-Button @click="submit" class="submit" style="background: #78BDF8;color:#fff;">提交</el-Button>
    </div>
  </div>
</template>
<style scoped>
span{
  color: #000;
  font-weight: bold;
  font-size: 16px;
}
  .submit {
    display: block;
    float: right;
    margin-right: 30px;
  }
  .el-button {
    padding: 7px 20px;
  }
  .doc-input {
    border:1px solid #ccc;
    border-radius: 3px;
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
    mounted(){
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
        switch (this. algorithms) {
          case "0":
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
          case "1":
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
          case "2":
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
          case "3":
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
