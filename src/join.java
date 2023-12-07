import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class join extends JFrame {
	public boolean a = true;
	
	public join() {
    	
        setTitle("회원가입");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension lblSize = new Dimension(100, 15);
        Dimension btnSize = new Dimension(70, 25);

        JPanel main = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지만 그림.
                g.drawImage(new ImageIcon("ui/0.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

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
        
        JLabel nameL = new JLabel("NAME ");
        JTextField nameT = new JTextField(20);
        nameL.setPreferredSize(lblSize);

        JLabel ageL = new JLabel("AGE ");
        JTextField ageT = new JTextField(20);
        ageL.setPreferredSize(lblSize);

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
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        N.add(nameL, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        N.add(nameT, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        N.add(ageL, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        N.add(ageT, gbc);

        JPanel S = new JPanel();

        JButton login = new JButton("Login");
        login.setPreferredSize(btnSize);
        gbc.gridx = 1;
        gbc.gridy = 5;
        N.add(login, gbc);

        JButton join = new JButton("Join");
        join.setPreferredSize(btnSize);
        gbc.gridx = 2;
        gbc.gridy = 3;
        N.add(join, gbc);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (a==false)
            		JOptionPane.showMessageDialog(join.this, "Login clicked");
            	else {
            		new signIn();
        			setVisible(false);
            	}
            }
        });

        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (a == false)
                    JOptionPane.showMessageDialog(join.this, "Join clicked");
                else {
                    // join 버튼이 클릭되었을 때 데이터베이스 작업을 백그라운드에서 처리
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            insertUserData(idT.getText(), new String(pwT.getPassword()), nameT.getText(), ageT.getText());
                            return null;
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(join.this, "회원가입이 완료되었습니다!\n로그인을 해주세요");
                            new signIn();
                            setVisible(false);
                        }
                    };

                    worker.execute(); // SwingWorker 실행
                }
                insertUserData(idT.getText(), new String(pwT.getPassword()), nameT.getText(), ageT.getText());
                if (a==false)
                    JOptionPane.showMessageDialog(join.this, "Join clicked");
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
	
	// 데이터베이스에 사용자 데이터 삽입
    private void insertUserData(String id, String password, String name, String age) {
    	int rowCount = 0;
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String countQuery = "SELECT COUNT(*) FROM 사용자";

            try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        //값 넣어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String insertQuery = "INSERT INTO 사용자(아이디, 비밀번호, 회원이름, 나이, 회원번호) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, age);
                preparedStatement.setInt(5, rowCount+1); // 회원번호는 정수 값으로 설정

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " 행이 추가되었습니다.");
            } catch (SQLException ex) {
                System.out.println("데이터 삽입 에러");
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            System.out.println("DB 연결 에러");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new join();
    }
}
