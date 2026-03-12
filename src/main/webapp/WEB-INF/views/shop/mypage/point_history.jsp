<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="point-history-wrapper">
  <h2>포인트 이용 내역</h2>

  <div class="point-summary-box">
    <div class="label">현재 보유 포인트</div>
    <div class="current-point">
      <fmt:formatNumber value="${sessionScope.user.userPoint}" pattern="#,###"/>
      <span class="unit">P</span>
    </div>
  </div>

  <div class="summary">
    총 내역: <strong><c:out value="${pointHistoryPage.totalCount}"/></strong>건
  </div>

  <table class="point-table">
    <thead>
    <tr>
      <th>번호</th>
      <th>내용(사유)</th>
      <th>변동액</th>
      <th>일시</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="history" items="${pointHistoryPage.content}">
      <tr>
        <td>${history.id}</td>
        <td class="reason">${history.reason}</td>
        <td class="amount ${history.amount > 0 ? 'plus' : 'minus'}">
          <c:if test="${history.amount > 0}">+</c:if>
          <fmt:formatNumber value="${history.amount}" pattern="#,###"/> P
        </td>
        <td class="date">
            ${fn:replace(history.createdAt, 'T', ' ')}
        </td>
      </tr>
    </c:forEach>

    <c:if test="${empty pointHistoryPage.content}">
      <tr>
        <td colspan="4" class="empty-msg">포인트 이용 내역이 없습니다.</td>
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
  .point-history-wrapper { padding: 20px; font-family: 'Malgun Gothic', sans-serif; max-width: 900px; margin: 0 auto; }
  .summary { margin-bottom: 15px; text-align: right; color: #666; font-size: 0.9em; }

  .point-table { width: 100%; border-collapse: collapse; border-top: 2px solid #333; }
  .point-table th { background: #f9f9f9; padding: 12px; border-bottom: 1px solid #ddd; font-weight: bold; }
  .point-table td { padding: 15px; border-bottom: 1px solid #eee; text-align: center; }

  .point-table td.reason { text-align: left; padding-left: 30px; font-weight: 500; }

  .amount { font-weight: bold; font-family: 'Verdana', sans-serif; }
  .amount.plus { color: #28a745; }
  .amount.minus { color: #dc3545; }

  .date { color: #888; font-size: 0.85em; }
  .empty-msg { padding: 80px 0; color: #bbb; }

  .pagination { margin-top: 30px; text-align: center; }
  .page-num { display: inline-block; padding: 6px 12px; margin: 0 3px; border: 1px solid #ddd; color: #333; text-decoration: none; border-radius: 4px; }
  .page-num.active { background: #ffc107; color: #000; border-color: #ffc107; font-weight: bold; }
  .prev, .next { display: inline-block; padding: 6px 12px; border: 1px solid #ddd; color: #333; text-decoration: none; background: #f4f4f4; border-radius: 4px; }
</style>