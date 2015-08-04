<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head><c:import url="/WEB-INF/views/common/title.html"></c:import></head>
<body>
<c:import url="/WEB-INF/views/common/header.html"></c:import>

<h1>${exception}</h1>
<c:import url="/WEB-INF/views/common/footer.html"></c:import>

<script src="/plugins/require.js/2.1.17/require.min.js" data-main="/js/index"></script>
</body>
</html>
