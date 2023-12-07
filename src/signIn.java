import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class signIn extends JFrame {
	public boolean a = true;
	movie m = new movie();
	
	public signIn() {
    	
        setTitle("로그인");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension lblSize = new Dimension(100, 15);
        Dimension btnSize = new Dimension(70, 25);

        JPanel main = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지만 그림.
                g.drawImage(new ImageIcon("ui/0.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel N = new JPanel(new GridBagLayout());

        JLabel idL = new JLabel("ID ");
        JTextField idT = new JTextField(20);
        idL.setPreferredSize(lblSize);

        JLabel pwL = new JLabel("PASSWORD ");
        JPasswordField pwT = new JPasswordField(20);
        pwL.setPreferredSize(lblSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        N.add(idL, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        N.add(idT, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        N.add(pwL, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        N.add(pwT, gbc);

        JPanel S = new JPanel();

        JButton login = new JButton("Login");
        login.setPreferredSize(btnSize);
        gbc.gridx = 3;
        gbc.gridy = 1;
        N.add(login, gbc);

        JButton join = new JButton("Join");
        join.setPreferredSize(btnSize);
        gbc.gridx = 1;
        gbc.gridy = 3;
        N.add(join, gbc);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (isLogin(idT.getText(), new String(pwT.getPassword()))==false)
            		JOptionPane.showMessageDialog(signIn.this, "다시 입력해주세요");
            	else
            	{            	
            		new main();
            		m.setId(getMyId(idT.getText(), new String(pwT.getPassword())));
            		setVisible(false);
            	}
            }
        });

        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (a==false)
            		JOptionPane.showMessageDialog(signIn.this, "Join clicked");
            	else
            	{
            		new join();
            		setVisible(false);
            	}
            }
        });

        main.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(N);
        centerPanel.add(S);

        add(main);
        setVisible(true);
    }
	
	
    private boolean isLogin(String id, String password) {
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String selectQuery  = "SELECT * FROM 사용자 WHERE 아이디=? AND 비밀번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery )) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If next() returns true, the user exists
                }
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private int getMyId(String id, String password) {
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";
        int myid = 0;

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String selectQuery  = "SELECT * FROM 사용자 WHERE 아이디=? AND 비밀번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery )) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                	myid = resultSet.getInt("회원번호");
                }
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return myid;
    }

    public static void main(String[] args) {
        new signIn();
    }
    
}
