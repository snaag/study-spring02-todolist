package snaag.company.todolist.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import snaag.company.todolist.domain.TodoItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcTodolistRepository implements TodolistRepository {

    // dataSource 객체를 통해 DB connection 을 얻을 수 있음
    // 그리고 그걸로 spring bean 을 만들 수 있음 -> DI 가능
    private final DataSource dataSource;

    public JdbcTodolistRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TodoItem save(TodoItem todoItem) {
        String sql = "insert into todolist(text, done) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, todoItem.getText());
            pstmt.setBoolean(2, todoItem.getDone());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
                todoItem.setId(rs.getLong("id"));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return todoItem;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 연결 끊기
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<TodoItem> findAll() {
        return null;
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void clear() {

    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close (Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            if(pstmt != null) {
                pstmt.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
