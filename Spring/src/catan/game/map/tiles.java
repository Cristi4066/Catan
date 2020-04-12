package tilegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tiles {
    
    private Map<Integer,List<Tile>> tiles;
    private int numberTiles;
    private boolean[][] adjentTiles;
    private int rings = 2;
    
    Tiles(int numberTiles){
        this.numberTiles = numberTiles;
    }
    
    public void createTiles(){
        tiles = new HashMap<Integer,List<Tile>>();
        List<Tile> ringTile = new ArrayList<>();
        ringTile.add(new Tile(0));
        tiles.put(0,ringTile);
        int countTiles = 1;
        int ring = 1;
        while(countTiles < numberTiles){
            int countRing = 6 * ring;
            ringTile = new ArrayList<>();
            for(int i = 1; i <= countRing; i++){
                ringTile.add(new Tile(countTiles));
                countTiles++;
            }
            tiles.put(ring,ringTile);
            ring++;
        }
       // for(int i = 0; i < 3; i++)
      //  System.out.println(tiles.get(i).toString());
    }
    
    public void createMatrix(){
        adjentTiles = new boolean[numberTiles][numberTiles];
        int ring = 1;
        int index = -1;
        int id;
        for(int i = 0; i < numberTiles; i++){
            
            if(i == 0){
                adjentTiles[0][1] = true;
                adjentTiles[0][2] = true;
                adjentTiles[0][3] = true;
                adjentTiles[0][4] = true;
                adjentTiles[0][5] = true;
                adjentTiles[0][6] = true;
                    
                adjentTiles[6][0] = true;
                adjentTiles[5][0] = true;
                adjentTiles[4][0] = true;
                adjentTiles[3][0] = true;
                adjentTiles[2][0] = true;
                adjentTiles[1][0] = true;
                tiles.get(0).get(0).setNeighbours(tiles.get(1));
            }
            else{
                index++;
                if(index >= ring * 6){
                    index = 0;
                    ring++;
                }
                if(index == 0){
                    id = tiles.get(ring).get(tiles.get(ring).size()-1).getID();
                }
                else {
                    id = tiles.get(ring).get(index - 1).getID();
                }
               // System.out.println( index);
                adjentTiles[i][id] = true;
                adjentTiles[id][i] = true;
                
                if(index == tiles.get(ring).size() - 1){
                    id = tiles.get(ring).get(0).getID();
                }
                else {
                    id = tiles.get(ring).get(index + 1).getID();
                }
                adjentTiles[i][id] = true;
                adjentTiles[id][i] = true;
                
                int adjentRing = ring + 1;
                if(adjentRing <= rings){
                    int adjentIndex = (index + 1) * adjentRing - 2;
                    id = tiles.get(adjentRing).get(adjentIndex).getID();
                    adjentTiles[i][id] = true;
                    adjentTiles[id][i] = true;
                    
                    if(adjentIndex == tiles.get(adjentRing).size() - 1){
                        id = tiles.get(adjentRing).get(0).getID();
                        }
                    else {
                        id = tiles.get(adjentRing).get(adjentIndex + 1).getID();
                    }
                    adjentTiles[i][id] = true;
                    adjentTiles[id][i] = true;
                    
                    if(adjentIndex == 0){
                        id = tiles.get(adjentRing).get(tiles.get(adjentRing).size()-1).getID();
                    }
                    else {
                        id = tiles.get(adjentRing).get(adjentIndex - 1).getID();
                    }
                    adjentTiles[i][id] = true;
                    adjentTiles[id][i] = true;
                }
                
            }
        }
    }
    
    public void printMatrix(){
        for(int i = 0; i < numberTiles; i++){
            for(int j = 0; j < numberTiles; j++){
                System.out.print(Integer.toString(j)+adjentTiles[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
