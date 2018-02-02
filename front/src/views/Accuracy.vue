<template>
  <div>
    <connect-info  @previewtable="previewTable" style="height:490px;float:left;width:180px"></connect-info>
    <div>

      <!--table class = "table_class_arru " style="float:top;text-align:center;width=90%; border=1 ;class=t1"  id=mytab;-->
      <!-- 用来显示字段那列 -->
      <div class="showtable" style="margin-left: 200px;width:800px;height:300px;background-color: #ffff;position:relative">
        <table class="imagetable">
          <thead>
          <tr>
            <th  class="removep" v-for="(key,item) in previewData[0]" v-on:click="sha(item)">{{item}}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for = "item in previewData">
            <td v-for ="it in  item">{{it}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>  <!-- 显示字段那列结束 -->
    <div >  <!-- 弹出选择类型 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:170px;border:1px solid #3333;" v-bind:class="{'md-show':show}">
        <div class="md-modal-inner">
          <div class="md-top">
            <button class="md-close" @click="show=false">Close</button>
          </div>
          <div class="md-content" style="position: relative" >
            <ol >
              <li>
                <a href="javascript:void(0)" @click="click_type1"> 公式检查</a>
              </li>
              <li>
                <a href="javascript:void(0)" @click="click_type2"> 条件检查</a>
              </li>
              <li>
                <a href="javascript:void(0)" @click="click_type3"> 位数检查</a>
              </li>
              <li>
                <a href="javascript:void(0)" @click="click_type4"> 范围检查</a>
              </li>
              <li>
                <a href="javascript:void(0)" @click="click_type5"> 邮箱检查</a>
              </li>
              <li>
                <a href="javascript:void(0)" @click="click_type6"> more</a>
              </li>
            </ol>
          </div>
        </div>
      </div>

    </div>   <!-- 弹出窗口来选择检查类型 -->

    <div >  <!-- 弹出类型1输入 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition"  v-bind:class="{'md-show':click_flag1}">
        <div class="md-modal-inner">
          <div class="md-top">
            <button class="md-close " @click="flag1_close">Close</button>
          </div>
          <div>
            <h4>当前的属性是:{{save_item}}</h4>
            <div style="margin:5px 0px">
            <span style="margin-right: 15px">公式：{{save_item}} = </span>
            <input v-model="type1_formu"/>
            </div>
            <button type="button" class=" button" aria-label="Left Align" @click="type1_ok">
              <span class="glyphicon glyphicon-plus" >确定</span>
            </button>
            <button type="button" class="  button" aria-label="Left Align" @click="type1_add_newcolumn">
              <span class="glyphicon glyphicon-plus" >添加新的字段</span>
            </button>
            <br><br>
            <dl v-for = "(change_for_add,index) in all_for_change" class = "imagetable">
            <span>公式中非数值字段</span>
            <input v-model="newclos[index].save"/>
              <br><br>
            <button type="button" class="  button" aria-label="Left Align" @click="type1_add(index)">
              <span class="glyphicon glyphicon-plus" >添加对应关系</span>
            </button>
            <table>
              <thead>
              <tr>
                <th >字段值</th>
                <th >对应的数</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for = "item in change_for_add" >
                <td >
                  <input v-model="item.mean">
                </td>
                <td>
                  <input v-model="item.truevalue">
                </td>
              </tr>
              </tbody>
            </table>
            </dl>
          </div>
        </div>
      </div>
    </div>   <!-- 类型1弹窗 -->
    <div >  <!-- 弹出类型2输入 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:40%" v-bind:class="{'md-show':click_flag2}">
        <div class="md-modal-inner">
          <div class="md-top">
            <button class="md-close" @click="flag2_close">Close</button>
          </div>
          <div>
            当前的属性是:{{save_item}}
            <br><br>
            <button type="button" class = "button" @click = "type2_ok">
              <sapn>确定</sapn>
            </button>
            <button type="button" class = "button" @click="flag2_close">
              <sapn>取消</sapn>
            </button>
            <br><br>
            <button type="button" class = "button" @click ="flag2_add">
              <span>添加对应对</span>
            </button>
            <table class = "imagetable">
              <thead>
              <tr>
                <th >字段值</th>
                <th >条件</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for = "item in type2_change" >
                <td >
                  <input v-model="item.num">
                </td>
                <td>
                  <input v-model="item.condition">
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

    </div>   <!-- 类型2弹窗 -->
    <div >  <!-- 弹出类型3输入   位数检查-->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:40%" v-bind:class="{'md-show':click_flag3}">
        <div class="md-modal-inner">
          <div class="md-top">
            <button class="md-close" @click="flag3_close">Close</button>
          </div>
          <div>
            当前的属性是:{{save_item}}
            <br>
             <span>位数</span>&nbsp &nbsp
             <input v-model="type3_value"/>
          </div>
          <button class = "button" type = "button" @click = "clcik_flag3_button_ok">
            <span>确定</span>
          </button>
          <button class = "button" type = "button" @click = 'flag3_close'>
            <span>取消</span>
          </button>
        </div>
      </div>

    </div>   <!-- 类型3弹窗 -->
    <div >  <!-- 弹出类型4输入 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:40%" v-bind:class="{'md-show':click_flag4}">
        <div class="md-modal-inner">
          <div class="md-top">
            <button class="md-close" @click="flag4_close">Close</button>
          </div>
          <div>
             当前的属性是:{{save_item}}
              <br><br>
              <span> 上限</span>
              <input v-model="type4_max"/>
              <br><br>
              <span >下限</span>
              <input v-model="type4_min" />
              <br>
             <button type = "button " class = "button" @click = "clcik_flag4_button_ok">
               <span>确定</span>
             </button>
            <button type = "button " class = "button" @click = "flag4_close">
              <span>取消</span>
            </button>
          </div>
        </div>
      </div>

    </div>   <!-- 类型4弹窗 -->
    <el-button type = "primary" plain @click="submit()">提交</el-button>
  </div>
</template>


<style>
  .md-modal .md-modal-inner{
    padding:30px 0px 0px 0px ;
  }
  .md-content{
    width:100%
  }
  .showtable{
    height:300px;
    width:600px;
    margin-top: 40px;
    overflow-x: auto;
    overflow-y: auto;
    position:relative;
    overflow: auto;
    background-color: #ffffff;
  }
  .md-content ol{
    width:100%;
  }
  .md-content li{
    border-bottom: 1px solid #3333;
    width:100%;
    height:30px;
    line-height: 30px;
  }

  /* 修饰表格  */
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
    border: 1px solid #ffffff;
  }
</style>
<script>
  import axios from 'axios'
  import connectInfo from '@/components/Connect/ConnectInfo.vue'
  export default{
    data() {
      return {
        previewData:         //请求的表格数据{
          [
          ],
        //   以下为新增的
        all_for_change:[
          [
          {
            mean: "",
            truevalue: ""
          },
          {
            mean: "",
            truevalue: ""
          },
          {
            mean: "",
            truevalue: ""
          }]],       //  用于类型一非数值字段对应对的描述
        newclos:[{save:""}], // 记录类型1非数值型字段名称
        type2_change:[{num:"",condition:""}],  ///   存储类型2的对应对
        save_item:"",        //  当前选中的字段名
        show : false,        //  选择类型检查弹窗显示标志变量
        click_flag1:false,  //  类型1弹窗显示标志变量
        click_flag2:false,  //  类型2弹窗显示标志变量
        click_flag3:false,  //  类型3弹窗显示标志变量
        click_flag4:false,  //  类型4弹窗显示标志变量
        type1_formu:"",     //  类型1检查的公式
        type1_nonumber:"",  //  类型1检查的非公式字段的对应对中的数值
        type1_mean:"",      //  类型1检查的非公式字段的对应对中的数值的意义
        type1_truevalue:'',
        type3_value:"", //   类型3 ---位数
        type4_max:"",   //   类型4 -- 上限
        type4_min:"",   //   类型4  --下限
        res_all:[],     //  最终存的数组
      }
    },   ///   data
    components: {
      connectInfo,
    },
    computed:{
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
      },
      // 鼠标的点击事件
      sha(item) {
        //alert(item);
        var pageX = event.pageX;
        var pageY = event.pageY;
        this.show = true;
        this.save_item = item;
        $("#jump").css("display", "block");
        $("#jump").css("position", "absolute");
        $("#jump").css("left", pageX);
        $("#jump").css("top", pageY + 130);
      },
      ///  以下为选择后将对应的类型输入窗口弹出
      click_type1() {
        this.show = false;
        this.click_flag1 = true;
      },
      click_type2() {
        this.show = false;
        this.click_flag2 = true;
      },
      click_type3() {
        this.show = false;
        this.click_flag3 = true;
      },
      click_type4() {
        this.show = false;
        this.click_flag4 = true;
      },
      //   这个比较特殊，无需输入直接add进去
      click_type5() {
        this.show = false;
        var a = "5" + "," + this.save_item;
        this.res_all.push(a);
        alert('添加成功');
        //   记录下来
      },
      click_type6() {
        this.show = false;
        alert('之后会添加，暂无');
      },
      //   类型1一个字段下添加新的对应对
      type1_add(index) {
        //this.change_for_add.push({mean:"",truevalue:""});
        // alert(index);
        this.all_for_change[index].push({mean: "", truevalue: ""});
      },
      //   类型1添加新字段
      type1_add_newcolumn() {
        this.all_for_change.push([
          {mean: "", truevalue: ""},
          {mean: "", truevalue: ""},
          {mean: "", truevalue: ""}]
        );
        this.newclos.push({save: ''})
      },
      //  类型1确认
      type1_ok() {
        if(this.type1_formu == "")
        {
          alert("公式为空");
          return;
        }
        ///   先检查this.newclos是否有空的
       // if(this.newclos.length < this.all_for_change.length)
        for(var k = 0; k<this.newclos.length; k++)
        {
          if(this.newclos[k].save == "")
          {
            alert("第"+ (k+1) +"个字段为空");
            return;
          }

        }
        ///  检查对应关系
        //alert("检查");
        for (var i = 0; i < this.all_for_change.length; i++) {
          for (var j = 0; j < this.all_for_change[i].length; j++) {
            if((this.all_for_change[i][j].mean == "" && this.all_for_change[i][j].truevalue != "" )||(this.all_for_change[i][j].mean != "" && this.all_for_change[i][j].truevalue ==""))           {
               alert("第"+(i+1)+"字段的第"+ (j+1) +"个对应关系不正确");
               return ;
            }
          }
        }
        //alert("结束检查");
        var a = "1" + "," + this.save_item + "="+this.type1_formu;
        // alert(a);
        for (var i = 0; i < this.all_for_change.length; i++) {
          var b = "{" + this.newclos[i].save + ":";
          for (var j = 0; j < this.all_for_change[i].length; j++) {
            if(this.all_for_change[i][j].mean != "" && this.all_for_change[i][j].truevalue != "" )
            b += "{" + this.all_for_change[i][j].mean + ":" + this.all_for_change[i][j].truevalue + "}"
          }
          b += "}"
          a += "," + b;
        }
        //alert(a);
        this.res_all.push(a);
        this.click_flag1 = false;
        alert('add ok');
        this.type1_formu="";
        this.all_for_change=[
          [
            {
              mean: "",
              truevalue: ""
            },
            {
              mean: "",
              truevalue: ""
            },
            {
              mean: "",
              truevalue: ""
            }]];
        this.newclos=[{save:""}];
      },
      ///  类型2添加新的对应关系
      flag2_add()
      {
        this.type2_change.push({num:"",condition:""});
      },
      ///  类型2确认
      type2_ok()
      {
        for(var i = 0;i<this.type2_change.length;i++)
        {
          if((this.type2_change[i].condition!=""&& this.type2_change[i].num=="")||(this.type2_change[i].condition==""&& this.type2_change[i].num!=""))
          {
            alert("第"+ i +"个对应对不匹配");
            return ;
          }
        }
        var a = "2" + "," + this.save_item+",";
        for(var i = 0;i<this.type2_change.length;i++)
        {
            if(this.type2_change[i].condition!=""&& this.type2_change[i].num!="") {
              a += "{" + this.type2_change[i].condition + ":" + this.type2_change[i].num + "}";
            }
        }
        this.res_all.push(a);
        this.click_flag2 = false;
        alert("add ok");
        this.type2_change=[{num:"",condition:""}];
      },
      ////   类型3确认
      clcik_flag3_button_ok(){
        var a ="3"+","+this.save_item+","+this.type3_value;
        this.click_flag3=false;
        alert(a);
        this.res_all.push(a);
        alert('add ok');
        this.type3_value="";
      },
      //// 类型4确认
      clcik_flag4_button_ok(){
        if(this.type4_min == "" && this.type4_max== "")
        {
          alert('请输入数据');
          return;
        }
        else if(this.type4_max =="")  //   只有下限
        {
          var a = "4"+","+this.save_item+","+this.type4_min+"<="+this.save_item;
          this.click_flag4=false;
          alert(a);
          this.res_all.push(a);
          alert('add ok');
          this.type4_max="";
          this.type4_min="";
        }
        else if(this.type4_min=="")  //   只有上限
        {
          var a = "4"+","+this.save_item+","+this.save_item+"<="+this.type4_max;
          this.click_flag4=false;
          alert(a);
          this.res_all.push(a);
          alert('add ok');
          this.type4_max="";
          this.type4_min="";
        }
       else if(parseInt(this.type4_max)<parseInt(this.type4_min)) ///  不合法
       {
           alert('min的值大于max的值，请检查后输入');
       }
        else  //  都有合法
        {
          var a = "4"+","+this.save_item+","+this.type4_min+"<="+this.save_item +  " and " + this.save_item + "<=" +this.type4_max;
          this.click_flag4=false;
          alert(a);
          this.res_all.push(a);
          alert('add ok');
          this.type4_max="";
          this.type4_min="";
        }
      },
      ///  以下全部是点叉和取消以后的清空
      flag1_close() {
        this.click_flag1=false;
        this.type1_formu="";
        this.all_for_change=[
          [
            {
              mean: "",
              truevalue: ""
            },
            {
              mean: "",
              truevalue: ""
            },
            {
              mean: "",
              truevalue: ""
            }]];
        this.newclos=[{save:""}];
      },
      flag2_close() {
        this.click_flag2=false;
        this.type2_change=[{num:"",condition:""}];
      },
      flag3_close() {
        this.click_flag3=false;
        this.type3_value="";
      },
      flag4_close() {
        this.click_flag4=false;
        this.type4_max="";
        this.type4_min="";
      },
      submit(){
        axios.post("/kjb/dgs/accuracy/commitjob",this.res_all).then
        ((response)=>{
          var res=response.data;
          if(res.status==1){
            alert("submit complete check success");
          }
        })
      }
    }   ///  method
  }
</script>

