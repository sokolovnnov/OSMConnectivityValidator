package com.github.sokolovnnov.connectivitytest.repository.inMemory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sokolovnnov.connectivitytest.model.SimpleNode;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class IsolatedNodeStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<SimpleNode> simpleNodes = new ArrayList<>();


    public IsolatedNodeStorage() {
    }

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeInt(simpleNodes.size());
//        for (SimpleNode simpleNode : simpleNodes) {
//            out.writeObject(simpleNode);
//        }
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        List<SimpleNode> simpleNodes = new ArrayList<>();
//        int size = in.readInt();
//        for (int i = 0; i < size; i++) {
//            SimpleNode simpleNode = (SimpleNode) in.readObject();
//            simpleNodes.add(simpleNode);
//        }
//        this.simpleNodes = simpleNodes;
//    }


}
