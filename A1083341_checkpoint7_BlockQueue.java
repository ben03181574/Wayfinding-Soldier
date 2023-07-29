import java.util.*;

public class A1083341_checkpoint7_BlockQueue implements A1083341_checkpoint7_Fringe {
    Queue<A1083341_checkpoint7_Block> queue;
    //Description : the constuctor of BlockQueue.
    public A1083341_checkpoint7_BlockQueue(){
        //The TODO(4) This Time (Checkpoint7) : Initialize the queue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        queue = new LinkedList<A1083341_checkpoint7_Block>();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    //Description : add the input object into Fringe
    public void add(A1083341_checkpoint7_Block block){
        //The TODO(4) This Time (Checkpoint7) : add block into the queue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        queue.offer(block);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    //Description : return and remove the object from Fringe.
    public A1083341_checkpoint7_Block remove(){
        //The TODO(4) This Time (Checkpoint7) :First check the queue is empty or not and return and remove the object from the queue.
        // If queue is empty return null.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(!isEmpty()){
            return queue.poll();
        }else{
            return null;
        }
        
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    //Description : Check if the Fringe has a object at least. if it is empty return true, vice versa. 
    public boolean isEmpty(){
        //The TODO(4) This Time (Checkpoint7) :Check the queue is empty or not.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
         return queue.isEmpty();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
}