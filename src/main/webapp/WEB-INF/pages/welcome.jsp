<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h1 align="center">Sita : Spring integration test project  </h1>

<h2 align="center">Please place file in input directory: ${inputDir} to process them</h2>
<table border="5" align="center">
<thead >
<tr>
  <td><b>Input Dir</b></td>
<td><b>Files processed in last 30 mins</b></td>
<td><b>Files errored in last 30 mins</b></td>
</tr>


</thead>
<tr>
<td>${inputDir}</td>
<td>${processedFiles}</td>
<td>${erroredFiles}</td>
</tr>

</table>
</body>
</html>