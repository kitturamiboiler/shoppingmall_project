<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="margin: auto; width: 600px;" class="mt-5">
  <div class="text-center mb-4">
    <h2 class="fw-bold">${sessionScope.user.userName}님의 마이페이지</h2>
    <p class="text-muted">
      가입일: ${sessionScope.user.createdAt.toLocalDate()}
    </p>
  </div>

  <div class="card shadow-sm border-0">
    <div class="card-body p-4">
      <h5 class="card-title mb-4 fw-bold">내 정보 확인</h5>

      <div class="mb-3">
        <label class="form-label text-muted small">아이디</label>
        <p class="fs-5 border-bottom pb-2">${sessionScope.user.userId}</p>
      </div>

      <div class="mb-3">
        <label class="form-label text-muted small">이름</label>
        <p class="fs-5 border-bottom pb-2">${sessionScope.user.userName}</p>
      </div>

      <div class="mb-4">
        <label class="form-label text-muted small">생년월일</label>
        <p class="fs-5 border-bottom pb-2">${sessionScope.user.userBirth}</p>
      </div>

      <div class="d-grid gap-2">
        <a href="/mypage/orderList.do" class="btn btn-outline-primary btn-lg py-3">
          <i class="bi bi-bag-check"></i> 나의 주문 내역 보기
        </a>

        <a href="/mypage/pointHistory.do" class="btn btn-lg py-3" style="border-color: #ffc107; color: #d39e00; background-color: transparent;">
          <i class="bi bi-database-fill-add"></i> 나의 포인트 내역 보기
        </a>

        <a href="/mypage/passwordCheck.do" class="btn btn-dark btn-lg py-3">
          <i class="bi bi-person-gear"></i> 회원 정보 수정
        </a>
      </div>
    </div>

    <div class="card-footer bg-white border-0 text-center pb-4">
      <a href="/logout.do" class="btn btn-link text-muted text-decoration-none">로그아웃</a>
      <span class="text-muted">|</span>
      <a href="/mypage/deleteAccount.do" class="btn btn-link text-danger text-decoration-none" onclick="return confirm('정말 탈퇴하시겠습니까?')">회원 탈퇴</a>
    </div>
  </div>
</div>