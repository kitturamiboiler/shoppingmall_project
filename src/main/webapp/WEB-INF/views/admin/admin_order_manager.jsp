<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 4:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container-fluid py-2">
  <h4 class="fw-bold mb-4"><i class="bi bi-cart-check me-2"></i>주문 관리</h4>

  <div class="row g-3 mb-4">
    <div class="col-md-3">
      <div class="card border-0 shadow-sm bg-primary text-white">
        <div class="card-body">
          <h6 class="card-title opacity-75">전체 주문</h6>
          <h3 class="fw-bold mb-0">${orderPage.totalCount}건</h3>
        </div>
      </div>
    </div>
  </div>

  <div class="card border-0 shadow-sm overflow-hidden">
    <table class="table table-hover align-middle mb-0">
      <thead class="table-light">
      <tr>
        <th style="width: 100px;" class="text-center">주문번호</th>
        <th>주문일시</th>
        <th>주문자 ID</th>
        <th>상품명</th>
        <th class="text-end">수량</th>
        <th class="text-end">결제금액</th>
        <th style="width: 120px;" class="text-center">상태</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="order" items="${orderPage.content}">
        <tr>
          <td class="text-center fw-bold text-primary">${order.id}</td>
          <td class="text-muted">
            <fmt:parseDate value="${order.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" />
          </td>
          <td><span class="badge bg-light text-dark border">${order.userId}</span></td>
          <td><span class="fw-semibold">${order.productId}</span></td>
          <td class="text-end">${order.quantity}개</td>
          <td class="text-end fw-bold text-danger">
            <fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₩" />
          </td>
          <td class="text-center">
            <span class="badge bg-success">결제완료</span>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty orderPage.content}">
        <tr>
          <td colspan="7" class="text-center py-5 text-muted">
            <i class="bi bi-inbox fs-1 d-block mb-3"></i>
            최근 주문 내역이 없습니다.
          </td>
        </tr>
      </c:if>
      </tbody>
    </table>
  </div>

  <div class="pagination">
    <c:if test="${currentPage > 1}">
      <a href="?page=${currentPage - 1}" class="prev">이전</a>
    </c:if>

    <c:forEach var="i" begin="1" end="${totalPages}">
      <c:choose>
        <c:when test="${i == currentPage}">
          <span class="page-num active">${i}</span>
        </c:when>
        <c:otherwise>
          <a href="?page=${i}" class="page-num">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
      <a href="?page=${currentPage + 1}" class="next">다음</a>
    </c:if>
  </div>
</div>

<style>
  .pagination { margin-top: 30px; text-align: center; }
  .page-num { display: inline-block; padding: 5px 12px; margin: 0 3px; border: 1px solid #ddd; color: #333; text-decoration: none; }
  .page-num.active { background: #333; color: #fff; border-color: #333; }
  .prev, .next { display: inline-block; padding: 5px 12px; border: 1px solid #ddd; color: #333; text-decoration: none; background: #f4f4f4; }
</style>