package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public int save(Order order) {
        String sql = "INSERT INTO orders(user_id, total, created_at) VALUES(?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getUserId());
            ps.setDouble(2, order.getTotal());
            ps.setTimestamp(3, order.getCreatedAt() != null ?
                    Timestamp.valueOf(order.getCreatedAt()) :
                    new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    order.setId(generatedId);
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Order 저장 실패", e);
        }
        return 0;
    }

    @Override
    public List<Order> findAllByUserId(String userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Order> orderList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderList.add(mapToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Order 목록 조회 실패", e);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Order 단건 조회 실패", e);
        }
        return Optional.empty();
    }

    private Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getString("user_id"));
        order.setTotal(rs.getDouble("total"));
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return order;
    }
}