import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

public class DatabaseView extends JFrame {

    private DatabaseApp databaseApp;
    private JButton getAllMembersButton;
    private JButton registerMemberButton;
    private JButton updateProfileButton;
    private JButton displayDashboardButton;
    private JButton scheduleSessionButton;
    private JButton getAllTrainersButton;
    private JButton setTrainerAvailability;
    private JButton viewMemberProfileByName;
    private JButton getAllRoomsButton;
    private JButton addRoomBooking;
    private JButton updateRoomBooking;
    private JButton deleteRoomBooking;
    private JButton getAllEquipment;
    private JButton updateMaintenanceRecord;
    private JButton getLatestMaintenanceInfo;
    private JButton getAllClasses;
    private JButton updateClassDay;
    private JButton processBilling;


    private JTextArea resultTextArea;

    public DatabaseView(DatabaseApp databaseApp) {
        this.databaseApp = databaseApp;
        setTitle("Gym Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        initializeComponents();
        addComponentsToPane(getContentPane());
    }

    private void initializeComponents() {
        // Initialize buttons for member functions
        getAllMembersButton = new JButton("Get All Members");
        getAllMembersButton.addActionListener(new ButtonListener());

        registerMemberButton = new JButton("Register Member");
        registerMemberButton.addActionListener(new ButtonListener());

        // Initialize buttons for profile management
        updateProfileButton = new JButton("Update Profile");
        updateProfileButton.addActionListener(new ButtonListener());

        // Initialize buttons for dashboard display
        displayDashboardButton = new JButton("Display Dashboard");
        displayDashboardButton.addActionListener(new ButtonListener());

        // Initialize buttons for schedule management
        scheduleSessionButton = new JButton("Schedule Session");
        scheduleSessionButton.addActionListener(new ButtonListener());


        getAllTrainersButton = new JButton("Get all Trainers");
        getAllTrainersButton.addActionListener(new ButtonListener());

        // Initialize buttons for schedule management
        setTrainerAvailability = new JButton("Set Trainer Availability");
        setTrainerAvailability.addActionListener(new ButtonListener());

        // Initialize buttons for schedule management
        viewMemberProfileByName = new JButton("View Member details by name");
        viewMemberProfileByName.addActionListener(new ButtonListener());

        getAllRoomsButton = new JButton("Get all rooms");
        getAllRoomsButton.addActionListener(new ButtonListener());

        addRoomBooking = new JButton("Add a room booking");
        addRoomBooking.addActionListener(new ButtonListener());

        updateRoomBooking = new JButton("Update room booking");
        updateRoomBooking.addActionListener(new ButtonListener());

        deleteRoomBooking = new JButton("Delete a room booking");
        deleteRoomBooking.addActionListener(new ButtonListener());

        getAllEquipment = new JButton("Get all equipment");
        getAllEquipment.addActionListener(new ButtonListener());

        updateMaintenanceRecord = new JButton("Update maintenance record for equipment");
        updateMaintenanceRecord.addActionListener(new ButtonListener());

        getLatestMaintenanceInfo = new JButton("Get latest maintenance of equipment");
        getLatestMaintenanceInfo.addActionListener(new ButtonListener());

        getAllClasses = new JButton("Get all classes");
        getAllClasses.addActionListener(new ButtonListener());

        updateClassDay = new JButton("Update class day");
        updateClassDay.addActionListener(new ButtonListener());

        processBilling = new JButton("Process Billing");
        processBilling.addActionListener(new ButtonListener());

        // Initialize result text area
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
    }


    private void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());

        // Create panel for member functions
        JPanel memberFunctionPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        memberFunctionPanel.setBorder(BorderFactory.createTitledBorder("Member Functions"));
        memberFunctionPanel.add(getAllMembersButton);
        memberFunctionPanel.add(registerMemberButton);
        memberFunctionPanel.add(updateProfileButton);
        memberFunctionPanel.add(displayDashboardButton);
        memberFunctionPanel.add(scheduleSessionButton);

        // Add member function panel to the left side
        pane.add(memberFunctionPanel, BorderLayout.NORTH);

        JPanel trainerFunctionPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        trainerFunctionPanel.setBorder(BorderFactory.createTitledBorder("Trainer Functions"));
        trainerFunctionPanel.add(getAllTrainersButton);
        trainerFunctionPanel.add(setTrainerAvailability);
        trainerFunctionPanel.add(viewMemberProfileByName);

        // Add trainer function panel next to member function panel
        pane.add(trainerFunctionPanel, BorderLayout.EAST);

        JPanel adminstrativeFunctionPanel = new JPanel(new GridLayout(10,1,10,10));
        adminstrativeFunctionPanel.setBorder(BorderFactory.createTitledBorder("Adminstrative Functions"));
        adminstrativeFunctionPanel.add(getAllRoomsButton);
        adminstrativeFunctionPanel.add(addRoomBooking);
        adminstrativeFunctionPanel.add(updateRoomBooking);
        adminstrativeFunctionPanel.add(deleteRoomBooking);
        adminstrativeFunctionPanel.add(getAllEquipment);
        adminstrativeFunctionPanel.add(updateMaintenanceRecord);
        adminstrativeFunctionPanel.add(getLatestMaintenanceInfo);
        adminstrativeFunctionPanel.add(getAllClasses);
        adminstrativeFunctionPanel.add(updateClassDay);
        adminstrativeFunctionPanel.add(processBilling);
        pane.add(adminstrativeFunctionPanel, BorderLayout.WEST);
        // Add result text area to the center
        pane.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
    }


    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == getAllMembersButton) {
                // Call the getAllMembers method and display the result
                String result = databaseApp.getAllMembers();
                resultTextArea.setText(result);
            } else if (e.getSource() == registerMemberButton) {
                String firstName = JOptionPane.showInputDialog("Enter first name:");
                String lastName = JOptionPane.showInputDialog("Enter last name:");
                String  dateOfBirthString =  JOptionPane.showInputDialog("Enter date of birth (YYYY-MM-DD):");
                Date dateOfBirth = Date.valueOf(dateOfBirthString);
                String fitnessGoals = JOptionPane.showInputDialog("Enter fitness goals:");
                String healthMetrics = JOptionPane.showInputDialog("Enter health metrics:");
                String membershipType = JOptionPane.showInputDialog("Enter membership type:");

                String FitnessAchievements = JOptionPane.showInputDialog("Enter fitness achievements to create the personalized dashboard");
                String HealthStatistics = JOptionPane.showInputDialog("Enter health statistics to create the personalized dashboard");
                String ExerciseRoutines = JOptionPane.showInputDialog("Enter exercise routines to create the personalized dashboard");
                int result = databaseApp.registerMember(firstName, lastName, dateOfBirth, fitnessGoals, healthMetrics, membershipType);
                System.out.println(result);
                if (result == -1){
                    resultTextArea.setText("Couldn't register member");
                } else {
                    String result2 = databaseApp.createPersonalizedDashboard(FitnessAchievements, HealthStatistics, ExerciseRoutines, result);
                    resultTextArea.setText("Member "+ result + " has been registered and " + result2);
                }

            } else if (e.getSource() == updateProfileButton) {
                String userIDInput = JOptionPane.showInputDialog("Enter User ID:");
                int userID = Integer.parseInt(userIDInput);
                String firstName = JOptionPane.showInputDialog("Enter first name:");
                String lastName = JOptionPane.showInputDialog("Enter last name:");
                String  dateOfBirthString =  JOptionPane.showInputDialog("Enter date of birth (YYYY-MM-DD):");
                Date dateOfBirth = Date.valueOf(dateOfBirthString);
                String fitnessGoals = JOptionPane.showInputDialog("Enter fitness goals:");
                String healthMetrics = JOptionPane.showInputDialog("Enter health metrics:");
                String membershipType = JOptionPane.showInputDialog("Enter membership type:");

                String result = databaseApp.updateMemberProfile(userID,firstName, lastName, dateOfBirth, fitnessGoals, healthMetrics, membershipType);
                resultTextArea.setText(result);

            } else if (e.getSource() == displayDashboardButton) {
                String userIDInput = JOptionPane.showInputDialog("Enter User ID:");
                int userID = Integer.parseInt(userIDInput);

                String result = databaseApp.displayDashboard(userID);
                resultTextArea.setText(result);

            } else if  (e.getSource() == scheduleSessionButton) {
                // Prompt the user to input session details
                String memberIDInput = JOptionPane.showInputDialog("Enter Member ID:");
                int memberID = Integer.parseInt(memberIDInput);
                String trainerIDInput = JOptionPane.showInputDialog("Enter Trainer ID:");
                int trainerID = Integer.parseInt(trainerIDInput);
                String sessionDateString = JOptionPane.showInputDialog("Enter Session Date (YYYY-MM-DD):");
                Date sessionDate = Date.valueOf(sessionDateString);
                String sessionTimeString = JOptionPane.showInputDialog("Enter Session Time (HH:MM:SS):");
                Time sessionTime = Time.valueOf(sessionTimeString);

                // Call the schedulePersonalTrainingSession method and display the result
                String result = databaseApp.schedulePersonalTrainingSession(memberID, trainerID, sessionDate, sessionTime);
                resultTextArea.setText(result);
            } else if(e.getSource() == getAllTrainersButton){
                String result = databaseApp.getAllTrainers();
                resultTextArea.setText(result);
            } else if(e.getSource() == setTrainerAvailability){
                String trainerIDInput = JOptionPane.showInputDialog("Enter Trainer ID:");
                int trainerID = Integer.parseInt(trainerIDInput);
                String availability = JOptionPane.showInputDialog("Enter availability:");

                String result = databaseApp.setTrainerAvailability(trainerID, availability);
                resultTextArea.setText(result);
            } else if(e.getSource() == viewMemberProfileByName) {
                String memberFirstNameInput = JOptionPane.showInputDialog("Enter Member First Name:");
                String memberLastNameInput = JOptionPane.showInputDialog("Enter Member Last Name:");

                String result = databaseApp.viewMemberProfileByName(memberFirstNameInput, memberLastNameInput);
                resultTextArea.setText(result);
            }else if(e.getSource() == getAllRoomsButton){
                String result = databaseApp.getAllRooms();
                resultTextArea.setText(result);
            }else if (e.getSource() == addRoomBooking) {
                String name = JOptionPane.showInputDialog("Enter Name:");
                String capacityInput = JOptionPane.showInputDialog("Enter Capacity:");
                int capacity = Integer.parseInt(capacityInput);
                String roomNumberInput = JOptionPane.showInputDialog("Enter Room Number:");
                int roomNumber = Integer.parseInt(roomNumberInput);
                String bookingDateString = JOptionPane.showInputDialog("Enter Booking Date (YYYY-MM-DD):");
                Date bookingDate = Date.valueOf(bookingDateString);

                String result = databaseApp.addRoomBooking(name, capacity, roomNumber, bookingDate);
                resultTextArea.setText(result);
            }else if (e.getSource() == updateRoomBooking) {
                String roomName = JOptionPane.showInputDialog("Enter room name: ");
                String bookingDateString = JOptionPane.showInputDialog("Enter Booking Date (YYYY-MM-DD):");
                Date bookingDate = Date.valueOf(bookingDateString);
                String result = databaseApp.updateRoomBooking(roomName, bookingDate);
                resultTextArea.setText(result);
            }else if (e.getSource() == deleteRoomBooking) {
                String roomName = JOptionPane.showInputDialog("Enter Room Name:");
                String result = databaseApp.deleteRoomBooking(roomName);
                resultTextArea.setText(result);
            }else if(e.getSource() == getAllEquipment){
                String result = databaseApp.getAllEquipment();
                resultTextArea.setText(result);
            }else if (e.getSource() == updateMaintenanceRecord) {
                String equipmentName = JOptionPane.showInputDialog("Enter equipment name");
                String maintenanceDateInput = JOptionPane.showInputDialog("Enter Maintenance Date (YYYY-MM-DD):");
                Date maintenanceDate = Date.valueOf(maintenanceDateInput);
                String maintenanceType = JOptionPane.showInputDialog("Enter Maintenance Type:");
                String details = JOptionPane.showInputDialog("Enter Maintenance Details:");

                String result = databaseApp.updateMaintenanceRecord(equipmentName,maintenanceDate, maintenanceType, details);
                resultTextArea.setText(result);
            } else if (e.getSource() == getLatestMaintenanceInfo) {
                String equipmentType = JOptionPane.showInputDialog("Enter Equipment Type:");
                String result = databaseApp.getLatestMaintenanceInfo(equipmentType);
                resultTextArea.setText(result);
            }else if (e.getSource() == getAllClasses) {
                String result = databaseApp.getAllClasses();
                resultTextArea.setText(result);

            }else if (e.getSource() == updateClassDay) {
                String className = JOptionPane.showInputDialog("Enter Class Name:");
                String newDay = JOptionPane.showInputDialog("Enter New Day:");
                String result = databaseApp.updateClassDay(className,newDay);
                resultTextArea.setText(result);

            } else if (e.getSource() == processBilling) {
                String memberIDInput = JOptionPane.showInputDialog("Enter Member ID:");
                int memberID = Integer.parseInt(memberIDInput);
                String amountInput = JOptionPane.showInputDialog("Enter Amount:");
                double amount = Double.parseDouble(amountInput);
                String paymentMethod = JOptionPane.showInputDialog("Enter Payment Method:");
                String service = JOptionPane.showInputDialog("Enter Service:");

                String result = databaseApp.processBilling(memberID, amount, paymentMethod, service);
                resultTextArea.setText(result);
                String showAllBillings = JOptionPane.showInputDialog("Do you want to see all the billings (Y or N): ");
                if (showAllBillings.equalsIgnoreCase("Y")){
                    String result2 = databaseApp.getAllBillings();
                    resultTextArea.append(result2);
                }
            }


        }
    }

    public static void main(String[] args) {
        DatabaseApp databaseApp = new DatabaseApp();
        SwingUtilities.invokeLater(() -> {
            DatabaseView gui = new DatabaseView(databaseApp);
            gui.setVisible(true);
        });
    }
}
