$(function(){
    $("#type-manage-li").addClass("active");
    $("#type-list-li").addClass("active");
    var page = $("#current-page").val();
    if (page==null || page ==0 ){
        page = 1;
    }
    $.ajax({
        url : '/admin/type/initPage',
        data : 'page='+page,
        success  : function(data) {
            $("#total-num").text(data.totalCount);
            $("#total-page").text(data.totalPageNum);
            $("#current-page").text(data.page);
            if (data.totalCount > 0) {
                $.jqPaginator('#pagination', {
                    totalPages: data.totalPageNum,
                    visiblePages: 5,
                    currentPage: data.page,
                    prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
                    next: '<li class="next"><a href="javascript:;">下一页</a></li>',
                    page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
                    onPageChange: function (num, type) {
                        // 加载分类列表
                        $("#current-page").text(num);
                        loadList();
                    }
                });
            }else {
                loadList();
            }
        }
    });


})


// 跳转分页
function toPage(page){
    $("#page").val(page);
    loadList();
}

// 加载分类列表
function loadList(){
    // 收集参数
    var keyword = $("#keyword").val();
    var page = $("#current-page").text();
    if(isEmpty(page) || page == 0){
        page = 1;
    }
    // 查询列表
    $.ajax({
        url : '/admin/type/load',
        data : 'page='+page+"&param="+keyword,
        success  : function(data) {
            $("#dataList").html(data);
        }
    });

}

// 搜索
$("#search").on("click",function () {
    loadList();
});
//绑定删除分类的点击事件
$("#dataList").on('click','.delete',function () {
    var typeId = $(this).parent().data("id");
    $.ajax({
        url:"/admin/type/delCheckExist",
        data : 'typeId='+typeId,
        success : function (data) {
            if (data.code == '400'){
                new $.flavr({
                    content: '删除分类将会将此分类的文章移除此分类，您确定要删除吗?',

                    buttons: {
                        primary: {
                            text: '确定', style: 'primary', action: function () {
                                deleteById(typeId);
                            }
                        },
                        success: {
                            text: '取消', style: 'danger', action: function () {

                            }
                        }
                    }
                });
            }else {
                deleteById(typeId);
            }
        }

    });
})

// 删除分类的ajax方法
function deleteById(id){
    $.ajax({
        url: "/admin/type/delete",
        data: 'typeId='+id,
        success : function (data) {
            if(data.code == '200'){
                loadList();
                autoCloseAlert(data.msg,1000);
            }else{
                autoCloseAlert(data.msg,1000);
            }
        }
    });
}

// 跳转编辑页
$("#dataList").on('click','.edit',function () {
    $.ajax({
        url : '/admin/type/editJump',
        data: 'typeId='+$(this).parent().data("id"),
        success  : function(data) {
            $('#editContent').html(data);
            $('#editModal').modal('show');
            $('#editModal').addClass('animated');
            $('#editModal').addClass('flipInY');
        }
    });
});


// 关闭编辑分类窗口
function closeEditWindow(){
    $('#editModal').modal('hide');
}

// 关闭新增分类窗口
function closeAddWindow(){
    $('#addModal').modal('hide');
}


// 编辑分类
function saveEdit(){
    if(validateEdit()){
        $.ajax({
            url : '/admin/type/update',
            data : $("#editForm").serialize(),
            success  : function(data) {
                if(data.code == '200'){
                    $('#editModal').modal('hide');
                    closeEditWindow();
                    autoCloseAlert(data.msg,1000);
                    loadList();
                }else{
                    autoCloseAlert(data.msg,1000);
                }
            }
        });
    }
}

// 保存分类
function saveAdd(){
    if(validateAdd()){
        $.ajax({
            url : '/admin/type/save',
            data : $("#addForm").serialize(),
            success  : function(data) {
                if(data.code == '200'){
                    $('#addModal').modal('hide');
                    loadList();
                    closeAddWindow();
                    autoCloseAlert(data.msg,1000);
                }else{
                    autoCloseAlert(data.msg,1000);
                }
            }
        });
    }
}

// 校验新增分类输入框
function validateAdd(){
    var typeName = $("#typeName").val();
    var aliasName = $("#aliasName").val();
    if(!isEmpty(typeName)){
        if(isSpecialSymbols(typeName)){
            autoCloseAlert("分类名称不能包含特殊符号",1000);
            return false;
        }
    }else{
        autoCloseAlert("分类名称不能为空",1000);
        return false;
    }
    if(!isEmpty(aliasName)){
        if(isSpecialSymbols(aliasName)){
            autoCloseAlert("分类别名不能包含特殊符号",1000);
            return false;
        }
    }else{
        autoCloseAlert("分类别名不能为空",1000);
        return false;
    }
    return true;
}

// 校验编辑分类输入框
function validateEdit(){
    var typeName = $("#typeName").val();
    var aliasName = $("#aliasName").val();
    if(!isEmpty(typeName)){
        if(isSpecialSymbols(typeName)){
            autoCloseAlert("标签不能包含特殊符号",1000);
            return false;
        }
    }else{
        autoCloseAlert("标签不能为空",1000);
        return false;
    }
    if(!isEmpty(aliasName)){
        if(isSpecialSymbols(aliasName)){
            autoCloseAlert("分类别名不能包含特殊符号",1000);
            return false;
        }
    }else{
        autoCloseAlert("分类别名不能为空",1000);
        return false;
    }

    return true;
}

// 跳转新增分类页面
$("#add").on("click",function () {
    $.ajax({
        url : '/admin/type/addJump',
        success  : function(data) {
            $('#addContent').html(data);
            $('#addModal').modal('show');
            $('#addModal').addClass('animated');
            $('#addModal').addClass('bounceInLeft');
        }
    });
});