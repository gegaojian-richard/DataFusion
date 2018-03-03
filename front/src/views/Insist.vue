<template>
  <div>
    <connect-info  @previewtable="previewTable" style="height:490px;float:left;width:180px"></connect-info>
    <div style="float:right;width:400px;clearfix:none;">
      <table class="imagetable">
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
        <tr v-for = "(dataarray,index) in save_item">
          <td>
            <el-cascader
              expand-trigger="hover"
              :options="options_primary_column"
              v-model="dataarray.primary_column"
              @change="handleChange1">
            </el-cascader>
          </td>
          <td>
            <el-cascader
              expand-trigger="hover"
              :options="options_primary_key"
              v-model="dataarray.primary_key"
              @change="handleChange2">
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
              v-model="dataarray.source_key"
              @change="handleChange4">
            </el-cascader>
          </td>
          <td>
            <el-button  type="primary" plain @click="delete_map(index)">删除</el-button>
          </td>
        </tr>
        </tbody>
      </table>
      <el-button type="primary" plain @click="add_map">添加</el-button>
      <el-button type="primary" plain @click="add_ok">确认提交</el-button>
    </div>
    <div class="showtable" style="margin-left: 200px;width:400px;height:490px;background-color: #ffff;position:relative">
    <table class="imagetable">
      <thead>
      <tr>
        <th  class="removep" v-for="(key,item) in previewData[0]" v-on:contextmenu.prevent="sha(item)">{{item}}</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for = "item in previewData">
        <td v-for ="it in  item">{{it}}</td>
      </tr>
      </tbody>
    </table>
    </div>
    <div>
      {{  res_all }}
    </div>
  </div>

</template>

<style>
 .showtable{
   overflow-x: auto;
   overflow-y: auto;
 }
 .imagetable {
   width:100%;
   margin:15px 0;
   border:0;
   font-size:1.2em;
   text-align:center;
   padding:4px;
   border-collapse:collapse;
 }
 .imagetable th {
   font-weight:bold;
   background-color: #103251;
   color:#bfcbd9;
   font-size:0.95em;
   text-align:center;
   padding:4px;
   border-collapse:collapse;
   border: 1px solid #ffffff;
   border-width:1px
 }
 .imagetable td {
   font-size:0.95em;
   text-align:center;
   padding:4px;
   border-collapse:collapse;
   border: 1px solid #ffffff;
   border-width:1px
 }
 .imagetable tr {
   border-bottom: 1px solid #103251;
 }
</style>
<script>
  import axios from 'axios'
  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  import {mapGetters} from 'vuex'
  export default{
    data(){
      return {
        previewData:[]  ,       //请求的表格数据
        save_item:[{primary_column:'',primary_key:'',source_column:'',source_key:''},{primary_column:'',primary_key:'',source_column:'',source_key:''}],
        options_primary_column:[ {  value:'',  label:''}],
        options_primary_key:[{ value:'',label:''}],
        options_source_column:[
          {
            value:'',label:'',children:[
              {
                value:'',label:'',children:[
                  {
                    value:'',label:'',children:[]
                  }]
              }]
          }],
        options_source_key:[[ { value:'', label:''}],[ { value:'', label:''}]],
        save_jsondata:'',
        res_all:[],
      }
    },
    components: {
      connectInfo,
    },
    computed:{
      ...mapGetters(['conns']),
    },
    methods: {
      previewTable(emitdata){
        ///  每次请求数据之前先清空之前的
        this.options_primary_column= [ {  value:'',  label:''}];
        this.options_primary_key=    [{  value:'',label:''}];
        this.options_source_key=  [[ { value:'', label:''}],[ { value:'', label:''}]];
        this.options_source_column=[
          {
            value:'',label:'',children:[
              {
                value:'',label:'',children:[
                  {
                    value:'',label:''
                  }]
              }]
          }];
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
          /////  以下部分是完成目标表的对应字段选择   和 目标表 主键
          var count = 0;
          for(var property in this.previewData[0])
          {
            count = count +1;
          }
          for(var zz = 0;zz<count-1;zz++)
          {
            this.options_primary_column.push({  value:'',  label:''});
          }
          var z = 0;
          for(var property in this.previewData[0])
          {
            this.options_primary_column[z].label = property;
            this.options_primary_column[z].value = property;
            z++;
          }
          this.options_primary_key = this.options_primary_column;
          //////////////////////   以下部分是完成  源表的对应字段的选择
          for(var i = 0;i<this.conns.length;i++) {
            this.options_source_column[i].label = this.conns[i].displayName;
            this.options_source_column[i].value = this.conns[i].id;  //   数据库
            axios.get("/kjb/cms/descriptionDataBase",{
              params:{
                "nick":this.conns[i].id,
              }
            }).then((response)=> {
              var res = response.data;
              if (res.status == 1) {
                var jsondata = JSON.parse(res.data);
                this.save_jsondatandata = jsondata;
                /////    莫名奇妙i 加了 1
                i= i -1;
                //////   真是简直了
                for(var j = 0;j<jsondata.tableStructures.length-1;j++) {  ///   表添加
                  this.options_source_column[i].children.push({
                    value: '',
                    label: '',
                    children: [{value: '',label: '',children: []}]
                  });
                }
                for(var j = 0;j<jsondata.tableStructures.length;j++) {  ///   表
                  this.options_source_column[i].children[j].label = jsondata.tableStructures[j].tableName;
                  this.options_source_column[i].children[j].value = jsondata.tableStructures[j].tableName;
                  for(var k = 0;k<jsondata.tableStructures[j].colmnuStructures.length-1;k++ )   ///  字段添加
                  {
                    this.options_source_column[i].children[j].children.push({value: '',label: ''});
                  }
                  for(var k = 0;k<jsondata.tableStructures[j].colmnuStructures.length;k++ )
                  {
                    this.options_source_column[i].children[j].children[k].label = jsondata.tableStructures[j].colmnuStructures[k].name;
                    this.options_source_column[i].children[j].children[k].value = jsondata.tableStructures[j].colmnuStructures[k].name;
                  }
                }
              }  //  if
            })

          } //  for
        })
      },  //  当前函数
      add_map(){
        this.save_item.push({primary_column:"",primary_key:"",source_column:"",source_key:""});
        this.options_source_key.push([ { value:'', label:''}]);
      },
      delete_map(index)
      {
        this.save_item.splice(index,1);
        this.options_source_key.splice(index,1);
      },
      handleChange1() {
        //  给  主表选主键填充
      },
      handleChange2() {

      },
      handleChange3(index) {
        //alert(this.save_item[index].source_column[0]);
         for(var i = 0;i<this.options_source_column.length;i++)
         {
            if(this.save_item[index].source_column[0] == this.options_source_column[i].value)  ///  确定数据库
            {
              for(var j = 0;j<this.options_source_column[i].children.length;j++)
              {
                 if(this.options_source_column[i].children[j].value == this.save_item[index].source_column[1])  //  确定表
                 {
                   for(var k = 0;k<this.options_source_column[i].children[j].children.length-1;k++)
                   {
                     this.options_source_key[index].push({ value:'', label:''});
                   }
                    for(var k = 0;k<this.options_source_column[i].children[j].children.length;k++)
                    {
                       this.options_source_key[index][k].label =  this.options_source_column[i].children[j].children[k].label;
                       this.options_source_key[index][k].value =  this.options_source_column[i].children[j].children[k].value;
                    }
                 }
              }
            }
         }
      },
      handleChange4() {

      },
      ////   最终的确认提交
      add_ok()
      {
           for(var i = 0;i<this.save_item.length;i++)
           {
             if(this.save_item[i].primary_column=="" && this.save_item[i].primary_key=="" && this.save_item[i].source_column=="" && this.save_item[i].source_key=="")
             {

             }
             else if(this.save_item[i].primary_column!="" && this.save_item[i].primary_key!="" && this.save_item[i].source_column!="" && this.save_item[i].source_key!="")
             {
                  var a = "key"+":"+this.save_item[i].primary_column+","+this.save_item[i].primary_key+","+":"+
                    "value"+":"+this.save_item[i].source_column[0]+","+this.save_item[i].source_column[1]+","+this.save_item[i].source_column[2]+","+this.save_item[i].source_key;
                  this.res_all.push(a);
             }
             else
             {
               alert('第'+(i+1)+"个检查配置不完整");
             }
           }
      },
//      submit(){
//        axios.post("/kjb/dgs/accuracy/commitjob",this.res_all).then
//        ((response)=>{
//          var res=response.data;
//          if(res.status==1){
//            alert("submit complete check success");
//          }
//        })
//      }


    }  //   method;
  }
</script>
