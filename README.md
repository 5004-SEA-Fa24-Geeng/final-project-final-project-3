[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)
# Final Project for CS 5004 - (APPLICATION NAME/Update)

(remove this and add your sections/elements)
This readme should contain the following information: 

* The group member's names and link to their personal githubs
  - Yuchen Huang: https://github.com/StarfishJ
  - Xinqi Cao: https://github.com/xiaoyesaya
  - Hewen Shen: https://github.com/Strawbelly
  - LU ZHANG: https://github.com/amit204joshi

* The application name and a brief description of the application
  - Name: Celebrity Wishlist
  - Description: This application is a Character Management System built using Java and the MVC (Model-View-Controller) architecture. It provides a user-friendly interface for managing and viewing celebrity information, with features including:
    - Display and manage celebrity profiles
    - Build a wishlist
    - get info from online API
    - Show celebrity image
    - Search celebrity's name
    - Filter celebrity list
    - Sort celebrity list
    - Add/remove celebrities to/from wishlist
    - Save wishlist to JSON file
    

* Links to design documents and manuals
    - [design document](DesignDocuments/README.md)
    - [manual](Manual/README.md)

* Instructions on how to run the application
  How to Run the Character Management System

    Prerequisites:
    1. Java Development Kit (JDK) 21 or later
       2. Gradle build tool
       3. Internet connection (for image loading)
    
    Setup Instructions:
    
    1. Environment Setup
        - Install JDK 21 from Oracle or OpenJDK
        - Set JAVA_HOME environment variable
        - Install Gradle if not already installed
    
       2. Project Setup
           - Clone the repository
           - Navigate to project directory
           - Ensure celebrities.json is in the correct location
    
       3. Building the Application
          Open terminal/command prompt and run:
          ```
          gradle build
          ```
          This will:
           - Download dependencies
           - Compile source code
           - Run tests
           - Create executable JAR
    
       4. Running the Application
          After successful build, run:
           Main.java
    
       5. Using the Application
           - Main window will open automatically
           - Use filter panel to search celebrities
           - Click heart icon to add to wishlist
           - Use sort options to organize list
           - Save wishlist using File menu
    
    Troubleshooting:
    
    1. Build Issues
        - Check Java version: java -version
        - Verify Gradle installation: gradle -v
        - Ensure proper file permissions
    
       2. Runtime Issues
           - Check internet connection
           - Verify JSON file location
           - Ensure sufficient memory
           - Check console for error messages
    
       3. Common Errors
           - "Java version mismatch": Update JDK
           - "File not found": Check file paths
           - "Out of memory": Increase JVM heap size
           - "Connection error": Check network
       Additional Notes:
           - Application requires internet for image loading
           - First run may be slower due to image caching
           - Regular saves recommended for wishlist
           - Check console for detailed logs if needed
