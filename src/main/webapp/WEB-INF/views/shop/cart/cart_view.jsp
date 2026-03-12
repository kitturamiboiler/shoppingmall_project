<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container mt-5 mb-5">
  <h2 class="mb-4 fw-bold text-dark"><i class="bi bi-cart3 me-2"></i>장바구니</h2>

  <c:choose>
    <c:when test="${empty cartItems}">
      <div class="card shadow-sm border-0 py-5 text-center">
        <div class="card-body">
          <p class="text-muted fs-5">장바구니가 비어 있습니다.</p>
          <a href="/product/list.do" class="btn btn-primary px-4 py-2 mt-2">쇼핑하러 가기</a>
        </div>
      </div>
    </c:when>

    <c:otherwise>
      <div class="card shadow-sm border-0 overflow-hidden">
        <div class="table-responsive">
          <table class="table table-hover align-middle mb-0">
            <thead class="table-dark">
            <tr>
              <th class="px-4 py-3" style="width: 45%">상품 정보</th>
              <th class="text-center py-3">단가</th>
              <th class="text-center py-3" style="width: 150px;">수량</th>
              <th class="text-end py-3">소계</th>
              <th class="text-center py-3">관리</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cartItems}">
              <tr>
                <td class="px-4">
                  <div class="d-flex align-items-center">
                    <img src="/resources/images/products/${item.key.id}.jpg"
                         onerror="this.onerror=null; this.src='/resources/no-image.png';"
                         class="rounded border me-3" style="width: 60px; height: 60px; object-fit: cover;">
                    <div>
                      <h6 class="mb-0 fw-bold"><c:out value="${item.key.title}"/></h6>
                      <small class="text-muted"><c:out value="${item.key.vendor}"/></small>
                    </div>
                  </div>
                </td>
                <td class="text-center"><fmt:formatNumber value="${item.key.price}" type="number"/> P</td>
                <td class="text-center">
                  <form action="/cart/update.do" method="post" class="d-flex align-items-center justify-content-center">
                    <input type="hidden" name="productId" value="${item.key.id}">
                    <input type="number" name="quantity" value="${item.value}" min="1"
                           class="form-control form-control-sm text-center me-1" style="width: 60px;">
                    <button type="submit" class="btn btn-sm btn-outline-primary">변경</button>
                  </form>
                </td>
                <td class="text-end fw-bold text-primary">
                  <fmt:formatNumber value="${item.key.price * item.value}" type="number"/> P
                </td>
                <td class="text-center">
                  <form action="/cart/delete.do" method="post">
                    <input type="hidden" name="productId" value="${item.key.id}">
                    <button type="submit" class="btn btn-sm btn-outline-danger border-0" onclick="return confirm('삭제하시겠습니까?');">
                      <i class="bi bi-trash"></i> 삭제
                    </button>
                  </form>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>

        <div class="card-footer bg-white p-4 border-top">
          <div class="d-flex justify-content-between align-items-center">
            <a href="/product/list.do" class="btn btn-link text-decoration-none text-muted fw-bold">
              <i class="bi bi-arrow-left me-1"></i> 상품 페이지로 돌아가기
            </a>

            <div class="d-flex align-items-center gap-4">
              <div class="text-end">
                <span class="text-muted small d-block mb-1">최종 결제 금액</span>
                <h3 class="fw-bold text-danger mb-0">
                  <fmt:formatNumber value="${totalPrice}" type="number"/> <small>P</small>
                </h3>
              </div>
              <form action="/order/post.do" method="post">
                <button type="submit" class="btn btn-success btn-lg px-5 py-3 shadow-sm fw-bold">
                  주문하기
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</div>