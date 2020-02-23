package com.nayak.samplespringbootapp.repository;

import com.nayak.samplespringbootapp.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {
    // Trying both Named and normal jdbc template...
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("select * from student", new StudentMapper());
    }

    @Override
    public Student findStudentById(long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return namedParameterJdbcTemplate.queryForObject("select * from student where id=:id", map, new StudentMapper());
    }

    @Override
    public long update(Student student) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", student.getId());
        map.put("name", student.getName());
        map.put("age", student.getAge());
        map.put("height", student.getHeight());
        map.put("weight", student.getWeight());
        namedParameterJdbcTemplate.update("update sashelp set name=:name, age=:age, height=:height , weight=:weight where id=:id", map);

        return student.getId();
    }

    @Override
    public long save(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement preparedStatement = connection.prepareStatement("insert into student(name, age, height, weight) values(?,?,?,?)");
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setFloat(3, student.getHeight());
            preparedStatement.setFloat(4, student.getWeight());
            return preparedStatement;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from sashelp where id=?", id);
    }

    static class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            return Student.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .age(resultSet.getInt("age"))
                    .height(resultSet.getFloat("height"))
                    .weight(resultSet.getFloat("weight"))
                    .build();
        }
    }
}
