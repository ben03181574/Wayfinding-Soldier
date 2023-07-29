import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Description : This class will implement Runnable and checkpoint7_Soldier_Movement.(may need to rename)
public class A1083341_checkpoint7_Soldier extends JLabel implements Runnable, A1083341_checkpoint7_Soldier_Movement {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : the normal image size.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    // Description : The image of progress to grow up.
    private ImageIcon[] growingIcons;
    // Description : The root frame.
    private A1083341_checkpoint7_GameFrame parentFrame;
    // Description : To check if this soldier is selected.
    private boolean isSelected;
    // Description : To check if this soldier is a grown up.
    private boolean isGrown;
    // Description : To store the route to get to the destination.
    private A1083341_checkpoint7_RouteLinkedList route = new A1083341_checkpoint7_RouteLinkedList();
    // Description : The grid location of destination.
    private int destinationX, destinationY;
    //Description : UCS : 2, BFS : 1, DFS : 0
    private int algorithm;

    /********************************** The TODO (Past) ********************************
     * 
     * TODO(past): Aside from the "soldier.png", you also need to read in "baby0.png","baby1.png",
     * "baby2.png","baby3.png","baby4.png", and"baby5.png".You may store them into growingIcons.
     * Also,you need to add a mouse listener to detect the mouse click event.When the soldier is clicked
     * you need to change the variable "isSelected" and use "setSelectedSoldier()" or "resetSelectedSoldier()".
     * Noted that a soldier can't be selected while he/she is still a baby.Also,there could be only one selected
     * soldier on the field.
     * Hint1: When a selected soldier being clicked, you need to unselect the soldier.
     ********************************** The End of the TODO**************************************/
    public A1083341_checkpoint7_Soldier(int locationX, int locationY, int scaler, int horizontalAlignment, int algorithm) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/soldier.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);
        this.isSelected = false;
        this.isGrown = false;
        this.algorithm = algorithm; 
        setParentFrame();
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        growingIcons = new ImageIcon[6];
        for(int i=0;i<6;i++){
            growingIcons[i] = new ImageIcon("Resource/baby"+i+".png");
            Image temp = growingIcons[i].getImage();
            temp = temp.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
            growingIcons[i].setImage(temp);
        }
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setParentFrame();
                if(route.length()==0){
                    if(parentFrame.selectedSoldier==null || parentFrame.selectedSoldier==getSelf()){
                        if(isGrown){
                            if(isSelected){
                                isSelected=false;
                                resetSelectedSoldier();
                            }else{
                                isSelected=true;
                                setSelectedSoldier();
                            }
                        }                    
                    }else{
                        parentFrame.selectedSoldier.isSelected=false;
                        parentFrame.selectedSoldier.repaint();
                        resetSelectedSoldier();
                        isSelected=true;
                        setSelectedSoldier();
                    }
                    repaint();                    
                }

            }
        });       
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }


    /*********************************** The TODO (Past) ********************************
     * 
     * TODO(past): Upon a soldier is spawn, you needs to change the soldier's picture from 'baby0.png' to 
     * 'baby5.png' every 0.5 secs, and finally you need to set 'soldier.png' as his/her icon. After that 
     * you need to set 'isGrown' to true. Last, this thread will turn into waiting state until being 
     * notified. Also, upon being notified, this thread will first detecting the route then starting to move,
     * and finally turn back to waiting state when the soldier gets to the destination.
     * Hint 1: The process will be like -> 'baby0.png' ->wait 0.5secs -> 'baby1.png'->wait 0.5secs ->
     *  'baby2.png'->wait 0.5secs -> 'baby3.png'->wait 0.5secs -> 'baby4.png'->wait 0.5secs -> 'baby5.png'
     * ->wait 0.5secs -> 'soldier.png'.
     * Hint 2:You could use "synchronized (this){}" to get the lock so as to making 
     * this thread wait(). As wait() being executed, the thread will turn in to waiting state then 
     * release the lock. 
     * 
     ********************************** The End of the TODO**************************************/
    @Override
    public void run() {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        for(int i=0;i<6;i++){
            this.setIcon(growingIcons[i]);
            doNothing(500);
        }
        isGrown=true;
        this.setIcon(icon);
        synchronized(this){
            while(true){
                try{
                    wait();
                    isSelected=false;
                    resetSelectedSoldier();
                    if(!(destinationX<0 ||destinationX>15 || destinationY>15||destinationY<0)){
                        detectRoute();
                        startMove();
                    }
                }catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

            }
        }       
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    // Description : Stop the thread in milliseconds.
    private static void doNothing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Unexpected interruption");
            System.exit(0);
        }
    }
    /*********************************The TODO This Time (Checkpoint7)***************************
     * 
     * TODO(11): This time you have to instantiate "checkpoint7_RouteFinder" , 
     * and get "route" by method "route = routefinder.createRoute();"
     * Hint1: You could change variable "isSelected" and use method resetSelectedSoldier().
     * Hint2: You could get root block and target block from "parentFrame.getMap()".
     * 
     ********************************** The End of the TODO**************************************/
    
    @Override
    public void detectRoute() {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/

        A1083341_checkpoint7_Block[][] map = parentFrame.getMap();
        A1083341_checkpoint7_Block target = map[destinationX][destinationY];
        A1083341_checkpoint7_Block root= map[locationX][locationY];
        A1083341_checkpoint7_RouteFinder routefinder = new A1083341_checkpoint7_RouteFinder(parentFrame,target,root,algorithm,map);       
        route = routefinder.createRoute();
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }
    /*******************************The TODO This Time (Checkpoint7)******************************
     * 
     * TODO(12): After detecting the route, the soldier starts to move to the destination based on the route.
     * There should be a 0.5 seconds pause between each movement.
     * Hint1: You could use "ClickCheckGridLocation()" to check whether the next grid is empty. 
     * 
     ********************************** The End of the TODO**************************************/
    @Override
    // @Override
    public void startMove() {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        while(route.length()>0){
            A1083341_checkpoint7_Node step = route.getHead();
            int direction = step.getDirection();
            int axis = step.getAxis();
            if(axis==0){
                if(!parentFrame.ClickCheckGridLocation(locationX+direction,locationY,false)){
                    locationX = locationX+direction;
                    locationY = locationY;
                    repaint();
                    revalidate();
                    doNothing(500);
                }else{
                    route =new A1083341_checkpoint7_RouteLinkedList();
                }
            }else if(axis==1){
                if(!parentFrame.ClickCheckGridLocation(locationX,locationY+direction,false)){                
                    locationY = locationY+direction;
                    locationX = locationX;
                    repaint();
                    revalidate();
                    doNothing(500);
                }else{
                    route =new A1083341_checkpoint7_RouteLinkedList();
                }
            }
            route.delete(axis,direction);
        }   
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected && isGrown) //Draw a rectangle border around the selected soldier.
            g.drawRect(0, 0, getImage().getWidth(null) - 1, getImage().getHeight(null) - 1);
    }

    // Description : Set the root frame.
    public void setParentFrame() {
        this.parentFrame = (A1083341_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }

    // Description : Set the destination in grid location format.
    public void setDestination(int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    // Description : Set selected soldier.
    public void setSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = this;
    }

    // Description : Reset selected soldier.
    public void resetSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = null;
    }
    // Description : Return current position X.
    public int getlocationX() {
        return this.locationX;
    }
    // Description : Set current position X.
    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }
    // Description : Return current position Y.
    public int getlocationY() {
        return this.locationY;
    }
    // Description : Set current position Y.
    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }
    // Description : Return self.
    public A1083341_checkpoint7_Soldier getSelf(){
        return this;
    }

}
