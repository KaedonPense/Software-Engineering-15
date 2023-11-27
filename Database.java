import java.sql.*;
import java.util.Properties;

public class Database
{
    static final String jbdcURL = "jdbc:postgresql://db.rjhwvtbdfxvprvcpmqvm.supabase.co:5432/postgres?user=postgres&password=IN2nISBf1dRIKaKw";
    static final String apiKey = "<eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJqaHd2dGJkZnh2cHJ2Y3BtcXZtIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTQ3MTQ3NDMsImV4cCI6MjAxMDI5MDc0M30.p2LS3j7jcTVwXxWg0O1HkwF_97wK8foSzW7DNjDTfq4>";
    static Connection connect = null;

    Database() //Constructor calls connection function
    {
        connectToSupa();
    }

    public static Connection connectToSupa() // Opens the Database Connection called during object creation
    {
        try
        {
            Properties apiProperty  = new Properties();
            apiProperty.setProperty("apiKey", apiKey);
            connect = DriverManager.getConnection(jbdcURL, apiProperty);
            if(connect != null)
                System.out.println("Connected to supabase");
            else
                System.out.println("Error connecting to supabase");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return connect;
    }
    
    void ShowAll() //Shows all player ID and Code Names in ascending order.
    {
        String q1 = "SELECT * FROM players ORDER BY id";
        try(Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(q1);
        ){
                
            while(rs.next())
            {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.println("    Code Name: "+ rs.getString("code_name"));
            }

        } catch (SQLException e)
        { e.printStackTrace();}  

    }

    void InsertData(String ID, String Name) // Inserts data into database
    {
        try(Statement stmt = connect.createStatement();
        ){
            String q1 = "INSERT into players Values ('" +ID+ "', '" +Name+ "')";
            int x = stmt.executeUpdate(q1);
            if (x > 0)            
                System.out.println("Successfully Inserted");            
            else           
                System.out.println("Insert Failed");

        } catch(Exception e)
        {System.out.println(e);}
    }


    String ReturnCodeName(String id) // Returns code name of given ID or returns null
    {
        String q1 = "SELECT code_name FROM players WHERE id = " + id;
        String a1 = null;
        try(Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(q1);
        ){
                
            while(rs.next())
            {
                //System.out.println("Code Name: "+ rs.getString("code_name"));
                a1 = rs.getString("code_name");
            }

        } catch (SQLException e)
        { e.printStackTrace();} 

        return a1;
    }

    void UpdateData(String ID, String Name) // Updates Code Name in the database for given ID
    {
        try(Statement stmt = connect.createStatement();
        ){
            String q1 = "UPDATE players SET code_name = '" + Name + "' WHERE id = " + ID;
            int x = stmt.executeUpdate(q1);
            if (x > 0)            
                System.out.println("Successfully Updated");            
            else           
                System.out.println("Update Failed");

        } catch(Exception e)
        {System.out.println(e);}
    }


    void CloseConnection() // IMPORTANT: Closes the database connection
    {
        try
        {
            connect.close();
        }catch(Exception e)
        {System.out.println(e);}

    }
}