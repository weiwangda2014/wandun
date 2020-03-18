<#import "../../control/treeSelect.ftl" as tree>
<#import "../../control/iconSelect.ftl" as icon>
<html>
<head>
    <title>菜单管理</title>
    <JavaScripts>
        <script type="text/javascript">
            var validateForm;

            function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
                if (validateForm.form()) {
                    $("#inputForm").submit();
                    return true;
                }

                return false;
            }

            $(document).ready(function () {
                $("#name").focus();
                validateForm = $("#inputForm").validate({
                    submitHandler: function (form) {
                        loading('正在提交，请稍等...');
                        form.submit();
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
<form id="inputForm" action="${admin}/menus/save" method="post" class="form-horizontal">
    <input type="hidden" value="${menu.id}" name="id">
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">上级菜单:</label></td>
            <td class="width-35">
                <@tree.select id="menu" name="parent.id" value="${menu.parent.id}"
                labelName="parent.name" labelValue="${menu.parent.name}"
                title="归属公司" url="${admin}/menus/treeData" extId="${menu.id}"
                cssClass="form-control required" />
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font> 名称:</label></td>
            <td class="width-35">
                <input name="name" class="form-control required" type="text" value="${menu.name}"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">链接:</label></td>
            <td class="width-35">
                <input name="href" class="form-control" type="text" value="${menu.href}" maxlength="2000"/>
                <span class="help-inline">点击菜单跳转的页面</span></td>
            <td class="width-15 active"><label class="pull-right">目标:</label></td>
            <td class="width-35">
                <input name="target" class="form-control" type="text" value="${menu.target}"/>
                <span class="help-inline">链接打开的目标窗口，默认：mainFrame</span></td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图标:</label></td>
            <td class="width-35">
                <@icon.select id="icon" name="icon" value="${menu.icon}"/>
            </td>
            <td class="width-15 active"><label class="pull-right">排序:</label></td>
            <td class="width-35">
                <input name="sort" class="form-control digits" type="text" value="${menu.sort}"/>
                <span class="help-inline">排列顺序，升序。</span></td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">可见:</label></td>
            <td class="width-35">
                <#list dictList("show_hide") as d>
                    <label class="radio-inline">
                        <input type="radio" name="display"
                               value="${d.value}" <#if d.value== menu.display> checked </#if>
                               class="required i-checks"/>${d.label}
                    </label>
                </#list>
                <span class="help-inline">该菜单或操作是否显示到系统菜单中</span></td>
            <td class="width-15 active"><label class="pull-right">权限标识:</label></td>
            <td class="width-35">
                <input name="permission" class="form-control required" type="text" value="${menu.permission}"/>
                <span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span></td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">备注:</label></td>
            <td class="width-35">
                <input name="remarks" class="form-control" type="text" value="${menu.remarks}"/>
            </td>
            <td class="width-15 active"><label class="pull-right"></label></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>