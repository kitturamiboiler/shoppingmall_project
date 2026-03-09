package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public int save(Product product) {
        Objects.requireNonNull(product, "상품 객체는 null일 수 없습니다.");

        String sql = "INSERT INTO products (category, title, price, quantity, ean, rating, vendor, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, product.getCategory());
            psmt.setString(2, product.getTitle());
            psmt.setInt(3, product.getPrice());
            psmt.setInt(4, product.getQuantity());
            psmt.setString(5, product.getEan());
            psmt.setDouble(6, product.getRating());
            psmt.setString(7, product.getVendor());
            psmt.setTimestamp(8, Timestamp.valueOf(product.getCreatedAt()));

            return psmt.executeUpdate();

        } catch (SQLException e) {
            log.error("상품 등록 실패: {}", e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 저장 중 오류 발생", e);
        }
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("category"),
                            rs.getString("title"),
                            rs.getInt("price"),
                            rs.getInt("quantity"),
                            rs.getString("ean"),
                            rs.getDouble("rating"),
                            rs.getString("vendor"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
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
    public int countAll() {
        String sql = "SELECT count(*) FROM products";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("전체 상품 개수 조회 실패 (SQL Error): {}", e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("전체 상품 개수 조회 중 오류가 발생했습니다.", e);
        }
        return 0;
    }

    @Override
    public java.util.List<Product> findAll(int offset, int limit) {
        int safeOffset = Math.max(0, offset);
        int safeLimit = Math.max(1, limit);

        String sql = "SELECT * FROM products ORDER BY created_at DESC LIMIT ? OFFSET ?";
        java.util.List<Product> productList = new java.util.ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, safeLimit);
            psmt.setInt(2, safeOffset);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    productList.add(new Product(
                            rs.getInt("id"),
                            rs.getString("category"),
                            rs.getString("title"),
                            rs.getInt("price"),
                            rs.getInt("quantity"),
                            rs.getString("ean"),
                            rs.getDouble("rating"),
                            rs.getString("vendor"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("상품 목록 페이징 조회 실패 (SQL Error): offset={}, limit={}", safeOffset, safeLimit, e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 목록 조회 중 오류가 발생했습니다.", e);
        }

        return productList;
    }
}
