import java.sql.*;
import java.util.*;


public class movie {
	public static int id = 1;

	private String title;
    private String genre;
    private String director;
    private String actor;
    private int runningTime;
    private int rating;
    private int releaseYear;
    private String synopsis;
    private String trailer;
    private String poster;
    private int number;
    private String ott;
    
    public int setId(int a) {return id = a;}
    public int getId() {return id;}

    public String setTitle(String a) {return title = a;}
    public String setGenre(String a) {return genre = a;}
    public String setDirector(String a) {return director = a;}
    public String setActor(String a) {return actor = a;}
    public int setRunningTime(int a) {return runningTime = a;}
    public int setRating(int a) {return rating = a;}
    public int setReleaseYear(int a) {return releaseYear = a;}
    public String setSynopsis(String a) {return synopsis = a;}
    public String setTrailer(String a) {return trailer = a;}
    public String setPoster(String a) {return poster = a;};
    public int setMovieNumber(int a) {return number = a;}
    public String setOtt(String a) {return ott = a;};
    
    public String getTitle() {return title ;}
    public String getGenre() {return genre ;}
    public String getDirector() {return director ;}
    public String getActor() {return actor ;}
    public int getRunningTime() {return runningTime ;}
    public int getRating() {return rating ;}
    public int getReleaseYear() {return releaseYear ;}
    public String getSynopsis() {return synopsis ;}
    public String getTrailer() {return trailer ;}
    public String getPoster() {return poster ;};
    public int getMovieNumber() {return number ;}
    public String getOtt() {return ott ;};
    
    public  void getMovie(int movieNumber) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";
        
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 영화 WHERE 영화번호 = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movieNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        setTitle(resultSet.getString("영화제목"));
                        setGenre(resultSet.getString("장르"));
                        setDirector(resultSet.getString("감독"));
                        setActor(resultSet.getString("배우"));
                        setRunningTime(resultSet.getInt("러닝타임"));
                        setRating(resultSet.getInt("관람등급"));
                        setReleaseYear(resultSet.getInt("개봉연도"));
                        setSynopsis(resultSet.getString("줄거리"));
                        setTrailer(resultSet.getString("트레일러"));
                        setPoster(resultSet.getString("포스터"));
                        setMovieNumber(resultSet.getInt("영화번호"));
                        setOtt(resultSet.getString("OTT"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getURL(int movieNumber) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";
        String URL = "";
        
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 영화 WHERE 영화번호 = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movieNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                    	URL = resultSet.getString("포스터");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return URL;
    }
    
    public int getMovieId(String title) {
        int movie_id = -1;  // Initialize with a default value, indicating no movie found
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 영화 WHERE 영화제목 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        movie_id = resultSet.getInt("영화번호");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, log it, or throw a custom exception if needed
        }

        return movie_id;
    }

    
    // 데이터베이스에 사용자 데이터 삽입
    public int count() {
    	int rowCount = 0;
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String countQuery = "SELECT COUNT(*) FROM 영화";

            try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowCount;
    }
    
 // 이 메서드는 리뷰회원번호에 해당하는 사용자 이름을 가져오는 메서드입니다.
    private String getName(String num) {
        String name = "";  // Initialize with a default value, indicating no user found
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 사용자 WHERE 회원번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, num);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        name = resultSet.getString("회원이름");
                        return name;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }
    
    private String getmovieName(String num) {
        String name = "";  // Initialize with a default value, indicating no user found
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 영화 WHERE 영화번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, num);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        name = resultSet.getString("영화제목");
                        return name;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }
    
    public int countRe(int movieNumber) {
        int rowCount = 0;
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String countQuery = "SELECT COUNT(*) FROM 리뷰 WHERE 감상영화번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
                preparedStatement.setInt(1, movieNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        rowCount = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }
    
    public int countMo() {
        int rowCount = 0;
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        // 행 세어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String countQuery = "SELECT COUNT(*) FROM 리뷰 WHERE 리뷰회원번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        rowCount = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    
    public String[] getMovieReviews(int movieNumber) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        int num = countRe(movieNumber);
        String[] reviews = new String[num];

        int i = 0;
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 리뷰 WHERE 감상영화번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movieNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String reviewerName = getName(resultSet.getString("리뷰회원번호"));

                        // 리뷰어의 이름이 존재할 때만 리뷰 정보 추가
                        if (!reviewerName.isEmpty()) {
                            String reviewContent = resultSet.getString("감상평");
                            int reviewPoint = resultSet.getInt("별점");
                            reviews[i] = reviewerName + " | " + reviewPoint + " | " + reviewContent;
                            i++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
    
    //내가 쓴 리뷰 모아보기
    public String[] getmyReviews() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        int num = countMo();
        String[] reviews = new String[num];

        int i = 0;
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String query = "SELECT * FROM 리뷰 WHERE 리뷰회원번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String movieName = getmovieName(resultSet.getString("감상영화번호"));

                        // 리뷰어의 이름이 존재할 때만 리뷰 정보 추가
                        if (!movieName.isEmpty()) {
                            String reviewContent = resultSet.getString("감상평");
                            int reviewPoint = resultSet.getInt("별점");
                            reviews[i] = movieName + " | " + reviewPoint + " | " + reviewContent;
                            i++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        

        return reviews;
    }


    // 데이터베이스에 리뷰 데이터 삽입
    public void addReviews(String re, int movie, int point) {
    	int rowCount = 0;
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

      //값 넣어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String insertQuery = "INSERT INTO 리뷰(감상영화번호, 리뷰회원번호, 감상평, 별점) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, movie);
                preparedStatement.setInt(2, id);
                preparedStatement.setString(3, re);
                preparedStatement.setInt(4, point);

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
    
    // 보고싶은 영화에 저장
    public void addMo(int movie) {
    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

      //값 넣어주기
        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String insertQuery = "INSERT INTO 보고(영화번호, 사용자번호) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, movie);
                preparedStatement.setInt(2, id);

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
    
    // 데이터베이스에 사용자 데이터 업데이트 (회원이름 변경)
    public void updateUserName(String newUserName) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String passwordDB = "0202";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDB)) {
            String updateQuery = "UPDATE 사용자 SET 회원이름 = ? WHERE 회원번호 = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newUserName);
                preparedStatement.setInt(2, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("회원이름이 변경되었습니다.");
                } else {
                    System.out.println("회원번호에 해당하는 사용자를 찾을 수 없습니다.");
                }
            } catch (SQLException ex) {
                System.out.println("데이터 업데이트 에러");
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            System.out.println("DB 연결 에러");
            ex.printStackTrace();
        }
    }
    
    
}

