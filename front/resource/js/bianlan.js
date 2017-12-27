/**
 * Created by tsy on 2017/12/22.
 */
$(function() {
    var clickX;
    var dragging = false;
    var out = false;
    var doc = document;
    var labBtn = $("#side-label");
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

    labBtn.bind('mousedown', function () {
        dragging = true;
      }
    );
    doc.onmousemove = function (e) {
      if (dragging) {
        clickX = e.pageX;
        labBtn.css('left', clickX - 5 + 'px');//按钮移动
        $("side-conn").width(clickX + 'px');
      }
    };
    $(doc).mouseup(function (e) {
      dragging = false;
      e.cancelBubble = true;
    });
  }
  );
