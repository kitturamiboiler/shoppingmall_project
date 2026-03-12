<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="order-list-wrapper">
    <h2>주문 내역</h2>

    <div class="summary">
        총 주문 건수: <strong><c:out value="${orderPage.totalCount}"/></strong>건
    </div>

    <table class="order-table">
        <thead>
        <tr>
            <th>주문번호</th>
            <th>상품 ID</th>
            <th>수량</th>
            <th>총액</th>
            <th>주문일시</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orderPage.content}">
            <tr>
                <td>${order.id}</td>
                <td>${order.productId}</td>
                <td>${order.quantity}</td>
                <td class="price">
                    <fmt:formatNumber value="${order.total}" pattern="#,###"/>원
                </td>
                <td>
                        ${fn:replace(order.createdAt, 'T', ' ')}
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty orderPage.content}">
            <tr>
                <td colspan="5" class="empty-msg">주문 내역이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}" class="prev">이전</a>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="page-num active">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}" class="page-num">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}" class="next">다음</a>
        </c:if>
    </div>
</div>

<style>
    .order-list-wrapper { padding: 20px; font-family: 'Malgun Gothic', sans-serif; }
    .summary { margin-bottom: 15px; text-align: right; color: #666; }
    .order-table { width: 100%; border-collapse: collapse; border-top: 2px solid #333; }
    .order-table th { background: #f9f9f9; padding: 12px; border-bottom: 1px solid #ddd; text-align: center;}
    .order-table td { padding: 15px; border-bottom: 1px solid #eee; text-align: center; }
    .order-table td.price { color: #d9534f; font-weight: bold; }
    .empty-msg { padding: 50px 0; color: #999; }

    .pagination { margin-top: 30px; text-align: center; }
    .page-num { display: inline-block; padding: 5px 12px; margin: 0 3px; border: 1px solid #ddd; color: #333; text-decoration: none; }
    .page-num.active { background: #333; color: #fff; border-color: #333; }
    .prev, .next { display: inline-block; padding: 5px 12px; border: 1px solid #ddd; color: #333; text-decoration: none; background: #f4f4f4; }
</style>