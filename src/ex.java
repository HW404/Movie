import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ex {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
    	movie movie = new movie();
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";
        
        
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT 영화번호 FROM 보고 WHERE 사용자번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movie.getId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int movieNumber = resultSet.getInt("영화번호");
                        System.out.print(movieNumber);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
        JFrame frame = new JFrame("Button with Internet Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        JButton button = new JButton();

        panel.add(button);
        frame.add(panel);

        frame.setVisible(true);
    }
}
