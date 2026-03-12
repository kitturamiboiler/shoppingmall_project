<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <title>NHN Academy Shopping Mall</title>
    <style>
        .search-input { border: 2px solid #0074e9 !important; }
        .nav-link { color: #333 !important; font-weight: 500; }
        .nav-link:hover { color: #0074e9 !important; }
        .admin-link { color: #dc3545 !important; font-weight: bold; }
        .user-id-tag { font-size: 0.9rem; color: #666; }
        .btn-success { background-color: #28a745 !important; border: none; }
    </style>
</head>
<body class="bg-light">

<div class="mainContainer">
    <header class="p-3 bg-white border-bottom shadow-sm">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-3">
                    <a href="/index.do" class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
                        <i class="bi bi-shop2 text-primary me-2" style="font-size: 2.5rem;"></i>
                        <span class="fs-4 fw-bold">NHN-SHOP</span>
                    </a>
                </div>

                <div class="col-lg-9">
                    <div class="d-flex justify-content-end mb-2">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <div class="d-flex align-items-center user-id-tag">
                                    <span class="fw-bold me-2">
                                        <i class="bi bi-person-circle text-primary"></i>
                                        <span class="text-primary">${sessionScope.user.userId}</span>님 환영합니다
                                    </span>
                                    <a class="btn btn-sm btn-outline-secondary py-0" href="/logout.do">로그아웃</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <a class="btn btn-sm btn-outline-primary me-2" href="/login.do">로그인</a>
                                    <a class="btn btn-sm btn-primary" href="/signup.do">회원가입</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex align-items-center justify-content-end gap-3">
                        <ul class="nav mb-2 justify-content-center mb-md-0">
                            <li><a href="/index.do" class="nav-link px-3">Home</a></li>

                            <c:choose>
                                <c:when test="${not empty sessionScope.user and sessionScope.user.userAuth.name() eq 'ROLE_ADMIN'}">
                                    <li><a href="/admin/dashboard.do" class="nav-link px-3 admin-link">관리자 페이지</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="/mypage/index.do" class="nav-link px-3">마이페이지</a></li>
                                </c:otherwise>
                            </c:choose>

                            <li>
                                <a href="/cart.do" class="nav-link px-3 position-relative">
                                    <i class="bi bi-cart3"></i> 장바구니
                                    <c:if test="${not empty sessionScope.cart and sessionScope.cart.totalItemCount > 0}">
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" style="font-size: 0.65rem;">
                                                ${sessionScope.cart.totalItemCount}
                                        </span>
                                    </c:if>
                                </a>
                            </li>

                            <c:if test="${not empty sessionScope.user}">
                                <li>
                                    <a href="/mypage/pointHistory.do" class="nav-link px-3 position-relative">
                                        <i class="bi bi-coin me-1"></i>
                                        <fmt:formatNumber value="${sessionScope.user.userPoint}" type="number" />P
                                    </a>
                                </li>
                            </c:if>
                        </ul>

                        <form action="/index.do" method="get" class="input-group" style="width: 320px;">
                            <input type="search" name="searchKeyword" class="form-control search-input"
                                   placeholder="상품 검색" value="${param.searchKeyword}">
                            <button class="btn btn-primary" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <main>
        <div class="py-5" style="min-height: 75vh;">
            <div class="container">
                <jsp:include page="${layout_content_holder}" />
            </div>
        </div>
    </main>

    <footer class="text-muted py-5 border-top bg-white">
        <div class="container text-center">
            <p class="mb-1">© 2026 NHN Academy Shopping Mall. Built for your success.</p>
            <p><a href="#" class="text-decoration-none text-primary">Back to top</a></p>
        </div>
    </footer>
</div>
</body>
</html>