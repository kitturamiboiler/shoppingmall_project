package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public int save(Product product) {
        Objects.requireNonNull(product, "상품 객체는 null일 수 없습니다.");

        String sql = "INSERT INTO products (category_id, title, price, quantity, ean, rating, vendor, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setLong(1, product.getCategoryId());
            psmt.setString(2, product.getTitle());
            psmt.setInt(3, product.getPrice());
            psmt.setInt(4, product.getQuantity());
            psmt.setString(5, product.getEan());
            psmt.setDouble(6, product.getRating());
            psmt.setString(7, product.getVendor());
            psmt.setTimestamp(8, Timestamp.valueOf(product.getCreatedAt()));

            int result = psmt.executeUpdate();

            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            }
            return result;
        } catch (SQLException e) {
            log.error("상품 등록 실패: {}", e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 저장 중 오류 발생", e);
        }
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT p.*, c.name AS category_name " +
                "FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE p.id = ?";

        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return mapToProduct(rs);
                }
            }
        } catch (SQLException e) {
            log.error("상품 조회 실패 {}: {}", id, e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 조회 중 오류 발생", e);
        }
        return null;
    }

    @Override
    public List<Product> findAll(int offset, int limit) {
        int safeOffset = Math.max(0, offset);
        int safeLimit = Math.max(1, limit);

        String sql = "SELECT p.*, c.name AS category_name " +
                "FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "ORDER BY p.created_at DESC LIMIT ? OFFSET ?";

        List<Product> productList = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, safeLimit);
            psmt.setInt(2, safeOffset);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    productList.add(mapToProduct(rs));
                }
            }
        } catch (SQLException e) {
            log.error("상품 목록 조회 실패: {}", e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("목록 조회 중 오류 발생", e);
        }
        return productList;
    }

    @Override
    public List<Product> findAllCategory(String categoryName, int offset, int limit) {
        String sql = "SELECT p.*, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE c.name = ? " +
                "ORDER BY p.created_at DESC LIMIT ? OFFSET ?";

        List<Product> productList = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            ps.setInt(2, Math.max(1, limit));
            ps.setInt(3, Math.max(0, offset));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(mapToProduct(rs));
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("카테고리별 상품 조회 중 오류 발생", e);
        }
        return productList;
    }
    @Override
    public List<String> getProductsByCategory() {
        String sql = "SELECT DISTINCT c.name FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "ORDER BY c.name ASC";

        List<String> categories = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            log.error("카테고리 목록 조회 실패: {}", e.getMessage());
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("카테고리 조회 중 오류 발생", e);
        }
        return categories;
    }
    @Override
    public List<Product> getProductsByTitle(String title, int offset, int limit) {
        String sql = "SELECT p.*, c.name AS category_name " +
                "FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE p.title LIKE ? " +
                "ORDER BY p.created_at DESC LIMIT ? OFFSET ?";

        List<Product> productList = new ArrayList<>();
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            ps.setInt(2, Math.max(1, limit));
            ps.setInt(3, Math.max(0, offset));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(mapToProduct(rs));
                }
            }
        } catch (SQLException e) {
            log.error("상품명 검색 중 오류 발생: {}", e.getMessage());
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 검색 실패", e);
        }
        return productList;
    }

    @Override
    public int update(Product product) {
        String sql = "UPDATE products SET category_id=?, title=?, price=?, quantity=?, ean=?, vendor=? WHERE id=?";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, product.getCategoryId());
            psmt.setString(2, product.getTitle());
            psmt.setInt(3, product.getPrice());
            psmt.setInt(4, product.getQuantity());
            psmt.setString(5, product.getEan());
            psmt.setString(6, product.getVendor());
            psmt.setInt(7, product.getId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("상품 수정 실패 (ID: {}): {}", product.getId(), e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 수정 중 오류 발생", e);
        }
    }

    @Override
    public int deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, id);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("상품 삭제 실패 (ID: {}): {}", id, e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 삭제 중 오류 발생", e);
        }
    }

    @Override
    public int countAll() {
        String sql = "SELECT count(*) FROM products";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("개수 조회 실패", e);
        }
        return 0;
    }

    private Product mapToProduct(ResultSet rs) throws SQLException {
        String categoryName = rs.getString("category_name");

        if (categoryName == null) {
            categoryName = "미분류";
        }

        return new Product(
                rs.getInt("id"),
                categoryName,
                rs.getString("title"),
                rs.getInt("price"),
                rs.getInt("quantity"),
                rs.getString("ean"),
                rs.getDouble("rating"),
                rs.getString("vendor"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
        );
    }

    @Override
    public void updateStock(int productId, int quantity) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("재고 부족 (ID: " + productId + ")");
            }
        } catch (SQLException e) {
            throw new RuntimeException("재고 업데이트 오류", e);
        }
    }
}