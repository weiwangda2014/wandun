[#escape x as x?html]
    <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
    <input type="hidden" id="searchProperty" name="searchProperty" value="${page.searchProperty}"/>
    <input type="hidden" id="orderProperty" name="orderProperty" value="${page.orderProperty}"/>
    <input type="hidden" id="orderDirection" name="orderDirection" value="${page.orderDirection}"/>
    <div class="col-sm-12">
        <div class="pull-left pagination-detail">

            <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}" maxlength="9"
                   onpaste="return false;"/>

            <span class="pagination-info">显示第 ${(pageNumber-1)*page.pageSize+1} 到第
                    [#if isLast]
                        ${page.total}
                    [#else]
                        ${pageNumber*page.pageSize}
                    [/#if]
                 条记录，总共 ${message("admin.page.totalPages", totalPages)} 条记录</span>
            <span class="page-list">每页显示
                <span class="btn-group dropup">
                    <button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown">
                        <span class="page-size">10</span>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" id="pageSizeMenu">
                        <li[#if page.pageSize == 10] class="current"[/#if] val="10"><a>10</a></li>
						<li[#if page.pageSize == 20] class="current"[/#if] val="20"><a>20</a></li>
						<li[#if page.pageSize == 50] class="current"[/#if] val="50"><a>50</a></li>
						<li[#if page.pageSize == 100] class="current"[/#if] val="100"><a>100</a></li>
                    </ul>
                </span>
                条记录</span>
        </div>
        <div class="pull-right pagination-roll">
            [#if totalPages > 1]
                <ul class="pagination">
                    [#if isFirst]
                        <li class="firstPage disabled">&nbsp;<a>&nbsp;<i class="fa fa-angle-double-left"></i></a></li>
                    [#else]
                        <li>
                            <a href="javascript: $.pageSkip(${firstPageNumber});" class="firstPage">&nbsp;<i
                                        class="fa fa-angle-double-left"></i></a>
                        </li>
                    [/#if]
                    [#if hasPrevious]
                        <li>
                            <a href="javascript: $.pageSkip(${previousPageNumber});" class="previousPage"><i
                                        class="fa fa-angle-left"></i></a>
                        </li>
                    [#else]
                        <li class="previousPage disabled">&nbsp;<a><i class="fa fa-angle-left"></i></a></li>
                    [/#if]
                    [#list segment as segmentPageNumber]
                        [#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
                            <li class="pageBreak"><a>...</a></li>
                        [/#if]
                        [#if segmentPageNumber != pageNumber]
                            <li><a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a></li>
                        [#else]
                            <li class="active"><a>${segmentPageNumber}</a></li>
                        [/#if]
                        [#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
                            <li class="pageBreak"><a>...</a></li>
                        [/#if]
                    [/#list]
                    [#if hasNext]
                        <li>
                            <a href="javascript: $.pageSkip(${nextPageNumber});" class="nextPage">&nbsp;<i
                                        class="fa fa-angle-right"></i></a>
                        </li>
                    [#else]
                        <li class="nextPage disabled"><a><i class="fa fa-angle-right"></i></a></li>
                    [/#if]
                    [#if isLast]
                        <li class="lastPage disabled"><a><i class="fa fa-angle-double-right"></i></a></li>
                    [#else]
                        <li>
                            <a href="javascript: $.pageSkip(${lastPageNumber});" class="lastPage">&nbsp;<i
                                        class="fa fa-angle-double-right"></i></a>
                        </li>
                    [/#if]
                </ul>
            [/#if]
        </div>
    </div>
[/#escape]