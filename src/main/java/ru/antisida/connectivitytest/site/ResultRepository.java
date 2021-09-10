package ru.antisida.connectivitytest.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.connectivitytest.site.model.SimpleNode;
import ru.antisida.connectivitytest.site.model.SimpleWay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class ResultRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<SimpleNode> ROW_MAPPER = BeanPropertyRowMapper.newInstance(SimpleNode.class);

    public ResultRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable("ways")
    public List<SimpleWay> getAll() {
        log.info("getAll");

        List<SimpleNode> simpleNodes =  jdbcTemplate.query("SELECT * FROM nodes ORDER BY way_osm_id DESC ", ROW_MAPPER);

        Collection<List<SimpleNode>> simpleNodeList = simpleNodes.stream()
                .collect(Collectors.groupingBy(simpleNode -> simpleNode.getWayOsmId()))
                .values();

        List<SimpleWay> simpleWays = new ArrayList<>();
        for (List<SimpleNode> list : simpleNodeList) {
            SimpleWay simpleWay = new SimpleWay();
            SimpleNode[] nodes = new SimpleNode[list.size()];
            for (SimpleNode simpleNode : list) {
                nodes[simpleNode.getOrderInWay()] = simpleNode;
            }
            simpleWay.setNodes(nodes);
            simpleWay.setOsmId(list.get(0).getWayOsmId());
            simpleWays.add(simpleWay);
        }
        return simpleWays;
    }
}
