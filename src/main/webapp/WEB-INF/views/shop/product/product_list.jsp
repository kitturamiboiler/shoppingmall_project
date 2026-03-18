<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mt-5 mb-5">
    <h2 class="mb-4 fw-bold">상품 리스트</h2>
    <div class="row row-cols-1 row-cols-md-4 g-4">

        <%-- 1. 메인 상품 리스트 출력 --%>
        <c:if test="${empty productPage.content}">
            <div class="col-12">
                <div class="alert alert-secondary text-center" role="alert">
                    현재 등록된 상품이 없습니다.
                </div>
            </div>
        </c:if>

        <c:forEach var="product" items="${productPage.content}">
            <div class="col">
                <div class="card h-100 shadow-sm border-0">
                    <img src="${product.resolvedImageUrl}"
                         onerror="this.onerror=null; this.src='/resources/no-image.png';"
                         class="card-img-top" alt="Product Image" style="height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title text-truncate" title="<c:out value='${product.title}'/>">
                            <c:out value="${product.title}" />
                        </h5>
                        <p class="card-text text-danger fw-bold fs-5 mb-1"><c:out value="${product.price}" /> P</p>
                        <p class="card-text text-muted small">재고: <c:out value="${product.quantity}" />개</p>
                    </div>
                    <div class="card-footer bg-white border-top-0 pt-0">
                        <div class="d-flex gap-2">
                            <a href="/product/view.do?productId=${product.id}" class="btn btn-outline-primary btn-sm w-50">상세보기</a>
                            <form action="/cart/add.do" method="post" class="m-0 w-50">
                                <input type="hidden" name="productId" value="${product.id}">
                                <button type="submit" class="btn btn-primary btn-sm w-100">담기</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <%-- 페이징 영역 --%>
    <c:if test="${totalPages > 0}">
        <nav aria-label="Page navigation" class="mt-5">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link" href="/product/list.do?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/shop/product/recent_sidebar.jsp" />