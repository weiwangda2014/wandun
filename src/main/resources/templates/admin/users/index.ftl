<html>
<head>
    <title>用户管理</title>
    <Styles>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ztree@3.5.24/css/metroStyle/metroStyle.css">
    </Styles>
</head>
<body class="gray-bg">

<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-content">
            <div id="content" class="row">
                <div id="left" style="background-color:#e7eaec" class="leftBox col-sm-1">
                    <a onclick="refresh()" class="pull-right">
                        <i class="fa fa-refresh"></i>
                    </a>
                    <div id="ztree" class="ztree leftBox-content"></div>
                </div>
                <div id="right" class="col-sm-11  animated fadeInRight">
                    <iframe id="officeContent" name="officeContent" src="${admin}/users/list" width="100%" height="91%"
                            frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>


    <JavaScripts>

        <script src="https://cdn.jsdelivr.net/npm/ztree@3.5.24/js/jquery.ztree.all.min.js"></script>
        <script type="text/javascript">
            var setting = {
                data: {simpleData: {enable: true, idKey: "id", pIdKey: "pId", rootPId: '0'}},
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        var id = treeNode.id == '0' ? '' : treeNode.id;
                        $('#officeContent').attr("src", "${admin}/users/list?office.id=" + id);
                    }
                }
            };

            function refreshTree() {
                $.getJSON("${admin}/offices/treeData", function (data) {
                    $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
                });
            }

            refreshTree();

            var leftWidth = 180; // 左侧窗口大小
            var htmlObj = $("html"), mainObj = $("#main");
            var frameObj = $("#left, #openClose, #right, #right iframe");

            function wSize() {
                var strs = getWindowSize().toString().split(",");
                htmlObj.css({"overflow-x": "hidden", "overflow-y": "hidden"});
                mainObj.css("width", "auto");
                frameObj.height(strs[0] - 120);
                var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
                $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 60);
                $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
            }

            function refresh() {//刷新
                window.location = "${admin}/users/index";
            }
        </script>
        <script src="${ctx}/static/js/wsize.min.js" type="text/javascript"></script>
    </JavaScripts>
</body>
</html>