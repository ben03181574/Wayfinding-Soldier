import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
public class A1083341_checkpoint7_RouteFinder {
    //Description : The target block.
    private A1083341_checkpoint7_Block target;
    //Description : The hashmap that records the parent block.
    private HashMap<A1083341_checkpoint7_Block, A1083341_checkpoint7_Block> ParentBlock;
    //Description : Record which block has been visited.
    private boolean[][] visited ;
    // Description : The root frame.
    private A1083341_checkpoint7_GameFrame parentFrame;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083341_checkpoint7_Block[][] map;
    //Description : record the cost if you go on the block.
    private HashMap<A1083341_checkpoint7_Block, Integer> accumulatedCost;
    // Description : The route searching algorithm.
    private int algorithm;
    private A1083341_checkpoint7_Fringe fringe;
    private static final int DFS = 0;
    private static final int BFS = 1;
    private static final int UCS = 2;
    public A1083341_checkpoint7_RouteFinder(A1083341_checkpoint7_GameFrame parentFrame, A1083341_checkpoint7_Block target, A1083341_checkpoint7_Block root,int algorithm, A1083341_checkpoint7_Block[][] map){
        /**********************************The TODO This Time (Checkpoint7)**************************
         * 
         * TODO(1): For the TODO here, you have to implement fringe according "algorithm".
         * 
         * Hint(1): The BFS algorithm needs to use the queue to work, so we make a object named BlockQueue for BFS.
         * Hint(2): The DFS algorithm needs to use the stack to work, so we make a object named BlockStack for DFS.
         * Hint(3): The UCS algorithm needs to use the priority queue  to work, so we make a object named PriorityQueue for UCS.
         * Hint(4): These three objects all implement the fringe, and the detail description can be found 
         *          in the code of Fringe.java, BlockQueue.java, BlockStack.java, BlockPriorityQueue.java.
         * Hint(5): You have to add the root (the player current location) into fringe.
         * Hint(6): To calculate the priority, you have to implement a Comparator<block> object and make 
         *          it as an input in the constructor of BlockPriorityQueue.
         * Hint(7): Before starting the searching, you need to initialize the accumulatedCost and set the root with
         *          its cost.
         **********************************The End of the TODO**************************************/
        this.target = target;
        this.ParentBlock = new HashMap<A1083341_checkpoint7_Block, A1083341_checkpoint7_Block>();
        this.parentFrame = parentFrame;
        this.visited = new boolean[4096 / 256][4096 / 256];
        this.accumulatedCost = new HashMap<A1083341_checkpoint7_Block, Integer>();
        this.algorithm = algorithm;
        this.map = map;
        for(int x = 0 ; x < 4096 / 256; x++ ){
            for(int y = 0 ; y < 4096 / 256; y++ ){
                visited[x][y] = false;
            }
        }
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(algorithm == DFS){
            fringe = new  A1083341_checkpoint7_BlockStack();
        }else if(algorithm ==BFS){
            fringe = new  A1083341_checkpoint7_BlockQueue();
        }else if(algorithm ==UCS){
            fringe = new  A1083341_checkpoint7_BlockPriorityQueue(new BlockComparator());
        }
        fringe.add(root);
        visited[root.getX()][root.getY()]=true;
        accumulatedCost.put(root,root.getCost());
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    private A1083341_checkpoint7_Block search(){
        /*********************************The TODO (Checkpoint7)********************************
         * 
         * TODO(14.1): For the TODO here, you have to implement the searching funciton;
         * TODO(14.2): You MUST print the message of "Searching at (x, y)" in order to let us check if you sucessfully do it.
         * TODO(14.3): After you find the target, you just need to return the target block.
         * //System.out.println("Searching at ("+Integer.toString(YOURBLOCK.getX())+", "+Integer.toString(YOURBLOCK.getY())+")");
         * 
         * Hint(1): If the target can not be search you should return null(that means failure).
         * 
         * pseudo code is provided here: 
         *   1. get the block from fringe.
         *   2. print the message
         *   3. if that block equals target return it.
         *   4. if not, expand the block and insert then into fringe.
         *   5. return to 1. until the fringe does not have anyting in it.
         * 
         **********************************The End of the TODO**************************************/
        
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        while(!fringe.isEmpty()){
            A1083341_checkpoint7_Block temp = fringe.remove();
            System.out.println("Searching at ("+Integer.toString(temp.getX())+", "+Integer.toString(temp.getY())+")");
            if(temp.getX()==target.getX() && temp.getY()==target.getY()){
                return temp;
            }else{
                ArrayList<A1083341_checkpoint7_Block> tempList = expand(temp,ParentBlock,visited);                
                for(int i=0;i<tempList.size();i++){
                    fringe.add(tempList.get(i));
                }
            }
        }
        return null;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    private ArrayList<A1083341_checkpoint7_Block> expand(A1083341_checkpoint7_Block currentBlock,HashMap<A1083341_checkpoint7_Block, A1083341_checkpoint7_Block> ParentBlock, boolean[][] visited){
        /*********************************The TODO This Time (Checkpoint7)*****************************
         * 
         * TODO(15.1): For the TODO here, you have to implement the expand funciton and return the Blocks(successor);
         * TODO(15.2): the order that you expand is North(Up) West(Left) South(Down) East(Right).
         * TODO(15.3): before adding the block into successor, you have to check if it is valid by checkBlock().
         * TODO(15.4): For the TODO here, you have to calculate the cost of the path that the player walked from 
         * root to new blocks and set it into the HashMap accumulatedCost.
         * 
         * Hint(1): While the block is valid, before you add the block into successor, 
         *        you should set its ParentBlock (We prepare a HashMap to implement this).
         *        And you should also set it is visited. (We prepare 2D boolean array for you) (the (x,y) block <--> visited[x][y] )
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        ArrayList<A1083341_checkpoint7_Block> blockList = new ArrayList<A1083341_checkpoint7_Block>();

        if(currentBlock.getY()<1){
            A1083341_checkpoint7_Block north = null;
        }else{
            A1083341_checkpoint7_Block north = parentFrame.getMap()[currentBlock.getX()][currentBlock.getY()-1];
            if(!visited[north.getX()][north.getY()] && !parentFrame.ClickCheckGridLocation(north.getX(),north.getY(),false)){
                blockList.add(north);
                ParentBlock.put(north,currentBlock);
                visited[north.getX()][north.getY()]=true;
                accumulatedCost.put(north,north.getCost());
            }        
        }

        if(currentBlock.getX()<1){
            A1083341_checkpoint7_Block west = null;
        }else{
            A1083341_checkpoint7_Block west = parentFrame.getMap()[currentBlock.getX()-1][currentBlock.getY()];            
            if(!visited[west.getX()][west.getY()] && !parentFrame.ClickCheckGridLocation(west.getX(),west.getY(),false)){
                blockList.add(west);
                ParentBlock.put(west,currentBlock);
                visited[west.getX()][west.getY()]=true;
                accumulatedCost.put(west,west.getCost());
            }         
        }

        if(currentBlock.getY()>14){
            A1083341_checkpoint7_Block south = null;
        }else{
            A1083341_checkpoint7_Block south = parentFrame.getMap()[currentBlock.getX()][currentBlock.getY()+1];      
            if(!visited[south.getX()][south.getY()] && !parentFrame.ClickCheckGridLocation(south.getX(),south.getY(),false)){
                blockList.add(south);
                ParentBlock.put(south,currentBlock);
                visited[south.getX()][south.getY()]=true;
                accumulatedCost.put(south,south.getCost());
            }        
        }

        if(currentBlock.getX()>14){
            A1083341_checkpoint7_Block east = null;
        }else{
            A1083341_checkpoint7_Block east = parentFrame.getMap()[currentBlock.getX()+1][currentBlock.getY()];
            if(!visited[east.getX()][east.getY()] && !parentFrame.ClickCheckGridLocation(east.getX(),east.getY(),false)){
                blockList.add(east);
                ParentBlock.put(east,currentBlock);
                visited[east.getX()][east.getY()]=true;
                accumulatedCost.put(east,east.getCost());
            }        
        }

        return blockList;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }

    public A1083341_checkpoint7_RouteLinkedList createRoute(){
        /******************************The TODO This Time (Checkpoint7)*****************************
         * 
         * TODO(16): For the TODO here, you have to trace back the route and return the route;
         * 
         * Hint1: You can get the parent block of target by HashMap ParentBlock, thus you can calculate
         * the last step of the route. And then you get the parent block of  target, 
         * you can calculate the backward step and so on. 
         * 
         * presudo code is provided here:
         *      1. get parent block
         *      2. calculate the delta location
         *      3. insert into head
         *      4. make the target equals its parent block and so on.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083341_checkpoint7_Block destination = search();
        A1083341_checkpoint7_RouteLinkedList routeLinkedList = new A1083341_checkpoint7_RouteLinkedList();

        if(destination !=null){
            A1083341_checkpoint7_Block childBlock = destination;
            while(ParentBlock.containsKey(childBlock)){
                A1083341_checkpoint7_Block parentBlock = ParentBlock.get(childBlock);
                int deltaX = childBlock.getX()-parentBlock.getX();
                int deltaY = childBlock.getY()-parentBlock.getY();
                if(deltaX==0){
                    A1083341_checkpoint7_Node childNode = new A1083341_checkpoint7_Node(deltaY,1);
                    if(routeLinkedList.getHead()!=null){
                        A1083341_checkpoint7_Node parentNode = routeLinkedList.getHead();
                        routeLinkedList.insert(parentNode.getAxis(),parentNode.getDirection(),childNode.getAxis(),childNode.getDirection());    
                    }else{
                        routeLinkedList.setHead(childNode);
                    }                     
                }else if(deltaY==0){
                    A1083341_checkpoint7_Node childNode = new A1083341_checkpoint7_Node(deltaX,0);
                    if(routeLinkedList.getHead()!=null){
                        A1083341_checkpoint7_Node parentNode = routeLinkedList.getHead();
                        routeLinkedList.insert(parentNode.getAxis(),parentNode.getDirection(),childNode.getAxis(),childNode.getDirection());    
                    }else{
                        routeLinkedList.setHead(childNode);
                    }  
                }
                childBlock = parentBlock;
            }            
        }

        return routeLinkedList;

        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
}
class BlockComparator implements Comparator<A1083341_checkpoint7_Block>{
    public int compare(A1083341_checkpoint7_Block b1, A1083341_checkpoint7_Block b2) {
        return b1.getCost()-b2.getCost();
    }
}
