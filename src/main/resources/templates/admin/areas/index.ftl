<#import "../../control/addRow.ftl" as add>
<#import "../../control/editRow.ftl" as edit>
<#import "../../control/delRow.ftl" as del>

<#import "../../control/exportExcel.ftl" as export>
<#import "../../control/importExcel.ftl" as import>
<#import "../../control/refreshRow.ftl" as refresh>

<!DOCTYPE html>
<html>
<head>
    <title>区域管理</title>
</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>区域列表</h5>
        </div>

        <form id="listForm" action="index" class="form-inline" method="get" autocomplete="off">
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="pull-left">
                            <div id="searchPropertyMenu" class="dropdownMenu">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="searchValue" name="searchValue"
                                           value="${page.searchValue}" maxlength="200"/>
                                    <div class="input-group-btn">
                                        <button type="button" class="btn dropdown-toggle" data-toggle="dropdown"
                                                aria-haspopup="true" aria-expanded="true">
                                            Action <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li<#if page.searchProperty == "name"> class="current" </#if>
                                                    val="name">
                                                <a>${message("Area.name")}</a></li>
                                            <li<#if page.searchProperty == "code"> class="current" </#if>
                                                    val="code">
                                                <a>${message("Area.code")}</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="pull-right">
                            <button class="btn btn-primary btn-rounded btn-outline btn-sm" type="submit">
                                <i class="fa fa-search"></i> 查询
                            </button>
                            <button class="btn btn-primary btn-rounded btn-outline btn-sm" type="reset">
                                <i class="fa fa-refresh"></i> 重置
                            </button>
                        </div>

                    </div>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-12" style="overflow: auto; width: 100%;">
                        <div class="pull-left">
                            <@shiro.hasPermission name="admin:area:add">
                                <!-- 增加按钮 -->
                                <@add.button id="listTable" url="${admin}/areas/form" title='角色' label='添加' width='100%' height='100%' target=''/>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="admin:area:edit">
                                <!-- 编辑按钮 -->
                                <@edit.button id="listTable" url="${admin}/areas/form" title='角色' label='编辑' width='100%' height='100%' target=''/>
                            </@shiro.hasPermission>

                            <@shiro.hasPermission name="admin:area:del">
                                <!-- 删除按钮 -->
                                <@del.button id="listTable" label='删除' url='${admin}/areas/edit'/>
                            </@shiro.hasPermission>

                            <!-- 刷新按钮 -->
                            <@refresh.button id="datatable"/>

                        </div>
                        <div class="pull-right">

                        </div>
                        <table id="listTable" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="selectAll" class="i-checks"/></th>
                                <th><a href="javascript:;" class="sort" name="name">${message("Area.name")}</a></th>
                                <th><a href="javascript:;" class="sort" name="code"></a>${message("Area.code")}</th>
                                <th><a href="javascript:;" class="sort" name="type"></a>${message("Area.type")}</th>
                                <th><a href="javascript:;" class="sort" name="remarks"></a>${message("Area.remarks")}
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list page.content as area>
                                <tr data-tt-id="${area.id}" ${(area.parent?has_content) ? string("data-tt-parent-id='${area.parent.id}'",'')}
                                    data-tt-branch='true'>
                                    <td><input type="checkbox" name="ids" value="${area.id}"
                                               class="i-checks"/></td>
                                    <td>${area.name}</td>
                                    <td>${area.code}</td>
                                    <td>${dictlabel(area.type,'sys_area_type','无')}</td>
                                    <td>${area.remarks}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                        <@pagination pageNumber = page.pageNumber totalPages = page.totalPages>
                            <#include "admin/include/pagination.ftl">
                        </@pagination>
                    </div>


                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/template" id="treeTableTpl">
    <tr data-tt-id="{{row.id}}" data-tt-parent-id="{{pid}}"
        data-tt-branch={{#row.hasChildren}}'true'{{/row.hasChildren}} {{^row.hasChildren}}'false'{{/row.hasChildren}}>
    <td><input type="checkbox" id="{{row.id}}" name="ids" class="i-checks"></td>
    <td>{{row.name}}</td>
    <td>{{row.code}}</td>
    <td>{{dict.type}}</td>
    <td>{{row.remarks}}</td>
    </tr>
</script>
<Styles>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-treetable@3.2.0-1/css/jquery.treetable.css">
    <link rel="stylesheet" href="${ctx}/static/js/plugins/treetable/css/jquery.treetable.theme.default.css"/>
</Styles>
<JavaScripts>
    <script src="https://cdn.jsdelivr.net/npm/jquery-treetable@3.2.0-1/jquery.treetable.min.js"></script>
    <script type="text/javascript">
        var table = $("#listTable");
        $("#listTable tbody").on("mousedown", "tr", function () {
            $(".selected").not(this).removeClass("selected");
            $(this).toggleClass("selected");
        });
        table.treetable({
            expandable: true,
            onNodeInitialized: nodeInitialized,
            onInitialized: initialized,
            onNodeExpand: nodeExpand,
            onNodeCollapse: nodeCollapse
        })

        function nodeInitialized() {
            $('input[type="checkbox"].i-checks, input[type="radio"].i-checks').iCheck({
                handle: 'checkbox',
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
        }

        function initialized() {
            $('input[type="checkbox"].i-checks, input[type="radio"].i-checks').iCheck({
                handle: 'checkbox',
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
        }

        function nodeExpand() {
            getNodeViaAjax(this.id);
        }

        function nodeCollapse() {
            var node = this;
            table.treetable("unloadBranch", node);
        }

        function getNodeViaAjax(parentNodeID) {
            $.ajax({
                type: 'POST',
                url: '${admin}/areas/data/',
                data: {
                    id: parentNodeID
                },
                success: function (data) {
                    var children = data;
                    if (children) {
                        var parentNode = $("#listTable").treetable("node", parentNodeID);
                        for (var i = 0; i < children.length; i++) {
                            var node = children[i];
                            var nodeToAdd = $("#listTable").treetable("node", node.id);

                            if (!nodeToAdd) {
                                var tpl = $("#treeTableTpl").html();
                                var row = Mustache.render(tpl, {
                                    dict: {
                                        type: getDictLabel(${dictJson('sys_area_type')}, node.type)
                                    }, pid: parentNodeID, row: node
                                })
                                $("#listTable").treetable("loadBranch", parentNode, row);
                            }
                        }
                    }
                },
                error: function (error) {
                },
                dataType: 'json'
            });
        }

        function addRow(list, tpl, data, pid, root) {
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                if (row.parentId == pid) {
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            type: getDictLabel(${dictJson('sys_area_type')}, node.type)
                        }, pid: (root ? 0 : pid), row: row
                    }));

                    addRow(list, tpl, data, row.id);
                }
            }
        }
    </script>
</JavaScripts>
</body>
</html>
