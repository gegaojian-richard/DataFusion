/**
 * Created by tsy on 2017/12/22.
 */
$(function(){
  var out = false;
  var buttonslide=$("#add-conn");
  buttonslide.bind("click",function() {
      if (!out) {
        $(".sidebar").removeClass("movein");
        $(".sidebar").addClass("moveout");
        out = true;
      } else if (out) {
        $(".sidebar").removeClass("moveout");
        $(".sidebar").addClass("movein");
        out = false;
      }
    }
  );
 // $("#mysql-info").modal();
 // $("#oracle-info").modal();
})
