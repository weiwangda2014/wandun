<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property='title'/></title>
    <%@include file="head.jsp" %>
    <sitemesh:write property='head'/>
    <sitemesh:write property="Styles"/>
    <sitemesh:write property="page"/>
</head>
<body>
<sitemesh:write property='body'/>
<%@include file="foot.jsp" %>

<sitemesh:write property="JavaScripts"/>
<sitemesh:write property="addRow"/>
<sitemesh:write property="addRowBatch"/>
<sitemesh:write property="addRowPrint"/>
<sitemesh:write property="batchAddRow"/>
<sitemesh:write property="batchUpdateRow"/>
<sitemesh:write property="delRow"/>
<sitemesh:write property="editRow"/>
<sitemesh:write property="exportExcel"/>
<sitemesh:write property="importExcel"/>
<sitemesh:write property="printRow"/>
<sitemesh:write property="sortColumn"/>
<sitemesh:write property="treeSelect"/>
<sitemesh:write property="ckeditor"/>
<sitemesh:write property="ckfinder"/>
<sitemesh:write property="gridSelect"/>
<sitemesh:write property="iconSelect"/>
</body>
</html>