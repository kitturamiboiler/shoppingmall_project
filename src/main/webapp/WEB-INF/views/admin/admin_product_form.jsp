<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn13
  Date: 26. 3. 12.
  Time: 오후 2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div class="container-fluid py-2">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="fw-bold"><i class="bi bi-plus-circle me-2"></i>신규 상품 등록</h4>
    <a href="/admin/product/list.do" class="btn btn-outline-secondary">목록으로</a>
  </div>

  <div class="card border-0 shadow-sm">
    <div class="card-body p-4">
      <form action="/admin/product/register.do" method="post" enctype="multipart/form-data">
        <div class="row">
          <div class="col-md-4 border-end">
            <label class="form-label fw-bold">상품 대표 이미지</label>
            <div class="mb-3 text-center p-4 bg-light rounded" id="imagePreviewContainer">
              <i class="bi bi-image text-muted" style="font-size: 4rem;"></i>
              <p class="small text-muted">권장 사이즈: 500x500px</p>
            </div>
            <input type="file" name="productImage" class="form-control" id="productImage" accept="image/*" required onchange="previewImage(this)">
          </div>

          <div class="col-md-8 ps-md-4">
            <div class="row g-3">
              <div class="col-md-8">
                <label class="form-label fw-bold">상품명</label>
                <input type="text" name="title" class="form-control" placeholder="예: [NHN] 시그니처 텀블러" required>
              </div>
              <div class="col-md-4">
                <label class="form-label fw-bold">카테고리</label>
                <select name="category" class="form-select" required>
                  <option value="">선택하세요</option>
                  <option value="Gadget">Gadget</option>
                  <option value="Doohickey">Doohickey</option>
                  <option value="Gizmo">Gizmo</option>
                  <option value="Widget">Widget</option>
                </select>
              </div>
              <div class="col-md-6">
                <label class="form-label fw-bold">판매 가격 (Point)</label>
                <div class="input-group">
                  <input type="number" name="price" class="form-control" min="0" required>
                  <span class="input-group-text">P</span>
                </div>
              </div>
              <div class="col-md-6">
                <label class="form-label fw-bold">초기 재고량</label>
                <input type="number" name="quantity" class="form-control" min="0" required>
              </div>
              <div class="col-12">
                <label class="form-label fw-bold">제조사 (Vendor)</label>
                <input type="text" name="vendor" class="form-control" placeholder="제조사명을 입력하세요">
              </div>
            </div>
          </div>
        </div>
        <hr class="my-4">
        <div class="text-end">
          <button type="reset" class="btn btn-light me-2">초기화</button>
          <button type="submit" class="btn btn-primary px-5">상품 등록 완료</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function previewImage(input) {
    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = function(e) {
        const container = document.getElementById('imagePreviewContainer');
        container.innerHTML = `<img src="${e.target.result}" class="img-fluid rounded shadow-sm" style="max-height: 250px;">`;
      }
      reader.readAsDataURL(input.files[0]);
    }
  }
</script>
