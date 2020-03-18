<html>
<head>
    <title>分配角色</title>
    <JavaScripts>
        <script type="text/javascript">

            $("#assignButton").click(function () {
                top.layer.open({
                    type: 2,
                    area: ['100%', '100%'],
                    title: "选择用户",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${admin}/roles/usertorole?id=${role.id}",
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        var pre_ids = layero.find("iframe")[0].contentWindow.pre_ids;
                        var ids = layero.find("iframe")[0].contentWindow.ids;
                        if (ids[0] == '') {
                            ids.shift();
                            pre_ids.shift();
                        }
                        if (pre_ids.sort().toString() == ids.sort().toString()) {
                            top.$.jBox.tip("未给角色【其他】分配新成员！", 'info');
                            return false;
                        }
                        ;
                        // 执行保存
                        loading('正在提交，请稍等...');
                        var idsArr = "";
                        for (var i = 0; i < ids.length; i++) {
                            idsArr = (idsArr + ids[i]) + (((i + 1) == ids.length) ? '' : ',');
                        }
/*
                        $('#idsArr').val(idsArr);
                        $('#assignRoleForm').submit();


*/

                        $.ajax({
                            url: "${admin}/roles/assignrole",
                            async: true,
                            type: "POST",
                            data: {
                                "ids": idsArr,
                                "id": ${role.id}
                            },
                            success: function (json) {
                                top.layer.close(this.layerIndex);
                                switch (json.type) {
                                    case "error":
                                        top.layer.alert(json.content, {icon: 2});
                                        break;
                                    case "warn":
                                        top.layer.alert(json.content, {icon: 3});
                                        break;
                                    default:
                                        top.layer.alert(json.content, {icon: 1});
                                        parent.layer.close(index);
                                }
                            },
                            error: function () {

                            }
                        });

                    },
                    cancel: function (index) {
                    }
                });
            });

            function outrole(uid, rid) {
                $.ajax({
                    url: "${admin}/roles/outrole",
                    async: true,
                    type: "POST",
                    data: {
                        "uid": uid,
                        "rid": rid
                    },
                    success: function (json) {
                        top.layer.close(this.layerIndex);
                        switch (json.type) {
                            case "error":
                                top.layer.alert(json.content, {icon: 2});
                                break;
                            case "warn":
                                top.layer.alert(json.content, {icon: 3});
                                break;
                            default:
                                top.layer.alert(json.content, {icon: 1});
                                parent.layer.close(index);
                        }
                    },
                    error: function () {

                    }
                });
            }
        </script>
    </JavaScripts>
</head>
<body>

<div class="wrapper wrapper-content">
    <div class="container-fluid breadcrumb">
        <div class="row-fluid span12">
            <span class="span4">角色名称: <b>${role.name}</b></span>
            <span class="span4">归属机构: ${role.office.name}</span>
            <span class="span4">英文名称: ${role.enname}</span>
        </div>
        <div class="row-fluid span8">
            <span class="span4">角色类型: ${role.roleType}</span>
            <span class="span4">数据范围:  ${dictlabel(role.dataScope,'sys_data_scope','无')}</span>
        </div>
    </div>
    <div class="breadcrumb">
        <form id="assignRoleForm" action="${admin}/roles/assignrole" method="post" class="hide">
            <input type="hidden" name="id" value="${role.id}"/>
            <input id="idsArr" type="hidden" name="ids" value=""/>
        </form>
        <button id="assignButton" type="submit" class="btn btn-outline btn-primary btn-sm" title="添加人员"><i
                    class="fa fa-plus"></i> 添加人员
        </button>
    </div>
    <table id="contentTable" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>归属公司</th>
            <th>归属部门</th>
            <th>登录名</th>
            <th>姓名</th>
            <th>电话</th>
            <th>手机</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list userList as user>
            <tr>
                <td>${user.company.name}</td>
                <td>${user.office.name}</td>
                <td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
                <td>${user.name}</td>
                <td>${user.phone}</td>
                <td>${user.mobile}</td>
                <td>
                    <a href="#" onclick="outrole('${user.id}','${role.id}')">移除</a>
                </td>

            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>
