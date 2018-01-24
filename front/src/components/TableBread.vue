<template>
  <div class="entity">
    <div class="entity-title">
      <h2 class="entity-title-h2"><span>实体库</span></h2>
    </div>
    <div class="entity-item">
      <div class="entity-item-head">
        <ul>
          <li>实体名</li>
          <li>数据库地址</li>
          <li>表名</li>
          <li>属性</li>
          <li>管理</li>
        </ul>
      </div>
      <ul class="entity-item-list">
        <li v-for="item in entityLi">
          <div class="entity-tab-1">
            {{item.displayName}}
          </div>
          <div class="entity-tab-2">
            {{item.dbPostion}}
          </div>
          <div class="entity-tab-3">
            {{item.tableName}}
          </div>
          <div class="entity-tab-4">
            {{item.properties}}
          </div>
          <div class="entity-tab-5">
            <a href="javascript:void(0);" v-on:click="startEdit(item.id)" v-if="item.stuId!=nowEditCol">编辑</a>
            <a href="javascript:void(0);" v-on:click="cancelEdit" v-if="item.stuId==nowEditCol">取消</a>
            <a href="javascript:void(0);" v-on:click="sureEdit(item.id)" v-if="item.stuId==nowEditCol">确认</a>
          </div>
          <div class="entity-tab-6">
            <td><a href="javascript:void(0);" v-on:click="deleteStu(item.id)">删除</a></td>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<style>
  .entity-item{
    display:table;
    width:100%;
  }
  .entity-item-head{
    display:table-header-group;
    width:100%;
  }

  .entity-item-head ul{
    display: table-row;
    width: 100%;
  }
  .entity-item-head li{
    display: table-cell;
    height: 40px;
    line-height: 40px;
    background: #605F5F;
    color: #fff;
    text-align: center;
    text-transform: uppercase;
    font-family: "moderat", sans-serif;
    letter-spacing: .25em;
  }
  .entity-item-head li{
    padding: 0 10px;
  }
  .entity-item-list {
    display: table-row-group; }
  .entity-item-list > li {
    position: relative;
    display: table-row;
    padding: 32px 0;
    background: #fff; }
  .entity-item-list > li > div {
    position: relative;
    display: table-cell;
    text-align: center;
    vertical-align: top;
    border-bottom: 1px solid #e9e9e9;
    height: 100%; }

</style>
<script>
  import MyHeader from '../../resource/MyHeader'
  import  SideBar from '../../resource/SideBar'
  export default{
    data(){
      return{
        entityEvent:[],
        entityLi:[],
        editStatus:false,
        nowEditCol:-1,
      }
    },
    mounted(){
        this.filterEntity();
    },
    components:{
      MyHeader,
      SideBar
    },
    computed:{

      editArr:function(){

      }

    },
    methods:{
      filterEntity:function(){
        var entityEventList=this.entityEvent;
        return entityEventList.filter(function(item){
          return item.entityType==0;
        })
      },
      startEdit:function(id){
        this.nowEditCol=id;
      },
      cancelEdit:function(){
        this.nowEditCol=-1;
      },
      sureEdit:function(id){
        for(var i=0,len=this.entityLi.length;i<len;i++){
          if(id === this.entityLi[i]['id'] ){
            this.entityLi.splice(i,1,this.editArr);
            break;
          }
        }
        this.nowEditCol=-1;
      },
      showEntity(){
        axios.get("kjb/entity/show").then((response)=>{
          let res=response.data;
          if(res.status==1){

          }
        })

      },
      deleteEntity(id){
        axios.get("kjb/entity/delete",{
          params:{
            "entityId":id
          }
        }).then((response)=>{
          let res=response.data;
          if(res.status==1){

          }
        })

      },
      insertEntity(){
        axios.post("kjb/entity/insert",params).then(
          (response)=>{
            let res=response.data;
            if(res.status==1){

            }
          }
        )
      },
      updateEntity(){
        axios.get("kjb/entity/update",{
          params:param
        }).then((response)=>{
            let res=response.data;
            if(res.status==1){

            }
          }

        )
      }

    }
  }
</script>
