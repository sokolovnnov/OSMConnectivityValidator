package com.github.sokolovnnov.connectivitytest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sokolovnnov.connectivitytest.model.AdjacencyList;
import com.github.sokolovnnov.connectivitytest.model.SimpleNode;
import com.github.sokolovnnov.connectivitytest.repository.inMemory.IsolatedNodeStorage;

import java.io.*;
import java.util.List;

public class SerializeUtils {

    private static final String SERIALIZE_PATH = "d:\\osmtmp\\serialized\\";
    private static final String SERIALIZE_JSON_PATH = "C:\\Users\\ILYA\\Downloads\\ZIPBackup1S\\osmserialized";
    private static final ObjectMapper mapper = new ObjectMapper();


    public static void serializeAdjList(AdjacencyList adjacencyList) throws IOException {
        String fileName = adjacencyList.getRegionName();
        FileOutputStream outputStream =
                new FileOutputStream(SERIALIZE_PATH + fileName.substring(0, fileName.length() - 4));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(adjacencyList);
        objectOutputStream.close();
    }

    public static AdjacencyList deSerializeAdjList(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(SERIALIZE_PATH + path.substring(0, path.length() - 4));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        AdjacencyList adjacencyList = (AdjacencyList) objectInputStream.readObject();
        objectInputStream.close();
        return adjacencyList;
    }

    public static void serializeInMemoryRepository(IsolatedNodeStorage isolatedNodeStorage) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(SERIALIZE_PATH + "repository");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(isolatedNodeStorage);
        objectOutputStream.close();
    }

    public static void toJson(List<SimpleNode> simpleNodes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(SERIALIZE_JSON_PATH + "jsonRepository");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        mapper.writeValue(bufferedOutputStream, simpleNodes);
    }
}
