package com.epam.esm.repositories;

import com.epam.esm.models.TagGiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.repositories.TagCertificateSqlConstants.*;

@Repository
@RequiredArgsConstructor
public class TagCertificateRepositoryImpl implements TagCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TagGiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL,
                new BeanPropertyRowMapper<>(TagGiftCertificate.class));
    }

    @Override
    public Optional<TagGiftCertificate> findByIds(int certificateId, int tagId) {
        return jdbcTemplate.query(SQL_SELECT_ONE,
                new BeanPropertyRowMapper<>(TagGiftCertificate.class), certificateId, tagId).stream().findAny();
    }

    @Override
    public List<TagGiftCertificate> findByTagId(int tagId) {
        return jdbcTemplate.query(SQL_SELECT_BY_TAG,
                new BeanPropertyRowMapper<>(TagGiftCertificate.class), tagId);
    }

    @Override
    public List<TagGiftCertificate> findByCertificateId(int certificateId) {
        return jdbcTemplate.query(SQL_SELECT_BY_CERTIFICATE,
                new BeanPropertyRowMapper<>(TagGiftCertificate.class), certificateId);
    }

    @Override
    public void save(int certificateId, int tagId) {
        jdbcTemplate.update(SQL_INSERT, certificateId, tagId);
    }

    @Override
    public void delete(int certificateId, int tagId) {
        jdbcTemplate.update(SQL_DELETE, certificateId, tagId);
    }

    @Override
    public boolean existsIds(int certificateId, int tagId) {
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_IDS, Integer.class, certificateId, tagId);
        return count != null && count == 1;
    }
}
