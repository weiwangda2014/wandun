<#import "../../control/addRow.ftl" as add>
<#import "../../control/editRow.ftl" as edit>
<#import "../../control/delRow.ftl" as del>

<#import "../../control/exportExcel.ftl" as export>
<#import "../../control/importExcel.ftl" as import>
<#import "../../control/refreshRow.ftl" as refresh>
<#escape x as x?html>
    <!DOCTYPE html>
    <html>
    <head>
        <title>角色管理</title>
    </head>
    <body>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>角色列表</h5>
            </div>

            <form id="listForm" action="index" class="form-inline" method="get" autocomplete="off">
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="pull-left">
                                <div id="searchPropertyMenu" class="dropdownMenu">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200"/>
                                        <div class="input-group-btn">
                                            <button type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                Action <span class="caret"></span></button>
                                            <ul class="dropdown-menu">
                                                <li<#if page.searchProperty == "name"> class="current" </#if> val="name">
                                                    <a>${message("Role.name")}</a></li>
                                                <li<#if page.searchProperty == "enname"> class="current" </#if> val="enname">
                                                    <a>${message("Role.enname")}</a></li>
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
                                <!-- 增加按钮 -->
                                <@add.button id="listTable" url="${admin}/roles/form" title='角色' label='添加' width='100%' height='100%' target=''/>
                                <!-- 编辑按钮 -->
                                <@edit.button id="listTable" url="${admin}/roles/form" title='角色' label='编辑' width='100%' height='100%' target=''/>
                                <!-- 删除按钮 -->
                                <@del.button id="listTable" label='删除' url='${admin}/roles/edit'/>
                                <!-- 导入按钮 -->
                                <@import.button url="${admin}/roles/edit"/>
                                <!-- 导出按钮 -->
                                <@export.button url="${admin}/roles/edit"/>
                                <!-- 刷新按钮 -->
                                <@refresh.button id="datatable"/>
                            </div>
                            <div class="pull-right">

                            </div>
                            <table id="listTable" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" class="i-checks"/></th>
                                    <th><a href="javascript:;" class="sort" name="name">${message("Role.name")}</a></th>
                                    <th><a href="javascript:;" class="sort" name="enname"></a>${message("Role.enname")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="office.name"></a>${message("Role.office.name")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="dataScope"></a>${message("Role.dataScope")}
                                    </th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.content as role>
                                    <tr>
                                        <td><input type="checkbox" name="ids" value="${role.id}" class="i-checks"/></td>
                                        <td>${role.name}</td>
                                        <td>${role.enname}</td>
                                        <td>${role.office.name}</td>
                                        <td>${dictlabel(role.dataScope,'sys_data_scope','无')}</td>
                                        <td><a href="#"
                                                    onclick="openDialog('权限设置', '${admin}/roles/auth?id=${role.id}','350px', '500px')"
                                                    class="btn btn-primary btn-xs"><i class="fa fa-edit"></i> 权限设置</a>
                                        <a href="#"
                                                    onclick="openDialogView('分配用户', '${admin}/roles/assign?id=${role.id}','800px', '600px')"
                                                    class="btn  btn-warning btn-xs"><i class="glyphicon glyphicon-plus"></i>
                                                分配用户</a></td>
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

    <JavaScripts>
        <script type="text/javascript">
            /*        $(document).ready(function () {
                        $('#datatable').DataTable({
                            "processing": true,
                            "serverSide": true,
                            "ajax": {
                                url: "${admin}/roles/paginated",
                    type: "POST",
                    contentType: "application/json",
                    data: function (d) {
                        return JSON.stringify(d)
                    }
                },
                columns: [
                    {"data": "name", "name": "name", "title": "角色名称"},
                    {"data": "enname", "name": "enname", "title": "英文名称"},
                    {"data": "office.name", "name": "office.name", "title": "归属机构"},
                    {"data": "dataScope", "name": "dataScope", "title": "数据范围"}
                ],
                "language": {//自定义语言提示
                    "processing": "处理中...",
                    "lengthMenu": "显示 _MENU_ 项结果",
                    "zeroRecords": "没有找到相应的结果",
                    "info": "第 _START_ 至 _END_ 行，共 _TOTAL_ 行",
                    "infoEmpty": "第 0 至 0 项结果，共 0 项",
                    "infoFiltered": "(由 _MAX_ 项结果过滤)",
                    "infoPostFix": "",
                    "search": "搜索:",
                    "searchPlaceholder": "请输入要搜索内容...",
                    "url": "",
                    "thousands": "'",
                    "emptyTable": "表中数据为空",
                    "loadingRecords": "载入中...",
                    "infoThousands": ",",
                    "paginate": {
                        "first": "首页",
                        "previous": "上页",
                        "next": "下页",
                        "last": "末页"
                    }
                }
            });
        });*/
        </script>
    </JavaScripts>
    </body>
    </html>
</#escape>