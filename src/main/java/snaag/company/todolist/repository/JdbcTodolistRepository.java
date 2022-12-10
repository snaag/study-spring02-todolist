package snaag.company.todolist.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import snaag.company.todolist.domain.TodoItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from todolist";
        List<TodoItem> store = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                TodoItem item = new TodoItem();

                String text = rs.getString("text");
                Boolean done = rs.getBoolean("done");
                Long id = rs.getLong("id");

                item.setText(text);
                item.setId(id);
                item.setDone(done);

                store.add(item);
            }
            return store;
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from todolist where id=?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            TodoItem item = new TodoItem();

            while(rs.next()) {
                if(rs.getLong("id") == id) {
                    item.setId(rs.getLong("id"));
                    item.setText(rs.getString("text"));
                    item.setDone(rs.getBoolean("done"));
                    return Optional.of(item);
                }
            }
            return Optional.empty();
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "update todolist set text=?, done=? where id=?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, todoItem.getText());
            pstmt.setBoolean(2, todoItem.getDone());
            pstmt.setLong(3, todoItem.getId());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            TodoItem item = new TodoItem();

            while(rs.next()) {
                item.setText(rs.getString("text"));
                item.setId(rs.getLong("id"));
                item.setDone(rs.getBoolean("done"));

                break;
            }

            return item;
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void delete(Long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "delete from todolist where id=?";

        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    @Override
    public void clear() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "truncate table todolist";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        } catch (Exception e) {

        } finally {
            close(conn, pstmt, null);
        }
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
