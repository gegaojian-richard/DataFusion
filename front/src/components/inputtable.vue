<template>
  <el-table
    :data="tableData"
    border
    height="200"
    style="width: 100%">
    <el-table-column
      align="center"
      label="字段名"
      width="100">
      <template slot-scope="scope">
        <span v-if="editingRow!=scope.row.name">{{ scope.row.name }}</span>
        <span v-if="editingRow==scope.row.name" class="cell-edit-input"><el-input
          v-model="scope.row.name"></el-input></span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="类型"
      width="100">
      <template slot-scope="scope">
        <span v-if="editingRow!=scope.row.name">{{ scope.row.type }}</span>
        <span v-if="editingRow==scope.row.name" class="cell-edit-input"><el-input
          v-model="scope.row.type"></el-input></span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="主键"
      width="100px">
      <template slot-scope="scope">
        <span v-if="editingRow!=scope.row.name">{{ scope.row.prime }}</span>
        <el-select v-if="editingRow==scope.row.name" v-model="scope.row.prime" placeholder="请选择" width="40px">
        <el-option v-for="item in options" :key="scope.row.prime" :value="item" :label="item"></el-option>
        </el-select>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="操作"
      width="100px">
      <template slot-scope="scope">
        <span v-if="editingRow!=scope.row.name" class="cell-icon" @click="handleEdit(scope.row)">  <i
          class="el-icon-edit"></i> </span>
        <span v-if="editingRow!=scope.row.name" class="cell-icon" @click="">  <i class="el-icon-delete"></i> </span>
        <span v-if="editingRow==scope.row.name" class="cell-icon" @click="handleSave(scope.row)">  <i
          class="el-icon-document"></i> </span>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default{
    data(){
      return {
        options:[0,1],
        editingRow: null,
        tableData: [
          {
            name: "test1",
            type: "string",
            prime: 1

          },
          {
            name: "test2",
            type: "string",
            prime: 0
          },
          {
            name: "test3",
            type: "string",
            prime: 0
          },
          {
            name: "test4",
            type: "string",
            prime: 0
          },
        ],
        inputColumn1: ""//第一列的输入框
      }
    },
    methods: {
      handleEdit: function (row) {
        //遍历数组改变editeFlag
//       for(var i=0;i<this.tableData.length;i++){
//           if(this.tableData[i].name==row.name){
//               this.tableData[i].editFlag=true;
//           }
//      }
        this.editingRow = row.name;
      },
      handleSave: function (row) {
        //保存数据，向后台取数据
        this.editingRow = null;
      }
    }
  }

</script>

<style>
  .cell-edit-input .el-input, .el-input__inner {
    width: 80px;
    height: 30px;
  }

  .cell-icon {
    cursor: pointer;
    color: black;
  }
</style>
