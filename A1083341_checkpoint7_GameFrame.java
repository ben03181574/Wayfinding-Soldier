import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083341_checkpoint7_GameFrame extends JFrame {
    // Description : Width of Frame
    private int FWidth;
    // Description : Height of Frame
    private int FHeight;
    // Description : the displaysize of the map
    public int jfScaler = 2;
    private int houseCount=0;
    private int barrackCount=0;
    private int pyramidCount=0;
    // Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer, Image> obstacleImg = new HashMap<>();
    // Description : the filenames of the obstacle image set. bar_id -> filename
    private HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the obstacle location set queryed from database
    private ArrayList<Integer[]> obstacleDataStructure;
    // Description : the obstacle location set in GUI index version.
    private ArrayList<Integer[]> obstacleList;
    // Description : the object to query data.
    private A1083341_checkpoint7_QueryDB querydb;
    private static ArrayList<A1083341_checkpoint7_House> houseList = new ArrayList<A1083341_checkpoint7_House>();
    private static ArrayList<A1083341_checkpoint7_Barrack> barrackList = new ArrayList<A1083341_checkpoint7_Barrack>();
    private static ArrayList<A1083341_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083341_checkpoint7_Pyramid>();
    private static int PressedX = 0;
    private static int PressedY = 0;
    private static int ReleasedX = 0;
    private static int ReleasedY = 0;
    private static int ClickedX = 0;
    private static int ClickedY = 0;
    private static int keytype = 1;
    
    //Description : the cost of sand weight;
    private final int GRASSWEIGHT = 3;
    //Description : the cost of space weight;
    private final int SAPCEWEIGHT = 1;
    // Description : The main panel.
    public A1083341_checkpoint7_GamePanel gamePanel;
    // Description : The UI panel of spawnMenu.
    public A1083341_checkpoint7_SpawnMenu spawnMenu;
    // Description : The soldier that is selected.
    public A1083341_checkpoint7_Soldier selectedSoldier;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083341_checkpoint7_Block[][] map;
    // Description : The route searching algorithm.
    public  int algorithm;
    public A1083341_checkpoint7_GameFrame(int FWidth, int FHeight, String mapID, int jfScaler, int algorithm) throws HeadlessException {
        this.FWidth = FWidth;
        this.FHeight = FHeight;
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FWidth, FHeight);
        this.jfScaler = jfScaler;
        this.obstacleList = new ArrayList<Integer[]>();
        this.obstacleDataStructure = new ArrayList<Integer[]>();
        this.querydb = new A1083341_checkpoint7_QueryDB();
        this.querydb.setMapID(mapID);
        this.algorithm = algorithm;
        /********************************** The TODO (Checkpoint7) ********************************
         * 
         * TODO(1): This time you need to create a map  recording  the info. of every blocks.   
         * Hint 1: You could use "createMap" after using "toGUIIdx" to create the map. 
         * 
        ********************************** The End of the TODO **************************************/
        // TODO(Past): You need to get the obstacle from database and transform it into
        // GUI index version and set your map(panel) on the frame.
        // Hint: In order to build Hashmap obstacleImg, key means the bar_type from
        // database and value equals the Image class that load from the corresponding
        // filepath.
        // Hint2: To get the obstacle set from database, we need you to realize the
        // queryData() in the object QueryDB and get the result.
        // Hint3: obstacle is transformed by obstacleDataStructure via toGUIIdx() in
        // order to let the location transformed from database to panel location.(GUI
        // index version)
        // Hint4: ObstacleDataStructure is a Integer array ([row, column, bartype]) like
        // ArrayList.
        // Obstacle is a Integer array ([x_coordinate, y_coordinate, bartype]) like
        // ArrayList.
        // TODO(Past): This time you need to add a spawnMenu at the bottom of main frame, and set the parent frame.
        // Hint 1: You could use "BorderLayout.SOUTH" to add something at the bottom of main frame.

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        this.querydb.queryData(obstacleDataStructure,typeChar);
        this.typeChar=this.querydb.getObstacleImg();
        this.obstacleDataStructure=this.querydb.getObstacle();
        for (HashMap.Entry<Integer, String> each : typeChar.entrySet()) {
            obstacleImg.put(each.getKey(),new ImageIcon("Resource/"+each.getValue()).getImage());
        }
        A1083341_checkpoint7_GameFrame.toGUIIdx(this.obstacleDataStructure,this.obstacleList);
        map = createMap(16,16);
        gamePanel = new A1083341_checkpoint7_GamePanel(this.houseList,this.barrackList,this.pyramidList,this.obstacleList,this.obstacleImg,this.jfScaler); 
        spawnMenu =new A1083341_checkpoint7_SpawnMenu();
        spawnMenu.setParentFrame();

        add(gamePanel); 
        add(spawnMenu,BorderLayout.SOUTH);
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

        this.addComponentListener(new ComponentAdapter() {
            @Override
            // Description : While resizing the windows, the evnet will be happenned(Reset
            // the location of player).
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (houseList.size() != 0) {
                    int x = gamePanel.getWidth() / 2 - gamePanel.getCenterX();
                    int y = gamePanel.getHeight() / 2 - gamePanel.getCenterY();
                    for (A1083341_checkpoint7_House house : houseList) {
                        house.setLocation(x + house.getlocationX() * gamePanel.getGridLen(),
                                y + house.getlocationX() * gamePanel.getGridLen());
                    }
                }
            }
        });
        // TODO(Past): For key event here, you should implement key pressed here.
        // Hint1: For example, after pressing 'b', the building should change to barrack
        // when mouse clicked.
        // Hint2: You should get keyChar and set it into keytype.
        // Hint3: 'h' represents to house, 'b' represents to barrack, and others
        // represent to pyramid.
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed!");
                char key = e.getKeyChar();
                if (key == 'h') {
                    keytype = 1;
                } else if (key == 'b') {
                    keytype = 2;
                } else {
                    keytype = 3;
                }
            }

        });
        // TODO(Past): For mouse event here, you should implement map drag here.
        // Hint: For example, if you click on the top and release in the bottom, the map
        // should be dragged from up to down.
        // Hint: You should got both pressed location and release location and than
        // calculate the moving.
        gamePanel.addMouseListener(new MouseAdapter() {
            // Description : the event happenned while mouse be pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // TODO(Past) Get the location of mousePressed.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
                A1083341_checkpoint7_GameFrame.this.PressedX=e.getX();
                A1083341_checkpoint7_GameFrame.this.PressedY=e.getY();
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

            // Description : the event happenned while mouse be released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // TODO(Past). Get the location of mouseReleased.
                // TODO(Past) The map displacement will be calculated by Released location minus
                // Pressed location
                // TODO(Past) And then make the map moving by controlling it's location variable
                // and repaint the map via repaint() in object JPanel.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/

                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /********************************** The TODO (Past) ********************************
                 * TODO(Past): Aside from achieving all the functions before, you also need make the selected soldier
                 * move to the target destination.
                 * Hint1: Instead of directly calling the detectRoute() and startMove(), you should first get control
                 * of the thread of the selected soldier, then set the destination for the selected soldier, and finally
                 * use notify() to resume that thread.
                 * Hint2: Noted that you could only build when there are no soldiers being selected.
                 * Hint3: You could use "synchronized (selectedSoldier){}" to get the lock, then notify the other thread 
                 * that is waiting for this lock back to runnable state.
                 ***************************************** The End of the TODO**************************************/
                // TODO(Past): This time you need to implement the multi-thread in constructing
                // buildings! Once you click to build a house/barrack/pyramid, it starts a thread to build one. 
                // (Past)Hint: You should make a Thread object then make it start.
                // (Past)Hint: After you get the location that you clicked, use
                // ClickCheckMouseLocation() method to check if the place you clicked is available for
                // building.
                // (Past)Hint2: If the place you clicked is available, calculate the grid
                // locationX
                // and grid locationY where you clicked on map.
                // For example, if I clicked (2,1) on the map, then grid locationX is 2, grid
                // locationY is 1.
                // (Past)Hint3: There are total three diffirent types of building, including
                // house,
                // barrack and pyramid.According to diffirent keytype, diffirent types of
                // building object should be
                // initialized individually.
                // (Past)Hint4: For example, if keytype is 1, initialize a checkpoint7_House
                // object(int locationX, int locationY, String text, int scaler,int
                // horizontalAlignment) and set it into houseList.
                // Then add it into panel, use revalidate() method refresh panel and set
                // houseList into panel through setHouseList(ArrayList<checkpoint7_House>
                // houseList) method.
                // (Past)Hint5: The text of each type of building needs to be numbered in
                // ascending order of that type.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
                A1083341_checkpoint7_GameFrame.this.ClickedX=e.getX();
                A1083341_checkpoint7_GameFrame.this.ClickedY=e.getY();  
                int finalX =gamePanel.getCenterX()-gamePanel.getWidth()/2+ClickedX;  
                int finalY =gamePanel.getCenterY()-gamePanel.getHeight()/2+ClickedY;
                int locX=finalX/gamePanel.getGridLen();
                int locY=finalY/gamePanel.getGridLen(); 
                if(finalX<0||finalY<0){ locX=-1;locY=-1; }  
                ArrayList<A1083341_checkpoint7_Soldier> soldierList = gamePanel.getSoldierList();                    
                if(selectedSoldier!=null){  
                    synchronized(selectedSoldier){ 
                        selectedSoldier.setDestination(locX,locY);
                        selectedSoldier.notify();
                    }
                }else{
                    for(A1083341_checkpoint7_Soldier soldier:soldierList){
                        if(locY==soldier.getlocationY() && locX==soldier.getlocationX()){
                            selectedSoldier=soldier;
                            return;
                        }
                    }                    
                    if(!ClickCheckMouseLocation(e.getX(),e.getY(),true)){
                        if(A1083341_checkpoint7_GameFrame.this.keytype==1){
                            A1083341_checkpoint7_House house = new A1083341_checkpoint7_House(locX, locY, Integer.toString(houseCount), jfScaler,SwingConstants.RIGHT);
                            A1083341_checkpoint7_GameFrame.this.houseList.add(house); 
                            gamePanel.setHouseList(A1083341_checkpoint7_GameFrame.this.houseList);
                            gamePanel.add(house);
                            gamePanel.revalidate();
                            houseCount+=1;
                            Thread houseThread= new Thread(house);
                            houseThread.start();
                        }else if(A1083341_checkpoint7_GameFrame.this.keytype==2){
                            A1083341_checkpoint7_Barrack barrack = new A1083341_checkpoint7_Barrack(locX, locY, Integer.toString(barrackCount), jfScaler,SwingConstants.RIGHT);
                            A1083341_checkpoint7_GameFrame.this.barrackList.add(barrack);
                            gamePanel.setBarrackList(A1083341_checkpoint7_GameFrame.this.barrackList);
                            gamePanel.add(barrack);
                            gamePanel.revalidate();
                            barrackCount+=1;
                            Thread barrackThread= new Thread(barrack);
                            barrackThread.start();
                        }else{
                            A1083341_checkpoint7_Pyramid pyramid = new A1083341_checkpoint7_Pyramid(locX, locY, Integer.toString(pyramidCount), jfScaler,SwingConstants.RIGHT);
                            A1083341_checkpoint7_GameFrame.this.pyramidList.add(pyramid);
                            gamePanel.setPyramidList(A1083341_checkpoint7_GameFrame.this.pyramidList);
                            gamePanel.add(pyramid);
                            gamePanel.revalidate();
                            pyramidCount+=1;
                            Thread pyramidThread= new Thread(pyramid);
                            pyramidThread.start();
                        }              
                    }                    
                }    
                revalidate();
                repaint();  
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }
        });
        gamePanel.addMouseMotionListener(new MouseAdapter() {
            // Description : the event happenned while mouse be dragged.
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                // TODO(Past) we hope you can drag the map smoothly, you can override this
                // function
                // instead of mousePressed
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
                ReleasedX=e.getX();
                ReleasedY=e.getY();

                Integer moveX=ReleasedX-PressedX;
                Integer moveY=ReleasedY-PressedY;
                PressedX=e.getX();
                PressedY=e.getY();
                int finalX=(gamePanel.getCenterX())-moveX;

                int finalY=(gamePanel.getCenterY())-moveY;

                if(finalX > 0& finalX < 4096/jfScaler & finalY > 0 & finalY < 4096/jfScaler){
                    gamePanel.setCenterX(finalX);
                    gamePanel.setCenterY(finalY);
                    repaint(); 
                } 
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

        });
        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    // Description : transform the obstacle location from database version to GUI
    // index version
    // data is the database one, and the other.
    public static void toGUIIdx(ArrayList<Integer[]> data, ArrayList<Integer[]> dataGui) {
        for (Integer[] x : data) {
            dataGui.add(new Integer[] { x[1] - 1, x[0] - 1, x[2] });
        }
    }
    /********************************** The TODO (Checkpoint7) ********************************
    * TODO(2): At this time, grass isn't an obstacles, so you have to return false at the situation.
    * 
    /********************************** The TODO (Past) ********************************
    * TODO(Past): Here you will implement the method to check if the grid location passed in is empty.
    * If the location is empty, return false, else return true. The variable "isBuilding" is to check 
    * if you need to take building's  construction scope into consideration. If the "isBuilding" is true,
    * it means that now it's going to build a building, you need to take this building's construction scope
    * into consideration. On the other hand, you only need to check that grid location is empty or not, if
    * "isBuilding" is false.
    * Rules: There are several situations that will cause the location is not empty.
    * 1. There are obstacles on the location.
    * 2. There are buildings on the location.
    * 3. There are soldiers on the location.
    * Hint 1: There are diffirent construction scope for diffirent types of building.
    * Houses are 1*1 grid; Barracks are 1*1 grid; Pyramids are 2*2 grid.
    * Hint 2: You should consider about diffirent types of buildings' situation
    * while checking if there exists obstacle or building in buildings' construction scope.
    * For example, pyramid construction scope is 2*2, In other words, there should
    * be empty in pyramid construction scope.
    ***************************************** The End of the TODO**************************************/
    private boolean locationVarify(int locationX,int locationY,boolean isBuilding){
        /********************************************************************************************
        * START OF YOUR CODE
        ********************************************************************************************/
        ArrayList<A1083341_checkpoint7_Soldier> soldierList = gamePanel.getSoldierList();
        if(isBuilding){
            if(A1083341_checkpoint7_GameFrame.keytype==1 || A1083341_checkpoint7_GameFrame.keytype==2){
                if(locationX>=16||locationX<0||locationY<0||locationY>=16){
                    return true;
                }
                for(Integer[] element:obstacleList){
                    if(element[2]==2){
                        continue;
                    }
                    if(locationY==element[1] && locationX==element[0]){
                        return true;
                    }
                }
                for(A1083341_checkpoint7_House house:houseList){
                    if(locationY==house.getlocationY() && locationX==house.getlocationX()){
                        return true;
                    }
                }  
                for(A1083341_checkpoint7_Barrack barrack:barrackList){
                    if(locationY==barrack.getlocationY() && locationX==barrack.getlocationX()){
                        return true;
                    }
                } 
                for(A1083341_checkpoint7_Pyramid pyramid:pyramidList){
                    if(pyramid.getlocationX()<=locationX && pyramid.getlocationX()>=locationX-1 && pyramid.getlocationY()<=locationY && pyramid.getlocationY()>=locationY-1){
                        return true;
                    }
                }for(A1083341_checkpoint7_Soldier soldier:soldierList){
                    if(locationY==soldier.getlocationY() && locationX==soldier.getlocationX()){
                        return true;
                    }
                }         
            }else{
                if(locationX>=15||locationX<0||locationY<0||locationY>=15){
                    return true;
                }
                for(Integer[] element:obstacleList){
                    if(element[2]==2){
                        continue;
                    }
                    if(element[1]>=locationY && element[1] <=locationY+1 && element[0]>=locationX && element[0]<=locationX+1){
                        return true;
                    }
                }
                for(A1083341_checkpoint7_House house:houseList){
                    if(house.getlocationY()>=locationY && house.getlocationY() <=locationY+1 && house.getlocationX()>=locationX && house.getlocationX()<=locationX+1){
                        return true;
                    }
                }  
                for(A1083341_checkpoint7_Barrack barrack:barrackList){
                    if(barrack.getlocationY()>=locationY && barrack.getlocationY() <=locationY+1 && barrack.getlocationX()>=locationX && barrack.getlocationX()<=locationX+1){
                        return true;
                    }
                }
                for(A1083341_checkpoint7_Pyramid pyramid:pyramidList){
                    if(pyramid.getlocationX()<=locationX+1 && pyramid.getlocationX()>=locationX-1 && pyramid.getlocationY()<=locationY+1 && pyramid.getlocationY()>=locationY-1){
                        return true;
                    }
                }for(A1083341_checkpoint7_Soldier soldier:soldierList){
                    if(soldier.getlocationX()<=locationX+1 && soldier.getlocationX()>=locationX && soldier.getlocationY()<=locationY+1 && soldier.getlocationY()>=locationY ){
                        return true;
                    }
                }              
            }            
        }else{
            if(locationX>=16||locationX<0||locationY<0||locationY>=16){
                return true;
            }
            for(Integer[] element:obstacleList){
                if(element[2]==2){
                    continue;
                }
                if(locationY==element[1] && locationX==element[0]){
                    return true;
                }
            }
            for(A1083341_checkpoint7_House house:houseList){
                if(locationY==house.getlocationY() && locationX==house.getlocationX()){
                    return true;
                }
            }  
            for(A1083341_checkpoint7_Barrack barrack:barrackList){
                if(locationY==barrack.getlocationY() && locationX==barrack.getlocationX()){
                    return true;
                }
            } 
            for(A1083341_checkpoint7_Pyramid pyramid:pyramidList){
                if(pyramid.getlocationX()<=locationX && pyramid.getlocationX()>=locationX-1 && pyramid.getlocationY()<=locationY && pyramid.getlocationY()>=locationY-1){
                    return true;
                }
            }for(A1083341_checkpoint7_Soldier soldier:soldierList){
                if(locationY==soldier.getlocationY() && locationX==soldier.getlocationX()){
                    return true;
                }
            }    
        }
        return false;
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    /********************************** The TODO (Past) ********************************
    * 
    * TODO(Past): Here you need to check if the passed in location is empty or not, and the passed in variable
    * "isBuilding" is to determine if it needs to take buildings' construction scope into consideration.
    * You should return false if the location is empty.
    * The location passed in is in grid location format, so you could directly call locationVarify().
    * Hint 1: Grid location format is something like-->[0,0] ,[2,8].
    ***************************************** The End of the TODO**************************************/
    public boolean ClickCheckGridLocation(int locationX, int locationY,boolean isBuilding) {
        /********************************************************************************************
        * START OF YOUR CODE
        ********************************************************************************************/
        return locationVarify(locationX,locationY,isBuilding); 
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }
    /********************************** The TODO (Past) ********************************
    * 
    * TODO(Past): Here you need to check if the passed in location is empty or not  and the passed in variable
    * "isBuilding" is to determine if it needs to take buildings' construction scope into consideration.
    * You should return false if the location is empty.
    * The location passed in is in mouse location format, you should convert it into grid location
    * format before calling locationVarify().
    *
    ***************************************** The End of the TODO**************************************/
    public boolean ClickCheckMouseLocation(int ClickX, int ClickY,boolean isBuilding) {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        int finalX =gamePanel.getCenterX()-gamePanel.getWidth()/2+ClickX;  
        int finalY =gamePanel.getCenterY()-gamePanel.getHeight()/2+ClickY;
        int locX=finalX/gamePanel.getGridLen();
        int locY=finalY/gamePanel.getGridLen(); 
        if(finalX<0||finalY<0){
            locX=-1;
            locY=-1;
        } 
        return locationVarify(locX,locY,isBuilding);
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }
    //Description : create the map, if each of the loaction will be tag as "grass", "obstacle" or "space".
    public A1083341_checkpoint7_Block[][] createMap(int height,int width){
        A1083341_checkpoint7_Block[][] map = new A1083341_checkpoint7_Block[width][height];
        for (Integer[] block: obstacleList){
            int cost = block[2] == 2 ? GRASSWEIGHT : 100;
            String type = block[2] == 2 ? "grass" : "obstacle";
            map[block[0]][block[1]] = new A1083341_checkpoint7_Block(block[0], block[1], type, cost);
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(map[x][y] == null){
                    map[x][y] = new A1083341_checkpoint7_Block(x,y,"space",SAPCEWEIGHT);
                }
            }
        }
        return map;
    }
    public A1083341_checkpoint7_Block[][] getMap(){
        return this.map;
    }
}
