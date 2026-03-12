<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오전 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<style>
  .management-card {
    border: 1px solid #eee;
    border-radius: 12px;
    transition: all 0.2s ease-in-out;
    cursor: pointer;
  }
  .management-card:hover {
    border-color: #0074e9;
    background-color: #f8fbff;
    box-shadow: 0 4px 12px rgba(0,116,233,0.1);
  }
  .icon-circle {
    width: 60px;
    height: 60px;
    background: #f1f1f1;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    color: #555;
  }
  .management-card:hover .icon-circle { background: #0074e9; color: #fff; }
</style>

<div class="container py-4">
  <h3 class="fw-bold mb-4">관리 서비스 바로가기</h3>

  <div class="row g-4">
    <div class="col-md-4" onclick="location.href='/admin/product/list.do'">
      <div class="card management-card h-100 p-4">
        <div class="icon-circle mb-3"><i class="bi bi-box-seam"></i></div>
        <h5 class="fw-bold">상품 관리</h5>
        <p class="text-muted small">신규 상품 등록, 가격 수정 및 재고 관리를 수행합니다.</p>
      </div>
    </div>

    <div class="col-md-4" onclick="location.href='/admin/order/manager.do'">
      <div class="card management-card h-100 p-4">
        <div class="icon-circle mb-3"><i class="bi bi-receipt"></i></div>
        <h5 class="fw-bold">주문/배송 관리</h5>
        <p class="text-muted small">결제 내역 확인 및 배송 상태를 일괄 업데이트합니다.</p>
      </div>
    </div>

    <div class="col-md-4" onclick="location.href='/admin/user/list.do'">
      <div class="card management-card h-100 p-4">
        <div class="icon-circle mb-3"><i class="bi bi-people"></i></div>
        <h5 class="fw-bold">회원 관리</h5>
        <p class="text-muted small">사용자 권한 설정 및 포인트 내역을 조회합니다.</p>
      </div>
    </div>
  </div>
</div>