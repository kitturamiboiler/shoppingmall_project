<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/mypage/passwordCheckAction.do">

            <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>
            <p class="text-muted small">정보 수정을 위해 현재 비밀번호를 입력해주세요.</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger p-2" style="font-size: 0.9rem;">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>

            <div class="form-floating mb-2">
                <input type="text" class="form-control" id="display_id" value="${sessionScope.user.userId}" disabled>
                <label for="display_id">아이디</label>
            </div>

            <div class="form-floating mb-2">
                <input type="password" name="current_password" class="form-control" id="current_password" placeholder="패스워드" required>
                <label for="current_password">현재 패스워드 입력</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">확인</button>

            <div class="mt-3 text-center">
                <a href="/mypage/index.do" class="text-decoration-none text-secondary">취소하고 돌아가기</a>
            </div>

        </form>
    </div>
</div>