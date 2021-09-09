package ru.connectivitytest.validator.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.connectivitytest.site.model.SimpleNode;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class IsolatedWayJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insert;

    public IsolatedWayJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("nodes")
                .usingGeneratedKeyColumns("id");
    }

    public void save(SimpleNode simpleNode) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("osm_id", simpleNode.getOsmId())
                .addValue("way_osm_id", simpleNode.getWayOsmId())
                .addValue("order_in_way", simpleNode.getOrderInWay())
                .addValue("lat", simpleNode.getLat())
                .addValue("lon", simpleNode.getLon());
        Number newId = insert.executeAndReturnKey(map);
        simpleNode.setId(newId.intValue());
    }

    public void saveAll(List<SimpleNode> simpleNodes) {
        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(simpleNodes);
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO nodes (osm_id, way_osm_id, order_in_way, lat, lon) " +
                                               "VALUES (:osmId, :wayOsmId, :orderInWay, :lat, :lon) " +
                                               "ON CONFLICT DO NOTHING", sqlParameterSources);
    }
}
