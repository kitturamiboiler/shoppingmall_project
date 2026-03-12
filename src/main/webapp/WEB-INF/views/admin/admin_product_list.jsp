<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 1:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<style>
  .product-img {
    width: 50px; height: 50px;
    object-fit: cover;
    border-radius: 4px;
    border: 1px solid #eee;
  }
  .table thead th {
    background-color: #f9fafb;
    font-weight: 600;
    font-size: 0.85rem;
    color: #4b5563;
    border-top: none;
  }
  .price-text { color: #e11d48; font-weight: 700; }
  .btn-action { padding: 0.25rem 0.5rem; font-size: 0.8rem; }
</style>

<div class="container-fluid py-2">
  <div class="d-flex justify-content-between align-items-end mb-4">
    <div>
      <h4 class="fw-bold mb-1">상품 목록</h4>
      <p class="text-muted small mb-0">총 <span class="text-primary fw-bold">${products.size()}</span>개의 상품이 등록되어 있습니다.</p>
    </div>
    <a href="/admin/product/register_form.do" class="btn btn-dark shadow-sm">
      <i class="bi bi-plus-lg me-1"></i> 상품 등록
    </a>
  </div>

  <div class="card border-0 shadow-sm overflow-hidden">
    <table class="table table-hover align-middle mb-0">
      <thead>
      <tr>
        <th class="text-center">ID</th>
        <th>이미지</th>
        <th>상품 정보</th>
        <th>카테고리</th>
        <th class="text-end">가격(P)</th>
        <th class="text-center">재고</th>
        <th class="text-center">관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="p" items="${products}">
        <tr>
          <td class="text-center text-muted small">${p.id}</td>
          <td>
            <img src="/resources/images/products/${p.id}.jpg"
                 onerror="this.src='https://placehold.co/50x50?text=No+Img';" class="product-img">
          </td>
          <td>
            <div class="fw-bold text-dark">${p.title}</div>
            <div class="text-muted small" style="font-size: 0.75rem;">${p.vendor} | EAN: ${p.ean}</div>
          </td>
          <td><span class="badge rounded-pill bg-light text-dark border">${p.category}</span></td>
          <td class="text-end price-text"><fmt:formatNumber value="${p.price}" type="number"/> P</td>
          <td class="text-center">${p.quantity}</td>
          <td class="text-center">
            <a href="/admin/product/edit_form.do?id=${p.id}" class="btn btn-sm btn-outline-secondary btn-action">수정</a>
            <form action="/admin/product/delete.do" method="post" class="d-inline">
              <input type="hidden" name="id" value="${p.id}">
              <button type="submit" class="btn btn-sm btn-outline-danger btn-action"
                      onclick="return confirm('삭제 후 복구가 불가능합니다. 삭제하시겠습니까?');">삭제</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>