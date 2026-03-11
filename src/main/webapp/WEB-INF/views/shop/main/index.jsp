<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- shop/main/index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="album py-5 bg-light">
    <div class="container">
        <h3 class="pb-3 fw-bold">- 상품 -</h3>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4">
            <c:forEach var="product" items="${productList}" end="7">
                <div class="col">
                    <div class="card shadow-sm h-100 border-0">
                        <img src="/resources/images/products/${product.id}.jpg"
                             onerror="this.src='/resources/no-image.png';"
                             class="card-img-top" style="height: 225px; object-fit: cover;">
                        <div class="card-body">
                            <p class="card-text fw-bold text-truncate">${product.title}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="/product/view.do?productId=${product.id}" class="btn btn-sm btn-outline-secondary">상세보기</a>
                                </div>
                                <small class="text-danger fw-bold">${product.price}P</small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="text-center mt-5">
            <a href="/product/list.do" class="btn btn-primary btn-lg px-5 shadow">전체 상품 더 보러가기</a>
        </div>
    </div>
</div>