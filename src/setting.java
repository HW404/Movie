import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

public class setting extends JFrame {
    public boolean a = true;
    movie movie = new movie();

    public setting() {
        setTitle("세팅화면");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension lblSize = new Dimension(200, 50);
        Dimension btnSize = new Dimension(120, 50);
        Dimension movieSize = new Dimension(106, 150);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel mainP = new JPanel(new BorderLayout());

        //왼쪽 메뉴창----------------
        JPanel menu = new JPanel(new GridBagLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지만 그림.
                g.drawImage(new ImageIcon("ui/1.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton btnHome = new JButton(new ImageIcon("ui/home2.png"));
        btnHome.setPreferredSize(btnSize);
        btnHome.setBorderPainted(false);
        btnHome.setContentAreaFilled(false);
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new main();
                setVisible(false);
            }
        });
        gbc.gridy = 0;
        menu.add(btnHome, gbc);

        JButton btnSerch = new JButton(new ImageIcon("ui/search2.png"));
        btnSerch.setPreferredSize(btnSize);
        btnSerch.setBorderPainted(false);
        btnSerch.setContentAreaFilled(false);
        btnSerch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new search();
                setVisible(false);
            }
        });
        gbc.gridy = 1;
        menu.add(btnSerch, gbc);
        

        JButton btnSetting = new JButton(new ImageIcon("ui/app.png"));
        btnSetting.setPreferredSize(btnSize);
        btnSetting.setBorderPainted(false);
        btnSetting.setContentAreaFilled(false);
        btnSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new setting();
                setVisible(false);
            }
        });
        gbc.gridy = 2;
        menu.add(btnSetting, gbc);

        JButton a = new JButton(new ImageIcon("ui/ui2.png"));
        a.setBorderPainted(false);
        a.setContentAreaFilled(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        menu.add(a, gbc);

        //화면---------------
        JPanel home = new JPanel(new GridBagLayout());

        JButton btn1 = new JButton("내 이름 수정하기");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(null, "새로운 이름을 입력하세요:");
                if (newName != null && !newName.isEmpty()) {
                    movie.updateUserName(newName);
                    JOptionPane.showMessageDialog(null, "이름이 변경되었습니다.");
                }
            }
        });
        home.add(btn1);
        
        JButton btn2 = new JButton("내가 쓴 리뷰들 모아 보기");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyReviewsPopup();
            }
        });
        home.add(btn2);
        
        JButton btn3 = new JButton("로그아웃");
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new signIn();
            	4
                setVisible(false);
            }
        });
        home.add(btn3);

        mainP.add(menu, BorderLayout.WEST);
        mainP.add(home, BorderLayout.CENTER);

        add(mainP);
        setVisible(true);
    }

    private void showMyReviewsPopup() {
        JDialog popupDialog = new JDialog(this, "내가 쓴 리뷰들", true);
        popupDialog.setSize(600, 400);
        popupDialog.setLocationRelativeTo(this);

        JTextArea reviewsTextArea = new JTextArea();
        reviewsTextArea.setEditable(false);
        reviewsTextArea.setLineWrap(true);
        reviewsTextArea.setWrapStyleWord(true);

        // JScrollPane로 감싸서 스크롤이 가능하도록 설정
        JScrollPane scrollPane = new JScrollPane(reviewsTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// 패널에 JScrollPane 추가
        popupDialog.add(scrollPane);

        // 현재 영화에 대한 리뷰 검색
        String re[] = movie.getmyReviews();

        // 각 리뷰를 JTextArea에 추가
        for (String review : re) {
            reviewsTextArea.append(review + "\n");
        }

        popupDialog.setVisible(true);
    }
    

    // 인터넷 링크로부터 ImageIcon 생성
    private ImageIcon createImageIcon(String imageUrl, String description) {
        try {
            URL url = new URL(imageUrl);
            return new ImageIcon(url, description);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new setting();
    }
}
