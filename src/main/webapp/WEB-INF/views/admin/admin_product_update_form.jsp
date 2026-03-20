<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 13.
  Time: 오후 1:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid py-2">
    <div class="mb-4">
        <h4 class="fw-bold"><i class="bi bi-pencil-square me-2"></i>${empty product ? '신규 상품 등록' : '상품 정보 수정'}</h4>
    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger p-2" style="font-size: 0.9rem;">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <div class="card border-0 shadow-sm">
        <div class="card-body p-4">
            <form action="${empty product ? '/admin/product/register.do' : '/admin/product/update.do'}"
                  method="post" enctype="multipart/form-data">

                <c:if test="${not empty product}">
                    <input type="hidden" name="id" value="${product.id}">
                </c:if>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">카테고리 ID</label>
                        <input type="number" name="categoryId" class="form-control" value="${product.categoryId}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">상품명</label>
                        <input type="text" name="title" class="form-control" value="${product.title}" required>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">판매가 (P)</label>
                        <input type="number" name="price" class="form-control" value="${product.price}" required>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">재고 수량</label>
                        <input type="number" name="quantity" class="form-control" value="${product.quantity}" required>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">제조사/벤더</label>
                        <input type="text" name="vendor" class="form-control" value="${product.vendor}" required>
                    </div>

                    <div class="col-md-12">
                        <label class="form-label">EAN 번호</label>
                        <input type="text" name="ean" class="form-control" value="${product.ean}">
                    </div>

                    <div class="col-md-12">
                        <label class="form-label">상품 이미지 ${not empty product ? '(수정 시에만 선택)' : ''}</label>
                        <input type="file" name="productImage" class="form-control">
                        <c:if test="${not empty product}">
                            <div class="mt-2">
                                <small class="text-muted">현재 이미지: </small>
                                <img src="${product.resolvedImageUrl}" style="width: 100px;" class="rounded border">
                            </div>
                        </c:if>
                    </div>
                </div>

                <div class="mt-4 pt-3 border-top text-end">
                    <a href="/admin/product/list.do" class="btn btn-light me-2">취소</a>
                    <button type="submit" class="btn btn-primary px-4">
                        ${empty product ? '등록하기' : '수정완료'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
