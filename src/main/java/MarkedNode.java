import java.util.HashSet;
import java.util.Objects;

public class MarkedNode {

    private long id;
    private long wayId;
    public long[] wayIds;
    private boolean visited;
    private boolean isolated;
    private int connectedComponentId;
    private int connectedComponentSize;
 //   public HashSet<Long> neighbours;
    public long[] nghbrs;

    public MarkedNode(long id, long wayId, long[] nghbrs) {
        this.id = id;
        this.wayIds = new long[]{wayId};
        this.wayId = wayId; //todo от этого поля нужно избавитсь
        this.nghbrs = nghbrs;
    }

 /*   public MarkedNode(long id, HashSet<Long> neighbours, long wayId) {
        this.id = id;
        this.neighbours = neighbours;
        this.wayId = wayId;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWayId() {
        return wayId;
    }

    public void setWayId(long wayId) {
        this.wayId = wayId;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getConnectedComponentId() {
        return connectedComponentId;
    }

    public void setConnectedComponentId(int connectedComponentId) {
        this.connectedComponentId = connectedComponentId;
    }




    public int getConnectedComponentSize() {
        return connectedComponentSize;
    }

    public void setConnectedComponentSize(int connectedComponentSize) {
        this.connectedComponentSize = connectedComponentSize;
    }

    public boolean isIsolated() {
        return isolated;
    }

    public void setIsolated(boolean isolated) {
        this.isolated = isolated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkedNode that = (MarkedNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MarkedNode{" +
                "id=" + id +
                ", wayId=" + wayId +
                ", visited=" + visited +
                ", connectedComponentId=" + connectedComponentId +
                '}';
    }
}
