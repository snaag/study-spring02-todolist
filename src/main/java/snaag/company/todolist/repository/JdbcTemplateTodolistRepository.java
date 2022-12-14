package snaag.company.todolist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import snaag.company.todolist.domain.TodoItem;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateTodolistRepository implements TodolistRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodolistRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TodoItem save(TodoItem todoItem) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todolist").usingGeneratedKeyColumns("id");

//        parameter 만들기 (HashMap)
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("text", todoItem.getText());
        parameter.put("done", false);

//        jdbcInsert 에 Parameter 넣고 쿼리 실행하기
        Number id = jdbcInsert.executeAndReturnKey(parameter);

//        반환할 todoItem 에, DB 로부터 받아온 id 넣어주기
        todoItem.setId(id.longValue());
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {
        return jdbcTemplate.query("select * from todolist", rowMapper());
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
//        .query 메소드를 사용하여, List 로 받음
        List<TodoItem> result = jdbcTemplate.query("select * from todolist where id=?", rowMapper(), id);
//
//        https://codechacha.com/ko/java8-stream-difference-findany-findfirst/
//        findAny()는 Stream에서 가장 먼저 탐색되는 요소를 리턴한다

        return result.stream().findAny();
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        // update 에 성공하면 1, 실패하면 0
        jdbcTemplate.update("update todolist set text=?, done=? where id=?",
                todoItem.getText(), todoItem.getDone(), todoItem.getId());

        return todoItem;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from todolist where id=?", id);

    }

    @Override
    public void clear() {
        jdbcTemplate.update("truncate table todolist");
    }

    private RowMapper<TodoItem> rowMapper() {
        return ((rs, rowNum) -> {
            TodoItem todoItem = new TodoItem();
            todoItem.setText(rs.getString("text"));
            todoItem.setDone(rs.getBoolean("done"));
            todoItem.setId(rs.getLong("id"));
            return todoItem;
        });
    }
}
