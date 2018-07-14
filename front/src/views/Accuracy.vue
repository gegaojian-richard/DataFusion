<template>
  <div>
    <connect-info  @previewtable="previewTable"style="height:100%;width:180px;position:fixed;overflow: auto;z-index:2000;"></connect-info>
    <div style="padding: 20px;margin-left:180px;overflow: visible;">
      <div style="height: 100%;;border:1px solid #bfcbd9;padding: 0px 20px;">
        <p style="height: 50px;text-align: left;border-bottom: 1px solid #bfcbd9;line-height: 60px;color:#698EC3;font-size: 16px;">
          <span style="display: inline-block;height:20px;width:5px;background: #698EC3;margin-bottom:-5px;margin-right: 5px;"></span>
          <span>准确性检测</span>
        </p>
        <div class="showtable" style="width:100%;background-color: #ffff;position:relative;height:400px;overflow: auto;top:-20px;">
          <table class="imagetable">
            <thead>
            <tr>
              <th  class="removep" v-for="(key,item) in previewData[0]" v-on:click="sha(item)">{{item}}</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for = "item in previewData">
              <td v-for ="it in  item" style="color: #333;">{{it}}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <el-button type = "primary" plain @click="submit" style="width:100px;float:right;margin-top: 30px;position:relative;background-color: #82B7E3;color:#fff;">提交</el-button>
      </div>

    </div>  <!-- 显示字段那列结束 -->
    <div >  <!-- 弹出选择类型 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:170px;border:1px solid #3333;border-radius: 2px;top:358px!important;" v-bind:class="{'md-show':show}">
        <div class="md-modal-inner" style="border: 1px solid #ccc;">
          <div class="md-top" style="height:25px">
            <button class="md-close" @click="show=false" style="top:0px;width:20px;height: 20px;right: 15px;">Close</button>
          </div>
          <div class="md-content" style="position: relative;padding: 0px;color:#333;" >
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
      <div  class="md-modal modal-msg md-modal-transition"  v-bind:class="{'md-show':click_flag1}">
        <div class="md-modal-inner" style="height:500px;overflow:auto;padding-bottom: 30px;">
          <div class="md-top">
            <div class="md-title">公式检查</div>
            <button class="md-close " @click="flag1_close">Close</button>
          </div>
          <div style="color:#333;">
            <span style="display: inline-block;width: 100%;padding: 10px 20px;text-align: left;">当前的属性是:{{save_item}}</span>
            <div style="margin:15px 20px;text-align: left">
            <span style="margin-right: 15px">公式：{{save_item}} = </span>
              <input v-model="type1_formu" type="text" class="edit" style="width:200px">
              <el-button type="primary" plain @click="type1_ok" style="position: relative;height:35px;color:#fff;background:#51BA65;">确定</el-button>
            </div>
            <button type="button" class="  button" style="width: 100%;background: url('/static/home/add.png') 15px center no-repeat;padding-left: 20px;" aria-label="Left Align" @click="type1_add_newcolumn">
              <span style="display: inline-block;width: 100%;padding: 10px 20px;text-align: left;">添加新的字段</span>
            </button>
            <br><br>
            <dl v-for = "(change_for_add,index) in all_for_change" class = "imagetable1">
              <div style="margin:15px 20px;text-align: left">
                <span style="margin-right: 15px;font-size: 14px;">公式中非数值字段</span>
                <input v-model="newclos[index].save"type="text" class="edit">
              </div>
              <button type="button" class="  button" style="width: 100%;background: url('/static/home/add.png') 15px center no-repeat;padding-left: 20px;" aria-label="Left Align" click="type1_add(index)">
                <span style="display: inline-block;width: 100%;padding: 10px 20px;text-align: left;font-size: 14px;">添加对应关系</span>
              </button>
            <table style="margin-left: 50px;">
              <thead>
              <tr class="ziduan">
                <th >字段值</th>
                <th >对应的数</th>
              </tr>
              </thead>
              <tbody style="height: 600px;overflow-y: auto;">
              <tr v-for = "item in change_for_add" >
                <td >
                  <input v-model="item.mean" type="text" class="edit">
                </td>
                <td>
                  <input v-model="item.truevalue"type="text" class="edit">
                </td>
              </tr>
              </tbody>
            </table>
            </dl>
          </div>
        </div>
      </div>
      <div class="md-overlay" v-if="click_flag1" @click="click_flag1=false"></div>
    </div>   <!-- 类型1弹窗 -->
    <div >  <!-- 弹出类型2输入 -->
      <div  class="md-modal modal-msg md-modal-transition" style="width:500px;" v-bind:class="{'md-show':click_flag2}">
        <div class="md-modal-inner" style="padding-bottom: 30px;">
          <div class="md-top">
            <div class="md-title">条件检查</div>
            <button class="md-close" @click="flag2_close">Close</button>
          </div>
          <div style="color:#333;padding-left: 30px;">
            <span style="display: inline-block;width: 100%;padding: 10px 0px;text-align: left;">当前的属性是:{{save_item}}</span>
            <button type="button" class = "button" @click = "type2_ok" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;">
              <span>确定</span>
            </button>
            <button type="button" class = "button" @click="flag2_close" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;margin-left:20px;">
              <span>取消</span>
            </button>
            <br><br>
            <button type="button" class="  button" style="width: 100%;background: url('/static/home/add.png') 0px center no-repeat;" aria-label="Left Align" click="type1_add(index)">
              <span style="display: inline-block;width: 100%;padding: 10px 20px;text-align: left;font-size: 14px;">添加对应关系</span>
            </button>
            <table class = "imagetable1" style="margin-left: 10px;">
              <thead>
              <tr class="ziduan">
                <th >字段值</th>
                <th >条件</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for = "item in type2_change" >
                <td >
                  <input v-model="item.num" type="text" class="edit">
                </td>
                <td>
                  <input v-model="item.condition" type="text" class="edit">
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="md-overlay" v-if="click_flag2" @click="click_flag2=false"></div>
    </div>   <!-- 类型2弹窗 -->
    <div >  <!-- 弹出类型3输入   位数检查-->
      <div  class="md-modal modal-msg md-modal-transition" style="width:500px;" v-bind:class="{'md-show':click_flag3}">
        <div class="md-modal-inner" style="height: 220px;">
          <div class="md-top">
            <div class="md-title">位数检查</div>
            <button class="md-close" @click="flag3_close">Close</button>
          </div>
          <div style="color:#333;padding-left: 30px;">
            <span style="display: inline-block;width: 100%;padding: 10px 0px;text-align: left;">当前的属性是:{{save_item}}</span>
            <div style="margin:15px 0px;text-align: left">
              <span>位数</span>&nbsp &nbsp
              <input v-model="type3_value" type="text" class="edit">
            </div>
          </div>
          <div style="padding-left: 30px;">
            <button type="button" class = "button" @click = "clcik_flag3_button_ok" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;">
              <span>确定</span>
            </button>
            <button type="button" class = "button" @click="flag3_close" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;margin-left:20px;">
              <span>取消</span>
            </button>
          </div>

        </div>
      </div>
      <div class="md-overlay" v-if="click_flag3" @click="click_flag3=false"></div>
    </div>   <!-- 类型3弹窗 -->
    <div >  <!-- 弹出类型4输入 -->
      <div id = 'jump' class="md-modal modal-msg md-modal-transition" style="width:500px;" v-bind:class="{'md-show':click_flag4}">
        <div class="md-modal-inner" style="height: 300px;">
          <div class="md-top">
            <div class="md-title">范围检查</div>
            <button class="md-close" @click="flag4_close">Close</button>
          </div>
          <div>
            <div style="color:#333;padding-left: 30px;">
              <span style="display: inline-block;width: 100%;padding: 10px 0px;text-align: left;">当前的属性是:{{save_item}}</span>
              <div style="margin:15px 0px;text-align: left">
                <span> 上限</span>
                <input v-model="type4_max" type="text" class="edit">
              </div>
              <div style="margin:15px 0px;text-align: left">
                <span >下限</span>
                <input v-model="type4_min" type="text" class="edit">
              </div>
            </div>
            <div style="padding-left: 30px;">
              <button type="button" class = "button" @click = "clcik_flag4_button_ok" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;">
                <span>确定</span>
              </button>
              <button type="button" class = "button" @click = "flag4_close" style="padding:0 90px;height:35px;color:#fff;background:#51BA65;float: left;margin-left:20px;">
                <span>取消</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="md-overlay" v-if="click_flag4" @click="click_flag4=false"></div>
    </div>   <!-- 类型4弹窗 -->
  </div>
</template>


<style rel="stylesheet/scss" lang="scss" scoped>
  .edit{
    height:40px;
    width: 270px;
  }
  .imagetable {
    border:none;
    margin-top: 0px;
    font-size:1.2em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    width: 100%;
  }
  .imagetable th {
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
  .imagetable td {
    font-size:0.95em;
    text-align:center;
    padding:4px;
    border-collapse:collapse;
    border-bottom: 1px dashed #ccc;
  }
  .imagetable thead {
    background-color: #6787B0;
  }
  .imagetable tbody tr {
    height: 50px;
    &:nth-child(2n) {
      background-color: #F2F7FD;
    }
  }
  .el-tree__empty-text{
    color: #fff!important;
  }
  .md-modal .md-modal-inner{
    padding:30px 0px 0px 0px ;
  }
  .md-content{
    width:100%
  }
  .showtable{
    width:600px;
    margin-top: 40px;
    position:relative;
    overflow: auto;
    background-color: #ffffff;
    border: 1px solid #ccc;
  }
  .el-button {
    padding: 7px 20px;
  }
  .md-content ol{
    width:100%;
  }
  .md-content li{
    border-bottom: 1px,bold;
    width:100%;
    height:30px;
    line-height: 30px;
  }
  .md-modal {
    overflow: hidden;
    border-radius: 5px;
  }
  .md-modal .md-modal-inner .md-top{
    width:100%;
    height: 50px;
    line-height: 50px;
    background-color: #266CB4;
    color: #fff;
    .md-title {
      position: absolute;
      top: 0px;
      left: 20px;
      line-height: 50px;
      padding: 0;
      color: #333;
      font-size: 18px;
      font-weight: 400;
      font-style: normal;
      color: #Fff;
    }
  }
  .md-modal .md-modal-inner {
    padding: 0px;
  }
  .md-modal .md-modal-inner .md-content {
    padding: 30px 30px 50px 30px;
    color:#333;
    .btn-login {
      height: 50px;
      line-height: 50px;
      border: 2px solid  #5ACD70;
      background: #5ACD70;
    }
  }
  .ziduan th{
    background: #fff;
    color:#333;
    text-align: center;
  }
  .ziduan tr {
    margin-bottom: 20px;
    th {
      border: none;
    }
  }
  .imagetable1 .edit {
    width: 200px;
  }
  .imagetable1 tbody tr td input{
    margin-top: 20px;
  }
  .imagetable1 tbody tr td:nth-child(2n) input {
    margin-left: 20px;
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
        dataSourceId:"",     //  数据库id
        tableName:"",         //  当前表名
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
            this.res_all=[];    ///  这里用于换表的时候清空之前的
            this.dataSourceId = emitdata.database
            this.tableName = emitdata.table
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
        $("#jump").css("top", pageY + 120);
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
//        alert('添加成功');
        const h = this.$createElement;
        this.$notify({
          title: '提示',
          message: h('i', {style: 'color: teal'}, "添加成功")
        })
        //   记录下来
      },
      click_type6() {
        this.show = false;
//        alert('之后会添加，暂无');
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
          //alert("公式为空");
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', {style: 'color: teal'}, "公式为空")
          })
          return;
        }
        ///   先检查this.newclos是否有空的
       // if(this.newclos.length < this.all_for_change.length)
//        for(var k = 0; k<this.newclos.length; k++)
//        {
//          if(this.newclos[k].save == "")
//          {
//         //   alert("第"+ (k+1) +"个字段为空");
//            const h = this.$createElement;
//            this.$notify({
//              title: '提示',
//              message: h('i', {style: 'color: teal'}, "第"+(k+1)+"个字段为空")
//            })
//            return;
//          }
//
//        }
        ///  检查对应关系
        //alert("检查");
        for (var i = 0; i < this.all_for_change.length; i++) {
          for (var j = 0; j < this.all_for_change[i].length; j++) {
            if((this.all_for_change[i][j].mean == "" && this.all_for_change[i][j].truevalue != "" )||(this.all_for_change[i][j].mean != "" && this.all_for_change[i][j].truevalue ==""))           {
              // alert("第"+(i+1)+"字段的第"+ (j+1) +"个对应关系不正确");
              const h = this.$createElement;
              this.$notify({
                title: '提示',
                message: h('i', {style: 'color: teal'},"第"+(i+1)+"字段的第"+ (j+1) +"个对应关系不正确")
              })
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
        //alert('add ok');
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
           // alert("第"+ i +"个对应对不匹配");
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'},"第"+ i +"个对应对不匹配")
            })
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
//        alert("add ok");
        this.type2_change=[{num:"",condition:""}];
      },
      ////   类型3确认
      clcik_flag3_button_ok(){
        var a ="3"+","+this.save_item+","+this.type3_value;
        this.click_flag3=false;
//        alert(a);
        this.res_all.push(a);
//        alert('add ok');
        this.type3_value="";
      },
      //// 类型4确认
      clcik_flag4_button_ok(){
        if(this.type4_min == "" && this.type4_max== "")
        {
//          alert('请输入数据');
          return;
        }
        else if(this.type4_max =="")  //   只有下限
        {
          var a = "4"+","+this.save_item+","+this.type4_min+"<="+this.save_item;
          this.click_flag4=false;
//          alert(a);
          this.res_all.push(a);
//          alert('add ok');
          this.type4_max="";
          this.type4_min="";
        }
        else if(this.type4_min=="")  //   只有上限
        {
          var a = "4"+","+this.save_item+","+this.save_item+"<="+this.type4_max;
          this.click_flag4=false;
         // alert(a);
          this.res_all.push(a);
        //  alert('add ok');
          this.type4_max="";
          this.type4_min="";
        }
       else if(parseInt(this.type4_max)<parseInt(this.type4_min)) ///  不合法
       {
//           alert('min的值大于max的值，请检查后输入');
          const h = this.$createElement;
          this.$notify({
            title: '提示',
            message: h('i', {style: 'color: teal'},"min的值大于max的值，请检查后输入")
          })
       }
        else  //  都有合法
        {
          var a = "4"+","+this.save_item+","+this.type4_min+"<="+this.save_item +  " and " + this.save_item + "<=" +this.type4_max;
          this.click_flag4=false;
//          alert(a);
          this.res_all.push(a);
//          alert('add ok');
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
          var forsubmit={
            "dataSourceId":this.dataSourceId,
            "tableName":this.tableName,
            "paramStrings":this.res_all
          }
        axios.post("/kjb/dgs/accuracy/commitjob",forsubmit).then
        ((response)=>{
          var res=response.data;
          this.res_all=[];
          if(res.status==1){
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, "任务提交成功，请在任务管理处查看进度")
            });
          } else {
            const h = this.$createElement;
            this.$notify({
              title: '提示',
              message: h('i', {style: 'color: teal'}, "提交失败")
            })
          }
      }).catch((e) => {
          const h = this.$createElement;
        this.$notify({
          title: '提示',
          message: h('i', {style: 'color: teal'}, "提交失败")
        })
      })  //   method;
      }
    }   ///  method
  }
</script>

