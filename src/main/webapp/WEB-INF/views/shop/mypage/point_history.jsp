<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container">
  <h2>나의 포인트 내역</h2>

  <div class="info-bar" style="margin-bottom: 10px; text-align: right;">
    <span>전체 내역: <strong>${pointHistoryPage.totalCount}</strong>건</span>
  </div>

  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>사유</th>
      <th>금액</th>
      <th>일시</th>
    </tr>
    </thead>
    <tbody>
    <%-- pointHistoryPage 내부의 리스트 필드명이 'list'라고 가정합니다 --%>
    <c:forEach var="item" items="${pointHistoryPage.list}">
      <tr>
        <td>${item.id}</td>
        <td class="text-left">${item.reason}</td>
        <td class="${item.amount > 0 ? 'plus' : 'minus'}">
          <fmt:formatNumber value="${item.amount}" pattern="#,###"/> P
        </td>
        <td>
          <fmt:formatDate value="${item.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
        </td>
      </tr>
    </c:forEach>

    <c:if test="${empty pointHistoryPage.list}">
      <tr>
        <td colspan="4">포인트 내역이 없습니다.</td>
      </tr>
    </c:if>
    </tbody>
  </table>

  <div class="pagination">
    <%-- 이전 페이지 이동 --%>
    <c:if test="${page > 1}">
      <a href="?page=${page - 1}">이전</a>
    </c:if>

    <%-- 페이지 번호 계산 및 출력 --%>
    <%-- totalPages = (totalCount / pageSize) 를 올림한 값 --%>
    <c:set var="totalPages" value="${(pointHistoryPage.totalCount + 9) / 10}" />
    <fmt:parseNumber var="lastPage" value="${totalPages}" integerOnly="true" />

    <c:forEach var="i" begin="1" end="${lastPage}">
      <a href="?page=${i}" class="${i == page ? 'active' : ''}">${i}</a>
    </c:forEach>

    <%-- 다음 페이지 이동 --%>
    <c:if test="${page < lastPage}">
      <a href="?page=${page + 1}">다음</a>
    </c:if>
  </div>
</div>

<style>
  .container { width: 80%; margin: 0 auto; }
  table { width: 100%; border-collapse: collapse; }
  th, td { border-bottom: 1px solid #ddd; padding: 10px; text-align: center; }
  th { background: #f8f9fa; }
  .text-left { text-align: left; }
  .plus { color: #28a745; font-weight: bold; } /* 적립은 초록색 */
  .minus { color: #dc3545; font-weight: bold; } /* 사용은 빨간색 */

  .pagination { margin-top: 20px; text-align: center; }
  .pagination a { padding: 5px 10px; border: 1px solid #ddd; text-decoration: none; color: #333; margin: 0 2px; }
  .pagination a.active { background: #333; color: #fff; border-color: #333; }
</style>