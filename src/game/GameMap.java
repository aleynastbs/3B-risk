package game;

import game.player.Area;
import game.player.Territory;

public class GameMap {

    private static GameMap instance;
    public static final int TOTAL_TERRITORY_COUNT = 47;
//    private static final int EAST_TERRITORY_COUNT = 10;
//    private static final int ISLAND_TERRITORY_COUNT = 5;
//    private static final int UPPER_MAIN_TERRITORY_COUNT = 16;
//    private static final int LOWER_MAIN_TERRITORY_COUNT = 16;
    private static final String[] TERRITORY_NAMES = {"East Dorms","East Sports Center", "East Library", "Prep Buildings", "Health Center", "East Cafeteria", "ATM",
            "East Coffee Break", "East Mozart Cafe", "East Entrance", "Bilkent 1 & 2", "Sports International", "Ankuva", "Bilkent Center", "Bilkent Hotel", "MSSF",
            "Concert Hall", "Dorms", "V Building", "F Buildings", "Dorm 76", "Mescit", "Starbucks", "M Building", "Meteksan", "Sports Center", "Nanotam",
            "Mayfest", "A Building", "S Building", "T Building", "G Building", "Coffee Break", "Square", "CafeIn", "Statue", "B Building", "Cyber Park", "ODEON",
            "Library", "Mozart Cafe", "Cafeteria", "EA Building", "Meteksan", "EE Building", "Mithat Coruh", "Entrance"};

    // A list of adjacent territories' ids for each territory
    private static final int[][] ADJACENT_TERRITORIES = {{1,3,4,15},{0,2,3},{1,3,5},{0,1,2,4,5},{0,3,5,6,27},{2,3,4,6,7,8},{4,5,7},{5,6,8,9},{5,7,9},{7,8,10},
            {9,11,28},{10,12},{11,13},{12,14,43},{13},{0,16},{15,21},{18,19,20},{17,19,21,22,23},{17,18,20,23,24,25},{17,19,25,26},{16,18,22,27},{18,21,23,27},
            {18,19,22,24,27,28},{19,23,25,28,29},{19,20,24,26,29},{20,25},{4,21,22,23},{10,23,24,29,30},{24,25,28,30},{28,29,32,33},{32,34,35},{30,31,33,35},
            {30,32,35,36},{31,35,39},{31,32,33,34,36,39},{33,35,37,39,40},{36},{41},{34,35,36,40,41,42},{36,39},{38,39,42,44},{39,41,44,45},{13,44,46},{41,42,43,45,46},{42,44},{43,44}};
    private Territory[] territories;

    //Area adjustments
    private Area east;
    private Area island;
    private Area upperMain;
    private Area lowerMain;

    // Initialize new game
    private GameMap(){
    }

    public Territory getTerritory(int index){
        return territories[index];
    }

    public static GameMap getInstance() {
        if (instance == null) {
            synchronized (GameMap.class) {
                if (instance == null) {
                    instance = new GameMap();
                }
            }
        }
        return instance;
    }

    public void init(){
        initAreas();
        initTerritories();
    }


    // Used to load from save file
    public void init(int[] troops, int[] rulers){
        for(int i = 0; i < territories.length; i++){
            territories[i].setNumOfArmies(troops[i]);
            territories[i].setRuler(GameEngine.getInstance().getPlayers().get(rulers[i]));
            GameEngine.getInstance().getPlayers().get(rulers[i]).increaseTerritory();
        }
    }

    private void initAreas(){
        east = new Area("East Campus");
        island = new Area("Bilkent Island");
        upperMain = new Area("Upper Main Campus");
        lowerMain = new Area("Lower Main Campus");
    }

    private void initTerritories(){
        territories = new Territory[TOTAL_TERRITORY_COUNT];

        // Initialize territories
        for(int i = 0; i <TOTAL_TERRITORY_COUNT; i++){
            if(i < 10){
                //Area adjustments
                //territories[i] = new Territory(Area.EASTCAMPUS, TERRITORY_NAMES[i], i);
                territories[i] = new Territory(east, TERRITORY_NAMES[i], i);
                east.addTerritory(territories[i]);
            }else if ( i < 15){
                //territories[i] = new Territory(Area.BILKENTISLAND, TERRITORY_NAMES[i], i);
                territories[i] = new Territory(island, TERRITORY_NAMES[i], i);
                island.addTerritory(territories[i]);
            }else if ( i < 31){
                //territories[i] = new Territory(Area.UPPERMAINCAMPUS, TERRITORY_NAMES[i], i);
                territories[i] = new Territory(upperMain, TERRITORY_NAMES[i], i);
                upperMain.addTerritory(territories[i]);
            }else{
                //territories[i] = new Territory(Area.LOWERMAINCAMPUS, TERRITORY_NAMES[i], i);
                territories[i] = new Territory(lowerMain, TERRITORY_NAMES[i], i);
                lowerMain.addTerritory(territories[i]);
            }
        }

        // Initialize Adjacent
        for(int i = 0; i <TOTAL_TERRITORY_COUNT; i++) {
            for(int id : ADJACENT_TERRITORIES[i]){
                territories[i].addAdjacentTerritory(territories[id]);
            }
        }
    }

    public Territory[] getTerritories() {
        return territories;
    }
    public Area[] getAreas() {
        Area[] areas = {east, island, upperMain, lowerMain};
        return areas;
    }
}