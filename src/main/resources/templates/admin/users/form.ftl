<#import "../../control/treeSelect.ftl" as tree>


<html>
<head>
    <title>用户管理</title>
    <JavaScripts>
        <script type="text/javascript">
            var validateForm;
            $(document).ready(function () {
                $("#no").focus();
                window.$.doSubmit = function (form, index) {
                    form.ajaxSubmit({
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
                                    top.officeContent.search();
                            }
                        },
                        beforeSubmit: function (arr, $form, options) {
                            this.layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
                        }
                    });
                }

                validateForm = $("#inputForm").validate({
                    rules: {
                        loginName: {remote: "${admin}/users/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')},
                        confirmNewPassword: {equalTo: "#newPassword"}
                    },
                    messages: {
                        loginName: {remote: "用户登录名已存在"},
                        confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                    },
                    submitHandler: function (form) {

                    },
                    errorContainer: "#messageBox",
                    errorPlacement: function (error, element) {
                        $("#messageBox").text("输入有误，请先更正。");
                        if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                            error.appendTo(element.parent().parent());
                        } else {
                            error.insertAfter(element);
                        }
                    }
                });
            });
        </script>
    </JavaScripts>
</head>
<body class="hideScroll">
<form id="inputForm" autocomplete="off" action="${admin}/users/save" method="post" class="form-horizontal">
    <input name="id" type="hidden" value="${user.id}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>头像：</label></td>
            <td class="width-35">

            </td>
            <td class="width-15" class="active"><label class="pull-right"><font color="red">*</font>归属公司:</label></td>
            <td class="width-35">
                <@tree.select id="company" name="company.id" value="${user.company.id}"
                labelName="company.name" labelValue="${user.company.name}"
                title="归属公司" url="${admin}/offices/treeData"
                cssClass="form-control required" />
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right"><font color="red">*</font>归属部门:</label></td>
            <td>
                <@tree.select id="office" name="office.id" value="${user.office.id}"
                labelName="office.name" labelValue="${user.office.name}"
                title="归属公司" url="${admin}/offices/treeData"
                cssClass="form-control required" notAllowSelectParent="true"/>
            </td>
            <td class="active"><label class="pull-right"><font color="red">*</font>工号:</label></td>
            <td>
                <input name="no" class="form-control required" type="text" value="${user.no}" maxlength="50"/>
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
            <td>
                <input name="name" class="form-control required" type="text" value="${user.name}" maxlength="50"/>
            </td>
            <td class="active"><label class="pull-right"><font color="red">*</font>登录名:</label></td>
            <td>
                <input name="loginName" class="form-control required userName" type="text" value="${user.loginName}"
                       maxlength="50"/>
            </td>
        </tr>

        <tr>
            <td class="active">
                <label class="pull-right"><#if user.id?has_content><font color="red">*</font></#if>密码:</label>
            </td>
            <td>

                <input id="newPassword" name="newPassword" type="password" value="${user.newPassword}" maxlength="50"
                       class="form-control ${(user.id?has_content) ? string('','required')}"/>
                <#if user.id?has_content><span class="help-inline">若不修改密码，请留空。</span></#if>
            </td>
            <td class="active"><label class="pull-right">
                    <#if user.id?has_content><font color="red">*</font></#if>
                    确认密码:</label></td>
            <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"
                       class="form-control ${(user.id?has_content) ? string('','required')}" value="" maxlength="50"
                />
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right">邮箱:</label></td>
            <td>
                <input name="email" class="form-control required email" type="text" value="${user.email}"
                       maxlength="50"/>
            </td>
            <td class="active"><label class="pull-right">电话:</label></td>
            <td>
                <input name="phone" class="form-control" type="text" value="${user.phone}"
                       maxlength="50"/>
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right">手机:</label></td>
            <td>
                <input name="mobile" class="form-control" type="text" value="${user.mobile}"
                       maxlength="50"/>
            </td>
            <td class="active"><label class="pull-right">是否允许登录:</label></td>
            <td>
                <select name="loginFlag" class="form-control">
                    <#list dictList("yes_no") as d>
                        <option value="${d.value}"<#if d.value== user.loginFlag> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right">用户类型:</label></td>
            <td>
                <select name="userType" class="form-control">
                    <#list dictList("sys_user_type") as d>
                        <option value="${d.value}"<#if d.value== user.userType> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>

            </td>
            <td class="active"><label class="pull-right"><font color="red">*</font>用户角色:</label></td>
            <td>
                <#list allRoles as role>
                    <input type="checkbox" name="roleIdList" value="${role.id}" class="i-checks required"/> ${role.name}
                </#list>
                <label id="roleIdList-error" class="error" for="roleIdList"></label>
            </td>
        </tr>

        <tr>
            <td class="active"><label class="pull-right">备注:</label></td>
            <td>
                <textarea name="remarks" class="form-control"> </textarea>
            </td>
            <td class="active"><label class="pull-right"></label></td>
            <td></td>
        </tr>

        <#if user.id?has_content>
            <tr>
                <td class=""><label class="pull-right">创建时间:</label></td>
                <td><span class="lbl">${user.createDate}</span></td>
                <td class=""><label class="pull-right">最后登陆:</label></td>
                <td><span class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：${user.loginDate}</span></td>
            </tr>
        </#if>

</form>
</body>
</html>