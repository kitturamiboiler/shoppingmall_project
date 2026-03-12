<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <title>관리자 센터 - NHN SHOP</title>
  <style>
    body { overflow-x: hidden; }
    .sidebar {
      width: 280px;
      min-height: 100vh;
      background-color: #212529;
      transition: all 0.3s;
    }
    .sidebar .nav-link {
      color: rgba(255,255,255,.75);
      padding: 1rem 1.5rem;
      border-radius: 0;
    }
    .sidebar .nav-link:hover {
      color: #fff;
      background-color: rgba(255,255,255,.1);
    }
    .sidebar .nav-link.active {
      color: #fff;
      background-color: #0d6efd;
    }
    .content-area {
      flex-grow: 1;
      background-color: #f8f9fa;
      min-height: 100vh;
    }
    .sidebar-heading {
      padding: 1.5rem;
      font-size: 1.2rem;
      color: #fff;
      font-weight: bold;
    }
  </style>
</head>
<body>

<div class="d-flex">
  <nav class="sidebar flex-shrink-0 shadow">
    <div class="sidebar-heading border-bottom border-secondary mb-2">
      <i class="bi bi-shield-lock me-2"></i>관리자 페이지
    </div>

    <ul class="nav flex-column mb-auto">
      <li class="nav-item">
        <a href="/admin/dashboard.do" class="nav-link ${pageContext.request.requestURI.contains('dashboard') ? 'active' : ''}">
          <i class="bi bi-speedometer2 me-2"></i> 대시보드
        </a>
      </li>
      <li class="nav-item">
        <a href="/admin/user/list.do" class="nav-link">
          <i class="bi bi-people me-2"></i> 회원 관리
        </a>
      </li>
      <li class="nav-item">
        <a href="/admin/category/list.do" class="nav-link">
          <i class="bi bi-box-seam me-2"></i> 상품 관리(카테고리)
        </a>
      </li>
      <li class="nav-item">
        <a href="/admin/order/manager.do" class="nav-link">
          <i class="bi bi-cart-check me-2"></i> 주문 관리
        </a>
      </li>
    </ul>

    <hr class="text-secondary mx-3">

    <ul class="nav flex-column mb-4">
      <li class="nav-item">
        <a href="/product/list.do" class="nav-link text-info">
          <i class="bi bi-eye me-2"></i> 상품 페이지 보기
        </a>
      </li>
      <li class="nav-item">
        <a href="/logout.do" class="nav-link text-danger">
          <i class="bi bi-box-arrow-right me-2"></i> 로그아웃
        </a>
      </li>
    </ul>
  </nav>

  <main class="content-area">
    <header class="py-3 px-4 bg-white border-bottom shadow-sm d-flex justify-content-between align-items-center">
      <span class="text-muted"><strong>${sessionScope.user.userName}</strong> (Admin)</span>
      <a href="/index.do" class="btn btn-sm btn-outline-primary">홈으로 이동</a>
    </header>

    <div class="p-4">
      <jsp:include page="${layout_content_holder}" />
    </div>
  </main>
</div>
</body>
</html>

