package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<String> findAll() {
        String sql = "SELECT name FROM categories";
        List<String> list = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) list.add(rs.getString("name"));
        } catch (SQLException e) { throw new RuntimeException(e); }

        return list;
    }

    @Override
    public int save(String categoryName) {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, categoryName);
            return psmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int update(String oldName, String newName) {
        String sql = "UPDATE categories SET name = ? WHERE name = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, newName);
            psmt.setString(2, oldName);
            return psmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public int delete(String categoryName) {
        String sql = "DELETE FROM categories WHERE name = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, categoryName);
            return psmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}