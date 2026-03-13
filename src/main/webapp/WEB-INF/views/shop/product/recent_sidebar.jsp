<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 11.
  Time: 오후 4:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  /* 우측 하단 플로팅 버튼 스타일 */
  .recent-floating-btn {
    position: fixed;
    right: 30px;
    bottom: 30px;
    z-index: 1045;
    border-radius: 50px;
    padding: 12px 25px;
    font-weight: bold;
    box-shadow: 0 4px 15px rgba(0,0,0,0.2);
    transition: all 0.3s ease;
  }
  .recent-floating-btn:hover { transform: scale(1.1); box-shadow: 0 6px 20px rgba(0,0,0,0.3); }

  /* 사이드바 내부 카드 스타일 */
  .offcanvas-body { background-color: #f8f9fa; }
  .recent-card {
    transition: background-color 0.2s;
    border: none;
    border-radius: 8px;
    margin-bottom: 15px;
  }
  .recent-card:hover { background-color: #e9ecef; }
</style>

<button class="btn btn-warning shadow-lg recent-floating-btn" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRecent">
  <i class="bi bi-clock-history"></i> 최근 본 상품
</button>

<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRecent" aria-labelledby="offcanvasRecentLabel">
  <div class="offcanvas-header bg-warning text-dark">
    <h5 class="offcanvas-title fw-bold" id="offcanvasRecentLabel">
      <i class="bi bi-eye-fill"></i> 최근 본 기록
    </h5>
    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
  </div>
  <div class="offcanvas-body">
    <c:choose>
      <c:when test="${empty sessionScope.recentProductList}">
        <div class="text-center mt-5 text-muted">
          <i class="bi bi-info-circle fs-1"></i>
          <p class="mt-2">기록이 없습니다.</p>
        </div>
      </c:when>
      <c:otherwise>
        <c:forEach var="recent" items="${sessionScope.recentProductList}">
          <div class="card mb-3 shadow-sm recent-card">
            <div class="row g-0 align-items-center">
              <div class="col-4 p-2 text-center">
                <img src="${recent.resolvedImageUrl}"
                     onerror="this.src='/resources/no-image.png';"
                     class="img-fluid rounded" style="max-height: 70px; object-fit: contain;">
              </div>
              <div class="col-8">
                <div class="card-body py-2 px-1">
                  <h6 class="card-title mb-1 text-truncate" style="font-size: 0.9rem;">
                    <c:out value="${recent.title}"/>
                  </h6>
                  <p class="text-danger fw-bold small mb-1"><c:out value="${recent.price}"/> P</p>
                  <a href="/product/view.do?productId=${recent.id}" class="btn btn-xs btn-outline-dark py-0 px-2" style="font-size: 0.7rem;">다시보기</a>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>
</div>
