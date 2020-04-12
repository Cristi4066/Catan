package tilegraph;

public class TileGraph {

    public static void main(String[] args) {
        Tiles tiles = new Tiles(19);
        tiles.createTiles();
        tiles.createMatrix();
        tiles.printMatrix();
    }
    
}
