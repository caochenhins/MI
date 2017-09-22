/*将初始化页面封装成一个方法*/
var page;
function initPage(id) {
    console.log('----------------');
    console.log(page);
    $("#total-num").text(3);
    $("#total-page").text(3);
    $("#current-page").text(3);
    $.jqPaginator('#pagination', {
        totalPages: 3,  //设置分页的总页数
        totalCounts: 3, //设置分页的总条目数
        visiblePages: 3, //设置最多显示的页码数
        currentPage: 1, //设置当前的页码
        pageSize: 3,
        first: '<li class="first"><a href="javascript:;">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
        last: '<li class="last"><a href="javascript:;">末页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) { //换页方法

            var type = $("#pagination").data("type");
            // loadList('article', id);
            // 当前第几页
            $("#current-page").text(3);
            $(".chosen-select").chosen({
                max_selected_options: 5,
                no_results_text: "没有找到",
                allow_single_deselect: true
            });
            $(".chosen-select").trigger("liszt:updated");
        }
    });
}



