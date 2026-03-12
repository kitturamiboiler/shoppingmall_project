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

<div class="container-fluid py-2">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="fw-bold mb-0"><i class="bi bi-box-seam me-2"></i>상품 관리</h4>
    <a href="/admin/product/register_form.do" class="btn btn-primary">
      <i class="bi bi-plus-lg me-1"></i>신규 상품 등록
    </a>
  </div>

  <div class="card border-0 shadow-sm overflow-hidden">
    <table class="table table-hover align-middle mb-0">
      <thead class="table-light">
      <tr>
        <th style="width: 80px;" class="text-center">ID</th>
        <th style="width: 100px;">이미지</th>
        <th>카테고리</th>
        <th>상품명 / 제조사</th>
        <th class="text-end">판매가</th>
        <th class="text-end">재고</th>
        <th class="text-center">등록일</th>
        <th style="width: 150px;" class="text-center">관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${products}">
        <tr>
          <td class="text-center text-muted">${item.id}</td>

          <td class="text-center">
            <img src="/resources/no-image.png" alt="product" class="rounded border" style="width: 50px; height: 50px; object-fit: cover;">
          </td>

          <td><span class="badge bg-info text-dark">${item.category}</span></td>

          <td>
            <div class="fw-bold">${item.title}</div>
            <small class="text-muted">${item.vendor} | EAN: ${item.ean}</small>
          </td>

          <td class="text-end fw-bold">
            <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₩" />
          </td>

          <td class="text-end">
            <c:choose>
              <c:when test="${item.quantity <= 10}">
                <span class="text-danger fw-bold">${item.quantity} (품절임박)</span>
              </c:when>
              <c:otherwise>${item.quantity}개</c:otherwise>
            </c:choose>
          </td>

          <td class="text-center text-muted" style="font-size: 0.85rem;">
            <fmt:parseDate value="${item.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="pDate" type="both" />
            <fmt:formatDate value="${pDate}" pattern="yyyy-MM-dd" />
          </td>

          <td class="text-center">
            <a href="/admin/product/edit.do?id=${item.id}" class="btn btn-sm btn-outline-primary">수정</a>
            <button type="button" class="btn btn-sm btn-outline-danger"
                    onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='/admin/product/delete.do?id=${item.id}'">삭제</button>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty products}">
        <tr>
          <td colspan="8" class="text-center py-5 text-muted">등록된 상품이 없습니다.</td>
        </tr>
      </c:if>
      </tbody>
    </table>
  </div>
</div>