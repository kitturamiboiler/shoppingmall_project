package com.nhnacademy.shoppingmall.user.repository.impl;

import ch.qos.logback.classic.db.names.DBNameResolver;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at " +
                "from users where user_id=? and user_password=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, userPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        String sql = "insert into users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getUserBirth());
            ps.setString(5, user.getUserAuth().name());
            ps.setInt(6, user.getUserPoint());
            ps.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(8, Objects.nonNull(user.getLatestLoginAt()) ? Timestamp.valueOf(user.getLatestLoginAt()) : null);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        String sql = "delete from users where user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        String sql = "update users set user_name=?, user_password=?, user_birth=?, user_auth=?, user_point=? where user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserPassword());
            ps.setString(3, user.getUserBirth());
            ps.setString(4, user.getUserAuth().name());
            ps.setInt(5, user.getUserPoint());
            ps.setString(6, user.getUserId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        String sql = "update users set latest_login_at=? where user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            ps.setString(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "select count(*) from users where user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { return rs.getInt(1); }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    //추가
    @Override
    public List<User> findAll() {
        String sql = "SELECT name, birth_date, created_at, password FROM users";
        List<User> userList = new ArrayList<>();

        try (Connection conn = DbConnectionThreadLocal.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                userList.add(new User(
                        rs.getString("name"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        User.Auth.ROLE_USER,
                        0,
                        rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        null
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("회원 조회 중 DB 에러: " + e.getMessage(), e);
        }
        return userList;
    }
    private User mapToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("user_id"),
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_birth"),
                User.Auth.valueOf(rs.getString("user_auth")),
                rs.getInt("user_point"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
        );
    }
}