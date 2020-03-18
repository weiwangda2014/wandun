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

            <form id="listForm" action="${admin}/users/list" class="form-inline" method="get" autocomplete="off"
                  target="_self">
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
                                <@shiro.hasPermission name="admin:user:add">
                                    <!-- 增加按钮 -->
                                    <@add.button id="listTable" url="${admin}/users/form" title='用户' label='添加' width='100%' height='100%' target=''/>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="admin:user:edit">
                                    <!-- 编辑按钮 -->
                                    <@edit.button id="listTable" url="${admin}/users/form" title='用户' label='编辑' width='100%' height='100%' target=''/>
                                </@shiro.hasPermission>

                                <@shiro.hasPermission name="admin:user:del">
                                    <!-- 删除按钮 -->
                                    <@del.button id="listTable" label='删除' url=""/>
                                </@shiro.hasPermission>

                                <@shiro.hasPermission name="admin:user:import">
                                    <!-- 导入按钮 -->
                                    <@import.button url="${admin}/users/import"/>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="admin:user:export">
                                    <!-- 导出按钮 -->
                                    <@export.button url="${admin}/users/export"/>
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
                                    <th><a href="javascript:;" class="sort" name="loginName">${message("User.loginName")}</a></th>
                                    <th><a href="javascript:;" class="sort" name="name"></a>${message("User.name")}</th>
                                    <th><a href="javascript:;" class="sort" name="phone"></a>${message("User.phone")}</th>
                                    <th><a href="javascript:;" class="sort" name="mobile"></a>${message("User.mobile")}</th>
                                    <th><a href="javascript:;" class="sort" name="company.name"></a>${message("User.company.name")}</th>
                                    <th><a href="javascript:;" class="sort" name="office.name"></a>${message("User.office.name")}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.content as user>
                                    <tr>
                                        <td><input type="checkbox" name="ids" value="${user.id}" class="i-checks"/></td>
                                        <td>${user.loginName}</td>
                                        <td>${user.name}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.mobile}</td>
                                        <td>${user.company.name}</td>
                                        <td>${user.office.name}</td>
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

    </JavaScripts>
    </body>
    </html>
</#escape>