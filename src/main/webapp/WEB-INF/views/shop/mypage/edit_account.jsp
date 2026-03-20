<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <form method="post" action="/mypage/editAccountAction.do">

      <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>

      <% if (request.getAttribute("errorMessage") != null) { %>
      <div class="alert alert-danger p-2" style="font-size: 0.9rem;">
        <%= request.getAttribute("errorMessage") %>
      </div>
      <% } %>

      <div class="form-floating mb-2">
        <input type="text" name="user_id" class="form-control bg-light" id="user_id"
               value="${editUser.userId}" readonly>
        <label for="user_id">아이디 (변경 불가)</label>
      </div>

      <div class="form-floating mb-2">
        <input type="text" name="user_name" class="form-control" id="user_name"
               value="${editUser.userName}" placeholder="이름" required>
        <label for="user_name">이름</label>
      </div>

      <div class="form-floating mb-2">
        <input type="password" name="user_password" class="form-control" id="user_password"
               value="${editUser.userPassword}" placeholder="패스워드" required>
        <label for="user_password">패스워드</label>
      </div>

      <div class="form-floating mb-2">
        <input type="text" name="user_birth" class="form-control" id="user_birth"
               value="${editUser.userBirth}" placeholder="생년월일(8자리)" maxlength="8" required
               pattern="\d{8}" title="8자리 숫자로 입력해주세요 (예: 19950101)">
        <label for="user_birth">생년월일 (8자리: 19950101)</label>
      </div>

      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">수정</button>

      <div class="mt-3 text-center">
        <a href="/mypage/index.do" class="text-decoration-none text-secondary">취소하고 돌아가기</a>
      </div>

    </form>
  </div>
</div>