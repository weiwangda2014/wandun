<html>
<head>
    <title>角色管理</title>
    <JavaScripts>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ztree@3.5.24/css/metroStyle/metroStyle.css">
        <script src="https://cdn.jsdelivr.net/npm/ztree@3.5.24/js/jquery.ztree.all.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                window.$.doSubmit = function (form, index) {
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    form.ajaxSubmit({
                        data: {
                            'ids': ids
                        },
                        dataType: "json",
                        forceSync: true,
                        success: function (data) {
                            top.layer.close(this.layerIndex);
                            switch (data.type) {
                                case "error":
                                    top.layer.alert(data.content, {icon: 2});
                                    break;
                                case "warn":
                                    top.layer.alert(data.content, {icon: 3});
                                    break;
                                default:
                                    top.layer.alert(data.content, {icon: 1});
                                    parent.layer.close(index);
                                    var timestamp = new Date().getTime();
                                    top.getActiveTab().attr("src", top.getActiveTab().attr("src") + "?timestamp=" + timestamp);
                                    top.getActiveTab()[0].contentDocument.forms['searchForm'].submit();
                            }
                        },
                        beforeSubmit: function (arr, $form, options) {
                            this.layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
                        }
                    });
                }


                var setting = {
                    check: {enable: true, nocheckInherit: true}, view: {selectedMulti: false},
                    data: {simpleData: {enable: true}},
                    callback: {
                        beforeClick: function (id, node) {
                            tree.checkNode(node, !node.checked, true, true);
                            return false;
                        },
                        onCheck: onCheck
                    }
                };

                function onCheck(e, treeId, treeNode) {  //这是将所有选中节点的名字 用,分割做拼接 用于持久化到数据库
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                }

                // 用户-菜单
                var zNodes = [
                    <#list menuList as menu>
                    {
                        id: "${menu.id}",
                        pId: "${menu.parent.id?has_content?string(menu.parent.id+'','0')}",
                        name: "${menu.parent.id?has_content?string(menu.name,'权限列表')}"
                    },
                    </#list>
                ];
                // 初始化树结构
                var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
                // 不选择父节点
                tree.setting.check.chkboxType = {"Y": "ps", "N": "s"};
                // 默认选择节点
                var ids = "${role.menuIds}".split(",");
                for (var i = 0; i < ids.length; i++) {
                    var node = tree.getNodeByParam("id", ids[i]);
                    try {
                        tree.checkNode(node, true, false);
                    } catch (e) {
                    }
                }
                // 默认展开全部节点
                tree.expandAll(true);
            });
        </script>

    </JavaScripts>
</head>
<body>
<form id="inputForm" modelAttribute="role" action="${admin}/roles/auth" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${role.id}">
    <input name="office.id" type="hidden" value="${role.office.id}">
    <input name="office.name" type="hidden" value="${role.office.name}">
    <input name="name" type="hidden" value="${role.name}">
    <input name="oldName" type="hidden" value="${role.name}">
    <input name="enname" type="hidden" value="${role.enname}">
    <input name="oldEnname" type="hidden" value="${role.enname}">
    <input name="roleType" type="hidden" value="${role.roleType}">
    <input name="sysData" type="hidden" value="${role.sysData}">
    <input name="useable" type="hidden" value="${role.useable}">
    <input name="dataScope" type="hidden" value="${role.dataScope}">
    <input name="remarks" type="hidden" value="${role.remarks}">
    <input type="hidden" id="menuIds" name="menuIds" value="${role.menuIds}">
    <input type="hidden" id="officeIds" name="officeIds" value="${role.officeIds}">
    <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
</form>
</body>
</html>