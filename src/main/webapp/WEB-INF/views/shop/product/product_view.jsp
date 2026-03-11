<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 11.
  Time: 오후 2:39
  To change this template use File | Settings | File Templates.
--%>
<%-- WEB-INF/views/shop/product/product_view.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mt-5 mb-5">
  <div class="row">
    <div class="col-md-6 mb-4">
      <div class="card shadow-sm">
        <img src="${product.resolvedImageUrl}"
             onerror="this.onerror=null; this.src='/resources/no-image.png';"
             class="img-fluid rounded" alt="<c:out value='${product.title}'/>">
      </div>
    </div>

    <div class="col-md-6">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="/index.do" class="text-decoration-none">Home</a></li>
          <li class="breadcrumb-item active text-muted"><c:out value="${product.category}"/></li>
        </ol>
      </nav>
      <h1 class="fw-bold mb-3"><c:out value="${product.title}"/></h1>
      <hr>

      <div class="mb-4">
        <h3 class="text-danger fw-bold"><c:out value="${product.price}"/> P</h3>
        <p class="text-muted">남은 재고: <span class="badge bg-secondary"><c:out value="${product.quantity}"/>개</span></p>
      </div>

      <div class="mb-5" style="min-height: 150px;">
        <h5 class="fw-bold">상품 상세 정보</h5>
        <div class="text-secondary">
          <p class="mb-1"><strong>제조사/브랜드:</strong> <c:out value="${product.vendor}"/></p>
          <p class="mb-1"><strong>상품 식별자(EAN):</strong> <c:out value="${product.ean}"/></p>
          <p class="mb-1"><strong>평점:</strong> <span class="text-warning">★</span> <c:out value="${product.rating}"/></p>
        </div>
      </div>

      <div class="d-grid gap-2">
        <form action="/cart/add.do" method="post">
          <input type="hidden" name="productId" value="${product.id}">
          <button type="submit" class="btn btn-primary btn-lg w-100 py-3 shadow">
            <i class="bi bi-cart-plus"></i> 장바구니 담기
          </button>
        </form>
        <a href="javascript:history.back()" class="btn btn-outline-secondary">이전 페이지로</a>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/shop/product/recent_sidebar.jsp" />