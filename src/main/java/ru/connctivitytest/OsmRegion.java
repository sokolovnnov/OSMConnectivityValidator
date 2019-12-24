package ru.connctivitytest;

import org.alex73.osmemory.MemoryStorage;
import org.alex73.osmemory.O5MReader;

import java.io.File;

public class OsmRegion {

    String name;
    int ID;
    boolean isRussian;
    int[] neighbors;
    MemoryStorage o5M_Data;
    public AdjList adjList;

    public OsmRegion(int ID) {
        boolean notFind = true;
        for (RussianRegion russianRegion : RussianRegion.values()) {
            if (russianRegion.id == ID) {
                notFind = false;
                this.name = russianRegion.name();
                this.ID = russianRegion.getId();
                this.neighbors = russianRegion.getNeighbors();
                o5M_Data = loadO5mData(russianRegion.getPath());
            }
        }
        if (notFind) System.out.println("Ошибка: Регион по ID не найден..." + ID);
    }

    public OsmRegion(RussianRegion russianRegion) {
        this.name = russianRegion.name();
      //  this.isRussian = russianRegion.isRussian;
        this.ID = russianRegion.getId();
        this.neighbors = russianRegion.getNeighbors();
      //  this.outData = new ReadyData();
      //  this.outData.nameRegion = russianRegion.name();
       // if (!(russianRegion.id == 101 || russianRegion.id == 102)) //todo переделать
        o5M_Data = loadO5mData(russianRegion.getPath());
    }

    private MemoryStorage loadO5mData(String path) {
        MemoryStorage memoryStorage;
        try {
            memoryStorage = new O5MReader().read(new File(path));
        } catch (Exception e) {
            System.out.println("Файл не найден: " + path);
            return null;//fixme
        }
        return memoryStorage;
    }

    public AdjList getAdjList() {
        return adjList;
    }

    public void createAdjList() {
        this.adjList = new AdjList(Util.memoryStorageToWays(o5M_Data));
      //  o5M_Data = null;
        System.out.println("createAdjList() OK");
    }

   /* *//*  Метод возвращающий ArrayList всех значащих веев *//*
    public static ArrayList<OsmWay> dataToWays(MemoryStorage data) {
        ArrayList<OsmWay> ss = new ArrayList<>();
        data.byTag("highway", o -> {
            if (
                    o.getTag("highway", data).equals("service") ||
                            o.getTag("highway", data).equals("unclassified") ||
                            o.getTag("highway", data).equals("residential") ||
                            o.getTag("highway", data).equals("tertiary") ||
                            o.getTag("highway", data).equals("secondary") ||
                            o.getTag("highway", data).equals("primary") ||
                            o.getTag("highway", data).equals("motorway") ||
                            o.getTag("highway", data).equals("trunk") ||
                            o.getTag("highway", data).equals("living_street") ||
                            o.getTag("highway", data).equals("motorway_link") ||
                            o.getTag("highway", data).equals("trunk_link") ||
                            o.getTag("highway", data).equals("primary_link") ||
                            o.getTag("highway", data).equals("tertiary_link") ||
                            o.getTag("highway", data).equals("secondary_link")
            )
                if (o instanceof OsmWay) ss.add((OsmWay) o);
        });
        return ss;
    }*/

//    boolean isUseful() {
//        System.out.println("Проверка на isUseful " + this.name);
//        boolean result = false;
//        for (int idNeb : this.neighbors) {
//            System.out.println("Сосед " + idNeb + " = " + BaseTest.outerTestCompletedRegions[idNeb]);
//            if (BaseTest.outerTestCompletedRegions[idNeb] != true) {
//                System.out.println(" =>> тест не пройден");
//                result = true;
//                break;
//            }
//        }
//        if (this.ID ==13) result = false; //убрать
//        if (this.ID == 44) result = false;
//        if (!result) System.out.println(this.name + " isUseful!!!!");
//        return result;
//    }
}
