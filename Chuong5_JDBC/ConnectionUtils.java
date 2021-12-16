
package Chuong5_JDBC;
import java.sql.*;
import java.sql.SQLException;
/**=
 *
 * @author ASUS
 */
public class ConnectionUtils {
    public static Connection getMyConnection() throws SQLException,ClassNotFoundException {
      return SQLServerConnUtils_SQLJDBC.getSQLServerConnection();
  }

  public static void main(String[] args) throws SQLException,ClassNotFoundException {
      System.out.println("tạo kết nối ... ");
      // Lấy ra đối tượng Connection kết nối vào database.
      Connection conn = ConnectionUtils.getMyConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from LTM_LOPHOC");
      while (rs.next()) 
      {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            
      }
      System.out.println("Hoàn thành xuất dữ liệu!");
  }
}
