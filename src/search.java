import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.List;

public class search extends JFrame {
	public boolean a = true;
	
	

    JButton m11 = new JButton();
    JButton m12 = new JButton();
    JButton m13= new JButton();
    JButton m14 = new JButton();
    JButton m21 = new JButton();
    JButton m22 = new JButton();
    JButton m23= new JButton();
    JButton m24 = new JButton();
    JButton m31 = new JButton();
    JButton m32 = new JButton();
    JButton m33= new JButton();
    JButton m34 = new JButton();

    int selectedRunningTime = 0;
    String selectedGenre = "";
    int selectedAgeRating = 0;
    movie movie = new movie();
	
	public search() {
		setTitle("메인화면");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension btnSize = new Dimension(120, 50);
        Dimension movieSize = new Dimension(106, 150);
        Dimension sbtnSize = new Dimension(106, 50);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

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

        JButton btnSerch = new JButton(new ImageIcon("ui/search.png"));
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
        
        JButton a = new JButton(new ImageIcon("ui/ui2.png"));
        a.setBorderPainted(false);
        a.setContentAreaFilled(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        menu.add(a, gbc);

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
        
     // 화면---------------
        JPanel home = new JPanel(new GridBagLayout());

        JLabel srL = new JLabel("search ");
        JTextField srT = new JTextField(50);
        JButton srBtn = new JButton("↲");
        srBtn.setPreferredSize(new Dimension(50, 20));
        
        movie m = new movie();

        // 검색 버튼에 ActionListener 추가
        srBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = srT.getText();
                int num = m.getMovieId(title);
                showMovieInfoPopup(num);
            }
        });;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        home.add(srL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        home.add(srT, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        home.add(srBtn, gbc);
        
        JPanel home2 = new JPanel(new GridBagLayout());
        
        
        
        // 검색 설정 버튼1에 대한 ActionListener 추가
        JButton s1 = new JButton("러닝타임 선택");
        s1.setPreferredSize(sbtnSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        home2.add(s1, gbc);

        s1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 러닝타임 선택
                Object[] runningTimeOptions = { "90분 미만", "120분 미만", "180미만", "200미만", "모두" };
                int runningTimeChoice = JOptionPane.showOptionDialog(
                        null,
                        "러닝타임을 선택하세요:",
                        "러닝타임 선택",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        runningTimeOptions,
                        runningTimeOptions[0]
                );

                int runningTime = getRunningTimeFromChoice(runningTimeChoice);
                selectedRunningTime = runningTime;
            }
            
            private int getRunningTimeFromChoice(int choice) {
                switch (choice) {
                    case 0: return 90;  // 90분 미만
                    case 1: return 120;  // 90분 이상
                    case 2: return 180; // 120분 이상
                    case 3: return 200; // 150분 이상
                    case 4: return 300; // 150분 이상
                    default: return 300;   // 기본값
                }
            }
            
        });

        // 검색 설정 버튼2에 대한 ActionListener 추가
        JButton s2 = new JButton("장르 선택");
        s2.setPreferredSize(sbtnSize);
        gbc.gridx = 1;
        gbc.gridy = 0;
        home2.add(s2, gbc);

        s2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 장르 선택
            	Object[] genreOptions = { "액션", "드라마", "코미디", "스릴러", "뮤지컬", "전기", "미스테리", "히어로", "로맨스", "범죄", "애니메이션", "판타지", "SF"};
                int genreChoice  = JOptionPane.showOptionDialog(
                        null,
                        "장르를 선택하세요:",
                        "장르 선택",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        genreOptions,
                        genreOptions[0]
                );
                
                String genre = getGenreFromChoice(genreChoice);
                selectedGenre = genre;
            }
            
         	// 선택된 값에 따라 장르 변환
            private String getGenreFromChoice(int choice) {
                String[] genreOptions = { "액션", "드라마", "코미디", "스릴러", "뮤지컬", "전기", "미스테리", "히어로", "로맨스", "범죄", "애니메이션", "판타지", "SF"};
                return genreOptions[choice];
            }
        });

        // 검색 설정 버튼3에 대한 ActionListener 추가
        JButton s3 = new JButton("관람등급 선택");
        s3.setPreferredSize(sbtnSize);
        gbc.gridx = 2;
        gbc.gridy = 0;
        home2.add(s3, gbc);

        s3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 관람등급 선택
                Object[] ageRatingOptions = { "전체 관람가", "12세 이상", "15세 이상", "청소년 관람불가" };
                int ageRatingChoice = JOptionPane.showOptionDialog(
                        null,
                        "관람등급을 선택하세요:",
                        "관람등급 선택",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        ageRatingOptions,
                        ageRatingOptions[0]
                );
                
                // Update the selected value
                int ageRating = getAgeRatingFromChoice(ageRatingChoice);
                selectedAgeRating = ageRating;
            }
            
            // 선택된 값에 따라 관람등급 변환
            private int getAgeRatingFromChoice(int choice) {
                switch (choice) {
                    case 0: return 0;  // 전체 관람가
                    case 1: return 12; // 12세 이상
                    case 2: return 15; // 15세 이상
                    case 3: return 19; // 청소년 관람불가
                    default: return 0; // 기본값
                }
            }
        });

        // 검색 실행 버튼에 대한 ActionListener 추가
        JButton s4 = new JButton("검색 실행");
        s4.setPreferredSize(sbtnSize);
        gbc.gridx = 3;
        gbc.gridy = 0;
        home2.add(s4, gbc);

        s4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 저장된 값으로 영화 검색
                List<Integer> movieNumbers = getMoviesBySettings(selectedRunningTime, selectedGenre, selectedAgeRating);

                // 가져온 영화 번호로 영화 버튼 업데이트
                updateMovieButtons(movieNumbers);
            }
        });

        
        m11.setPreferredSize(movieSize);
        m12.setPreferredSize(movieSize);
        m13.setPreferredSize(movieSize);
        m14.setPreferredSize(movieSize);
        
        m21.setPreferredSize(movieSize);
        m22.setPreferredSize(movieSize);
        m23.setPreferredSize(movieSize);
        m24.setPreferredSize(movieSize);
        
        m31.setPreferredSize(movieSize);
        m32.setPreferredSize(movieSize);
        m33.setPreferredSize(movieSize);
        m34.setPreferredSize(movieSize);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        home2.add(m11, gbc);
        gbc.gridx = 1;
        home2.add(m12, gbc);
        gbc.gridx = 2;
        home2.add(m13, gbc);
        gbc.gridx = 3;
        home2.add(m14, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        home2.add(m21, gbc);
        gbc.gridx = 1;
        home2.add(m22, gbc);
        gbc.gridx = 2;
        home2.add(m23, gbc);
        gbc.gridx = 3;
        home2.add(m24, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        home2.add(m31, gbc);
        gbc.gridx = 1;
        home2.add(m32, gbc);
        gbc.gridx = 2;
        home2.add(m33, gbc);
        gbc.gridx = 3;
        home2.add(m34, gbc);
        
        
        //----------------

        mainP.add(menu, BorderLayout.WEST);
        mainP.add(home, BorderLayout.NORTH);
        mainP.add(home2, BorderLayout.CENTER);

        add(mainP);
        setVisible(true);
    }
	
	// 가져온 영화 번호를 기반으로 영화 버튼 업데이트하는 메서드
    private void updateMovieButtons(List<Integer> movieNumbers) {
        JButton[] movieButtons = {m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34};
        for (int i = 0; i < movieButtons.length && i < movieNumbers.size(); i++) {
        	updateMovieButton(movieButtons[i], movieNumbers.get(i));
        }
    }
    
    // 영화 버튼 업데이트하는 메서드
    private void updateMovieButton(JButton button, int movieNumber) {
        movie movie = new movie();
        Dimension movieSize = new Dimension(106, 150);

        int i = movie.count();
        if (i >= movieNumber) {
            String posterUrl = movie.getURL(movieNumber);
            try {
                if (posterUrl != null && !posterUrl.isEmpty()) {
                    Image posterImage = ImageIO.read(new URL(posterUrl));
                    Image scaledImage = posterImage.getScaledInstance(movieSize.width, movieSize.height, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));

                    // ActionListener 추가
                    button.addActionListener(e -> showMovieInfoPopup(movieNumber));
                    button.setVisible(true); // 이미지가 있을 때 보이기
                } else {
                    // 영화 정보가 없는 경우
                    Image nullImage = ImageIO.read(new URL("ui/null.png"));
                    Image scaledNullImage = nullImage.getScaledInstance(movieSize.width, movieSize.height, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledNullImage));
                    button.setVisible(false); // 이미지가 없을 때 숨기기

                    // ActionListener 제거
                    for (ActionListener al : button.getActionListeners()) {
                        button.removeActionListener(al);
                    }

                 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
	
	// `movie` 클래스에 이 메서드 추가
	public List<Integer> getMoviesBySettings(int runningTime, String genre, int ageRating) {
	    List<Integer> movieNumbers = new ArrayList<>();
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String username = "SYSTEM";
	    String passwordDB = "0202";

	    try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
	        String query = "SELECT 영화번호 FROM 영화 WHERE 러닝타임 <= ? AND 장르 LIKE ? AND 관람등급 = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            // Convert selectedRunningTime to minutes (assuming it's in the format "X분 이상")
	            preparedStatement.setInt(1, runningTime);
	            preparedStatement.setString(2, "%" + genre + "%");
	            preparedStatement.setInt(3, ageRating);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    movieNumbers.add(resultSet.getInt("영화번호"));
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movieNumbers;
	}
	
    public static void main(String[] args) {
        new search();
    }
}
