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
        <title>菜单管理</title>
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
                                        <input type="text" class="form-control" id="searchValue" name="searchValue"
                                               value="${page.searchValue}" maxlength="200"/>
                                        <div class="input-group-btn">
                                            <button type="button" class="btn dropdown-toggle" data-toggle="dropdown"
                                                    aria-haspopup="true" aria-expanded="true">
                                                Action <span class="caret"></span></button>
                                            <ul class="dropdown-menu">
                                                <li<#if page.searchProperty == "name"> class="current" </#if>
                                                        val="name">
                                                    <a>${message("Menu.name")}</a>
                                                </li>
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
                                <@add.button id="listTable" url="${admin}/offices/form" title='菜单' label='添加' width='100%' height='100%' target=''/>
                                <!-- 编辑按钮 -->
                                <@edit.button id="listTable" url="${admin}/offices/form" title='菜单' label='编辑' width='100%' height='100%' target=''/>
                                <!-- 删除按钮 -->
                                <@del.button id="listTable" label='删除' url='${admin}/offices/edit'/>
                                <!-- 导入按钮 -->
                                <@import.button url="${admin}/offices/edit"/>
                                <!-- 导出按钮 -->
                                <@export.button url="${admin}/offices/edit"/>
                                <!-- 刷新按钮 -->
                                <@refresh.button id="datatable"/>
                            </div>
                            <div class="pull-right">

                            </div>
                            <table id="listTable" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" class="i-checks"/></th>
                                    <th><a href="javascript:;" class="sort" name="name">${message("Office.name")}</a>
                                    </th>
                                    <th><a href="javascript:;" class="sort"
                                           name="area.name">${message("Office.area.name")}</a></th>
                                    <th><a href="javascript:;" class="sort" name="code">${message("Office.code")}</a>
                                    </th>
                                    <th><a href="javascript:;" class="sort" name="type">${message("Office.type")}</a>
                                    </th>
                                    <th><a href="javascript:;" class="sort"
                                           name="remarks">${message("Office.remarks")}</a></th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.content as office>
                                    <#macro treetable item>
                                        <#if item?has_content>
                                            <tr data-tt-id="${item.id}" ${(item.parent?has_content) ? string("data-tt-parent-id='${item.parent.id}'",'')}>
                                                <td><input type="checkbox" name="ids" value="${item.id}"
                                                           class="i-checks"/></td>
                                                <td>
                                                    ${item.name}
                                                </td>
                                                <td>${item.area.name}</td>
                                                <td>
                                                    ${item.code}
                                                </td>
                                                <td>${dictlabel(item.type,'sys_office_type','无')} </td>
                                                <td>${item.remarks}</td>
                                            </tr>
                                            <#if item.children?exists>
                                                <#list item.children as child>
                                                    <@treetable child />
                                                </#list>
                                            </#if>
                                        </#if>
                                    </#macro>
                                    <@treetable office />
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

    <Styles>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-treetable@3.2.0-1/css/jquery.treetable.css">
        <link rel="stylesheet" href="${ctx}/static/js/plugins/treetable/css/jquery.treetable.theme.default.css"/>
    </Styles>
    <JavaScripts>
        <script src="https://cdn.jsdelivr.net/npm/jquery-treetable@3.2.0-1/jquery.treetable.min.js"></script>
        <script type="text/javascript">
            $("#listTable").treetable({expandable: true});
        </script>
    </JavaScripts>
    </body>
    </html>
</#escape>