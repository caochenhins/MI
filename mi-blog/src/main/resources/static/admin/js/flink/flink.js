$(function() {
    $("#flink-manage-li").addClass("active");
    $("#flink-list-li").addClass("active");
    $("#flink-list-li").parent().addClass("in");
    var page = $("#current-page").val();
    if (page == null || page == 0) {
        page = 1;
    }
    $.ajax({
        url: '/admin/flink/initPage',
        data: 'page=' + page,
        success: function (data) {
            $("#total-num").text(data.totalCount);
            $("#total-page").text(data.totalPageNum);
            $("#current-page").text(data.page);
            if (data.totalCount > 0) {

                $.jqPaginator('#pagination', {
                    totalPages: data.totalPageNum,
                    totalCounts: data.totalCount,
                    visiblePages: 5,
                    currentPage: data.page,
                    prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
                    next: '<li class="next"><a href="javascript:;">下一页</a></li>',
                    page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
                    onPageChange: function (num, type) {
                        $("#current-page").text(num);
                        loadList();
                    }
                });
            }else {
                loadList();
            }
        }
    });
});

// 跳转分页
function toPage(page) {
    $("#page").val(page);
    loadList();
}

// 加载菜单列表
function loadList() {
    var param = $("#keyword").val();
    // 收集参数
    var page = $("#now").val();
    if (isEmpty(page) || page == 0) {
        page = 1;
    }
    // 查询列表
    $.ajax({
        url: '/admin/flink/load',
        data: 'page=' + page+"&param="+param,
        success: function (data) {
            $("#dataList").html(data);
        }
    });

}
// 搜索
$("#search").on('click',function () {
    loadList();
});

// 删除栏目
$("#dataList").on('click','.delete',function () {
    var id = $(this).parent().data("id")

    new $.flavr({
        content: '确定要删除吗?',
        buttons: {
            primary: {
                text: '确定', style: 'primary', action: function () {

                    $.ajax({
                        url: '/admin/flink/delete',
                        data: 'id='+id,
                        success: function (data) {
                            if (data.code == '200') {
                                autoCloseAlert(data.msg, 1000);
                                loadList();
                            } else {
                                autoCloseAlert(data.msg, 1000);
                            }
                        }
                    });
                }
            },
            success: {
                text: '取消', style: 'danger', action: function () {

                }
            }
        }
    });
});

// 跳转栏目编辑页
$("#dataList").on('click','.edit',function () {
    var id = $(this).parent().data("id")
    $.ajax({
        url: '/admin/flink/editJump',
        data: 'id='+id,
        success: function (data) {
            $('#editContent').html(data);
            $('#editModal').modal('show');
            $('#editModal').addClass('animated');
            $('#editModal').addClass('flipInY');
        }
    });
});

// 跳转新增页面
$("#add").on("click",function () {
    $.ajax({
        url: '/admin/flink/addJump',
        success: function (data) {
            $('#addContent').html(data);
            $('#addModal').modal('show');
            $('#addModal').addClass('animated');
            $('#addModal').addClass('bounceInLeft');
        }
    });
});

function saveEdit() {
    if (validateEdit()) {
        $.ajax({
            url: '/admin/flink/update',
            data: $("#editForm").serialize(),
            success: function (data) {
                if (data.code == '200') {
                    $('#editModal').modal('hide');
                    loadList();
                    closeEditWindow();
                    autoCloseAlert(data.msg, 1000);
                } else {
                    autoCloseAlert(data.msg, 1000);
                }
            }
        });
    }
}

function validateEdit() {
    var siteName = $("#siteName").val();
    if (!isEmpty(siteName)) {
        if (isSpecialSymbols(siteName)) {
            autoCloseAlert("合作伙伴名称不能包含特殊符号", 1000);
            return false;
        }
    } else {
        autoCloseAlert("合作伙伴名称不能为空", 1000);
        return false;
    }

    var siteUrl = $("#siteUrl").val();
    if (isEmpty(siteUrl)) {
        autoCloseAlert("合作伙伴地址不能为空", 1000);
        return false;
    }

    var siteDesc = $("#siteDesc").val();
    if (isEmpty(siteDesc)) {
        autoCloseAlert("网站描述不能为空", 1000);
        return false;
    }

    var sort = $("#sort").val();
    if (isEmpty(sort)) {
        autoCloseAlert("排序不能为空", 1000);
        return false;
    }

    return true;
}

//关闭编辑窗口
function closeEditWindow() {
    $('#editModal').modal('hide');
}
function saveAdd() {
    if (validateAdd()) {
        $.ajax({
            url: '/admin/flink/save',
            data: $("#addForm").serialize(),
            success: function (data) {
                if (data.code == '200') {
                    $('#addModal').modal('hide');
                    loadList();
                    autoCloseAlert(data.msg, 1000);
                    closeAddWindow();
                } else {
                    autoCloseAlert(data.msg, 1000);
                }
            }
        });
    }
}

// 校验新增banner输入框
function validateAdd() {
    var siteName = $("#siteName").val();
    if (!isEmpty(siteName)) {
        if (isSpecialSymbols(siteName)) {
            autoCloseAlert("名称不能包含特殊符号", 1000);
            return false;
        }
    } else {
        autoCloseAlert("名称不能为空", 1000);
        return false;
    }

    var siteUrl = $("#siteUrl").val();
    if (isEmpty(siteUrl)) {
        autoCloseAlert("地址不能为空", 1000);
        return false;
    }

    var siteDesc = $("#siteDesc").val();
    if (isEmpty(siteDesc)) {
        autoCloseAlert("描述不能为空", 1000);
        return false;
    }

    var sort = $("#sort").val();
    if (isEmpty(sort)) {
        autoCloseAlert("排序不能为空", 1000);
        return false;
    }

    return true;
}


function getRootPath() {
    //获取当前网址，如：
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： xx/xx.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/xx
    var projectName = pathName.substring(0,
        pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName + "/uploads/");
}

//关闭新增窗口
function closeAddWindow() {
    $('#addModal').modal('hide');
}




