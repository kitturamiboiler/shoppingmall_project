<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container-fluid py-2">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="fw-bold"><i class="bi bi-people me-2"></i>회원 관리 리스트</h4>
    <span class="badge bg-primary">총 회원수: ${users.size()}명</span>
  </div>

  <div class="card border-0 shadow-sm overflow-hidden">
    <table class="table table-hover align-middle mb-0">
      <thead class="table-light">
      <tr>
        <th>아이디</th>
        <th>이름</th>
        <th>권한</th>
        <th class="text-end">포인트</th>
        <th class="text-center">생년월일</th>
        <th class="text-center">관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${users}">
        <tr>
          <td class="fw-bold">${u.userId}</td>
          <td>${u.userName}</td>
          <td>
            <span class="badge ${u.userAuth.name() eq 'ROLE_ADMIN' ? 'bg-danger' : 'bg-secondary'}">
                ${u.userAuth}
            </span>
          </td>
          <td class="text-end fw-bold text-primary">
            <fmt:formatNumber value="${u.userPoint}" type="number"/> P
          </td>
          <td class="text-center">${u.userBirth}</td>
          <td class="text-center">
            <button class="btn btn-sm btn-outline-primary me-1">포인트 수정</button>
            <form action="/admin/user/delete.do" method="post" class="d-inline">
              <input type="hidden" name="id" value="${u.userId}">
              <button type="submit" class="btn btn-sm btn-outline-danger"
                      onclick="return confirm('이 회원을 탈퇴 처리하시겠습니까?');">탈퇴</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
