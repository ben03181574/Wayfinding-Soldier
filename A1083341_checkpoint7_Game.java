// This is the main class of checkpoint this time.
public class A1083341_checkpoint7_Game {
    public static void main(String[] args) {
        // The TODO This Time (Checkpoint7) :You need to initialize GameFrame object (int FWidth, int FHeight,
        // String mapID, int jfScaler, int algorithm)
        // the size of JFrame is 500*500, mapID and jfScaler are the values that get
        // from the command line.
        // Finally, set JFrame visible.
        // Hint: Beware of the value that you should put in GameFrame object all
        // correct.
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        String mapID = args[0];
        String jfScalerStr = args[1];
        String algorithmStr = args[2];
        int algorithm = new Integer(algorithmStr);
        int jfScaler = new Integer(jfScalerStr);

        A1083341_checkpoint7_GameFrame GameFrame = new A1083341_checkpoint7_GameFrame(500,500,mapID,jfScaler,algorithm);
        GameFrame.setVisible(true);
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }
}
