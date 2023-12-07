import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.sql.*;
import java.util.*;
import java.util.Random;

public class main extends JFrame {
    private final movie movie = new movie();
    private final Dimension lblSize = new Dimension(200, 50);
    private final Dimension btnSize = new Dimension(120, 50);
    private final Dimension movieSize = new Dimension(106, 150);

    public main() {
        setTitle("메인화면");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        //왼쪽 메뉴창----------------
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JPanel menu = new JPanel(new GridBagLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 배경 이미지만 그림.
                g.drawImage(new ImageIcon("ui/1.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton btnHome = new JButton(new ImageIcon("ui/home.png"));
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

        JButton a = new JButton(new ImageIcon("ui/ui2.png"));
        a.setBorderPainted(false);
        a.setContentAreaFilled(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        menu.add(a, gbc);

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
        

        JButton btnSetting = new JButton(new ImageIcon("ui/app2.png"));
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
        

        mainPanel.add(menu, BorderLayout.WEST);
        

        // Home panel
        JPanel homePanel = createHomePanel();
        mainPanel.add(homePanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    

    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new GridBagLayout());

        addMovieSection1(homePanel, "나를 위한 추천 영화", 0, 1);
        addMovieSection2(homePanel, "내가 보고 싶던 영화", 3, 4);

        return homePanel;
    }

    private void addMovieSection1(JPanel panel, String labelText, int startRow, int startColumn) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(lblSize);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = startRow;
        panel.add(label, gbc);
        
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int numberOfMovies = random.nextInt(movie.count())+1;
            JButton movieButton = createMovieButton(numberOfMovies);
            gbc.gridx = i + 1;
            gbc.gridy = startRow + 1;
            panel.add(movieButton, gbc);
        }
    }

    private void addMovieSection2(JPanel panel, String labelText, int startRow, int startColumn) {
    	JLabel label = new JLabel(labelText);
        label.setPreferredSize(lblSize);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = startRow;
        panel.add(label, gbc);
    	
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        int j = 0;
        int arr[] = new int[10];
        
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT 영화번호 FROM 보고 WHERE 사용자번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movie.getId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int movieNumber = resultSet.getInt("영화번호");
                        arr[j] = movieNumber;
                        j++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int randomNumberIndex = random.nextInt(j-1)+1;
            int randomNumber = arr[randomNumberIndex];
            JButton movieButton = createMovieButton(randomNumber);

            gbc.gridx = i + 1;
            gbc.gridy = startRow + 1;
            panel.add(movieButton, gbc);
        }


    }


    private JButton createMovieButton(int movieNumber) {
        JButton movieButton = new JButton();
        movieButton.setPreferredSize(movieSize);

        int i = movie.count();
        if (i >= movieNumber) {
            String posterUrl = movie.getURL(movieNumber);
            try {
                Image posterImage = ImageIO.read(new URL(posterUrl));
                Image scaledImage = posterImage.getScaledInstance(movieSize.width, movieSize.height, Image.SCALE_SMOOTH);
                movieButton.setIcon(new ImageIcon(scaledImage));

                // ActionListener 추가
                movieButton.addActionListener(e -> showMovieInfoPopup(movieNumber));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return movieButton;
    }
    
    private void showMovieInfoPopup(int movieNumber) {
        JDialog movieInfoDialog = new JDialog(this, "영화 정보", true);
        movieInfoDialog.setSize(800, 550);
        movieInfoDialog.setLocationRelativeTo(this);

        movie.getMovie(movieNumber);

        // 패널 생성
        JPanel movieInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 영화 포스터 추가
        JLabel posterLabel = new JLabel();
        Dimension movieSize = new Dimension(212, 300);
        try {
            Image posterImage = ImageIO.read(new URL(movie.getPoster()));
            Image scaledImage = posterImage.getScaledInstance(movieSize.width, movieSize.height, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4; // 4줄에 걸쳐있도록 설정
        movieInfoPanel.add(posterLabel, gbc);

        // 영화 정보 추가
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        movieInfoPanel.add(new JLabel("" + movie.getTitle()), gbc);
        gbc.gridy = 1;
        movieInfoPanel.add(new JLabel("감독 : " + movie.getDirector()), gbc);
        gbc.gridy = 2;
        movieInfoPanel.add(new JLabel("배우 : " + movie.getActor()), gbc);
        gbc.gridy = 3;
        movieInfoPanel.add(new JLabel(movie.getRating() + "세 관람가 | " + movie.getRunningTime() + "분 | " + movie.getReleaseYear() + "년"), gbc);
        // 버튼 및 줄거리 추가
        gbc.gridy = 4;
        JButton saveButton = new JButton(new ImageIcon("ui/heart.png"));
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        Dimension buttonSize = new Dimension(40, 40);
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movie.addMo(movieNumber);
                saveButton.setIcon(new ImageIcon("ui/heart_on.png"));
            }
        });
        movieInfoPanel.add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel synopsisLabel = new JLabel(movie.getSynopsis());
        movieInfoPanel.add(synopsisLabel, gbc);

        // 선 추가
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(500, 2));
        gbc.gridx = 0;
        gbc.gridy = 5;
        movieInfoPanel.add(separator, gbc);

        // 리뷰 추가
        gbc.gridy = 6; // 리뷰 위치 조정
        JLabel reviewLabel = new JLabel("리뷰");
        movieInfoPanel.add(reviewLabel, gbc);

        // 리뷰를 표시할 JTextArea 추가
        gbc.gridy = 7; // 리뷰 위치 조정
        JTextArea reviewsTextArea = new JTextArea();
        reviewsTextArea.setEditable(false);
        reviewsTextArea.setLineWrap(true);
        reviewsTextArea.setWrapStyleWord(true);

        // JScrollPane로 감싸서 스크롤이 가능하도록 설정
        JScrollPane scrollPane = new JScrollPane(reviewsTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 최소 크기 설정
        scrollPane.setMinimumSize(new Dimension(500, 200)); // 원하는 크기로 조절

        // 패널에 JScrollPane 추가
        gbc.gridy = 7; // 리뷰 위치 조정
        movieInfoPanel.add(scrollPane, gbc);

        // 현재 영화에 대한 리뷰 검색
        String re[] = movie.getMovieReviews(movieNumber);

        // 각 리뷰를 JTextArea에 추가
        for (String review : re) {
            reviewsTextArea.append(review + "\n");
        }
        
        
        //리뷰 쓰기
        gbc.gridx = 1;
        gbc.gridy = 7;
        JButton write = new JButton(new ImageIcon("ui/writing.png"));
        write.setBorderPainted(false);
        write.setContentAreaFilled(false);
        write.setPreferredSize(buttonSize);
        movieInfoPanel.add(write, gbc);
        
        write.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a panel with components for review and rating
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 2));

                JTextField reviewTextField = new JTextField();
                
                // Dropdown for age rating
                Object[] ageRatingOptions = { "1점", "2점", "3점", "4점", "5점" };
                JComboBox<Object> ageRatingComboBox = new JComboBox<>(ageRatingOptions);
                
                panel.add(new JLabel("감상평:"));
                panel.add(reviewTextField);
                panel.add(new JLabel("별점:"));
                panel.add(ageRatingComboBox);

                int result = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "리뷰 작성",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null
                );

                if (result == JOptionPane.OK_OPTION) {
                    String review = reviewTextField.getText();
                    int rating = ageRatingComboBox.getSelectedIndex() + 1;

                    movie.addReviews(review, movieNumber, rating);
                }
            }
        });


        movieInfoDialog.add(movieInfoPanel);
        movieInfoDialog.setVisible(true);


    }


    public static void main(String[] args) {
        new main();
    }
}
