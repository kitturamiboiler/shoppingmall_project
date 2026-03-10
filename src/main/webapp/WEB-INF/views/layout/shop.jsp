<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <title>nhn아카데미 shopping mall</title>
</head>
<body>

<div class="mainContainer">
    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

                <a href="/index.do" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <i class="bi bi-shop me-2" style="font-size: 2rem;"></i>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="/index.do" class="nav-link px-2 text-white">Home</a></li>
                    <li><a href="/mypage/index.do" class="nav-link px-2 text-white">마이페이지</a></li>
                    <li>
                        <a href="/cart.do" class="nav-link px-2 text-white position-relative">
                            장바구니
                            <c:if test="${not empty sessionScope.cart and sessionScope.cart.totalItemCount > 0}">
                                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                            ${sessionScope.cart.totalItemCount}
                                    </span>
                            </c:if>
                        </a>
                    </li>
                </ul>
                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search">
                </form>
                <div class="text-end">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <span class="me-3 fw-bold text-warning">
                                    <i class="bi bi-person-circle"></i> ${sessionScope.user.userName}님
                                </span>
                            <a class="btn btn-outline-light me-2" href="/logout.do">로그아웃</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-light me-2" href="/login.do">로그인</a>
                            <a class="btn btn-warning" href="/signup.do">회원가입</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </header>

    <main>
        <div class="album py-5 bg-light" style="min-height: 70vh;">
            <div class="container">
                <jsp:include page="${layout_content_holder}" />
            </div>
        </div>
    </main>

    <footer class="text-muted py-5 border-top">
        <div class="container text-center">
            <p class="mb-1">shoppingmall example is &copy; nhnacademy.com</p>
            <p><a href="#">Back to top</a></p>
        </div>
    </footer>

</div>

</body>
</html>