import javax.swing.*;
import java.awt.*;

public class A1083341_checkpoint7_Pyramid extends JLabel implements Runnable {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : identify the player is onclick or not.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    private boolean understructure;
    private int jfScaler;
    private int maximum;
    // Description : The number of this type of building.
    private String produced_num;
    // Description : The image of under constructing.
    private ImageIcon constructingIcon;

    /********************************** The TODO (Past) ********************************
     * 
     * TODO(Past): Upon a pyramid is built, it needs to first wait for 5 seconds, then
     * it will change its photo from "constructing.png" to "pyramid.png".Also, there
     * will be a counter to show the progress, thus you have to refresh the text of
     * this building every 0.5 sec. Hint : The text of counter will be like-> "0%"
     * -> wait 0.5sec -> "10%" -> wait 0.5sec -> "20%" -> wait 0.5sec -> "30%" ->
     * wait 0.5sec -> "40%" -> wait 0.5sec -> "50%" -> wait 0.5sec -> "60%" -> wait
     * 0.5sec -> "70%" -> wait 0.5sec -> "80%" -> wait 0.5sec -> "90%" -> wait
     * 0.5sec -> "100%" -> produced_num(the number of this type of building).
     * 
     ********************************** The End of the TODO**************************************/
    @Override
    public void run() {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        doNothing(500);
        this.setText("10%");
        doNothing(500);
        this.setText("20%");
        doNothing(500);
        this.setText("30%");
        doNothing(500);
        this.setText("40%");
        doNothing(500);
        this.setText("50%");
        doNothing(500);
        this.setText("60%");
        doNothing(500);
        this.setText("70%");
        doNothing(500);
        this.setText("80%");
        doNothing(500);
        this.setText("90%");
        doNothing(500);
        this.setText("100%");
        this.setText(produced_num);
        this.setIcon(icon);
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
    /********************************** The TODO(Past) ********************************
     * 
     * TODO(Past): Aside from the "pyramid.png", you also need to read in the
     * "constructing.png".You need to set the text of this building to "0%" when it
     * is built. Hint : The size of "constructing.png" has to be the same size of
     * "pyramid.png".
     * 
     ********************************** The End of the TODO**************************************/

    // Description: this is the player's constructor, we set icon of player here and
    // calculate the distance every step.
    // Hint text : "player", horizontalAlignment : SwingConstants.CENTER
    public A1083341_checkpoint7_Pyramid(int locationX, int locationY, String text, int scaler, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.locationX = locationX;
        this.locationY = locationY;
        this.understructure = true;
        this.jfScaler = scaler;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/pyramid.png");
        this.maximum = 5;
        this.produced_num = text;
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler * 2, originalGridLen / scaler * 2, Image.SCALE_DEFAULT);
        icon.setImage(img);
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        this.constructingIcon = new ImageIcon("Resource/constructing.png");
        Image imgC = constructingIcon.getImage();
        imgC = imgC.getScaledInstance(originalGridLen / scaler *2, originalGridLen / scaler*2, Image.SCALE_DEFAULT);
        constructingIcon.setImage(imgC);
        this.setIcon(constructingIcon);
        this.setText("0%");
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }

    public void produce_people(int maximum, int produced_num) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getlocationX() {
        return this.locationX;
    }

    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getlocationY() {
        return this.locationY;
    }

    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }

}
