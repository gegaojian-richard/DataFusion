<template>
  <tree
    class="filter-tree"
    :data="conRewrite"
    :props="defaultProps"
    default-expand-all
    @node-click="handleNodeClick"
    :render-content="renderContent"
    style="background-color:#1F5FA6;color:#fff;">
  </tree>
</template>
<script type="text/jsx">
  import Tree from 'element-ui/packages/tree'
  export default{
      components:{Tree},
      method:{
        renderContent(h,{node,data,store}){        //修改elementui 的树组件，添加delete
          if(node.children){
            return{
              <span>
                <span>
                  <span>{node.label}</span>
                </span>
                <span style="float: right; margin-right: 20px">
                  <a  onclick={ () => this.remove(store, data) }>Delete</a>
                </span>
              </span>}
          }
        },
        remove(store, data) {
          this.deleteConnect(data.id);
        },
    },
    deleteConnect(name){
      let param=new URLSearchParams();
      param.append("nick",name);
      axios.post("/kjb/cms/deletionDataBase",param).then((response)=>{
        var res=response.data;
        if(res.status==1){
          this.getConnect();
        }
      })
    },
  }
</script>
