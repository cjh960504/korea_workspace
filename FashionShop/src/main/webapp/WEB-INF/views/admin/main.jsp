<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="./inc/header.jsp" %>
</head>
<body>

<!-- 메인 네비게이션 영역 : jsp는 이제 보안된 폴더에 있으므로, 웹사이트 루트를 기준으로한 경로는 막혀있다. 따라서 상대 경로로 접근하자!!!-->
<!-- /(root)를 표시하면 안됨. WEB-INF에 존재하는 파일이므로 -->
<%@ include file="./inc/main_navi.jsp" %>

<h3>Dropdown Menu inside a Navigation Bar</h3>
<p>Hover over the "Dropdown" link to see the dropdown menu.</p>

</body>
</html>