<template>
  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="数据连接">
        <el-select v-model="formInline.region" >
        <el-option label="conn-stu" value="shanghai"></el-option>
        <el-option label="conn-car" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据表">
        <el-select v-model="formInline.region" >
          <el-option label="学生信息" value="shanghai"></el-option>
          <el-option label="学生成绩" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%;margin-top:20px">
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
      </el-table-column>
    </el-table>
    <el-form ref="form" :model="form" label-width="80px" style="margin-top:20px">
      <el-form-item label="属性约束">
        <el-input type="textarea" v-model="form.desc"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">开始</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
    <el-form ref="form" :model="form" label-width="80px" style="margin-top:20px">
      <el-form-item label="属性关系">
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
    data() {
      return {
        form:{
          desc:""
        },
        formInline: {
          user: '',
          region: ''
        },
        tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
      }
    },
    methods: {}
  }
</script>
