package ru.connectivitytest.validator;

import org.alex73.osmemory.IOsmNode;
import org.alex73.osmemory.MemoryStorage;
import org.alex73.osmemory.O5MReader;
import org.alex73.osmemory.OsmWay;
import ru.connectivitytest.site.model.SimpleNode;
import ru.connectivitytest.validator.model.AdjacencyList;
import ru.connectivitytest.validator.model.ValidationResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageUtil {

    private static final String SERIALIZE_PATH = "d:\\osmtmp\\serialized\\";
    private static final String PATH = "d:\\osmtmp\\";
    private static final float DIVIDER = 0.0000001F;

    public static ArrayList<OsmWay> readFromOM5toWays(String path) throws FileNotFoundException {
        MemoryStorage memoryStorage;
        try {
            memoryStorage = new O5MReader().read(new File(PATH + path));
        } catch (Exception e) {
            System.out.println("Файл .om5 не найден: " + path);
            throw new FileNotFoundException("Файл .om5 не найден:  " + path);
        }

        ArrayList<OsmWay> osmWays = new ArrayList<>();
        memoryStorage.byTag("highway", o -> {
            if (
//                    o.getTag("highway", memoryStorage).equals("service") ||
                    o.getTag("highway", memoryStorage).equals("unclassified") ||
                    o.getTag("highway", memoryStorage).equals("residential") ||
                    o.getTag("highway", memoryStorage).equals("tertiary") ||
                    o.getTag("highway", memoryStorage).equals("secondary") ||
                    o.getTag("highway", memoryStorage).equals("primary") ||
                    o.getTag("highway", memoryStorage).equals("motorway") ||
                    o.getTag("highway", memoryStorage).equals("trunk") ||
                    o.getTag("highway", memoryStorage).equals("living_street") ||
                    o.getTag("highway", memoryStorage).equals("motorway_link") ||
                    o.getTag("highway", memoryStorage).equals("trunk_link") ||
                    o.getTag("highway", memoryStorage).equals("primary_link") ||
                    o.getTag("highway", memoryStorage).equals("tertiary_link") ||
                    o.getTag("highway", memoryStorage).equals("secondary_link")
            )
                if (o instanceof OsmWay) osmWays.add((OsmWay) o);
        });
        memoryStorage.byTag("ferry", o -> {
            if (
                    o.getTag("ferry", memoryStorage).equals("unclassified") ||
                    o.getTag("ferry", memoryStorage).equals("residential") ||
                    o.getTag("ferry", memoryStorage).equals("tertiary") ||
                    o.getTag("ferry", memoryStorage).equals("secondary") ||
                    o.getTag("ferry", memoryStorage).equals("primary") ||
                    o.getTag("ferry", memoryStorage).equals("trunk")
            )
                if (o instanceof OsmWay) osmWays.add((OsmWay) o);
        });
        return osmWays;
    }

    public static List<SimpleNode> getIsolatedSimpleNodesByWays(ValidationResult result) throws Exception {
        MemoryStorage memoryStorage = new O5MReader().read(new File(PATH + result.getPath()));

        List<SimpleNode> nodes = new ArrayList<>();
        for (OsmWay osmWay : result.getIsolatedWays()) {
            long[] nodeIds = osmWay.getNodeIds();
            for (int i = 0; i < nodeIds.length; i++) {
                IOsmNode osmNode = memoryStorage.getNodeById(nodeIds[i]);
                nodes.add(new SimpleNode(null, osmNode.getId(), osmWay.getId(), i, osmNode.getLat() * DIVIDER,
                        osmNode.getLon() * DIVIDER));
            }
        }
        return nodes;
    }

    public static void serializeAdjList(AdjacencyList adjacencyList) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(SERIALIZE_PATH + adjacencyList.getRegionName());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(adjacencyList);
        objectOutputStream.close();
        System.out.println("========================================serializeAdjList: " + adjacencyList.getRegionName());
    }

    public static AdjacencyList deSerializeAdjList(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(SERIALIZE_PATH + path.substring(0, path.length() - 4));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        AdjacencyList adjacencyList = (AdjacencyList) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println("_____________________________________deSerializeAdjList: " + path);
        return adjacencyList;
    }
}
