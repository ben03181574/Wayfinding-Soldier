import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083341_checkpoint7_QueryDB {
    // //Description : the driver description of mysql
    // private static final String driver = "com.mysql.cj.jdbc.Driver";
    // //Description : the protocol description of mysql
    // private static final String protocol = "jdbc:mysql://140.127.220.220:3306/";
    // Description : the obstacle set queried from database.
    private static ArrayList<Integer[]> data = new ArrayList<Integer[]>();
    // Description : the filename of obstacle image queried from database.
    private static HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the primary key of map in database.
    private static String mapID = "0";

    public static void queryData(ArrayList<Integer[]> data, HashMap<Integer, String> typeChar) {
        // TODO(Past): Querying the barrier location from the server, and set it into
        // Arraylist data.
        // TODO(Past): Querying the bar_type and the corresponding file_name from the
        // server, and set it into HashMap.
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        try {
            A1083341_checkpoint7_QueryDB ckp7 = new A1083341_checkpoint7_QueryDB();
            data.clear();
            typeChar.clear();

            Class.forName("org.postgresql.Driver");
            Connection c=null;
            c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/WayfindingSoldier","WayfindingSoldierUser", "WayfindingSoldierPwd");
            
            Statement s=null;
            s=c.createStatement();
            /********************/
            String getMapSizeSql="SELECT * FROM obstacle_info WHERE map_id="+ckp7.getMapID()+";";
            ResultSet resultSet=null;
            resultSet=s.executeQuery(getMapSizeSql);
            
            while(resultSet.next()){
                Integer[] loc = new Integer[3];
                loc[2]=resultSet.getInt("obstacle_type");
                loc[1]=resultSet.getInt("x_coordinate");
                loc[0]=resultSet.getInt("y_coordinate");
                data.add(loc);
            } 
            ckp7.setObstacle(data);
            resultSet.close();
            /********************/
            String getObstacleTypeSql="SELECT DISTINCT s.obstacle_type,s.filename FROM obstacle_style s,obstacle_info"+
            " i WHERE i.map_id="+ckp7.getMapID()+" AND s.obstacle_type=i.obstacle_type;";
            resultSet=s.executeQuery(getObstacleTypeSql);

            while(resultSet.next()){
                typeChar.put(resultSet.getInt("obstacle_type"),resultSet.getString("filename"));
            }
            ckp7.setObstacleImg(typeChar);
            resultSet.close();
            /********************/
            c.close();
        }catch (Exception e) {
            System.out.println("Something is Wrong!");
            System.out.println(e.getMessage());
            System.exit(0);
        }  
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    public ArrayList getObstacle() {
        return this.data;
    }

    public void setObstacle(ArrayList<Integer[]> data) {
        this.data = data;
    }

    public String getMapID() {
        return this.mapID;
    }

    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    public HashMap getObstacleImg() {
        return this.typeChar;
    }

    public void setObstacleImg(HashMap<Integer, String> typeChar) {
        this.typeChar = typeChar;
    }
}
