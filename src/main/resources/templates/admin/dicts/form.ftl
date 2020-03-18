<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>字典管理</title>
</head>
<body>
<form id="inputForm" action="${admin}/dicts/save" method="post" class="form-horizontal">
    <input type="hidden" value="${dict.id}" name="id">
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">键值:</label></td>
            <td class="width-35">
                <input name="value" class="form-control required" type="text" value="${dict.value}" maxlength="50"/>
            </td>
            <td class="width-15 active"><label class="pull-right">标签:</label></td>
            <td class="width-35">
                <input name="label" class="form-control required" type="text" value="${dict.label}" maxlength="50"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">类型:</label></td>
            <td class="width-35">
                <input name="type" class="form-control required" type="text" value="${dict.type}" maxlength="50"/>
            </td>
            <td class="width-15 active"><label class="pull-right">描述:</label></td>
            <td class="width-35">
                <input name="description" class="form-control required" type="text" value="${dict.description}" maxlength="50"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序:</label></td>
            <td class="width-35">
                <input name="sort" class="form-control required digits" type="text" value="${dict.sort}" maxlength="50"/>
            </td>
            <td class="width-15 active"><label class="pull-right">备注:</label></td>
            <td class="width-35">
                <textarea name="remarks" rows="3" class="form-control" maxlength="200">${dict.remarks}</textarea>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>