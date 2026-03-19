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
    <a href="/admin/product/register_form.do" class="btn btn-primary shadow-sm">
      <i class="bi bi-plus-lg me-1"></i>신규 상품 등록
    </a>
  </div>

  <div class="card border-0 shadow-sm overflow-hidden">
    <table class="table table-hover align-middle mb-0">
      <thead class="table-light">
      <tr>
        <th style="width: 80px;" class="text-center">ID</th>
        <th style="width: 100px;" class="text-center">이미지</th>
        <th style="width: 120px;">카테고리</th>
        <th>상품명 / 제조사</th>
        <th style="width: 140px;" class="text-end">판매가</th>
        <th style="width: 100px;" class="text-end">재고</th>
        <th style="width: 120px;" class="text-center">등록일</th>
        <th style="width: 160px;" class="text-center">관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${productPage.content}">
        <tr>
          <td class="text-center text-muted">${item.id}</td>

          <td class="text-center">
            <div style="width: 50px; height: 50px; overflow: hidden; border-radius: 6px; margin: 0 auto; border: 1px solid #eee;">
              <img src="${item.resolvedImageUrl}"
                   alt="product"
                   style="width: 100%; height: 100%; object-fit: cover;"
                   onerror="this.src='/resources/no-image.png'">
            </div>
          </td>

          <td>
            <span class="badge bg-info text-dark bg-opacity-25 border border-info border-opacity-25 px-2 py-1">
                ${item.categoryName}
            </span>
          </td>

          <td>
            <div class="fw-bold text-dark">${item.title}</div>
            <small class="text-muted">${item.vendor} | EAN: ${item.ean}</small>
          </td>

          <td class="text-end fw-bold text-primary">
            <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₩" />
          </td>

          <td class="text-end">
            <c:choose>
              <c:when test="${item.quantity <= 10}">
                <span class="text-danger fw-bold">${item.quantity} <small>(품절임박)</small></span>
              </c:when>
              <c:otherwise>${item.quantity}개</c:otherwise>
            </c:choose>
          </td>

          <td class="text-center text-muted small">
              ${item.createdAt.toString().substring(0, 10)}
          </td>

          <td class="text-center">
            <div class="btn-group" role="group">
              <a href="/admin/product/edit.do?id=${item.id}" class="btn btn-sm btn-outline-primary">수정</a>

              <form action="/admin/product/delete.do" method="post" class="d-inline ms-1">
                <input type="hidden" name="id" value="${item.id}">
                <button type="submit" class="btn btn-sm btn-outline-danger"
                        onclick="return confirm('진짜로 삭제하시겠습니까? 관련 이미지 파일도 함께 삭제됩니다.');">삭제</button>
              </form>
            </div>
          </td>
        </tr>
      </c:forEach>

      <c:if test="${empty productPage.content}">
        <tr>
          <td colspan="8" class="text-center py-5 text-muted">
            <i class="bi bi-inbox fs-1 d-block mb-2"></i>
            등록된 상품이 없습니다.
          </td>
        </tr>
      </c:if>
      </tbody>
    </table>
  </div>
  <c:if test="${totalPages > 0}">
    <nav aria-label="Page navigation" class="mt-5">
      <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${totalPages}" var="i">
          <li class="page-item ${currentPage == i ? 'active' : ''}">
            <a class="page-link" href="/admin/product/list.do?page=${i}">${i}</a>
          </li>
        </c:forEach>
      </ul>
    </nav>
  </c:if>
</div>