package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    //  Database
    static final String DB_URL = "jdbc:mysql://localhost:3306/pomona-transit";

    //  Database credentials
    static final String USER = "pomonatransit";
    static final String PASS = "pomonatransit";

    //  User input
    static Scanner in = new Scanner(System.in);

    public static void showTripsMenu(Connection conn) {
        System.out.println("showTripsMenu");
        System.out.println("Enter origin, destination, and date in YYYY-MM-DD format, space-separated:");

        String originIn = "A";//in.next();
        String destinationIn = "F";//in.next();
        String dateIn = "2000-01-01";//in.next();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM tripofferings AS t " +
                    "JOIN routes AS r " +
                    "ON r.id = t.route " +
                    "WHERE r.origin='" + originIn + "' " +
                    "AND r.destination = '" + destinationIn + "' " +
                    "AND t.date='" + dateIn + "'";
            rs = stmt.executeQuery(sql);

            String headerString = String.format("%-15s%-15s%-15s%-15s%-15s%-15s", "route", "date", "departs", "arrives", "driver", "bus");
            System.out.println(headerString);

            while (rs.next()) {
                String route = rs.getString("route");
                String date = rs.getString("date");
                String scheduledDeparture = rs.getString("scheduledDeparture");
                String scheduledArrival = rs.getString("scheduledArrival");
                String driver = rs.getString("driver");
                String bus = rs.getString("bus");

                String recordString = String.format("%-15s%-15s%-15s%-15s%-15s%-15s", route, date, scheduledDeparture, scheduledArrival, driver, bus);
                System.out.println(recordString);
            }

            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTripMenu(Connection conn) {
        System.out.println("Enter route, date (YYYY-MM-DD) and scheduled start time (hh:mm:ss), space-separated:");
        try {
            Statement stmt = conn.createStatement();
            String sql;

            String route = "";//in.next();
            String date = "";//in.next();
            String scheduledStartTime = "";//in.next();

            sql = String.format("delete from tripOfferings where route=%s and date=%s and scheduledDeparture=%s", route, date, scheduledStartTime);
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Trip successfully deleted!\n");
            }
            else {
                System.out.println("No such trip!\n");
            }
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void addTripOfferingsMenu(Connection conn) {
        System.out.println("Enter route, date (YYYY-MM-DD), scheduled start time (hh:mm:ss), bus ID, and driver name, space-separated ('q' for quit):");
        String firstInput = "";//in.next();
        if (firstInput != "q") {
            String route = firstInput;
            String date = "";//in.next();
            String scheduledStartTime = "";//in.next();
            String busId = "";//in.next();
            String driver = "";//in.next();

            try {
                Statement stmt = conn.createStatement();
                String sql = String.format("insert into tripOfferings values()");
                int rowsAffected = stmt.executeUpdate(sql);

                if (rowsAffected > 0) {
                    System.out.println("Trip successfully added!\n");
                }
                else {
                    System.out.println("No update made - please check validity of your data!\n");
                }

            }
            catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            //input complete, no more trips to add
        }




    }
    public static void changeTripOfferingDriverMenu(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String sql;

            System.out.println("Enter route, date (YYYY-MM-DD) and scheduled start time (hh:mm:ss), space-separated:");
            String route = "";//in.next();
            String date = "";//in.next();
            String scheduledStartTime = "";//in.next();

            System.out.println("Enter new driver name:");
            String newDriver = "";//in.next();


            sql = String.format("update from tripOfferings where route=%s and date=%s and scheduledDeparture=%s set driver=%s", route, date, scheduledStartTime, newDriver);
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Driver successfully updated!\n");
            }
            else {
                System.out.println("No such trip or invalid driver!\n");
            }

            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void changeTripOfferingBusMenu(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String sql;

            System.out.println("Enter route, date (YYYY-MM-DD) and scheduled start time (hh:mm:ss), space-separated:");
            String route = "";//in.next();
            String date = "";//in.next();
            String scheduledStartTime = "";//in.next();

            System.out.println("Enter new bus ID:");
            String newBusId = "";//in.next();


            sql = String.format("update from tripOfferings where route=%s and date=%s and scheduledDeparture=%s set bus=%s", route, date, scheduledStartTime, newBusId);
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Bus successfully updated!\n");
            }
            else {
                System.out.println("No such trip or invalid bus ID!\n");
            }

            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void editTripOfferingMenu(Connection conn) {

        System.out.println("editTripOfferingMenu");
        int menuSelection = 0;
            System.out.println("Welcome to Pomona Transit System!\n" +
                    "Please make a selection:\n" +
                    "1 - Delete a trip offering\n" +
                    "2 - Add a set of trip offerings\n" +
                    "3 - Change driver for a trip offering\n" +
                    "4 - Change bus for a trip offering\n";

            menuSelection = menuSelection == 0 ? 1 : in.nextInt();

            switch (menuSelection) {
                case 1:
                    deleteTripMenu(conn);
                    break;
                case 2:
                    addTripOfferingsMenu(conn);
                    break;
                case 3:
                    changeTripOfferingDriverMenu(conn);
                    break;
                case 4:
                    changeTripOfferingBusMenu(conn);
                    break;
                default:
                    break;
            }
    }

    public static void showStopsOnRouteMenu(Connection conn) {
        System.out.println("showStopsOnRouteMenu");
        System.out.println("Enter route id:");

        String routeIn = "101";//in.next();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;

            sql = "SELECT rs.route, rs.sequentialIndex, rs.stop, s.address FROM routeStops AS rs JOIN stops AS s ON rs.stop=s.id WHERE rs.route='" + routeIn + "' ORDER BY rs.sequentialIndex";
            rs = stmt.executeQuery(sql);

            String headerString = String.format("Stops for Route %s are as-follows:", routeIn);
            System.out.println(headerString);

            while (rs.next()) {
                String stop = rs.getString("stop");
                String address = rs.getString("address");

                String recordString = String.format("%s - %s", stop, address);
                System.out.println(recordString);
            }

            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void showDriverWeeklyScheduleMenu(Connection conn) {
        System.out.println("showDriverWeeklyScheduleMenu");
        System.out.println("Enter driver and first date in seven-day period (YYYY-MM-DD:, space-separated:");

        String routeIn = "101";//in.next();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;

            String driverIn = "Alex Driver";//in.next();
            String startDateIn = "2000-01-01";//in.next();

            sql = "SELECT * FROM tripofferings WHERE driver='" + driverIn + "' AND date >= '" + startDateIn + "' AND date <= date_add('" + startDateIn + "', INTERVAL 7 DAY) ORDER BY date, scheduledDeparture";
            rs = stmt.executeQuery(sql);

            String explainString = String.format("%s's Schedule for seven-day period starting %s is as-follows:", driverIn, startDateIn);
            System.out.println(explainString);

            String headerString = String.format("%-15s%-15s%-15s%-15s%-15s", "date", "route", "departure", "arrival", "bus");
            System.out.println(headerString);

            while (rs.next()) {
                String date = rs.getString("date");
                String route = rs.getString("route");
                String scheduledDeparture = rs.getString("scheduledDeparture");
                String scheduledArrival = rs.getString("scheduledArrival");
                String bus = rs.getString("bus");

                String recordString = String.format("%-15s%-15s%-15s%-15s%-15s", date, route, scheduledDeparture, scheduledArrival, bus);
                System.out.println(recordString);
            }

            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void addDriverMenu(Connection conn) {
        System.out.println("addDriverMenu");
        System.out.print("Enter new driver name, phone number (numbers only), space-delimited:");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;
            String explainString;

            String nameIn = "Some Dude";//in.next();
            String phoneIn = "0123456789";//in.next();
            System.out.println("\n");

            sql = "select * from drivers where name='" + nameIn + "'";
            rs = stmt.executeQuery(sql);


            if (rs.next()) {
                explainString = String.format("Record for driver %s already exists.  Please erase record first.\n", nameIn);
            }
            else {
                sql = String.format("INSERT INTO drivers VALUES ('%s','%s')", nameIn, phoneIn);
                int rowsAffected = stmt.executeUpdate(sql);
                explainString = (rowsAffected > 0) ? String.format("New record created for driver %s.\n", nameIn) : "No record created, invalid data";
            }

            System.out.println(explainString);

            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addBusMenu(Connection conn) {
        System.out.println("addBusMenu");
        System.out.print("Enter new bus id, model, and year-manufactured, space-delimited:");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;
            String explainString;

            String idIn = "X000X";//in.next();
            String modelIn = "bus model";//in.next();
            String yearManufacturedIn = "2020";//in.next();
            System.out.println("\n");

            sql = "select * from busses where id='" + idIn + "'";
            rs = stmt.executeQuery(sql);


            if (rs.next()) {
                explainString = String.format("Record for bus %s already exists.  Please erase record first.\n", idIn);
            }
            else {
                sql = String.format("INSERT INTO busses VALUES ('%s','%s','%s')", idIn, modelIn, yearManufacturedIn );
                int rowsAffected = stmt.executeUpdate(sql);
                explainString = (rowsAffected > 0) ? String.format("New record created for bus %s.\n", idIn) : "No record created, invalid data";
            }

            System.out.println(explainString);

            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteBusMenu(Connection conn) {
        System.out.println("deleteBusMenu");
        System.out.print("Enter new bus id, model, and year-manufactured, space-delimited:");

        try {
            Statement stmt = conn.createStatement();
            String sql;
            String explainString;

            String idIn = "X000X";//in.next();
            System.out.println("\n");

            sql = "delete from busses where id='" + idIn + "'";
            int rowsAffected = stmt.executeUpdate(sql);
            explainString = (rowsAffected > 0) ? String.format("Record deleted for bus %s.\n", idIn) : String.format("No record exists for bus %s.\n", idIn);
            System.out.println(explainString);

            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void recordTripVisitsMenu(Connection conn) {
        System.out.println("recordTripVisitsMenu");
        System.out.print("Enter route, date (YYYY-MM-DD), and scheduled departure time (HH:MM:SS), space-delimited:");
        try {
            Statement stmt = conn.createStatement();
            Statement insertStmt = conn.createStatement();
            ResultSet rs = null;
            String sql;
            String explainString;

            String routeIn = "101";// in.next();
            String dateIn = "2000-01-01";// in.next();
            String departureTimeIn = "09:00:00";// in.next();
            System.out.println("\n");

            sql = String.format("SELECT rs.route, t.date, t.scheduledDeparture, " +
                    "ADDTIME(t.scheduledDeparture, rs.driveTimeFromOrigin) AS scheduledArrival, " +
                    "rs.sequentialIndex, rs.stop, rs.driveTimeFromOrigin, s.address " +
                    "FROM routeStops AS rs " +
                    "JOIN stops AS s ON rs.stop=s.id join tripofferings AS t on t.route=rs.route "+
                    "WHERE rs.route='%s' AND t.date='%s' AND t.scheduledDeparture='%s' " +
                    "ORDER BY rs.sequentialIndex;", routeIn, dateIn, departureTimeIn);
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            System.out.println("Enter actual stop time (HH:MM:SS), pax in and pax out, space-delimited");
            String actualStart = "";
            while (rs.next()) {
                System.out.println("doing a stop record");
                int sequentialStop = rs.getInt("sequentialIndex");
                String stopId = rs.getString("stop");
                String address = rs.getString("address");
                String scheduledArrival = rs.getString("scheduledArrival");



                String recordString = String.format("Stop %02d - (%s) - %s (scheduled %s)", sequentialStop, stopId, address, scheduledArrival);
                System.out.println(recordString);

                String stopTimeIn = "09:00:10";//in.next();
                String paxInIn =  "10";//in.next();
                String paxOutIn = "0";//in.next();

                if (sequentialStop == 0) {
                    actualStart = stopTimeIn;
                }

                sql = String.format("INSERT INTO visits VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", routeIn, dateIn, sequentialStop, departureTimeIn, scheduledArrival, actualStart, stopTimeIn, paxInIn, paxOutIn);
                System.out.println(sql);
                int rowsAffected = insertStmt.executeUpdate(sql);
                if (rowsAffected == 0) {
                    System.out.println("Error - is there already data in the db for this stop?");
                }
            }

            rs.close();
            stmt.close();
            insertStmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            int menuSelection = 0;
            while (menuSelection != -1) {
                System.out.println("Welcome to Pomona Transit System!\n" +
                        "Please make a selection:\n" +
                        "1 - Show all trips, given origin/dest/date\n" +
                        "2 - Edit trip offering\n" +
                        "3 - Display all stops on route\n" +
                        "4 - Display driver's weekly schedule\n" +
                        "5 - Add driver\n" +
                        "6 - Add bus\n" +
                        "7 - Delete bus\n" +
                        "8 - Record actual stop times for a trip\n");

                menuSelection = menuSelection == 0 ? 8 : in.nextInt();

                switch (menuSelection) {
                    case 1:
                        showTripsMenu(conn);
                        break;
                    case 2:
                        editTripOfferingMenu(conn);
                        break;
                    case 3:
                        showStopsOnRouteMenu(conn);
                        break;
                    case 4:
                        showDriverWeeklyScheduleMenu(conn);
                        break;
                    case 5:
                        addDriverMenu(conn);
                        break;
                    case 6:
                        addBusMenu(conn);
                        break;
                    case 7:
                        deleteBusMenu(conn);
                        break;
                    case 8:
                        recordTripVisitsMenu(conn);
                        break;
                }
            }

            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
