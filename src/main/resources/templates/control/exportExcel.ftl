<#macro button url>

    <button  type="button"  id="btnExport" class="btn btn-sm " data-toggle="tooltip" data-placement="left" title="导出">
        <i class="fa fa-file-excel-o"></i> 导出
    </button>
    <export>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#btnExport").click(function () {
                    top.layer.confirm('确认要导出Excel吗?', {icon: 3, title: '系统提示'}, function (index) {
                        alert("导出")
                    });
                });

            });
        </script>
    </export>
</#macro>