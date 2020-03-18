<#macro button id="" url="" title="" label="" width="" height="" target="">

    <button type="button" id="editButton" class="btn btn-sm" data-toggle="tooltip" data-placement="left" title="修改">
        <i class="fa fa-file-text-o"></i> ${(label?has_content)?string(label,'修改')}</button>
    </button>
    <edit>
        <script type="text/javascript">
            $().ready(function () {
                let $editButton = $("#editButton");
                $editButton.click(function () {
                    let $this = $(this);
                    if ($this.hasClass("disabled")) {
                        return false;
                    }
                    let $checkedIds = $("#${id} input[name='ids']:enabled:checked");
                    if ($checkedIds.length == 0) {
                        top.layer.alert('请至少选择一条数据!', {icon: 0, title: '警告'});
                        return;
                    }

                    if ($checkedIds.length > 1) {
                        top.layer.alert('只能选择一条数据!', {icon: 0, title: '警告'});
                        return;
                    }
                    var id = $("#${id} tbody tr td input.i-checks:checkbox:checked").val();
                    openDialog("修改" + '${title}', "${url}?id=" + id, "${(width?has_content)?string(width,'800px')}", "${(height?has_content)?string(width'500px')}", "${target}");
                    return false;
                });
            });
        </script>
    </edit>
</#macro>