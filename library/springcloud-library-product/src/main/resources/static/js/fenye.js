// 分页JS
function fenye() {
    $(".yeshu").html("");
    var pagecount = $(".fen_count").val();
    var fen_PageNo = $(".fen_PageNo").val();
    fen_PageNo = parseInt(fen_PageNo);
    // 如果当前页为前三页
    if(fen_PageNo<=3){
        if (parseInt(pagecount)<=5) {
            if (parseInt(pagecount) == 4) {
                for (var i = 0; i < 4; i++) {
                    if ((i + 1) == fen_PageNo) {
                        $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\" style=\"background-color: #009688;color: #fff;\">" + (i + 1) + "</a>");
                    } else {
                        $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\">" + (i + 1) + "</a>");
                    }
                }
            } else {
                for (var i = 0; i < parseInt(pagecount); i++) {
                    if ((i + 1) == fen_PageNo) {
                        $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\" style=\"background-color: #009688;color: #fff;\">" + (i + 1) + "</a>");
                    } else {
                        $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\">" + (i + 1) + "</a>");
                    }
                }
            }
        }else {
            for (var i = 0;i < 4;i++){
                if((i+1)==fen_PageNo){
                    $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\" style=\"background-color: #009688;color: #fff;\">" + (i + 1) + "</a>");
                }else {
                    $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\">" + (i + 1) + "</a>");
                }
            }
            $(".yeshu").append("<a>...</a>");
            $(".yeshu").append("<a onclick=\"doPerson(" + parseInt(pagecount) + ")\">"+parseInt(pagecount)+"</a>");
        }
    }else if ((pagecount - fen_PageNo)<3) {
        if (parseInt(pagecount)>5) {
            $(".yeshu").append("<a onclick=\"doPerson(1)\">1</a>");
            $(".yeshu").append("<a>...</a>");
            for (var i = 0; i < (fen_PageNo - (parseInt(pagecount - 4))); i++) {
                if ((parseInt(pagecount) - 4 + (i + 1)) == fen_PageNo) {
                    $(".yeshu").append("<a onclick=\"doPerson("
                        + ((parseInt(pagecount) - 4) + (i + 1)) + ")\" style=\"background-color: #009688;color: #fff;\">" + ((parseInt(pagecount) - 4) + (i + 1)) + "</a>");
                } else {
                    $(".yeshu").append("<a onclick=\"doPerson("
                        + ((parseInt(pagecount) - 4) + (i + 1)) + ")\">" + ((parseInt(pagecount) - 4) + (i + 1)) + "</a>");
                }
            }
            for (var j = 0; j < parseInt(pagecount) - fen_PageNo; j++) {
                $(".yeshu").append("<a onclick=\"doPerson(" + (fen_PageNo + (j + 1)) + ")\">" + (fen_PageNo + (j + 1)) + "</a>");
            }
        }else{
            for (var i =0; i < parseInt(pagecount);i++){
                if ((i + 1) == fen_PageNo) {
                    $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\" style=\"background-color: #009688;color: #fff;\">" + (i + 1) + "</a>");
                } else {
                    $(".yeshu").append("<a onclick=\"doPerson(" + (i + 1) + ")\">" + (i + 1) + "</a>");
                }
            }
        }
    }else{
        $(".yeshu").append("<a onclick=\"doPerson(1)\">1</a><a>...</a>");
        for (var i = fen_PageNo-2; i<=fen_PageNo+1;i++){
            if (i==fen_PageNo){
                $(".yeshu").append("<a onclick=\"doPerson(" + i + ")\" style=\"background-color: #009688;color: #fff;\">" + i + "</a>");
            }else{
                $(".yeshu").append("<a onclick=\"doPerson("+ i +")\">" + i + "</a>");
            }
        }
        $(".yeshu").append("<a>...</a>");
        $(".yeshu").append("<a onclick=\"doPerson(" + parseInt(pagecount) + ")\">"+parseInt(pagecount)+"</a>");
        /*$(".yeshu").append("<a href=\"#\">" + (fen_PageNo + 1) + "</a>");*/
    }
}

function doPerson(numbera) {
    var chuqdate01 = $("#chuqdate01").val();
    var chuqdate02 = $("#chuqdate02").val();
    var pagecount = $(".fen_count").val();
    var fen_PageNo = $(".fen_PageNo").val();
    var daka_state = $("#daka_state").val();
    if(numbera=="a"){
        numbera = parseInt(fen_PageNo)-1;
    }else if (numbera=="b"){
        numbera = parseInt(fen_PageNo)+1;
    }
    var empName = $(".empName").text();
    var m_state = null;
    var e_state = null;
    $.ajax({
        url:"/library/dofenye",
        type: "GET",
        data: {empName:empName,pageNo:numbera,prefixdate:chuqdate01,suffixdate:chuqdate02,daka_state:daka_state},
        dataType:"json",
        success: function (data) {
            if (data.attendancelist.length!=0){
                $(".zanwu").css("display", "none");
            }else{
                $(".zanwu").show();
            }
            $(".attendList").html("");
            $(".fen_PageNo").val(data.fenye.currentPageNo);
            $(".fen_count").val(data.fenye.totalPageCount);
            fenye();
            for (var i =0; i <data.attendancelist.length; i++){
                if (data.attendancelist[i].morning_state==0){
                    m_state = "正常";
                }
                if (data.attendancelist[i].morning_state==1){
                    m_state = "迟到";
                }
                if (data.attendancelist[i].evening_state==0) {
                    e_state = "正常";
                }
                if (data.attendancelist[i].evening_state==1){
                    e_state = "早退";
                }
                if (data.attendancelist[i].evening_state==2){
                    e_state = "未打卡";
                }
                if (data.attendancelist[i].morning_state==2){
                    m_state = "未打卡";
                }
                $(".attendList").append("<tr>\n" +
                    "<td>" + data.attendancelist[i].id +"</td>\n"+
                    "<td>" + data.attendancelist[i].userid +"</td>\n"+
                    "<td>"+ $("#uname").val() +"</td>\n"+
                    "<td>"+ data.attendancelist[i].morning_time +"</td>\n"+
                    "<td>"+ m_state +"</td>\n"+
                    "<td>"+ data.attendancelist[i].evening_time +"</td>\n"+
                    "<td>"+ e_state +"</td>\n"+
                    "<td>"+ data.attendancelist[i].ondate +"</td>\n"+
                    "</tr>");
            }
        }
    });
}

function docondition() {
    doPerson(1);
}

function daka() {
      var username = $(".empName").text();
      var empid = $("#empid").val();
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth()+1;
      var day = date.getDate();
      var hour = date.getHours();
      var minutes = date.getMinutes();
      var datetime = year+"/"+month+"/"+day;
      var timea = hour + ":" + minutes;
      $.ajax({
        url: "/library/daka",
        type: "GET",
        data: {datetime:datetime,username:username,empid:empid,hour:hour,timea:timea},
        dateType:"json",
        success: function (data) {
          if (data==1){
              layer.alert('打卡成功，当前时间：'+timea, {icon: 1});
          } else{
              layer.msg('重复打卡!', {icon: 5});
          }
        }
      });
}

function jisuan() {
    var leave_starttime = $("input[name='leave_starttime']").val();
    var leave_overtime = $("input[name='leave_overtime']").val();
    if (leave_starttime == "" || leave_overtime == "") {
        return false;
    }
    $("input[name='leave_number']").val(datedifference(leave_starttime, leave_overtime));
}

function addleave() {
    var leave_empname = $("input[name='leave_empname']").val();
    var leave_starttime = $("input[name='leave_starttime']").val();
    var leave_overtime = $("input[name='leave_overtime']").val();
    var leave_number = $("input[name='leave_number']").val();
    var leave_cause = $(".layui-textarea").val();
    var leave_id = $("input[name='leave_id']").val();
    if (leave_starttime=="" || leave_overtime==""){
        layer.msg('时间信息未完善!', {icon: 5});
        return false;
    }
    $.ajax({
        url:"/library/addleave",
        type:"POST",
        data:{leave_userid:leave_id,leave_empname:leave_empname,leave_starttime:leave_starttime
            ,leave_overtime:leave_overtime,leave_number:leave_number,leave_cause:leave_cause},
        dataType: "json",
        success:function (data) {
            if (data==1){
                layer.alert('成功发送请假申请!', {icon: 1});
            }else{
                layer.msg('申请失败!', {icon: 5});
            }
        }
    });

}

function datedifference(sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式
    var dateSpan,
        tempDate,
        iDays;
    sDate1 = Date.parse(sDate1);
    sDate2 = Date.parse(sDate2);
    dateSpan = sDate2 - sDate1;
    dateSpan = Math.abs(dateSpan);
    iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
    return iDays
}

$(function () {
   $(".zhankai").toggle(function () {
       $(".notice_contenta").addClass();
   });
});