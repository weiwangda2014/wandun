<#import "../../control/addRow.ftl" as add>
<#import "../../control/editRow.ftl" as edit>
<#import "../../control/delRow.ftl" as del>
<#import "../../control/clearRow.ftl" as clear>
<#import "../../control/exportExcel.ftl" as export>
<#import "../../control/importExcel.ftl" as import>
<#import "../../control/refreshRow.ftl" as refresh>
<#escape x as x?html>
    <!DOCTYPE html>
    <html>
    <head>
        <title>日志管理</title>
    </head>
    <body>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>日志列表</h5>
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
                                <!-- 删除按钮 -->
                                <@del.button id="listTable" label='删除' url='${admin}/logs/edit'/>
                                <!-- 刷新按钮 -->
                                <@clear.button label="清空"/>
                                <!-- 刷新按钮 -->
                                <@refresh.button id="datatable"/>
                            </div>
                            <div class="pull-right">

                            </div>
                            <table id="listTable" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAll" class="i-checks"/></th>
                                    <th><a href="javascript:;" class="sort" name="title">${message("Log.title")}</a>
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="type"></a>${message("Log.type")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort"
                                           name="requestUri"></a>${message("Log.requestUri")}
                                    </th>
                                    <th>
                                        <a href="javascript:;" class="sort" name="method"></a>${message("Log.method")}
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.content as log>
                                    <tr>
                                        <td><input type="checkbox" name="ids" value="${log.id}" class="i-checks"/></td>
                                        <td>${log.title}</td>
                                        <td>${log.type}</td>
                                        <td>${log.requestUri}</td>
                                        <td>${log.method}</td>
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
            $().ready(function () {
                var $clearButton = $("#clearButton");
                var $resultRow = $("#listTable tr:gt(0)");
                $clearButton.click(function () {
                    top.layer.confirm('${message("admin.dialog.clearConfirm")}', {
                        icon: 3,
                        title: '系统提示'
                    }, function (index) {
                        var layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
                        $.ajax({
                            url: "clear",
                            type: "POST",
                            dataType: "json",
                            cache: false,
                            success: function (message) {
                                $.message(message);
                                if (message.type == "success") {
                                    top.layer.alert("删除成功", {icon: 0, title: '警告'});
                                    setTimeout(function () {
                                        location.reload(true);
                                    }, 3000);
                                }
                                top.layer.close(layerIndex);
                            }
                        });
                    });
                    return false;
                });
            });
        </script>
    </JavaScripts>
    </body>
    </html>
</#escape>