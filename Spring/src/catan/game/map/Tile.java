package tilegraph;

import java.util.List;

public class Tile {
    
    private int ID;
    private List<Tile> neighbours;
    
    Tile(int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Tile> neighbours) {
        this.neighbours = neighbours;
    }
    
    public void addNeighbour(Tile neighbour){
        this.neighbours.add(neighbour);
    }

    @Override
    public String toString() {
        return "Tile{" + "ID=" + ID + '}';
    }
}
