var i = 0;
$(function () {
        $(".notice_contenta").addClass("zhan");
        $(".zhankai span").text("查看全部");
});
function zhankai(obj) {

    if (i % 2 == 0) {
        $(obj).parent().prev().removeClass("zhan");
        $(obj).text("收起");
        i++;
    } else {
        $(obj).parent().prev().addClass("zhan");
        $(obj).text("查看全部");
        i++;
    }
}
