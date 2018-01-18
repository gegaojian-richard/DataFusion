<template>
  <div>
    <el-row :gutter="20" style="border-bottom: 1px solid #b8b8b8">
      <el-col :span="6"  style="border-right: 1px solid #b8b8b8">
        <span>目标数据表</span>
        <el-table
          :data="tableDetail"
          style="width:120px; margin-top:20px"
          height="400">
          <el-table-column
            prop="columnName"
            label="属性"
            width="60">
          </el-table-column>
          <el-table-column
            prop="columnType"
            label="类型"
            width="60">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="6"  style="border-right: 1px solid #b8b8b8">
        <span>数据源一</span>
        <el-table
          :data="tableDetail1"
          style="width:120px; margin-top:20px"
          height="400">
          <el-table-column
            prop="columnName"
            label="属性"
            width="60">
          </el-table-column>
          <el-table-column
            prop="columnType"
            label="类型"
            width="60">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="6"  style="border-right: 1px solid #b8b8b8">
        <span>数据源二</span>
        <el-table
          :data="tableDetail2"
          style="width:120px ; margin-top:20px"
          height="400">
          <el-table-column
            prop="columnName"
            label="属性"
            width="60">
          </el-table-column>
          <el-table-column
            prop="columnType"
            label="类型"
            width="60">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="6"  style="border-right: 1px solid #b8b8b8">
        <span>数据源三</span>
        <el-table
          :data="tableDetail3"
          style="width:120px; margin-top:20px"
          height="400">
          <el-table-column
            prop="columnName"
            label="属性"
            width="60">
          </el-table-column>
          <el-table-column
            prop="columnType"
            label="类型"
            width="60">
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-form ref="form" :model="form" label-width="80px" style="margin-top:20px">
      <el-form-item label="融合规则">
        <el-input type="textarea" v-model="form.desc"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">开始</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style>
</style>
<script>
export default{
    data(){
        return{
            form:{
                desc:""
            },
            tableDetail:[
              {
                "columnName":"学号",
                "columnType":"int"
              },{
                "columnName":"姓名",
                "columnType":"char"
            },
              {
                "columnName":"年龄",
                "columnType":"int"
              },

              {
                "columnName":"班级",
                "columnType":"char"
              },
              {
                "columnName":"住址",
                "columnType":"text"
              }],
          tableDetail1:[{
            "columnName":"id",
            "columnType":"char"
          },
            {
              "columnName":"name",
              "columnType":"char"
            },
            {
              "columnName":"address",
              "columnType":"char"
            }],
          tableDetail2:[{
            "columnName":"身份证号",
            "columnType":"char"
          },
            {
              "columnName":"学号",
              "columnType":"int"
            },
            {
              "columnName":"姓名",
              "columnType":"char"
            }],
          tableDetail3:[{
            "columnName":"学号",
            "columnType":"char"
          },
            {
              "columnName":"姓名",
              "columnType":"char"
            },
            {
              "columnName":"班级",
              "columnType":"char"
            }],
        }

    },
  methods:{
        onSubmit() {

    }
  }

}
</script>
