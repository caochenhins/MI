var pager = {page: 1, start: 0, limit: 10};

/*将初始化页面封装成一个方法*/
function initPage(id) {
    $("#total-num").text(pager.totalCount);
    $("#total-page").text(pager.totalPageNum);
    $("#current-page").text(pager.page);
    $.jqPaginator('#pagination', {
        totalPages: 10,  //设置分页的总页数
        totalCounts: 100, //设置分页的总条目数
        visiblePages: 3, //设置最多显示的页码数
        currentPage: 1, //设置当前的页码
        pageSize: 2,
        first: '<li class="first"><a href="javascript:;">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
        last: '<li class="last"><a href="javascript:;">末页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) { //换页方法
            pager.page = num;
            var type = $("#pagination").data("type");
            loadList(type, id);
            // 当前第几页
            $("#current-page").text(num);
            $(".chosen-select").chosen({
                max_selected_options: 5,
                no_results_text: "没有找到",
                allow_single_deselect: true
            });
            $(".chosen-select").trigger("liszt:updated");
        }
    });
}
/*将加载文章,文章分类,标签分类重构成一个方法*/
function loadList(type, id) {
    var url = "";
    if (type == "article") {
        url = '/' + type
    }
    else {
        url = '/' + type + "/" + id;
    }
    $.ajax({
        type: 'GET',
        url: url,
        data: pager,
        success: function (data) {
            $("#main-article").html(data);
        }
    });
}
