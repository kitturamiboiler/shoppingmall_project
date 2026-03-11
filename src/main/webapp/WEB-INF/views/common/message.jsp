<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;" class="mt-5">
    <div class="p-4 border rounded shadow-sm bg-white text-center">

        <div class="mb-4">
            <i class="bi bi-info-circle-fill text-primary" style="font-size: 3rem;"></i>
        </div>

        <h1 class="h3 mb-3 fw-normal" style="white-space: pre-wrap;">${message}</h1>

        <div class="mt-4">
            <p class="text-muted small">아래 버튼을 누르면 홈 페이지로 이동합니다.</p>
        </div>

        <a href="/index.do" class="w-100 btn btn-lg btn-primary mt-3">확인</a>

    </div>
</div>