package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void save(PointHistory pointHistory) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String sql = "INSERT INTO point_history(user_id, amount, reason, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, pointHistory.getUserId());
            psmt.setInt(2, pointHistory.getAmount());
            psmt.setString(3, pointHistory.getReason());
            psmt.setTimestamp(4, Timestamp.valueOf(pointHistory.getCreatedAt()));
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("포인트 이력 저장 실패", e);
        }finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<PointHistory> findAllByUserId(String userId, int offset, int limit) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        // [제약] 최근 사용 내역 순 (DESC) + 페이징 (LIMIT/OFFSET)
        String sql = "SELECT * FROM point_history WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, limit);
            psmt.setInt(3, offset);
            try (ResultSet rs = psmt.executeQuery()) {
                List<PointHistory> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new PointHistory(
                            rs.getLong("id"),
                            rs.getString("user_id"),
                            rs.getInt("amount"),
                            rs.getString("reason"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("포인트 이력 조회 실패", e);
        }finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public long countByUserId(String userId) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String sql = "SELECT COUNT(*) FROM point_history WHERE user_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("포인트 이력 카운트 실패", e);
        }finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String sql = "DELETE FROM point_history WHERE user_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("포인트 이력 삭제 실패", e);
        }finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}