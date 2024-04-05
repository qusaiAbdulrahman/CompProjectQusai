INSERT INTO Member (FirstName, LastName, DateOfBirth, FitnessGoals, HealthMetrics, MembershipType)
VALUES 
('Qusai', 'Abdulrahman', '2003-03-06', 'Muscle Gain and Fat Loss', 'Normal BMI, good sugar levels', 'Premium'),
('Mohamad', 'Ali', '1985-04-22', 'Improving fitness', 'Normal blood pressure', 'Premium'),
('Michael', 'Jordan', '1968-03-10', 'Strength training', 'High muscle mass', 'Basic');


INSERT INTO PersonalizedDashboard (FitnessAchievements, HealthStatistics, ExerciseRoutines, MemberID)
VALUES
('Ran a marathon last month', 'Blood pressure within healthy range, BMI improved by 5%', 'Morning jog, weightlifting routine', 1),
('Completed 100 push-ups in a single set', 'Lowered cholesterol by 10%', 'Push-up variations, cardio intervals', 2),
('Improved flexibility, can touch toes', 'Increased muscle mass by 7%', 'Yoga sessions, resistance training', 3);


INSERT INTO Trainer (FirstName, LastName, Speciality, Availability)
VALUES
('Jurgen', 'Klopp', 'Yoga', 'Monday'),
('David', 'Brown', 'Strength and Conditioning', 'Saturday'),
('Jessica', 'Alisson', 'Cardio and HIIT', 'Thursday');


INSERT INTO Class (ClassType, Description,Day,TrainerID)
VALUES
('Yoga', 'Beginner-friendly yoga class focusing on relaxation and flexibility','Monday',1),
('Strength', 'High-intensity iron training','Saturday',2),
('Cardio', 'Full-body workout incorporating cardio','Thursday',3);


INSERT INTO Room (Name, Capacity, RoomNumber, BookingDate)
VALUES
('Studio A', 25, 101,'2024-04-01'),
('Studio B', 12, 102,'2024-04-02'),
('Main Gym Room', 80, 100,'2024-04-03');

INSERT INTO AdministrativeStaff (FirstName, LastName)
VALUES
('Sarah', 'James'),
('Michael', 'Smith');

INSERT INTO GymEquipment (EquipmentType, Description, LastMaintenanceDate, MaintenanceType, MaintenanceInformation)
VALUES
('Treadmill', 'Commercial treadmill with adjustable speed and incline','2024-03-15', 'Routine check-up', 'Checked and lubricated all moving parts'),
('Dumbbells', 'Set of dumbbells ranging from 50lbs to 90lbs','2024-03-20', 'Repair', 'Replaced broken handle'),
('RubberBands', 'set of bands used for stretching','2024-03-10', 'Cleaning', 'Wiped down and disinfected');

INSERT INTO BillingAndPayment (MemberID, Amount, Date, PaymentMethod, Service)
VALUES
(1, 50.00, '2024-03-01', 'Credit Card', 'Monthly Membership Fee'),
(3, 100.00, '2024-03-10', 'Cash', 'Personal Training Session'),
(2, 505.00, '2024-03-05', 'Debit Card', 'Monthly Membership Fee');


INSERT INTO PersonalTrainingSession (Date, SessionDuration, MemberID, TrainerID)
VALUES
('2024-03-03', '10:00:00', 1, 1),
('2024-03-07', '15:00:00', 2, 2),
('2024-03-12', '09:00:00', 3, 3);


INSERT INTO Register (MemberID, ClassID)
VALUES
(1, 1),
(2, 2),
(3, 3);



