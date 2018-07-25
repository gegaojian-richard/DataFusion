<template>
  <div>
    <div>
      <h2>一致性结果</h2>
      <div  class="showtable" align="left" color="#3333" style="width:100%;margin:0px 5px 20px 5px;border: 1px solid #ccc;color: #333;height:400px;">
        <el-collapse >
          <el-collapse-item style="height:200px;overflow-y: auto;width:90%" v-bind:title="everyColumn[index_out]"  name="1" v-for="(item,index_out) in everyColumn" key="index">
            <table class="imagetable quarter-div_table"  >
              <thead>
              <tr>
                <th>选择</th>
                <th  style=" text-align:center;color:#fff;" v-for="(key,item) in getBackData[0][0]" >{{item}}</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for = "(item_inner,index_inner) in getBackData[index_out]">
                <td>
                  <el-radio style="margin-left: 20px" label="main" v-model="radio[index_out][index_inner]">{{description[index_out].mainTableName}}</el-radio>
                  <el-radio style="margin-left: 20px" label="follow" v-model="radio[index_out][index_inner]">{{description[index_out].followTableName}}</el-radio>
                </td>
                <td v-for ="(it,feature) in item_inner">
                  <span class="blockspan" >{{it}}</span>
                </td>
              </tr>
              </tbody>
            </table>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <el-button @click="submit()">提交</el-button>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  .imagetable {
    width:95%;
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    max-height:200px;
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
    height: 30px;
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

  /*.el-pagination {*/
    /*margin-bottom: 20px;*/
    /*margin-top: 20px;*/
    /*width:300px !important;*/
  /*}*/
</style>
<script>
  import axios from 'axios';
  export default{
    mounted(){
      this.$store.dispatch('GetConnect');
    },
    data(){
      return{
        getBackData:[],
//        currentPage:0,
        totalCount:10,
        editingRow:null,
        selectValue:[],
        selectResult:[],
        description:[],
        everyColumn:[],
        everyStartEnd:[],
        radio:[]  //选择的数据
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
          this.getDescription();
        }
      })
    },
    methods: {
      submit(){
          var success=0; //用来计算是否全都提交成功
 //       console.log(this.getBackData);
//        debugger;
          for(let i=0;i< this.radio.length;i++) {
            let tempInfo = {};
           // let u2r=[];
            tempInfo.mainDataSourceId = this.description[i].mainDataSourceId;
            tempInfo.mainTableName=this.description[i].mainTableName;
            tempInfo.mainPrimary_key=this.description[i].mainPrimary_key;
            tempInfo.mainColumnName=this.description[i].mainColumnName;
            tempInfo.followDataSourceId=this.description[i].followDatasourceID;
            tempInfo.followTableName=this.description[i].followTableName;
            tempInfo.followPrimary_key=this.description[i].followPrimary_key;
            tempInfo.followColumnName=this.description[i].followColumnName;
            tempInfo.u2r=[];
            for (let j = 0; j < this.radio[i].length; j++) {
                if(this.radio[i][j]=='main'){
                    let temp_u2r={};
                    temp_u2r.key="left";
                    let keyAndValue=[];
                    let dd=this.getBackData[i][j];
                    Object.keys(dd).forEach(function(key){
                        let c=dd[key];
                        keyAndValue.push(c);
                    })
                    keyAndValue.splice(2,1);
                    temp_u2r.value=keyAndValue[0]+","+keyAndValue[1];
                    tempInfo.u2r=temp_u2r;
                }else if( this.radio[i][j]=='follow'){
                  let temp_u2r={};
                  temp_u2r.key="right";
                  let keyAndValue=[];
                  let dd=this.getBackData[i][j];
                  Object.keys(dd).forEach(function(key){
                    keyAndValue.push(dd[key]);
                  })
                  keyAndValue.splice(1,1);
                  temp_u2r.value=keyAndValue[0]+","+keyAndValue[1];
                  tempInfo.u2r.push(temp_u2r);
                }
            }
            console.log(tempInfo);
            axios.post("/kjb/dgs/consistency/CommitUpdateJob",tempInfo).then((response)=>{
                var res=response.data;
                if(res.status==1){
                    success++;
                    if(success==(this.radio.length)){   //全都提交成功
                      const h = this.$createElement;
                      this.$notify({
                        title: '提示',
                        message: h('i', {style: 'color: teal'}, "提交成功")
                      });
                    }
                }else{

                }
              }

            )
          }
      },
      getEveryColumn(){
          for(let i in  this.description){
              let temp="主表属性:"+this.description[i].mainColumnName+"参考表属性:"+this.description[i].followColumnName;
              this.everyColumn.push(temp);
              let tempstart=[];
              tempstart.push(this.description[i].redisStart);
              tempstart.push(this.description[i].redisEnd);
//              this.description[i].pop();
              this.everyStartEnd.push(tempstart);
              this.getData(tempstart[0],tempstart[1]);
              this.radio.push([]);
              for(let t=0;t<tempstart[1]-tempstart[0];t++){
                  this.radio[i].push(' ');
              }
          }
      },
      getDescription() {   //先获取所有的检查一致性属性的信息
        var redisParam = {
          "key": this.$route.query.nowUserId+"-"+this.$route.query.nowEditJob+'-'+'description',
          "start": 0,
          "end": 10
        }
        axios.post("kjb/tvs/redisData",redisParam).then((response)=>{
            var res=response.data;
            if(res.status==1){
              this.description=JSON.parse(res.data).items;
              this.getEveryColumn();
            }
        })
      },
      getData(start,end){    //获取每个检查属性的对应不一致情况放在getData中
        var redisParam = {
          "key": this.$route.query.nowUserId+"-"+this.$route.query.nowEditJob,
          "start": start,
          "end": end
        }
        axios.post("/kjb/tvs/redisData", redisParam).then
        ((response) => {
          var res = response.data;
          if (res.status == 1) {
            let resultData = JSON.parse(res.data).items;
            this.getBackData.push(resultData);
           console.log(resultData);
//            for(var i=0;i<this.resultData.length;i++){
//                this.selectResult.push("1");
//            }
          }
        })
      },
    }
  }

</script>

