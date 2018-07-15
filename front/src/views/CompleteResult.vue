<template>
  <div style="background-color: #ffffff" >
    <div>
      <h2 style="margin-top: 20px">完整性结果</h2>
      <div  class="showtable" align="center" style="width:900px;margin:20px 0px 0px 100px;border: 1px solid #ccc;color: #333;overflow:auto;height:300px;">
        <table class="imagetable quarter-div_table" >
          <thead>
          <tr>
            <th  style=" text-algin:center;color:#fff;" v-for="(key,item) in resultData[0]" >{{item}}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for = "(item,index_outer) in resultData">
            <td v-for ="(it,index_inner) in item">
              <span class="blockspan" v-if="editingRow!=index_outer || !editRule" @click="handleEdit(index_outer)">{{it}}</span>
              <el-input class="smallinput" v-if="editRule && editingRow==index_outer" v-model="resultData[index_outer][index_inner]"></el-input>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <el-pagination
        small
        style="margin-left: 300px"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="10"
        layout="total, prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    </div>
    <div class="select-method">
      <el-button @click="handleRefresh()">手动更新</el-button>
      <el-button @click="methodRule=!methodRule" v-show="!this.editRule">规则更新</el-button>
      <div v-show="methodRule" style="border-top:2px solid #bbbbbb;margin:20px 0px;padding-top: 20px;display:flex;">
        <div style="width:400px">
          <p>
            <label style="margin-top: 10px;margin-left:30px;color: #bbbbbb;font-size: large">设置默认值</label>
          </p>
          <el-button @click="addmethodRule" style="margin-left:180px;">添加默认值</el-button>
          <div v-for="item in rulesforDefault" style="margin-top: 10px">
              <span style="color:#271e22">字段名:</span>
              <el-select v-model="item.name" style="width:80px;height:40px;" @change="selectdefault(item.name)">
                <el-option
                  v-for="it in options_primary_column"
                  :key="it.value"
                  :label="it.label"
                  :value="it.value"
                  :disabled="it.disabled"
               >
                </el-option>
              </el-select>
             <span style="color:#271e22">设置值:</span>
             <el-input v-model="item.value" style="border:1px solid #bbbbbb;height:40px;width:80px;border-radius:5px;"></el-input>
          </div>
        </div>
        <div style="width:600px">
          <p>
            <label style="margin-top: 10px;margin-left:30px;color: #bbbbbb;font-size: large">设置参考值</label>
          </p>
          <el-button @click="add_map" style="margin-left:180px">添加参考值</el-button>
          <table class="edittable" style="margin-top: 30px">
            <thead>
            <tr>
              <th > 主表中要检查的字段</th>
              <th>  主表的主键</th>
              <th>  该字段的来源</th>
              <th>  该字段所在表的主键</th>
              <th>  操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for = "(dataarray,index) in rulesforRefer">
              <td>
                <el-cascader
                  class="cascader"
                  expand-trigger="hover"
                  :options="options_primary_column"
                  v-model="dataarray.primary_column"
                >
                </el-cascader>
              </td>
              <td>
                <el-cascader
                  expand-trigger="hover"
                  :options="options_primary_key"
                  v-model="dataarray.primary_key"
                >
                </el-cascader>
              </td>
              <td>
                <el-cascader
                  expand-trigger="hover"
                  :options="options_source_column"
                  v-model="dataarray.source_column"
                  @change="handleChange3(index)">
                </el-cascader>
              </td>
              <td>
                <el-cascader
                  expand-trigger="hover"
                  :options="options_source_key[index]"
                  v-model="dataarray.source_key">
                </el-cascader>
              </td>
              <td>
                <el-button  type="primary" style="background-color: #FF8383;color:#fff;border:none" plain @click="delete_map(index)">删除</el-button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        </div>
      </div>
    <div style="margin-top: 30px">
      <el-button type="primary" @click="submit()">确认提交</el-button>
    </div>
    </div>
    </div>
  </div>
</template>
<style rel="stylesheet/scss" lang="scss" scoped>
  .imagetable {
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    height:300px;
    margin-top: 20px;
  }
  .el-select .el-input--suffix{
    height:20px  !important;
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
  .edittable {
    width:100%;
    border:none;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
  }
  .edittable th {
    font-weight:bold;
    /*background-color: #103251;*/
    color:#fff;
    font-size:0.95em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    border:none;
    min-width: 100px;
  }
  .edittable td {
    font-size:0.95em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
  }
  .edittable thead {
    background-color: #6787B0;
    font-size:0.95em;
    text-align:center;
    padding:4px;
  }
  .edittable tbody tr {
    border-bottom: 1px dashed #ccc;
    height: 50px;
    &:nth-child(2n) {
      background-color: #F2F7FD;
    }
  }
</style>
<script>
  import axios from 'axios';
  import {mapGetters} from 'vuex'
  export default{
    mounted(){
      this.$store.dispatch('GetConnect');
    },
    data(){
      return {
        editingRow: null, //当前定位的行
        resultData: [],
        currentPage: 0,
        totalCount: 10,
        editRule: false,   //选择手动修改
        methodRule: false,  //选择规则修改
        rulesforDefault: [{name: "", value: ""}],
        options_default_column: [{value: '', label: '', disable: null}],//设置默认值的可选属性
        rulesforRefer: [{primary_column: '', primary_key: '', source_column: '', source_key: ''}],
        options_primary_column: [{value: '', label: '', disable: null}], //设置主表的选择属性
        options_primary_key: [{value: '', label: '', disable: null}],  //设置主表的主键
        options_source_column: [],//设置从表的列
        options_source_key: [],//设置从表的主键
        backend_source_data: {}, //后端取得的数据
        haschanged: [],//改动的数据，此处使用每次一旦改动一次点击的条目就认为本页所有数据都已经改变
        fortest:{}  //用于测试
      }
    },
    created(){
      this.fillSource();
      console.log(this.$route.query.nowUserId);
      var redisParam = {
        "key": this.$route.query.nowUserId + "-" + this.$route.query.nowEditJob,
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
    computed: {
      ...mapGetters(['conns']),
    },
    methods: {
      handleRefresh(){
          this.editRule=!this.editRule;
          if(this.editRule){
              this.methodRule=false;
          }
          if(this.haschanged.length>0){
              this.submit();
          }
      },
      add_map(){
        this.rulesforRefer.push({primary_column: "", primary_key: "", source_column: "", source_key: ""});
        this.options_source_key.push([{value: '', label: ''}]);
      },
      delete_map(index)
      {
        this.rulesforRefer.splice(index, 1);
        this.options_source_key.splice(index, 1);
      },
      handleEdit(index){
        this.editingRow = index;
        this.haschanged = this.resultData;
      },
      sendAllData(){

      },
      handleCurrentChange(val){
        this.currentPage = val;
        this.editingRow=null;
        this.getData(val);
        if (this.haschanged.length > 0) {
          this.submit();
        }
      },
      handleChange3(index){
        var source = this.rulesforRefer[index].source_column;
        this.options_source_key[index] = [];
        console.log(source);
        for (var i in this.backend_source_data) {
          if (i == source[0]) {
            console.log(source[0])
            for (var j in this.backend_source_data[i].tableStructures) {

              if (this.backend_source_data[i].tableStructures[j].tableName == source[1]) {
                console.log(source[1]);
                for (var t in this.backend_source_data[i].tableStructures[j].colmnuStructures) {

                  this.options_source_key[index].push({
                    label: this.backend_source_data[i].tableStructures[j].colmnuStructures[t].name,
                    value: this.backend_source_data[i].tableStructures[j].colmnuStructures[t].name
                  });
                }
              }
            }
          }
        }

      },
      fillSource(){
        var temp = [];
        this.conns.forEach((item, index) => {
          var tempconns = {label: item.displayName, value: item.id, children: []};
          temp.push(tempconns);
        })
        this.options_source_column = temp;
        this.conns.forEach((item, index) => {
          this.fillTable(item, index);
        })
      },
      fillTable(item, index){
        axios.get("/kjb/cms/descriptionDataBase", {
          params: {
            "nick": item.id,
          }
        }).then((response) => {
          var res = response.data;
          if (res.status == 1) {
            var jsondata = JSON.parse(res.data);
            this.backend_source_data[item.id] = jsondata;
            var indexTable = index;
            jsondata.tableStructures.forEach((item, index) => {
              var temptable = {label: item.tableName, value: item.tableName, children: []};
              item.colmnuStructures.forEach((item, index) => {
                temptable.children.push({label: item.name, value: item.name})
              });
              this.options_source_column[indexTable].children.push(temptable);
            })

          }
        })
      },
      addmethodRule(){
        let piece = {name: "", value: ""};
        this.rulesforDefault.push(piece);
      },
      getData(val){
        var redisParam = {
          "key": this.$route.query.nowUserId + "-" + this.$route.query.nowEditJob,
          "start": this.currentPage * 10,
          "end": this.currentPage * 10 + 10
        }
        axios.post("/kjb/tvs/redisData", redisParam).then
        ((response) => {
          var res = response.data;
          if (res.status == 1) {
            this.resultData = JSON.parse(res.data).items;
            this.fillPrimary(this.resultData);
          }
        })
      },
      fillPrimary(resultData){
        var temp_all = [];
        for (var item in resultData[0]) {
          let temp = {label: item, value: item, disable: null};
          console.log(temp);
          temp_all.push(temp);
        }
        this.options_primary_column = temp_all;
        this.options_primary_key = temp_all;
        this.options_default_column = temp_all;
      },
      submit(){
//        debugger;
        let edittype = 0;
        this.editingRow=null;
        var integrity = {};
        var commit_ReferRule=[];
        var commit_DefaultRule={};
        if (!this.editRule) {
//            debugger;
            for(let i =0;i<this.rulesforRefer.length;i++){
//                debugger;
                if(this.rulesforRefer[i].primary_key &&( !this.rulesforRefer[i].primary_column|| !this.rulesforRefer[i].source_key ||!this.rulesforRefer[i].source_column )){
                  const h = this.$createElement;
                  this.$notify({
                    title: '提示',
                    message: h('i', {style: 'color: teal'}, "填充规则配置不完整")
                  });
                  return;
                }else if(this.rulesforRefer[i].primary_key){
//                    debugger;
                    let temp_column=this.rulesforRefer[i].source_column.join(',');
                    let temp_key=this.rulesforRefer[i].source_key[0];
                    commit_ReferRule.push({"key":this.rulesforRefer[i].primary_column[0]+','+this.rulesforRefer[i].primary_key[0],"value":temp_column+','+temp_key});
//                    debugger;
                }
            }
            for(let j=0;j<this.rulesforDefault.length;j++){
              if(this.rulesforDefault[j].name && !this.rulesforDefault[j].value){
                const h = this.$createElement;
                this.$notify({
                  title: '提示',
                  message: h('i', {style: 'color: teal'}, "填充规则配置不完整")
                });
                return;
              }else if(this.rulesforDefault[j].name){
                 commit_DefaultRule[this.rulesforDefault[j].name]=this.rulesforDefault[j].value;
              }
            }
          edittype = 1; //规则更新
          integrity = {
            'userId': parseInt(this.$route.query.nowUserId),
            'jobId': parseInt(this.$route.query.nowEditJob),
            'type': edittype,
            'mapEntries': commit_ReferRule,
            'unifyMap': commit_DefaultRule,
          }
//          debugger;
        } else {
          integrity = {    //手动更新
            'userId': parseInt(this.$route.query.nowUserId),
            'jobId': parseInt(this.$route.query.nowEditJob),
            'type': edittype,
            'mapEntries': this.haschanged,
            'unifyMap': null,
          }
        }
//        debugger;
        this.fortest=integrity;
        console.log(integrity);
        axios.post("/kjb/integrity/updateIntegrity", integrity).then((response) => {
          var res = response.data;
          if (res.status == 1) {
            this.haschanged = [];
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, "提交成功")
            });
          } else {
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, "提交失败，请再次提交")
            });
          }
        }).catch(() => {
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', {style: 'color: teal'}, "提交失败，请再次提交")
          });
        })
      }
    }
  }

</script>
