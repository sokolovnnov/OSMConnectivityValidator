package ru.antisida.connectivitytest.validator.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.connectivitytest.validator.model.OsmRegion;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RegionJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public RegionJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<OsmRegion> rowMapper = new RowMapper<>() {
        @Override
        public OsmRegion mapRow(ResultSet rs, int rowNum) throws SQLException {
            OsmRegion region = new OsmRegion();
            region.setId(rs.getInt("id"));
            region.setName(rs.getString("name"));
            region.setPath(rs.getString("path"));
            region.setRussian(rs.getInt("id") < 100/* || rs.getInt("id") == 300*/);
            Array arr = rs.getArray("neighbors");
            System.out.println(arr);
            Integer[] a = (Integer[]) arr.getArray();
            region.setNeighbors(a);
            return region;
        }
    };

    public OsmRegion get(int id) {
        List<OsmRegion> regions = jdbcTemplate.query(
                "SELECT * FROM regions WHERE id = ?", rowMapper, id);
        return DataAccessUtils.singleResult(regions);
    }

    public List<OsmRegion> getAll() {
        return jdbcTemplate.query("SELECT * FROM regions ORDER BY id", rowMapper);
    }
}
