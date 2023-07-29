import java.util.*;

public class A1083341_checkpoint7_BlockPriorityQueue implements A1083341_checkpoint7_Fringe {
    PriorityQueue<A1083341_checkpoint7_Block> priorityQueue;
    //Description : the constuctor of BlockPriorityQueue.
    
    public A1083341_checkpoint7_BlockPriorityQueue(Comparator c){
        //The TODO(5) This Time (Checkpoint7) : Initialize the PriorityQueue.
        //Hint1: While initializing the PriorityQueue, you have to input the comparator to the constructor.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        priorityQueue = new PriorityQueue<A1083341_checkpoint7_Block>(c);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public void add(A1083341_checkpoint7_Block block){
        //The TODO(5) This Time (Checkpoint7) : add block into the PriorityQueue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        priorityQueue.offer(block);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public A1083341_checkpoint7_Block remove(){
        //The TODO(5) This Time (Checkpoint7) :First check the PriorityQueue is empty or not and return and remove the object from the PriorityQueue.
        // If PriorityQueue is empty return null.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(!isEmpty()){
           return priorityQueue.poll(); 
        }else{
            return null;
        }
        
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    public boolean isEmpty(){
        //The TODO(5) This Time (Checkpoint7) :Check the PriorityQueue is empty or not.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(priorityQueue.size()==0){
            return true;
        }else{
            return false;
        }
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
}