package com.epam.esm.repositories;

import com.epam.esm.models.Tag;
import com.epam.esm.utils.SortingOrderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repositories.TagSqlConstants.*;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Tag> findAll(String sortField, String sortDirection) {
        String sqlQuery = SQL_SELECT_ALL + SortingOrderUtil.addOrderBy(Tag.class, sortField, sortDirection);
        return jdbcTemplate.query(sqlQuery,
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(int id) {
        return jdbcTemplate.query(SQL_SELECT_ONE,
                new BeanPropertyRowMapper<>(Tag.class), id).stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SQL_SELECT_BY_NAME,
                new BeanPropertyRowMapper<>(Tag.class), name).stream().findAny();
    }

    @Override
    public List<Tag> searchByName(String name, String sortField, String sortDirection) {
        String sqlQuery = SQL_SEARCH_BY_NAME + SortingOrderUtil.addOrderBy(Tag.class, sortField, sortDirection);
        return jdbcTemplate.query(sqlQuery,
                new BeanPropertyRowMapper<>(Tag.class), name + "%");
    }

    @Override
    public boolean existsId(int id) {
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_IDS, Integer.class, id);
        return count != null && count == 1;
    }

    @Override
    public boolean existsName(String name) {
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_NAMES, Integer.class, name);
        return count != null && count == 1;
    }

    @Override
    public int save(Tag tag) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKeys() == null ? 0 : (int) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(int id, Tag tag) {
        jdbcTemplate.update(SQL_UPDATE, tag.getName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }
}
