<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/signupAction.do">

            <h1 class="h3 mb-3 fw-normal">Sign up</h1>

            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger p-2" style="font-size: 0.9rem;">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>

            <div class="form-floating mb-2">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="아이디" required>
                <label for="user_id">아이디</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="user_name" class="form-control" id="user_name" placeholder="이름" required>
                <label for="user_name">이름</label>
            </div>

            <div class="form-floating mb-2">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="패스워드" required>
                <label for="user_password">패스워드</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="user_birth" class="form-control" id="user_birth"
                       placeholder="생년월일(8자리)" maxlength="8" required
                       pattern="\d{8}" title="8자리 숫자로 입력해주세요 (예: 19950101)">
                <label for="user_birth">생년월일 (8자리: 19950101)</label>
            </div>

            <button class="w-100 btn btn-lg btn-success mt-3" type="submit">sign up</button>

            <div class="mt-3 text-center">
                <a href="/login.do" class="text-decoration-none text-secondary">이미 계정이 있으신가요? 로그인</a>
            </div>

            <p class="mt-5 mb-3 text-muted">© 2022-2026</p>

        </form>
    </div>
</div>