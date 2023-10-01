package com.epam.esm.repositories;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.utils.SortingOrderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repositories.CertificateSqlConstants.*;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificate> findAll(String sortField, String sort) {
        String sqlQuery = SQL_SELECT_ALL + SortingOrderUtil.addOrderBy(GiftCertificate.class, sortField, sort);
        return jdbcTemplate.query(sqlQuery,
                new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> findById(int id) {
        return jdbcTemplate.query(SQL_SELECT_ONE,
                new BeanPropertyRowMapper<>(GiftCertificate.class), id).stream().findAny();
    }

    @Override
    public List<GiftCertificate> searchByName(String name, String sortField, String sort) {
        String sqlQuery = SQL_SEARCH_BY_NAME + SortingOrderUtil.addOrderBy(GiftCertificate.class, sortField, sort);
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class),
                name + "%", name + "%");
    }

    @Override
    public List<GiftCertificate> searchByTagAndName(String tagName, String key, String sortField, String sort) {
        String sqlQuery = SQL_SEARCH_BY_TAG_N_NAME + SortingOrderUtil.addOrderBy(GiftCertificate.class, sortField, sort);
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class),
                tagName + "%", key + "%", key + "%");
    }

    @Override
    public int save(GiftCertificate certificate) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        return keyHolder.getKeys() == null ? 0 : (int) keyHolder.getKeys().get("id");
    }

    @Override
    public void update(int id, GiftCertificate certificate) {
        jdbcTemplate.update(SQL_UPDATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getLastUpdateDate(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public boolean existsById(int id) {
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_IDS, Integer.class, id);
        return count != null && count == 1;
    }
}
