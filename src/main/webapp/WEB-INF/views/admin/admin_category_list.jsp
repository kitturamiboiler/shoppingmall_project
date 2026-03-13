<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid py-2">
    <h4 class="fw-bold mb-4"><i class="bi bi-tags me-2"></i>카테고리 관리</h4>

    <div class="card border-0 shadow-sm mb-4">
        <div class="card-body">
            <form action="/admin/category/register.do" method="post" class="row g-3 align-items-center">
                <div class="col-auto">
                    <label class="fw-bold">신규 카테고리명</label>
                </div>
                <div class="col-md-4">
                    <input type="text" name="categoryName" class="form-control" placeholder="예: 전자기기" required>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary px-4">등록</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card border-0 shadow-sm overflow-hidden">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
            <tr>
                <th style="width: 80px;" class="text-center">번호</th>
                <th>카테고리 명</th>
                <th style="width: 200px;" class="text-center">관리</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cat" items="${categories}" varStatus="status">
                <tr>
                    <td class="text-center text-muted">${cat.categoryId}</td>

                    <td>
            <span class="badge bg-light text-dark border p-2">
                    ${cat.categoryName}
            </span>
                    </td>
                    <td class="text-center">
                        <button type="button" class="btn btn-sm btn-outline-primary me-1"
                                onclick="editCategory('${cat.categoryName}')">수정</button>

                        <form action="/admin/category/delete.do" method="post" class="d-inline">
                            <input type="hidden" name="name" value="${cat.categoryName}">
                            <button type="submit" class="btn btn-sm btn-outline-danger"
                                    onclick="return confirm('이 카테고리를 삭제하시겠습니까?');">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty categories}">
                <tr>
                    <td colspan="3" class="text-center py-4 text-muted">등록된 카테고리가 없습니다.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<form id="updateForm" action="/admin/category/update.do" method="post" style="display:none;">
    <input type="hidden" name="oldName" id="oldName">
    <input type="hidden" name="newName" id="newName">
</form>

<script>
    function editCategory(name) {
        const newName = prompt("'" + name + "' 카테고리를 무엇으로 수정하시겠습니까?", name);

        if (newName && newName.trim() !== "" && newName.trim() !== name) {
            document.getElementById('oldName').value = name;
            document.getElementById('newName').value = newName.trim();
            document.getElementById('updateForm').submit();
        }
    }
</script>