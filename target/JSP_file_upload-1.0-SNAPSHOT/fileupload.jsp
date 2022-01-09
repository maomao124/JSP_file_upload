<%--
  Created by IntelliJ IDEA.
  Project name(项目名称)：JSP_file_upload
  Author(作者）: mao
  Author QQ：1296193245
  GitHub：https://github.com/maomao124/
  Date(创建日期)： 2022/1/9
  Time(创建时间)： 20:35
  Description(描述)： 无
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>上传文件</h3>
<form action="FileuploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="file" size="50" /> <br /> <br />
    <input type="submit" value="上传" />
</form>
</body>
</html>
