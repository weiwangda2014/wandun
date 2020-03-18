<html>
<head>
    <title>分配角色</title>
    <JavaScripts>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ztree@3.5.24/css/metroStyle/metroStyle.css">
        <script src="https://cdn.jsdelivr.net/npm/ztree@3.5.24/js/jquery.ztree.all.min.js"></script>
        <script type="text/javascript">

            var officeTree;
            var selectedTree;//zTree已选择对象

            // 初始化
            $(document).ready(function () {
                officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
                selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
            });

            var setting = {
                view: {selectedMulti: false, nameIsHTML: true, showTitle: false, dblClickExpand: false},
                data: {simpleData: {enable: true}},
                callback: {onClick: treeOnClick}
            };

            var officeNodes = [
                <#list officeList as office>

                {
                    id: "${office.id}",
                    pId: "${office.parent.id?has_content?string(office.parent.id+'','0')}",
                    name: "${office.name}"
                },
                </#list>];

            var pre_selectedNodes = [
                <#list userList as user>

                {
                    id: "${user.id}",
                    pId: "0",
                    name: "<font color='red' style='font-weight:bold;'>${user.name}</font>"
                },
                </#list>];

            var selectedNodes = [
                <#list userList as user>
                {
                    id: "${user.id}",
                    pId: "0",
                    name: "<font color='red' style='font-weight:bold;'>${user.name}</font>"
                },
                </#list>];

            var pre_ids = "${selectIds}".split(",");
            var ids = "${selectIds}".split(",");

            //点击选择项回调
            function treeOnClick(event, treeId, treeNode, clickFlag) {
                $.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
                if ("officeTree" == treeId) {
                    $.get("${admin}/roles/users?officeId=" + treeNode.id, function (userNodes) {
                        $.fn.zTree.init($("#userTree"), setting, userNodes);
                    });
                }
                if ("userTree" == treeId) {
                    if ($.inArray(String(treeNode.id), ids) < 0) {
                        selectedTree.addNodes(null, treeNode);
                        ids.push(String(treeNode.id));
                    }
                }
                ;
                if ("selectedTree" == treeId) {
                    if ($.inArray(String(treeNode.id), pre_ids) < 0) {
                        selectedTree.removeNode(treeNode);
                        ids.splice($.inArray(String(treeNode.id), ids), 1);
                    } else {
                        top.$.jBox.tip("角色原有成员不能清除！", 'info');
                    }
                }
            };

            function clearAssign() {
                var submit = function (v, h, f) {
                    if (v == 'ok') {
                        var tips = "";
                        if (pre_ids.sort().toString() == ids.sort().toString()) {
                            tips = "未给角色【${role.name}】分配新成员！";
                        } else {
                            tips = "已选人员清除成功！";
                        }
                        ids = pre_ids.slice(0);
                        selectedNodes = pre_selectedNodes;
                        $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
                        top.$.jBox.tip(tips, 'info');
                    } else if (v == 'cancel') {
                        // 取消
                        top.$.jBox.tip("取消清除操作！", 'info');
                    }
                    return true;
                };
                tips = "确定清除角色【${role.name}】下的已选人员？";
                top.$.jBox.confirm(tips, "清除确认", submit);
            };
        </script>
    </JavaScripts>
</head>
<body>
<div id="assignRole" class="row-fluid span12">
    <div class="span4" style="border-right: 1px solid #A8A8A8;">
        <p>所在部门：</p>
        <div id="officeTree" class="ztree"></div>
    </div>
    <div class="span3">
        <p>待选人员：</p>
        <div id="userTree" class="ztree"></div>
    </div>
    <div class="span3" style="padding-left:16px;border-left: 1px solid #A8A8A8;">
        <p>已选人员：</p>
        <div id="selectedTree" class="ztree"></div>
    </div>
</div>
</body>
</html>
