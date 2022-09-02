import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DogRepository {

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/Dog";
        String user = "postgres";
        String password = "titova92480";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save(Dog dog) {
        int status = 0;
        try {

            PreparedStatement ps = Connection().prepareStatement("insert into Dogs(name,breed,owner) values (?,?,?)");
            ps.setString(1, dog.getName());
            ps.setString(2, dog.getBreed());
            ps.setString(3, dog.getOwner());


            status = ps.executeUpdate();
            Connection().close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Dog dog) {

        int status = 0;

        try {
            PreparedStatement ps = Connection().prepareStatement("update Dogs set name=?,breed=?,owner=? where id=?");
            ps.setString(1, dog.getName());
            ps.setString(2, dog.getBreed());
            ps.setString(3, dog.getOwner());
            ps.setInt(4, dog.getId());

            status = ps.executeUpdate();
            Connection().close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            PreparedStatement ps = Connection().prepareStatement("delete from Dogs where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            Connection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Dog getDogById(int id) {

        var dog = new Dog();

        try {
            PreparedStatement ps = Connection().prepareStatement("select * from Dogs where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SetParams(dog, rs);
            }
            Connection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dog;
    }

    public static List<Dog> getAllDogs() {

        var listDogs = new ArrayList<Dog>();

        try {
            PreparedStatement ps = Connection().prepareStatement("select * from Dogs");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                var dog = new Dog();
                SetParams(dog, rs);

                listDogs.add(dog);
            }
            Connection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDogs;
    }

    private static Connection Connection(){
         Connection connection = DogRepository.getConnection();
         return connection;
    }

    private static void SetParams (Dog dog, ResultSet rs) throws SQLException {
        dog.setId(rs.getInt(1));
        dog.setName(rs.getString(2));
        dog.setBreed(rs.getString(3));
        dog.setOwner(rs.getString(4));
    }
}
