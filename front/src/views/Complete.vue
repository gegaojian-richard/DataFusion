<template>
  <div>
    <connect-info  @previewtable="previewTable"style="height:100%;width:180px;position:fixed;overflow: auto;z-index:2000;"></connect-info>
    <div style="padding: 20px;margin-left:180px;overflow: visible;">
      <div style="height: 100%;;border:1px solid #bfcbd9;padding: 0px 20px;">
        <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
          <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
          <span>完整性检测</span>
        </p>
        <div  class = "quarter-div" style="padding:10px;">   <!--  显示当前显示字段-->
          <span  style="font-size:18px;color:#000;float:left;margin-top:12px;">选取属性：</span>
          <el-tag
            v-for="(item,index) in res_all"
            :key="item"
            closable
            :disable-transitions="false"
            @close="delete_click(index)" style="float: left;margin-top: 10px;">
            {{item}}
          </el-tag>
          <el-button type="primary" plain style="float:right;margin-top: 30px;position:relative;background-color: #82B7E3;color:#fff;" @click="submit()">确认提交</el-button>
        </div>
        <!--table class = "table_class_arru " style="float:top;text-align:center;width=90%; border=1 ;class=t1"  id=mytab;-->
        <!-- 用来显示表格 -->
        <div class="showtable" style="width:100%;margin-top: 50px;border: 1px solid #ccc;color: #333;overflow:auto;height:350px;">
          <table class="imagetable quarter-div_table">
            <thead>
            <tr>
              <th  style=" text-algin:center;color:#fff;" v-for="(key,item) in previewData[0]" v-on:click="sha(item)">{{item}}</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for = "item in previewData">
              <td v-for ="it in  item">{{it}}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>  <!-- 显示字段那列结束 -->
    <div >  <!-- 弹出确认加入 -->
    </div>   <!-- 弹出窗口来选择检查类型 -->
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  .showtable{
    margin-top: 40px;
    position:relative;
    overflow: auto;
    background-color: #ffffff;
    tbody {
      height: 600px;
      overflow-x: auto;
      overflow-y: auto;
    }
  }

  .imagetable {
    width:100%;
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
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
  .el-button {
    padding: 7px 20px;
  }
  .el-tree__empty-text{
    color: #fff!important;
  }
</style>
<script>
  import axios from 'axios'
  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  export default{
    components:{connectInfo},
    data() {
      return {
        previewData:         //请求的表格数据{
          [],
        //   以下为新增的
        save_item:"",        //  当前选中的字段名
        show : false,        //  确认检查弹窗显示标志变量
        res_all:[],          //  最终存的数组
        dataSourceId:"",     //  数据库id
        tableName:"",         //  当前表名
        jobId:""              //任务id
      }
    },   ///   data
    components: {
      connectInfo,
    },
    methods: {
      previewTable(emitdata) {
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
            this.flag = true;
          }
        })

        this.res_all=[];    ///  这里用于换表的时候清空之前的
        this.dataSourceId = emitdata.database
        this.tableName = emitdata.table
      },
      // 鼠标的点击事件
      sha(item) {
        this.save_item = item;
        this.click_type1();
      },
      /// 添加操作
      click_type1() {

        for(var i =0;i<this.res_all.length;i++)
        {
          if(this.res_all[i] == this.save_item)
          {
            this.show = false;
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', { style: 'color: teal'}, '不能重复选取')
            });
            return;
          }

        }

        this.res_all.push(this.save_item);
        this.show = false;
      },
      ///  删除操作
      delete_click(index){
        this.res_all.splice(index,1);
      },
      submit(emitdata){
        var param ={
          "dataSourceId": this.dataSourceId,
          "tableName": this.tableName,
          "columnNames":this.res_all
        }
        axios.post("/kjb/dgs/integrity/commitjob",param).then
        ((response)=>{
          var res=response.data;
          if(res.status==1){
            const h = this.$createElement;
            this.$notify({
              title: '提交成功',
              message: h('i', { style: 'color: teal'}, '完整性检测任务提交成功，任务号为'+res.msg)
            });
            this.res_all=[];
          }else{
            const h = this.$createElement;
            this.$notify({
              title: '提交失败',
              message: h('i', { style: 'color: teal'}, '完整性检测任务提交失败，请重新提交')
            });
          }
        }).catch((e)=>{
          const h = this.$createElement;
        this.$notify({
          title: '提交失败',
          message: h('i', { style: 'color: teal'}, '完整性检测任务提交失败，请重新提交')
      });
      })


      }
    }  ///  method

  }
</script>

