package quotation.getXLdata;

import utils.dbOpener;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/5/23.
 */
public class moveDataToDB {
    public static void main(String args[]){
        File file = new File("C:\\Users\\11913\\Desktop\\USDCNY.txt");
        BufferedReader reader = null;
        String[] line = new String[25];
        int i = 0;
        try{
            reader = new BufferedReader(new FileReader(file));
            while((line[i] = reader.readLine())!=null){
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(line[21]);
        try {
            Connection conn = dbOpener.getDB();
            String sql = "INSERT IGNORE INTO tbl_USDCNY (date,open,high,low,close) VALUES(?,?,?,?,?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            for (int k = 21; k >= 0; k--) {
                System.out.println(k);
                ptmt.setString(1,line[k].split(",")[0]);
                ptmt.setDouble(2, Double.parseDouble(line[k].split(",")[2]));
                ptmt.setDouble(3, Double.parseDouble(line[k].split(",")[3]));
                ptmt.setDouble(4, Double.parseDouble(line[k].split(",")[4]));
                ptmt.setDouble(5, Double.parseDouble(line[k].split(",")[1]));
                ptmt.execute();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
