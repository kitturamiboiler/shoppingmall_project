<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 10.
  Time: 오후 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-5 text-center">
  <div class="card shadow-sm p-5 border-0">
    <div class="mb-4">
      <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
    </div>
    <h2 class="fw-bold">주문이 완료되었습니다!</h2>
    <p class="text-muted">고객님의 소중한 주문이 정상적으로 접수되었습니다.</p>

    <div class="bg-light p-4 rounded-3 my-4">
      <p class="mb-1">주문 번호: <strong>#${param.orderId}</strong></p>
      <p class="mb-0">감사합니다. 마이페이지에서 상세 내역을 확인하실 수 있습니다.</p>
    </div>

    <div class="d-grid gap-2 d-md-block">
      <a href="/index.do" class="btn btn-primary btn-lg px-5">홈으로 돌아가기</a>
      <a href="/mypage/index.do" class="btn btn-outline-secondary btn-lg px-5">주문 내역 보기</a>
    </div>
  </div>
</div>
