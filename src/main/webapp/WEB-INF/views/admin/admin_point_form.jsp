<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="fw-bold"><i class="bi bi-coin me-2"></i>포인트 수정 - [${user.userId} (${user.userName})]</h4>
    <a href="/admin/user/list.do" class="btn btn-sm btn-outline-secondary">목록으로 이동</a>
  </div>

  <div class="card shadow-sm border-0">
    <div class="card-body p-4">
      <div class="alert alert-info d-flex justify-content-between align-items-center mb-4">
        <span class="fw-bold">현재 보유 포인트</span>
        <span class="fs-4 fw-bold"><fmt:formatNumber value="${user.userPoint}" type="number"/> P</span>
      </div>

      <form action="/admin/user/point.do" method="post" id="pointForm">
        <input type="hidden" name="userId" value="${user.userId}">

        <div class="mb-4">
          <label class="fw-bold">수정 금액 입력</label>
          <input type="number" name="amount" id="pointInput"
                 class="form-control form-control-lg mb-3"
                 data-current-point="${user.userPoint}"
                 placeholder="0" required>

          <div class="mb-3">
            <label class="form-text text-success d-block mb-2">포인트 지급 (+)</label>
            <div class="btn-group w-100">
              <button type="button" class="btn btn-outline-success btn-point-adjust" data-value="10000">+1만</button>
              <button type="button" class="btn btn-outline-success btn-point-adjust" data-value="30000">+3만</button>
              <button type="button" class="btn btn-outline-success btn-point-adjust" data-value="50000">+5만</button>
              <button type="button" class="btn btn-outline-success btn-point-adjust" data-value="100000">+10만</button>
            </div>
          </div>

          <div class="mb-4">
            <label class="form-text text-danger d-block mb-2">포인트 회수 (-)</label>
            <div class="btn-group w-100">
              <button type="button" class="btn btn-outline-danger btn-point-adjust" data-value="-10000">-1만</button>
              <button type="button" class="btn btn-outline-danger btn-point-adjust" data-value="-30000">-3만</button>
              <button type="button" class="btn btn-outline-danger btn-point-adjust" data-value="-50000">-5만</button>
              <button type="button" class="btn btn-outline-danger btn-point-adjust" data-value="-100000">-10만</button>
            </div>
          </div>

          <div id="previewBox" class="p-3 bg-light rounded text-center d-none mb-4">
            <span class="text-muted">수정 후 잔액 예상: </span>
            <span id="afterPoint" class="fw-bold text-primary fs-5">0</span> P
          </div>
        </div>

        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary btn-lg px-5">수정 완료</button>
          <a href="/admin/user/list.do" class="btn btn-light btn-lg px-4">취소</a>
          <button type="button" class="btn btn-outline-secondary ms-auto" id="btnReset">초기화</button>
        </div>
      </form>

      <script src="${pageContext.request.contextPath}/resources/js/admin/point_admin.js"></script>
    </div>
  </div>
</div>