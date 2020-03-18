<#import "../../control/treeSelect.ftl" as tree>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#name").focus();
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")}
                },
                messages: {
                    name: {remote: "角色名已存在"}
                },
                submitHandler: function (form) {
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                    var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                    for (var i = 0; i < nodes2.length; i++) {
                        ids2.push(nodes2[i].id);
                    }
                    $("#officeIds").val(ids2);
                    loading('正在提交，请稍等...');
                    form.submit();
                }
            });

            var setting = {
                check: {enable: true, nocheckInherit: true},
                view: {selectedMulti: false},
                data: {
                    simpleData: {enable: true}
                },
                callback: {
                    beforeClick: function (id, node) {
                        tree.checkNode(node, !node.checked, true, true);
                        return false;
                    }
                }
            };

            // 用户-菜单
            var zNodes = [];
            // 初始化树结构
            var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
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

            // 用户-机构
            var zNodes2 = [];
            // 初始化树结构
            var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
            // 不选择父节点
            tree2.setting.check.chkboxType = {"Y": "s", "N": "s"};
            // 默认选择节点
            var ids2 = "${role.officeIds}".split(",");
            for (var i = 0; i < ids2.length; i++) {
                var node = tree2.getNodeByParam("id", ids2[i]);
                try {
                    tree2.checkNode(node, true, false);
                } catch (e) {
                }
            }
            // 默认展开全部节点
            tree2.expandAll(true);
            // 刷新（显示/隐藏）机构
            refreshOfficeTree();
            $("#dataScope").change(function () {
                refreshOfficeTree();
            });
        });

        function refreshOfficeTree() {
            // 按明细设置
            if ($("#dataScope").val() == 9) {
                $("#officeTree").show();
            } else {
                $("#officeTree").hide();
            }
        }
    </script>
</head>
<body class="hideScroll">
<form id="inputForm" autocomplete="off" action="${admin}/areas/save" method="post" class="form-horizontal">
    <input type="hidden" value="${area.id}" name="id">
    <table class="table table-striped table-bordered table-hover">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">上级区域:</label></td>
            <td class="width-35">
                <@tree.select id="area" name="parent.id" value="${(area.parent?has_content) ? string('${area.parent.id}','')}"
                labelName="parent.name" labelValue="${(area.parent?has_content)?string('${area.parent.name}','')}"
                title="机构" url="${admin}/areas/treeData"
                cssClass="form-control required"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">区域名称:</label></td>
            <td class="width-35">
                <input name="name" class="form-control required" type="text" value="${area.name}" maxlength="50"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">区域编码:</label></td>
            <td class="width-35">
                <input name="code" class="form-control required" type="text" value="${area.code}" maxlength="50"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">区域类型:</label></td>
            <td class="width-35">
                <select name="type" class="form-control">
                    <#list dictList("sys_area_type") as d>
                        <option value="${d.value}"<#if d.value== area.type> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>