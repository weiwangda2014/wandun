<#macro button url>

    <button  type="button"  id="btnImport" class="btn btn-sm " data-toggle="tooltip" data-placement="left" title="导入">
        <i class="fa fa-folder-open-o"></i> 导入
    </button>
    <div id="importBox" class="hide">
        <form id="importForm" action="${url}" method="post" enctype="multipart/form-data">
            <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
        </form>
    </div>
    <import>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#btnImport").click(function () {
                    top.layer.open({
                        type: 1,
                        area: [500, 300],
                        title: "导入数据",
                        content: $("#importBox").html(),
                        btn: ['下载模板', '确定', '关闭'],
                        btn1: function (index, layero) {
                            window.location.href = '${url}/template';
                        },
                        btn2: function (index, layero) {
                            var inputForm = top.$("#importForm");
                            var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
                            inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
                            top.$("#importForm").submit();
                            top.layer.close(index);
                        },
                        btn3: function (index) {
                            top.layer.close(index);
                        }
                    });
                });
            });
        </script>
    </import>
</#macro>