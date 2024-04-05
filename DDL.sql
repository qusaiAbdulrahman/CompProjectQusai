
CREATE TABLE Member (
    MemberID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    DateOfBirth DATE,
    FitnessGoals TEXT,
    HealthMetrics TEXT,
    MembershipType TEXT
);

CREATE TABLE PersonalizedDashboard (
    DashboardID SERIAL PRIMARY KEY,
    FitnessAchievements TEXT,
    HealthStatistics TEXT,
    ExerciseRoutines TEXT,
    MemberID INT,
    FOREIGN KEY (MemberID) REFERENCES member(MemberID)
);


CREATE TABLE Trainer (
    TrainerID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Speciality VARCHAR(255),
    Availability VARCHAR(255)
);

CREATE TABLE Class (
    ClassID SERIAL PRIMARY KEY,
    ClassType VARCHAR(255),
    Description TEXT,
    Day TEXT,
    TrainerID INT,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)  
);

CREATE TABLE Room (
    RoomID SERIAL PRIMARY KEY,
    Name VARCHAR(255),
    Capacity INT,
    RoomNumber INT,
    BookingDate DATE
);

CREATE TABLE AdministrativeStaff (
    StaffID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255)
);

CREATE TABLE GymEquipment (
    EquipmentID SERIAL PRIMARY KEY,
    EquipmentType VARCHAR(255),
    Description TEXT,
    LastMaintenanceDate DATE,
    MaintenanceType VARCHAR(255),
    MaintenanceInformation TEXT
);

CREATE TABLE BillingAndPayment (
    TransactionID SERIAL PRIMARY KEY,
    MemberID INT,
    Amount DECIMAL(10,2),
    Date DATE,
    PaymentMethod VARCHAR(50),
    Service VARCHAR(255),
    FOREIGN KEY (MemberID) REFERENCES member(MemberID)
);

CREATE TABLE PersonalTrainingSession (
    SessionID SERIAL PRIMARY KEY,
    Date DATE,
    SessionDuration TIME,
    MemberID INT,
    TrainerID INT,
    FOREIGN KEY (MemberID) REFERENCES member(MemberID),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);

CREATE TABLE Register (
    MemberID INT,
    ClassID INT,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID),
    PRIMARY KEY (MemberID, ClassID)
);
