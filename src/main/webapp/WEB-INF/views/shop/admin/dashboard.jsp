<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오전 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" session="true" trimDirectiveWhitespaces="true" %>
<div class="row g-4">
  <div class="col-12 border-bottom pb-3 mb-4">
    <h2 class="fw-bold"><i class="bi bi-speedometer2"></i> 관리자 대시보드</h2>
  </div>
  <div class="col-md-4">
    <div class="card h-100 shadow-sm border-0">
      <div class="card-body text-center py-4">
        <i class="bi bi-box-seam text-primary fs-1"></i>
        <h4 class="card-title mt-3">상품 관리</h4>
        <p class="text-muted small">상품 등록, 수정, 삭제 및 재고 관리</p>
        <a href="/admin/product/list.do" class="btn btn-primary w-100">이동하기</a>
      </div>
    </div>
  </div>

  <div class="col-md-4">
    <div class="card h-100 shadow-sm border-0">
      <div class="card-body text-center py-4">
        <i class="bi bi-people text-success fs-1"></i>
        <h4 class="card-title mt-3">회원 관리</h4>
        <p class="text-muted small">사용자 목록 조회 및 권한 설정</p>
        <a href="/admin/user/list.do" class="btn btn-success w-100 text-white">이동하기</a>
      </div>
    </div>
  </div>
</div>