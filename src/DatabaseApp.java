import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseApp {

    private final String url = "jdbc:postgresql://localhost:5432/ProjectGymDatabase";
    private final String user = "postgres";
    private final String password = "qusai";

    // Method to establish a connection to the PostgreSQL database
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // getAllMembers method
    public String getAllMembers() {
        StringBuilder result = new StringBuilder();
        // Append attribute names with proper spacing
        result.append(String.format("%-10s %-15s %-15s %-12s %-20s %-20s %-15s\n",
                "MemberID", "FirstName", "LastName", "DateOfBirth", "FitnessGoals", "HealthMetrics", "MembershipType"));

        String SQL = "SELECT * FROM Member";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(SQL)) {
            while (set.next()) {
                // Append formatted member details
                result.append(String.format("%-10d %-15s %-15s %-12s %-20s %-20s %-15s\n",
                        set.getInt("MemberId"),
                        set.getString("FirstName"),
                        set.getString("LastName"),
                        set.getDate("DateOfBirth"),
                        set.getString("FitnessGoals"),
                        set.getString("HealthMetrics"),
                        set.getString("MembershipType")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }


    // Register member method
    public int registerMember(String firstName, String lastName, Date dateOfBirth, String fitnessGoals, String healthMetrics, String membershipType) {
        String SQL = "INSERT INTO Member (FirstName, LastName, DateOfBirth, FitnessGoals, HealthMetrics, MembershipType) VALUES ( ?, ?, ?, ?, ?, ?)";
        int member_id = -1;
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setDate(3, dateOfBirth);
            statement.setString(4, fitnessGoals);
            statement.setString(5, healthMetrics);
            statement.setString(6, membershipType);
            int rows = statement.executeUpdate();
            System.out.println(rows);
            if (rows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                System.out.println(resultSet);
                if (resultSet.next()){
                    member_id = resultSet.getInt(1);
                    System.out.println(member_id);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return member_id;
    }

    // Update member profile method
    public String updateMemberProfile(int memberID, String firstName, String lastName, Date dateOfBirth, String fitnessGoals, String healthMetrics, String membershipType) {
        StringBuilder result = new StringBuilder();
        String SQL = "UPDATE Member SET FirstName=?, LastName=?, DateOfBirth=?, FitnessGoals=?, HealthMetrics=?, MembershipType=? WHERE MemberID=?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setDate(3, dateOfBirth);
            statement.setString(4, fitnessGoals);
            statement.setString(5, healthMetrics);
            statement.setString(6, membershipType);
            statement.setInt(7, memberID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Member profile updated successfully.");
            } else {
                result.append("No member found with the specified ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    public String createPersonalizedDashboard(String fitnessAchievements, String healthStatistics, String exerciseRoutines,int memberID) {
        StringBuilder result = new StringBuilder();
        String SQL = "INSERT INTO PersonalizedDashboard (FitnessAchievements, HealthStatistics, ExerciseRoutines, MemberID) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, fitnessAchievements);
            statement.setString(2, healthStatistics);
            statement.setString(3, exerciseRoutines);
            statement.setInt(4, memberID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Dashboard created successfully for member ID ").append(memberID);
            } else {
                result.append("Failed to create dashboard.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }
    // Display dashboard method
    public String displayDashboard(int memberID) {
        StringBuilder result = new StringBuilder();
        String SQL = "SELECT FitnessAchievements, HealthStatistics, ExerciseRoutines FROM PersonalizedDashboard WHERE MemberID = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, memberID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.append("Fitness Achievements: ").append(rs.getString("FitnessAchievements")).append("\n")
                        .append("Health Statistics: ").append(rs.getString("HealthStatistics")).append("\n")
                        .append("Exercise Routines: ").append(rs.getString("ExerciseRoutines"));
            } else {
                result.append("Member not found in Personalized Dashboard.");
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Schedule personal training session method
    public String schedulePersonalTrainingSession(int memberID, int trainerID, Date sessionDate, Time sessionTime) {
        StringBuilder result = new StringBuilder();
        try {
            // Check if the trainer is available
            if (isTrainerAvailable(trainerID, sessionDate)) {
                String SQL = "INSERT INTO PersonalTrainingSession (Date, SessionDuration, MemberID, TrainerID) VALUES (?, ?, ?, ?)";
                try (Connection connection = connect();
                     PreparedStatement statement = connection.prepareStatement(SQL)) {
                    statement.setDate(1, sessionDate);
                    statement.setTime(2, sessionTime);
                    statement.setInt(3, memberID);
                    statement.setInt(4, trainerID);
                    statement.executeUpdate();
                    result.append("Personal training session scheduled successfully.");
                }
            } else {
                result.append("Trainer is not available at the specified date and time.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Schedule group fitness class method
    public String scheduleGroupFitnessClass(int classID, int trainerID, Date sessionDate, Time sessionTime) {
        StringBuilder result = new StringBuilder();
        if (isTrainerAvailable(trainerID, sessionDate)) {
            try (Connection connection = connect();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO ClassSchedule (ClassID, TrainerID, SessionDate, SessionTime) VALUES (?, ?, ?, ?)")) {
                statement.setInt(1, classID);
                statement.setInt(2, trainerID);
                statement.setDate(3, sessionDate);
                statement.setTime(4, sessionTime);
                statement.executeUpdate();
                result.append("Group fitness class scheduled successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                result.append("Error: ").append(ex.getMessage());
            }
        } else {
            result.append("Trainer is not available at the specified date and time.");
        }
        return result.toString();
    }
    private String getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return daysOfWeek[dayOfWeek - 1];
    }

    private boolean isTrainerAvailable(int trainerID, Date sessionDate) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Trainer WHERE TrainerID = ? AND Availability = ?")) {
            // Extract day of week from sessionDate
            String dayOfWeek = getDayOfWeek(sessionDate);
            statement.setInt(1, trainerID);
            statement.setString(2, dayOfWeek);
            ResultSet rs = statement.executeQuery();

            // If the trainer's availability for the specified day of the week exists, return true; otherwise, return false
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Handle the exception according to your application's requirements
        }
    }


    //NOW TRAINER

    public String getAllTrainers() {
        StringBuilder result = new StringBuilder();
        // Append attribute names with proper spacing
        result.append(String.format("%-10s %-15s %-15s %-20s %-15s\n",
                "TrainerID", "FirstName", "LastName", "Speciality", "Availability"));

        String SQL = "SELECT * FROM Trainer";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(SQL)) {
            while (set.next()) {
                // Append formatted trainer details
                result.append(String.format("%-10d %-15s %-15s %-20s %-15s\n",
                        set.getInt("TrainerID"),
                        set.getString("FirstName"),
                        set.getString("LastName"),
                        set.getString("Speciality"),
                        set.getString("Availability")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }


    public String setTrainerAvailability(int trainerID, String availability) {
        StringBuilder result = new StringBuilder();
        String SQL = "UPDATE Trainer SET Availability = ? WHERE TrainerID = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, availability);
            statement.setInt(2, trainerID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Trainer availability updated successfully.");
            } else {
                result.append("No trainer found with the specified ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // View member profile by name method
    public String viewMemberProfileByName(String firstName, String lastName) {
        StringBuilder result = new StringBuilder();
        String SQL = "SELECT * FROM Member WHERE FirstName = ? AND LastName = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Append member details to the result string
                    result.append("Member ID: ").append(rs.getInt("MemberID")).append("\n")
                            .append("First Name: ").append(rs.getString("FirstName")).append("\n")
                            .append("Last Name: ").append(rs.getString("LastName")).append("\n")
                            .append("Date of Birth: ").append(rs.getDate("DateOfBirth")).append("\n")
                            .append("Fitness Goals: ").append(rs.getString("FitnessGoals")).append("\n")
                            .append("Health Metrics: ").append(rs.getString("HealthMetrics")).append("\n")
                            .append("Membership Type: ").append(rs.getString("MembershipType"));
                } else {
                    result.append("Member not found.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    public String getAllRooms() {
        StringBuilder result = new StringBuilder();
        // Append attribute names with proper spacing
        result.append(String.format("%-8s %-15s %-8s %-12s %-12s\n",
                "RoomID", "Name", "Capacity", "RoomNumber", "BookingDate"));

        String SQL = "SELECT * FROM Room";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(SQL)) {
            while (set.next()) {
                // Append formatted room details
                result.append(String.format("%-8d %-15s %-8d %-12d %-12s\n",
                        set.getInt("RoomID"),
                        set.getString("Name"),
                        set.getInt("Capacity"),
                        set.getInt("RoomNumber"),
                        set.getDate("BookingDate")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }
    // Add room booking method
    public String addRoomBooking(String name, int capacity, int roomNumber, Date bookingDate) {
        StringBuilder result = new StringBuilder();
        String SQL = "INSERT INTO Room (Name, Capacity, RoomNumber, BookingDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setInt(2, capacity);
            statement.setInt(3, roomNumber);
            statement.setDate(4, bookingDate);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int roomID = rs.getInt(1);
                result.append("Room booking added successfully. Room ID: ").append(roomID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Update room booking method
    public String updateRoomBooking(String roomName, Date bookingDate) {
        StringBuilder result = new StringBuilder();
        String SQL = "UPDATE Room SET BookingDate = ? WHERE Name = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setDate(1, bookingDate);
            statement.setString(2, roomName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Room booking updated successfully.");
            } else {
                result.append("No room booking found with the specified ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Delete room booking method
    public String deleteRoomBooking(String roomName) {
        StringBuilder result = new StringBuilder();
        String SQL = "DELETE FROM Room WHERE Name = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, roomName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Room booking for room: ").append(roomName).append(" deleted successfully.");
            } else {
                result.append("No room booking found with the specified room name: ").append(roomName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    public String getAllEquipment() {
        StringBuilder result = new StringBuilder();
        // Append attribute names with proper spacing
        result.append(String.format("%-12s %-20s %-60s %-20s %-20s %-40s\n",
                "EquipmentID", "EquipmentType", "Description", "LastMaintenanceDate", "MaintenanceType", "MaintenanceInformation"));

        String SQL = "SELECT * FROM GymEquipment";
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                // Append formatted equipment details
                result.append(String.format("%-12s %-20s %-60s %-20s %-20s %-40s\n",
                        resultSet.getInt("EquipmentID"),
                        resultSet.getString("EquipmentType"),
                        resultSet.getString("Description"),
                        resultSet.getDate("LastMaintenanceDate"),
                        resultSet.getString("MaintenanceType"),
                        resultSet.getString("MaintenanceInformation")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }
    // Update maintenance record method
    public String updateMaintenanceRecord(String equipmentName, Date maintenanceDate, String maintenanceType, String maintenanceInfo) {
        StringBuilder result = new StringBuilder();
        String SQL = "UPDATE GymEquipment SET LastMaintenanceDate = ?, MaintenanceType = ?, MaintenanceInformation = ? WHERE EquipmentType = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setDate(1, maintenanceDate);
            statement.setString(2, maintenanceType);
            statement.setString(3, maintenanceInfo);
            statement.setString(4, equipmentName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                result.append("Maintenance record updated successfully for equipment type: ").append(equipmentName);
            } else {
                result.append("No maintenance record found for equipment type: ").append(equipmentName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Function to retrieve latest maintenance information for gym equipment
    public String getLatestMaintenanceInfo(String equipmentType) {
        StringBuilder result = new StringBuilder();
        String SQL = "SELECT LastMaintenanceDate, MaintenanceType, MaintenanceInformation FROM GymEquipment WHERE EquipmentType = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, equipmentType);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.append("Last Maintenance Date: ").append(rs.getDate("LastMaintenanceDate")).append("\n")
                        .append("Maintenance Type: ").append(rs.getString("MaintenanceType")).append("\n")
                        .append("Maintenance Information: ").append(rs.getString("MaintenanceInformation"));
            } else {
                result.append("No maintenance record found for equipment Type ").append(equipmentType);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }
    public String getAllClasses() {
        StringBuilder result = new StringBuilder();
        String SQL = "SELECT * FROM Class";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(SQL)) {
            while (set.next()) {
                result.append("ClassID: ").append(set.getInt("ClassID")).append("\n")
                        .append("Class Type: ").append(set.getString("ClassType")).append("\n")
                        .append("Description: ").append(set.getString("Description")).append("\n")
                        .append("Day: ").append(set.getString("Day")).append("\n")
                        .append("TrainerID: ").append(set.getInt("TrainerID")).append("\n\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    public String updateClassDay(String className, String newDay) {
        StringBuilder result = new StringBuilder();
        String SQL = "UPDATE Class SET Day = ? WHERE ClassType = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, newDay);
            statement.setString(2, className);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result.append("Class schedule updated successfully.");
            } else {
                result.append("No class found with the specified name.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }
    public String processBilling(int memberID, double amount, String paymentMethod, String service) {
        StringBuilder result = new StringBuilder();
        String SQL = "INSERT INTO BillingAndPayment (MemberID, Amount, Date, PaymentMethod, Service) VALUES (?, ?, CURRENT_DATE, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, memberID);
            statement.setDouble(2, amount);
            statement.setString(3, paymentMethod);
            statement.setString(4, service);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int transactionID = resultSet.getInt(1);
                result.append("Billing processed successfully. Transaction ID: ").append(transactionID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    public String getAllBillings() {
        StringBuilder result = new StringBuilder();
        String SQL = "SELECT * FROM BillingAndPayment";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(SQL)) {
            while (set.next()) {
                result.append("TransactionID: ").append(set.getInt("TransactionID")).append("\n")
                        .append("MemberID: ").append(set.getInt("MemberID")).append("\n")
                        .append("Amount: ").append(set.getDouble("Amount")).append("\n")
                        .append("Date: ").append(set.getDate("Date")).append("\n")
                        .append("PaymentMethod: ").append(set.getString("PaymentMethod")).append("\n")
                        .append("Service: ").append(set.getString("Service")).append("\n\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            result.append("Error: ").append(ex.getMessage());
        }
        return result.toString();
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        //DatabaseApp app = new DatabaseApp();
        //System.out.printf("first\n");
        //String res = app.getAllMembers();
        //System.out.println(res);
    }
}
