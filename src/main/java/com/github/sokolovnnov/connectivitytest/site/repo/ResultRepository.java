package com.github.sokolovnnov.connectivitytest.site.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.github.sokolovnnov.connectivitytest.site.model.SimpleNode;
import com.github.sokolovnnov.connectivitytest.site.model.SimpleWay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
//@Transactional(readOnly = true)
public class ResultRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcTemplate jdbcTemplate;

//    private  final SimpleCacheManager simpleCacheManager23;

    private static final RowMapper<SimpleNode> ROW_MAPPER = BeanPropertyRowMapper.newInstance(SimpleNode.class);

    public ResultRepository(JdbcTemplate jdbcTemplate/*, SimpleCacheManager simpleCacheManager23*/) {
        this.jdbcTemplate = jdbcTemplate;
//        this.simpleCacheManager23 = simpleCacheManager23;
    }

    @Cacheable("SimpleWayCache")
    public List<SimpleWay> getAll(int test) {


//        log.info("getAll-");

//        Long start = System.nanoTime();
//        slowQuery(8_000L);
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
//        log.info("Time getAll: " + (double)(System.nanoTime() - start)/1_000_000_000.0);
        return simpleWays;
    }

    private void slowQuery(long seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
