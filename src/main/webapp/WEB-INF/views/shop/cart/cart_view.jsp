<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 10.
  Time: 오전 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5 mb-5">
  <h2 class="mb-4 fw-bold text-dark"><i class="bi bi-cart3"></i> 장바구니</h2>

  <c:choose>
    <%-- 장바구니가 비어있는 경우 --%>
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
                <td class="text-center text-secondary"><c:out value="${item.key.price}"/> P</td>
                <td class="text-center">
                    <%-- 수량 업데이트 --%>
                  <form action="/cart/update.do" method="post" class="d-flex align-items-center justify-content-center">
                    <input type="hidden" name="productId" value="${item.key.id}">
                    <input type="number" name="quantity" value="${item.value}" min="1"
                           class="form-control form-control-sm text-center me-1" style="width: 60px;">
                    <button type="submit" class="btn btn-sm btn-outline-primary">변경</button>
                  </form>
                </td>
                <td class="text-end fw-bold text-primary">
                  <c:out value="${item.key.price * item.value}"/> P
                </td>
                <td class="text-center">
                    <%-- 상품 삭제 Form --%>
                  <form action="/cart/delete.do" method="post">
                    <input type="hidden" name="productId" value="${item.key.id}">
                    <button type="submit" class="btn btn-sm btn-outline-danger"
                            onclick="return confirm('이 상품을 장바구니에서 삭제하시겠습니까?');">
                      <i class="bi bi-trash"></i> 삭제
                    </button>
                  </form>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="card-footer bg-white p-4 border-top-0">
          <div class="row align-items-center">
            <div class="col-md-6">
              <a href="/product/list.do" class="btn btn-link text-decoration-none text-secondary">
                <i class="bi bi-arrow-left"></i> 쇼핑 계속하기
              </a>
            </div>
            <div class="col-md-6 text-end">
              <h5 class="text-muted mb-1 small">최종 결제 금액</h5>
              <h3 class="fw-bold text-danger mb-3">${totalPrice} P</h3>
              <button type="button" class="btn btn-success btn-lg px-5 shadow-sm">주문하기</button>
            </div>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</div>