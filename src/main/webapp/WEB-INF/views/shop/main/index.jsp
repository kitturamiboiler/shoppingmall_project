<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- shop/main/index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<style>
    .product-card {
        transition: transform 0.2s, shadow 0.2s;
        border-radius: 12px;
    }
    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
    }
    .product-img-wrapper {
        height: 200px;
        background-color: rgba(248, 249, 250, 0.39);
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 12px 12px 0 0;
    }
    .card-title {
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
        height: 2.8rem;
        font-size: 0.95rem;
        line-height: 1.4;
    }
    .point-text {
        color: red;
        font-weight: 800;
    }
</style>

<div class="container my-4">
    <div class="d-flex justify-content-start gap-2 overflow-auto pb-2" style="white-space: nowrap;">
        <a href="/index.do" class="btn ${empty selectedCategory or selectedCategory eq 'ALL' ? 'btn-primary' : 'btn-outline-primary'} rounded-pill px-4">
            전체
        </a>
        <c:forEach var="cat" items="${categoryList}">
            <a href="/index.do?category=${cat}"
               class="btn ${selectedCategory eq cat ? 'btn-primary' : 'btn-outline-primary'} rounded-pill px-4">
                <c:out value="${cat}" />
            </a>
        </c:forEach>
    </div>
</div>

<div class="container mb-5">
    <c:choose>
        <c:when test="${not empty productList}">
            <h3 class="fw-bold mb-4">
                <c:choose>
                    <c:when test="${not empty searchKeyword}">"<c:out value="${searchKeyword}"/>" 검색 결과</c:when>
                    <c:when test="${not empty selectedCategory and selectedCategory ne 'ALL'}"><c:out value="${selectedCategory}"/> 카테고리</c:when>
                    <c:otherwise>오늘의 발견</c:otherwise>
                </c:choose>
            </h3>

            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4">

                <c:forEach var="product" items="${productList}" end="7">
                    <div class="col">
                        <div class="card h-100 border-0 shadow-sm product-card"
                             onclick="location.href='/product/view.do?id=${product.id}'" style="cursor:pointer;">

                            <div class="product-img-wrapper">
                                <img src="/resources/no-image.png"
                                     class="card-img p-3"
                                     alt="${product.title}"
                                     style="max-height: 180px; width: auto; object-fit: contain;">
                            </div>

                            <div class="card-body">
                                <p class="text-muted small mb-1"><c:out value="${product.category}"/></p>
                                <h6 class="card-title mb-2"><c:out value="${product.title}"/></h6>
                                <h5 class="point-text mb-0">
                                    <fmt:formatNumber value="${product.price}" type="number"/> <small class="fw-normal">P</small>
                                </h5>
                                <div class="d-flex align-items-center mt-2 small">
                                    <span class="text-warning me-1">★</span>
                                    <span class="text-muted">${product.rating}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="text-center mt-5">
                <a href="/product/list.do" class="btn btn-outline-primary btn-lg px-5 rounded-pill shadow-sm fw-bold">
                    전체 상품 보기 <i class="bi bi-arrow-right-short"></i>
                </a>
            </div>
        </c:when>

        <c:otherwise>
            <div class="text-center py-5 bg-white rounded shadow-sm">
                <i class="bi bi-search text-muted" style="font-size: 4rem;"></i>
                <h4 class="mt-3 text-muted fw-bold">찾으시는 상품이 없습니다.</h4>
                <p class="text-secondary">다른 검색어나 카테고리를 선택해 보세요.</p>
                <a href="/index.do" class="btn btn-primary mt-2 px-4 rounded-pill">전체 상품 보기</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>