package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.repository.OrderItemRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepository {

    @Override
    public int save(OrderItem orderItem) {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getProductId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setDouble(4, orderItem.getPrice());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("주문 상세 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<OrderItem> findAllByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<OrderItem> itemList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 상세 조회 중 오류 발생", e);
        }
        return itemList;
    }
}