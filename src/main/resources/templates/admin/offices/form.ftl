<#import "../../control/treeSelect.ftl" as tree>
<html>
<head>
    <title>机构管理</title>
    <JavaScripts>
        <script type="text/javascript">
            var validateForm;
            $(document).ready(function () {
                $("#name").focus();
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
            });
        </script>
    </JavaScripts>
</head>
<body class="hideScroll">
<form id="inputForm" modelAttribute="office" action="${admin}/offices/save" method="post" class="form-horizontal">
    <input name="id" type="hidden" value="${office.id}"/>
    <table class="table table-bordered table-hover">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">上级机构:</label></td>
            <td class="width-35">

                <@tree.select id="office" name="parent.id" value="${office.parent.id}"
                labelName="parent.name" labelValue="${office.parent.name}"
                title="机构" url="${admin}/offices/treeData" extId="${office.id}"
                cssClass="form-control" allowClear="${office.currentUser.admin}"/>

            </td>
            <td class="width-15" class="active"><label class="pull-right"><font color="red">*</font>归属区域:</label></td>
            <td class="width-35">
                <@tree.select id="area" name="area.id" value="${office.area.id}"
                labelName="area.name" labelValue="${office.area.name}"
                title="区域" url="${admin}/areas/treeData"
                cssClass="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
            <td class="width-35">
                <input name="name" maxlength="50" value="${office.name}" type="text"
                       class="form-control required"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">机构编码:</label></td>
            <td class="width-35">
                <input name="code" maxlength="50" value="${office.code}" type="text"
                       class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">机构类型:</label></td>
            <td class="width-35">
                <select name="type" class="form-control">
                    <#list dictList("sys_office_type") as d>
                        <option value="${d.value}"<#if d.value== office.type> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>
            </td>
            <td class="width-15" class="active"><label class="pull-right">机构级别:</label></td>
            <td class="width-35">
                <select name="grade" class="form-control">
                    <#list dictList("sys_office_grade") as d>
                        <option value="${d.value}"<#if d.value== office.grade> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">是否可用:</label></td>
            <td class="width-35">
                <select name="useable" class="form-control">
                    <#list dictList("yes_no") as d>
                        <option value="${d.value}"<#if d.value== office.useable> selected="selected" </#if>>${d.label}</option>
                    </#list>
                </select>
                <span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span></td>
            <td class="width-15" class="active"><label class="pull-right">主负责人:</label></td>
            <td class="width-35">
                <@tree.select id="primaryPerson" name="primaryPerson.id"
                value="${office.primaryPerson.id}"
                labelName="office.primaryPerson.name"
                labelValue="${office.primaryPerson.name}"
                title="用户" url="${admin}/offices/treeData?type=3" cssClass="form-control"
                allowClear="true" notAllowSelectParent="true"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">副负责人:</label></td>
            <td class="width-35">
                <@tree.select id="deputyPerson" name="deputyPerson.id"
                value="${office.deputyPerson.id}" labelName="office.deputyPerson.name"
                labelValue="${office.deputyPerson.name}"
                title="用户" url="${admin}/offices/treeData?type=3" cssClass="form-control"
                allowClear="true" notAllowSelectParent="true"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">联系地址:</label></td>
            <td class="width-35">
                <input name="address" maxlength="50" value="${office.address}" type="text"
                       class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">邮政编码:</label></td>
            <td class="width-35">
                <input name="zipCode" maxlength="50" value="${office.zipCode}" type="text"
                       class="form-control"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">负责人:</label></td>
            <td class="width-35">
                <input name="master" maxlength="50" value="${office.master}" type="text"
                       class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">电话:</label></td>
            <td class="width-35">
                <input name="phone" maxlength="50" value="${office.phone}" type="text"
                       class="form-control"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">传真:</label></td>
            <td class="width-35">
                <input name="fax" maxlength="50" value="${office.fax}" type="text"
                       class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">邮箱:</label></td>
            <td class="width-35">
                <input name="email" maxlength="50" value="${office.email}" type="text"
                       class="form-control"/>
            </td>
            <td class="width-15" class="active"><label class="pull-right">备注:</label></td>
            <td class="width-35">
                <textarea rows="3" class="form-control" name="remarks" maxlength="200">
                    ${office.remarks}
                </textarea>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>