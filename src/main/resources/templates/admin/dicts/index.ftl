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
        <title>字典管理</title>
    </head>
    <body>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>字典列表</h5>
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
                                                    <a>${message("Role.name")}</a></li>
                                                <li<#if page.searchProperty == "enname"> class="current" </#if>
                                                        val="enname">
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
                                <@add.button id="listTable" url="${admin}/dicts/form" title='标签' label='添加' width='100%' height='100%' target=''/>
                                <!-- 编辑按钮 -->
                                <@edit.button id="listTable" url="${admin}/dicts/form" title='标签' label='编辑' width='100%' height='100%' target=''/>
                                <!-- 删除按钮 -->
                                <@del.button id="listTable" label='删除' url='${admin}/dicts/edit'/>
                                <!-- 导入按钮 -->
                                <@import.button url="${admin}/dicts/edit"/>
                                <!-- 导出按钮 -->
                                <@export.button url="${admin}/dicts/edit"/>
                                <!-- 刷新按钮 -->
                                <@refresh.button id="datatable"/>
                            </div>
                            <div class="pull-right">

                            </div>
                            <table id="listTable" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" class="i-checks"/></th>
                                    <th><a href="javascript:;" class="sort" name="label">${message("Dict.label")}</a>
                                    </th>

                                    <th>
                                        <a href="javascript:;" class="sort" name="value"></a>${message("Dict.value")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="sort"></a>${message("Dict.sort")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="type"></a>${message("Dict.type")}
                                    </th>

                                    <th>
                                        <a href="javascript:;" class="sort"
                                           name="description"></a>${message("Dict.description")}
                                    </th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.content as dict>
                                    <#macro treemenu item>
                                        <#if item?has_content>
                                            <tr data-tt-id="${item.id}" ${(item.parentId?has_content) ? string("data-tt-parent-id='${item.parentId}'",'')}>
                                                <td><input type="checkbox" name="ids" value="${item.id}"
                                                           class="i-checks"/>
                                                </td>
                                                <td>
                                                    <i class="icon-${item.icon?has_content ? string(item.icon,'hide')}"></i>
                                                    ${item.label}
                                                </td>
                                                <td>${item.value}</td>
                                                <td>
                                                    <input type="hidden" name="ids" value="${item.id}"/>
                                                    <input name="sorts" type="text" value="${item.sort}"
                                                           class="form-control">
                                                    ${item.sort}
                                                </td>
                                                <td>${item.type}</td>
                                                <td>${item.description}</td>
                                                <td><a href="#"
                                                       onclick="openDialog('添加键值', '${admin}/dicts/form?type=${item.type}&sort=${item.sort+10}','800px', '500px')"
                                                       class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>
                                                        添加键值</a></td>
                                            </tr>
                                            <#if item.children?exists>
                                                <#list item.children as child>
                                                    <@treemenu child />
                                                </#list>
                                            </#if>
                                        </#if>
                                    </#macro>
                                    <@treemenu dict />
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