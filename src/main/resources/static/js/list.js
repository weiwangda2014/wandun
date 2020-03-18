$().ready(function () {

    var $listForm = $("#listForm");

    var $deleteButton = $("#deleteButton");
    var $refreshButton = $("#refreshButton");
    var $pageSizeMenu = $("#pageSizeMenu");
    var $pageSizeMenuItem = $("#pageSizeMenu li");
    var $searchPropertyMenu = $("#searchPropertyMenu");
    var $searchPropertyMenuItem = $("#searchPropertyMenu li");
    var $searchValue = $("#searchValue");
    var $listTable = $("#listTable");
    var $selectAll = $("#selectAll");
    var $ids = $("#listTable input[name='ids']");
    var $contentRow = $("#listTable tr:gt(0)");
    var $sort = $("#listTable a.sort");
    var $pageSize = $("#pageSize");
    var $searchProperty = $("#searchProperty");
    var $orderProperty = $("#orderProperty");
    var $orderDirection = $("#orderDirection");
    var $pageNumber = $("#pageNumber");

    // 删除
    $deleteButton.click(function () {
        var $this = $(this);
        if ($this.hasClass("disabled")) {
            return false;
        }
        var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
        top.layer.confirm('确认要彻底删除数据吗?', {icon: 3, title: '系统提示'}, function (index) {
            var layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
            $.ajax({
                url: "delete",
                type: "POST",
                data: $checkedIds.serialize(),
                dataType: "json",
                cache: false,
                success: function (message) {
                    switch (message.type) {
                        case "success":
                            $checkedIds.closest("tr").remove();
                            if ($listTable.find("tr").size() <= 1) {
                                setTimeout(function () {
                                    location.reload(true);
                                }, 3000);
                            }
                            top.layer.close(layerIndex);
                            top.layer.alert(message.content, {icon: 0, title: '信息'});
                            break;
                        case "warn":
                            top.layer.alert(message.content, {icon: 3});
                            top.layer.close(layerIndex);
                            break;
                        default:
                            top.layer.alert(message.content, {icon: 2});
                            top.layer.close(layerIndex);
                            $deleteButton.addClass("disabled");
                            $selectAll.prop("checked", false);
                            $checkedIds.prop("checked", false);
                    }
                }
            });
        });
        return false;
    });

    // 刷新
    $refreshButton.click(function () {
        location.reload(true);
        return false;
    });

    // 每页记录数菜单
    $pageSizeMenu.hover(
        function () {
            $(this).children("ul").show();
        }, function () {
            $(this).children("ul").hide();
        }
    );

    // 每页记录数
    $pageSizeMenuItem.click(function () {
        $pageSize.val($(this).attr("val"));
        $pageNumber.val("1");
        $listForm.submit();
    });

    // 搜索项菜单
    $searchPropertyMenu.hover(
        function () {
            $(this).children("ul").show();
        }, function () {
            $(this).children("ul").hide();
        }
    );

    // 搜索项
    $searchPropertyMenuItem.click(function () {
        var $this = $(this);
        $searchProperty.val($this.attr("val"));
        $searchPropertyMenuItem.removeClass("current");
        $this.addClass("current");
    });

    // 全选
    $selectAll.on("ifClicked", function (event) {
        var $checkbox = $("tbody").find("[type='checkbox']").not("[disabled]");
        if (event.target.checked) {
            $checkbox.iCheck('uncheck');
            i = 0;
        } else {
            $checkbox.iCheck('check');
            i = length;
        }
    });
    // 选择
    $ids.click(function () {
        var $this = $(this);
        if ($this.prop("checked")) {
            $this.closest("tr").addClass("selected");
            $deleteButton.removeClass("disabled");
        } else {
            $this.closest("tr").removeClass("selected");
            if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
                $deleteButton.removeClass("disabled");
            } else {
                $deleteButton.addClass("disabled");
            }
        }
    });

    // 排序
    $sort.click(function () {
        var orderProperty = $(this).attr("name");
        if ($orderProperty.val() == orderProperty) {
            if ($orderDirection.val() == "asc") {
                $orderDirection.val("desc");
            } else {
                $orderDirection.val("asc");
            }
        } else {
            $orderProperty.val(orderProperty);
            $orderDirection.val("asc");
        }
        $pageNumber.val("1");
        $listForm.submit();
        return false;
    });

    // 排序图标
    if ($orderProperty.val() != "") {
        $sort = $("#listTable a[name='" + $orderProperty.val() + "']");
        if ($orderDirection.val() == "asc") {
            $sort.removeClass("desc").addClass("asc");
        } else {
            $sort.removeClass("asc").addClass("desc");
        }
    }

    // 页码
    $pageNumber.keypress(function (event) {
        return (event.which >= 48 && event.which <= 57) || event.which == 8 || (event.which == 13 && $(this).val().length > 0);
    });

    // 表单提交
    $listForm.submit(function () {
        if (!/^\d*[1-9]\d*$/.test($pageNumber.val())) {
            $pageNumber.val("1");
        }
        if ($searchValue.size() > 0 && $searchValue.val() != "" && $searchProperty.val() == "") {
            $searchProperty.val($searchPropertyMenuItem.first().attr("val"));
        }
    });

    // 页码跳转
    $.pageSkip = function (pageNumber) {
        $pageNumber.val(pageNumber);
        $listForm.submit();
        return false;
    };

    // 列表查询
    if (location.search != "") {
        addCookie("listQuery", location.search, {expires: 10 * 60, path: ""});
    } else {
        removeCookie("listQuery", {path: ""});
    }

});