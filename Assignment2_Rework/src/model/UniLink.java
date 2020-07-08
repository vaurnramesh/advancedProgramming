package model;

import controllers.PostDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class UniLink {
    final static String DATE_FORMAT = "dd/MM/yyyy";
    Scanner sc = new Scanner(System.in);
    //int eventCount = 1;
    //int saleCount = 1;
    //int jobCount = 1;

    DAO dao = new DAO();
    private ArrayList<Post> postArrayList = new ArrayList<>();
    private ArrayList<Reply> replyList = new ArrayList<>();


    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void displaySystemMenu() {
        String username;
        int choice = 0;
        System.out.println("** Unilink System **");
        System.out.println("1. Log In");
        System.out.println("2. Quit");
        System.out.print("Enter your choice: ");
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Invalid input. Enter a number");
            sc.next();
        }

        if (choice == 1) {
            System.out.println("Enter your username!");
            try {


                username = sc.nextLine();
                if (username.trim().charAt(0) == 's' || username.trim().charAt(0) == 'S') {
                    displayStudentMenu(username);
                } else {
                    System.out.println("Invalid username. Username should start with 's' or 'S'");
                    displaySystemMenu();
                }
            } catch (Exception ex) {
                System.out.println("Enter a username starting with 's");
            } finally {
                displaySystemMenu();
            }
        } else if (choice == 2) {
            System.out.println("Thank You");
            System.exit(0);
        } else {
            displaySystemMenu();
        }


    }

    public void displayStudentMenu(String username) {
        System.out.println("Welcome " + username + "!");
        System.out.println("1. Event Post");
        System.out.println("2. Sale Post");
        System.out.println("3. Job Post");
        System.out.println("4. Reply To Post");
        System.out.println("5. Display My Posts");
        System.out.println("6. Display All Posts");
        System.out.println("7. Close ");
        System.out.println("8. Delete ");
        System.out.println("9. Log Out");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {
            case 1:
                //eventCount++;
                newEventPost(username);
                break;

            case 2:
                //saleCount++;
                newSalePost(username);
                break;

            case 3:
                //jobCount++;
                newJobPost(username);
                break;

            case 4:
                replyToPost(username);
                break;

            case 5:
                displayMyPost(username);
                break;


            case 6:
                displayAllPost(username);
                break;

            case 7:
                System.out.println("Enter postId to be closed: ");
                String postId = sc.nextLine();
                closePost(username, postId);
                break;

            case 8:
                System.out.println("Enter post to be deleted: ");
                postId = sc.nextLine();
                deletePost(username, postId);
                break;

            case 9:
                displaySystemMenu();
                break;

            default:
                displayStudentMenu(username);

        }
    }

    public void deletePost(String username, String postId) {

        PreparedStatement pst;
        PreparedStatement pst2;
        dao.connectionOb.openConnection();
        try {
            if (postId.startsWith("EVE")) {
                pst = dao.connectionOb.conn.prepareStatement("DELETE FROM event WHERE creatorId=? AND id=?");
                pst.setString(1, username);
                pst.setString(2, postId);
                pst.executeUpdate();
                System.out.println("Update event is completed");
                pst2 = dao.connectionOb.conn.prepareStatement("DELETE FROM reply WHERE postID=?");
                pst2.setString(1,postId);
                pst2.executeUpdate();
                dao.connectionOb.closeConnection();
            } else if (postId.startsWith("SAL")) {
                pst = dao.connectionOb.conn.prepareStatement("DELETE FROM sale WHERE creatorId=? AND id=?");
                pst.setString(1, username);
                pst.setString(2, postId);
                pst.executeUpdate();
                System.out.println("Update sale is completed");
                pst2 = dao.connectionOb.conn.prepareStatement("DELETE FROM reply WHERE postID=?");
                pst2.setString(1,postId);
                pst2.executeUpdate();
                dao.connectionOb.closeConnection();
            } else if (postId.startsWith("JOB")) {
                pst = dao.connectionOb.conn.prepareStatement("DELETE FROM job WHERE creatorId=? AND id=?");
                pst.setString(1, username);
                pst.setString(2, postId);
                pst.executeUpdate();
                System.out.println("Update Job is completed");
                pst2 = dao.connectionOb.conn.prepareStatement("DELETE FROM reply WHERE postID=?");
                pst2.setString(1,postId);
                pst2.executeUpdate();
                dao.connectionOb.closeConnection();
            } else {
                dao.connectionOb.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePost(String username, String postId) {

        dao.connectionOb.openConnection();
        try {
            PreparedStatement pst;
            if (postId.startsWith("EVE")) {
                pst = dao.connectionOb.conn.prepareStatement("UPDATE event SET status =? WHERE creatorId=? AND id=?");
                pst.setString(1, "Closed");
                pst.setString(2, username);
                pst.setString(3, postId);
                pst.executeUpdate();
                System.out.println("Update event is completed");
                dao.connectionOb.closeConnection();
            } else if (postId.startsWith("SAL")) {
                pst = dao.connectionOb.conn.prepareStatement("UPDATE sale SET status =? WHERE creatorId=? AND id=?");
                pst.setString(1, "Closed");
                pst.setString(2, username);
                pst.setString(3, postId);
                pst.executeUpdate();
                System.out.println("Update sale is completed");
                dao.connectionOb.closeConnection();
            } else if (postId.startsWith("JOB")) {
                pst = dao.connectionOb.conn.prepareStatement("UPDATE job SET status =? WHERE creatorId=? AND id=?");
                pst.setString(1, "Closed");
                pst.setString(2, username);
                pst.setString(3, postId);
                pst.executeUpdate();
                System.out.println("Update Job is completed");
                dao.connectionOb.closeConnection();
            } else {
                System.out.println("Please enter post starting from EVE,SAL,JOB");
                dao.connectionOb.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayAllPost(String username) {
        try {
            dao.connectionOb.openConnection();
            Statement statement = dao.connectionOb.conn.createStatement();

            //to print the result set on console
            ResultSet rs1 = statement.executeQuery("SELECT * FROM event;");

            while (rs1.next()) {

                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nVenue : " + rs1.getString("venue") + "\nDate : " + rs1.getString("Date") + "\nCapacity : " + rs1.getInt("capacity") +
                        "\nAttendee Count : " + rs1.getInt("attendeeCount") + "\n");

            }


            ResultSet rs2 = statement.executeQuery("SELECT * FROM sale;");
            while (rs2.next()) {
                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nAsking Price : " + rs1.getDouble("askingPrice") + "\nHighest Offer : " + rs1.getDouble("highestOffer") + "\nMinimum Raise : " + rs1.getInt("minimumRaise") + "\n");
            }

            ResultSet rs3 = statement.executeQuery("SELECT * FROM job;");
            while (rs3.next()) {
                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nProposed Price : " + rs1.getDouble("proposedPrice") + "\nLowest Offer : " + rs1.getDouble("lowestOffer") + "\n");
            }
            dao.connectionOb.closeConnection();

        } catch (Exception e) {
            System.out.println(e);
            dao.connectionOb.closeConnection();
        }

        displayStudentMenu(username);
    }

    private void displayMyPost(String username) {
//        Iterator<Post> postIterator = postArrayList.iterator();
//        Iterator<Reply> replyIterator = replyList.iterator();
//        if (postArrayList.size() == 0) {
//            System.out.println("You have no posts available");
//        } else {
//            while (postIterator.hasNext()) {
//                Post ob = postIterator.next();
//                if (ob.getCreatorId().equalsIgnoreCase(username)) {
//                    System.out.println(ob.getPostDetails());
//                    System.out.println(ob.getReplyDetails());
//                }
//            }
//        }
        try {
            dao.connectionOb.openConnection();
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs1 = statement.executeQuery("SELECT * FROM event WHERE creatorId= \"" + username + "\";");
            while (rs1.next()) {
                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nVenue : " + rs1.getString("venue") + "\nDate : " + rs1.getString("Date") + "\nCapacity : " + rs1.getInt("capacity") +
                        "\nAttendee Count : " + rs1.getInt("attendeeCount") + "\n");
            }
            ResultSet rs2 = statement.executeQuery("SELECT * FROM sale WHERE creatorId= \"" + username + "\";");
            while (rs2.next()) {
                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nAsking Price : " + rs1.getDouble("askingPrice") + "\nHighest Offer : " + rs1.getDouble("highestOffer") + "\nMinimum Raise : " + rs1.getInt("minimumRaise") + "\n");
            }
            ResultSet rs3 = statement.executeQuery("SELECT * FROM job WHERE creatorId = \"" + username + "\";");
            while (rs3.next()) {
                System.out.println("\nId : " + rs1.getString("id") + "\nTitle : " + rs1.getString("title") +
                        "\nDescription : " + rs1.getString("description") + "\nCreator ID : " + rs1.getString("creatorId") + "\nStatus : " + rs1.getString("status") +
                        "\nProposed Price : " + rs1.getDouble("proposedPrice") + "\nLowest Offer : " + rs1.getDouble("lowestOffer") + "\n");
            }
            dao.connectionOb.closeConnection();


        } catch (Exception ex) {
            System.out.println(ex);
            dao.connectionOb.closeConnection();

        }
        displayStudentMenu(username);
    }

    private void replyToPost(String username) {
        Reply reply;
        String value;
        System.out.println("Enter post id or Q to quit");
        String reqPostId = sc.nextLine();
        Iterator<Post> itr = postArrayList.iterator();
        if (!(reqPostId.equalsIgnoreCase("Q"))) {
            while (itr.hasNext()) {
                Post ob = itr.next();
                if (ob.getId().equalsIgnoreCase(reqPostId)) {
                    if (ob.getCreatorId().equalsIgnoreCase(username)) {
                        System.out.println("Creators cannot reply to their own posts");
                        displayStudentMenu(username);
                    } else {
                        if (ob.getId().equalsIgnoreCase(reqPostId)) {
                            System.out.println(ob.getPostDetails());
                        }
                        if (ob.getId().contains("JOB") && ob.getId().equalsIgnoreCase(reqPostId)) {
                            System.out.println("Enter your offer or 'Q' to quit: ");
                            value = sc.nextLine();
                            if (!value.equalsIgnoreCase("Q")) {
                                reply = new Reply(ob.getId(), Double.parseDouble(value), username);
                                ob.handleReply(reply);


                                System.out.println("Reply added");

                            } else {
                                System.out.println("exited");
                            }
                        }
                        if (ob.getId().contains("EVE") && ob.getId().equalsIgnoreCase(reqPostId)) {
                            boolean userHasReplied = false;
                            for (Reply replyOb : replyList) {
                                if (replyOb.getResponderId().equalsIgnoreCase(username)) {
                                    System.out.println("You have already replied to the event. Thank you.");
                                    userHasReplied = true;
                                    break;
                                }

                            }
                            if (!userHasReplied) {
                                System.out.println("Enter 1 if you are attending or 'Q' to quit: ");
                                value = sc.nextLine();
                                if (!value.equalsIgnoreCase("Q")) {
                                    reply = new Reply(ob.getId(), Double.parseDouble(value), username);

                                    if (ob.handleReply(reply)) {
                                        System.out.println("Reply added");
                                    }
                                } else {
                                    System.out.println("exited");
                                }
                            }

                        }
                        if (ob.getId().contains("SAL") && ob.getId().equalsIgnoreCase(reqPostId)) {
                            System.out.println("Enter your offer or 'Q' to quit: ");
                            value = sc.nextLine();
                            if (!value.equalsIgnoreCase("Q")) {
                                reply = new Reply(ob.getId(), Double.parseDouble(value), username);

                                if (ob.handleReply(reply)) {
                                    System.out.println("Reply added");
                                }
                            } else {
                                System.out.println("exited");
                            }
                        }
                    }
                }
            }
        } else {
            displayStudentMenu(username);
        }
        displayStudentMenu(username);


    }

    private void newJobPost(String username) {
        System.out.println("Enter details of the job below: ");
        String jobName = "";
        String description = "";
        String proposedPrice = "";
        boolean isNumber = false;
        int priceNum = 0;
        PreparedStatement pst;

        while (jobName.isEmpty()) {
            System.out.print("Name: ");
            jobName = sc.nextLine();
        }

        while (description.isEmpty()) {
            System.out.print("Description: ");
            description = sc.nextLine();
        }

        while ((proposedPrice.isEmpty()) || (!isNumber) || (priceNum < 0)) {
            System.out.print("Proposed Price: ");
            proposedPrice = sc.nextLine();
            isNumber = checkInt(proposedPrice);
            if (isNumber) {
                priceNum = Integer.parseInt(proposedPrice);
            }

        }


        String jobID = null;

        //Initialising jobCount
        int jobCount = 1;

        //get jobCount from db
        try {
            dao.connectionOb.openConnection();
            int jobCounter;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT jobCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            jobCounter = rs.getInt("jobCounter");
            jobCount = jobCount + jobCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }

        if (jobCount < 10) {
            jobID = "JOB00" + jobCount;
        }
        if (jobCount < 100 && jobCount > 9) {
            jobID = "JOB0" + jobCount;
        }
        if (jobCount > 99 && jobCount < 1000) {
            jobID = "JOB" + jobCount;
        }

        Job jobOb = new Job(jobID, jobName, description, username, "Open", null, priceNum,priceNum);


        postArrayList.add(jobOb);

        dao.dao_Job(jobOb);
        System.out.println("Success! Your event has been created with id " + jobOb.getId());
        //jobCount update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int jobCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT jobCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                jobCounter = rs.getInt("jobCounter");
                System.out.println("****************");
                System.out.println(jobCounter);
                ++jobCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET jobCounter =" + jobCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }

        displayStudentMenu(username);
    }

    private void newSalePost(String username) {
        System.out.println("Enter details of the item to sale below: ");
        String saleName = "";
        String description = "";
        String askingPrice = "";
        String minimumRaise = "";
        boolean isNumber = false;
        int priceNum = 0;
        int priceNum2 = 0;

        while (saleName.isEmpty()) {
            System.out.print("Name: ");
            saleName = sc.nextLine();
        }


        while (description.isEmpty()) {
            System.out.print("Description: ");
            description = sc.nextLine();
        }

        while ((askingPrice.isEmpty()) || (isNumber == false) || (priceNum < 0)) {
            System.out.print("Asking price: ");
            askingPrice = sc.nextLine();
            isNumber = checkInt(askingPrice);
            if (isNumber) {
                priceNum = Integer.parseInt(askingPrice);

            }
        }

        while ((minimumRaise.isEmpty()) || (isNumber == false) || (priceNum2 < 0)) {
            System.out.print("Minimum raise: ");
            minimumRaise = sc.nextLine();
            isNumber = checkInt(minimumRaise);
            if (isNumber) {
                priceNum2 = Integer.parseInt(minimumRaise);
            }
        }

        String saleID = null;

        //Initialising saleCount
        int saleCount = 1;
        PreparedStatement pst;

        //get saleCount from db
        try {
            dao.connectionOb.openConnection();
            int saleCounter;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT saleCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            saleCounter = rs.getInt("saleCounter");
            saleCount = saleCount + saleCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }

        // SaleCount++;
        if (saleCount < 10) {
            saleID = "SAL00" + saleCount;
        }
        if (saleCount < 100 && saleCount > 9) {
            saleID = "SAL0" + saleCount;
        }
        if (saleCount > 99 && saleCount < 1000) {
            saleID = "SAL" + saleCount;
        }

        Sale saleOb = new Sale(saleID, saleName, description, username, "Open", null, priceNum, 0, priceNum2);
        postArrayList.add(saleOb);
        dao.dao_Sale(saleOb);
        System.out.println("Success! Your new sale has been created " + saleOb.getId());

        //saleCount update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int saleCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT saleCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                saleCounter = rs.getInt("saleCounter");
                System.out.println("****************");
                System.out.println(saleCounter);
                ++saleCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET saleCounter =" + saleCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }


        displayStudentMenu(username);


    }

    public void newEventPost(String username) {

        String capacity = "";

        boolean isDate = false;
        String date = "";
        String description = "";
        String title = "";
        String venue = "";
        boolean isNumber = false;
        int priceNum = 0;
        System.out.println("Enter details of the event below: ");


        while (title.isEmpty()) {
            System.out.print("Name: ");
            title = sc.nextLine();
        }

        while (description.isEmpty()) {
            System.out.print("Description: ");
            description = sc.nextLine();
        }

        while (venue.isEmpty()) {
            System.out.print("Venue: ");
            venue = sc.nextLine();
        }


        while (!isDate || date.isEmpty()) {
            System.out.print("Date: ");
            date = sc.nextLine();
            isDate = isDateValid(date);
        }

        while ((capacity.isEmpty()) || (!isNumber) || (priceNum < 0)) {
            System.out.print("Capacity: ");
            capacity = sc.nextLine();
            isNumber = checkInt(capacity);
            if (isNumber) {
                priceNum = Integer.parseInt(capacity);
            }
        }
        PreparedStatement pst;
        //int type = 1;
        int eventCount = 1;


        //get eventCount from db
        try {
            dao.connectionOb.openConnection();
            int eventCounter;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT eventCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            eventCounter = rs.getInt("eventCounter");
            eventCount = eventCount + eventCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }


        // eventCount++;
        String eventID = null;
        if (eventCount < 10) {
            eventID = "EVE00" + eventCount;
        }
        if (eventCount < 100 && eventCount > 9) {
            eventID = "EVE0" + eventCount;
        }
        if (eventCount > 99 && eventCount < 1000) {
            eventID = "EVE" + eventCount;
        }


        Event eventOb = new Event(eventID, title, description, username, "Open", null, venue, date, priceNum, 0);
        postArrayList.add(eventOb);
        dao.dao_Event(eventOb);
        System.out.println("Success! Your event has been created with id " + eventOb.getId());


        //event count update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int eventCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT eventCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                eventCounter = rs.getInt("eventCounter");
                System.out.println("****************");
                System.out.println(eventCounter);
                ++eventCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET eventCounter =" + eventCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        displayStudentMenu(username);
    }

    public void startProgram() {

//        Event eventOb = new Event("EVE001", "World Cup", "Finals", "s10", "Open", null, "MCG", "30/3/2019", 10, 0);
//        postArrayList.add(eventOb);
//        Reply eventReply1 = new Reply("EVE001", 1, "s11");
//        Reply eventReply2 = new Reply("EVE001", 1, "s12");
//        eventOb.handleReply(eventReply1);
//        eventOb.handleReply(eventReply2);
//
//        Sale saleOb = new Sale("SAL001", "MacBook", "Quick Sale", "s10", "Open", null, 100, 20, 5);
//        postArrayList.add(saleOb);
//        Reply saleReply1 = new Reply("SAL001", 1, "s11");
//        saleOb.handleReply(saleReply1);
//
//        Job jobOb = new Job("JOB001", "Painting", "Very colorful", "s10", "Open", null, 50);
//        postArrayList.add(jobOb);
//        Reply jobReply1 = new Reply("JOB001", 1, "s11");
//        jobOb.handleReply(jobReply1);
        dao.connectionOb.openConnection();
        dao.create_PostTable();
        dao.create_ReplyTable();
        dao.connectionOb.closeConnection();
        //displaySystemMenu();

    }

    private boolean checkInt(String input) {

        boolean status = false;
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception ex) {
            System.out.println("Invalid input. Enter a number: ");
            return false;
        }
    }

    public void addEventPost(String title, String description, String username, String status, String venue, String date, int capacity, int attendeeCount, String imageEvent) {
        //status = "Open";

        PreparedStatement pst = null;
        //int type = 1;
        int eventCount = 1;
        //get eventCount from db
        try {
            dao.connectionOb.openConnection();
            int eventCounter = 0;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT eventCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            eventCounter = rs.getInt("eventCounter");
            eventCount = eventCount + eventCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        // eventCount++;
        String eventID = null;
        if (eventCount < 10) {
            eventID = "EVE00" + eventCount;
        }
        if (eventCount < 100 && eventCount > 9) {
            eventID = "EVE0" + eventCount;
        }
        if (eventCount > 99 && eventCount < 1000) {
            eventID = "EVE" + eventCount;
        }
        Event eventOb1 = new Event(eventID, title, description, username, status, null, venue, date, capacity, attendeeCount);
        postArrayList.add(eventOb1);
        //dao.dao_Event(eventOb1);


        dao.connectionOb.openConnection();

        try {

            Statement statement = dao.connectionOb.conn.createStatement();

            statement.execute("INSERT INTO \"event\" (\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"venue\",\"date\",\"capacity\",\"attendeeCount\", \"image\") " +
                    "VALUES (\"" + eventID + "\",\"" + title + "\",\"" + description + "\",\"" + username + "\"," +
                    " \"Open\" ,\"" + venue + "\",\"" + date + "\"," +
                    "\"" + capacity + "\",\"" + 0 + "\", \"" + imageEvent + "\");");

            System.out.println(imageEvent);
            statement.close();
            dao.connectionOb.closeConnection();


        } catch (NullPointerException ne) {
            System.out.println("FAILED : Event statement not added to database");
            ne.printStackTrace();
            dao.connectionOb.closeConnection();

        } catch (Exception ex) {
            System.out.println("FAILED : Event statement not added to database");
            ex.printStackTrace();
            dao.connectionOb.closeConnection();
        }


        System.out.println("Success! Your event has been created with id " + eventOb1.getId());


        //event count update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int eventCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT eventCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                eventCounter = rs.getInt("eventCounter");
                ++eventCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET eventCounter =" + eventCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        //displayStudentMenu(username);
    }

    public void addSalePost(String title, String description, String username, String status, double askingPrice, double minimumRaise, String imageSale) {
        String saleID = null;

        //Initialising saleCount
        int saleCount = 1;
        PreparedStatement pst = null;

        //get saleCount from db
        try {
            dao.connectionOb.openConnection();
            int saleCounter = 0;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT saleCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            saleCounter = rs.getInt("saleCounter");
            saleCount = saleCount + saleCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }

        // SaleCount++;
        if (saleCount < 10) {
            saleID = "SAL00" + saleCount;
        }
        if (saleCount < 100 && saleCount > 9) {
            saleID = "SAL0" + saleCount;
        }
        if (saleCount > 99 && saleCount < 1000) {
            saleID = "SAL" + saleCount;
        }

        Sale saleOb1 = new Sale(saleID, title, description, username, "Open", null, askingPrice, 0, minimumRaise);
        postArrayList.add(saleOb1);
        //dao.dao_Sale(saleOb1);


        try {
            dao.connectionOb.openConnection();

            Statement statement = dao.connectionOb.conn.createStatement();
            statement.execute("INSERT INTO sale(\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"askingPrice\",\"minimumRaise\", \"image\") " +
                    "VALUES (\"" + saleID + "\",\"" + title + "\",\"" + description + "\"," +
                    "\"" + username + "\", \"Open\" ,\"" + askingPrice + "\", \"" + minimumRaise + "\", \"" + imageSale + "\");");
            statement.close();
            dao.connectionOb.closeConnection();


        } catch (NullPointerException ne) {
            System.out.println("FAILED : Event statement not added to database");
            ne.printStackTrace();
            dao.connectionOb.closeConnection();

        } catch (Exception ex) {
            System.out.println("Sale statement added to database");
            ex.printStackTrace();
            dao.connectionOb.closeConnection();
        }


        System.out.println("Success! Your new sale has been created " + saleOb1.getId());

        //saleCount update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int saleCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT saleCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                saleCounter = rs.getInt("saleCounter");
                ++saleCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET saleCounter =" + saleCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }


        //displayStudentMenu(username);


    }

    public void addJobPost(String title, String description, String username, String status, int proposedPrice, String imageJob) {
        PreparedStatement pst = null;
        String jobID = null;

        //Initialising jobCount
        int jobCount = 1;

        //get jobCount from db
        try {
            dao.connectionOb.openConnection();
            int jobCounter = 0;
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT jobCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            jobCounter = rs.getInt("jobCounter");
            jobCount = jobCount + jobCounter;
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }

        if (jobCount < 10) {
            jobID = "JOB00" + jobCount;
        }
        if (jobCount < 100 && jobCount > 9) {
            jobID = "JOB0" + jobCount;
        }
        if (jobCount > 99 && jobCount < 1000) {
            jobID = "JOB" + jobCount;
        }

        Job jobOb1 = new Job(jobID, title, description, username, "Open", null, proposedPrice, proposedPrice);


        postArrayList.add(jobOb1);

        //dao.dao_Job(jobOb1);

        try {
            dao.connectionOb.openConnection();

            Statement statement = dao.connectionOb.conn.createStatement();
            statement.execute("INSERT INTO job(\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"proposedPrice\",\"lowestOffer\",\"image\") " +
                    "VALUES (\"" + jobID + "\",\"" + title + "\",\"" + description + "\",\"" + username + "\"," +
                    " \"Open\" ,\"" + proposedPrice + "\", \"" + 0 + "\", \"" + imageJob + "\");");
            System.out.println("Job statement has been added to database");
            statement.close();
            dao.connectionOb.closeConnection();


        } catch (NullPointerException ne) {
            System.out.println("FAILED : Job statement not added to database");
            ne.printStackTrace();
            dao.connectionOb.closeConnection();

        } catch (Exception ex) {
            dao.connectionOb.closeConnection();
            ex.printStackTrace();
        }


        System.out.println("Success! Your event has been created with id " + jobOb1.getId());
        //jobCount update in DB
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            int jobCounter = 0;
            ResultSet rs = statement.executeQuery("SELECT jobCounter FROM counter WHERE rowNo= \"" + 1 + "\";");
            while (rs.next()) {
                jobCounter = rs.getInt("jobCounter");
                ++jobCounter;
            }
            pst = dao.connectionOb.conn.prepareStatement("UPDATE counter SET jobCounter =" + jobCounter + " WHERE rowNo = \"" + 1 + "\";");
            pst.executeUpdate();
            pst.close();
            statement.close();
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            System.out.println("Could not update row number");
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
    }

    public ObservableList<PostDetails> addFilter(String type, String status, String creator, String username) {

        ObservableList<PostDetails> posts = FXCollections.observableArrayList();

        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            String event = "SELECT * FROM event";
            String sale = "SELECT * FROM sale";
            String job = "SELECT * FROM job";
            String all = "SELECT id,title,status,creatorId,image,description FROM event UNION " +
                    "SELECT id,title,status,creatorId,image,description FROM sale UNION " +
                    "SELECT id,title,status,creatorId,image,description FROM job";

            if (type.equalsIgnoreCase("event")) {

                ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("../images/eventImage.jpg")));
                img.setFitHeight(80);
                img.setFitWidth(100);

                ResultSet rs1 = null;
                if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into open and all");
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ") WHERE status = \"Open\"; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into closed and all");
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ") WHERE status= \"Closed\";");
                } else if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("my posts")) {
                    System.out.println("I came into open and my");
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ") WHERE status = \"Open\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("my posts")) {
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ") WHERE status = \"Closed\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("my posts")) {
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ") WHERE creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("all posts")) {
                    rs1 = statement.executeQuery("SELECT * FROM event;");
                } else
                    rs1 = statement.executeQuery("SELECT * FROM (" + event + ");");
                while (rs1.next()) {

                    PostDetails postDetailsOb = new PostDetails(rs1.getString("id"), rs1.getString("title"), rs1.getString("status"), rs1.getString("creatorId"), rs1.getString("image"), img, rs1.getString("description"));
                    posts.add(postDetailsOb);
                }
            } else if (type.equalsIgnoreCase("sale")) {
                ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("../images/saleImage.png")));
                img.setFitHeight(80);
                img.setFitWidth(100);
                ResultSet rs2 = null;
                if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into open and all");
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ") WHERE status = \"Open\"; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into closed and all");
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ") WHERE status= \"Closed\";");
                } else if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("my posts")) {
                    System.out.println("I came into open and my");
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ") WHERE status = \"Open\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("my posts")) {
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ") WHERE status = \"Closed\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("my posts")) {
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ") WHERE creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("all posts")) {
                    rs2 = statement.executeQuery("SELECT * FROM sale;");
                } else
                    rs2 = statement.executeQuery("SELECT * FROM (" + sale + ");");
                while (rs2.next()) {
                    PostDetails postDetailsOb = new PostDetails(rs2.getString("id"), rs2.getString("title"), rs2.getString("status"), rs2.getString("creatorId"), rs2.getString("image"), img, rs2.getString("description"));
                    posts.add(postDetailsOb);
                }
            } else if (type.equalsIgnoreCase("job")) {
                ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("../images/jobImage.jpg")));
                img.setFitHeight(80);
                img.setFitWidth(100);
                ResultSet rs3 = null;
                if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into open and all");
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ") WHERE status = \"Open\"; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into closed and all");
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ") WHERE status= \"Closed\";");
                } else if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("my posts")) {
                    System.out.println("I came into open and my");
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ") WHERE status = \"Open\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("my posts")) {
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ") WHERE status = \"Closed\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("my posts")) {
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ") WHERE creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("all posts")) {
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ");");
                } else
                    rs3 = statement.executeQuery("SELECT * FROM (" + job + ");");
                while (rs3.next()) {
                    PostDetails postDetailsOb = new PostDetails(rs3.getString("id"), rs3.getString("title"), rs3.getString("status"), rs3.getString("creatorId"), rs3.getString("image"), img, rs3.getString("description"));
                    posts.add(postDetailsOb);
                }
            } else if (type.equalsIgnoreCase("all")) {
                ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("../images/jobImage.jpg")));
                img.setFitHeight(80);
                img.setFitWidth(100);
                ResultSet rs4 = null;
                if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into open and all");
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ") WHERE status = \"Open\"; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("all")) {
                    System.out.println("I came into closed and all");
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ") WHERE status= \"Closed\";");
                } else if (status.equalsIgnoreCase("open") && creator.equalsIgnoreCase("my posts")) {
                    System.out.println("I came into open and my");
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ") WHERE status = \"Open\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("closed") && creator.equalsIgnoreCase("my posts")) {
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ") WHERE status = \"Closed\" AND creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("my posts")) {
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ") WHERE creatorId = '" + username + "'; ");
                } else if (status.equalsIgnoreCase("all") && creator.equalsIgnoreCase("all posts")) {
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ");");
                } else
                    rs4 = statement.executeQuery("SELECT * FROM (" + all + ");");
                while (rs4.next()) {
                    PostDetails postDetailsOb = new PostDetails(rs4.getString("id"), rs4.getString("title"), rs4.getString("status"), rs4.getString("creatorId"), rs4.getString("image"), img, rs4.getString("description"));
                    posts.add(postDetailsOb);
                }

            } else if (type.equalsIgnoreCase("type") || status.equalsIgnoreCase("Status") || creator.equalsIgnoreCase("Creator")) {
                ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("../images/jobImage.jpg")));
                img.setFitHeight(80);
                img.setFitWidth(100);
                ResultSet rs5 = null;
                rs5 = statement.executeQuery("SELECT * FROM (" + all + ");");
                while (rs5.next()) {
                    PostDetails postDetailsOb = new PostDetails(rs5.getString("id"), rs5.getString("title"), rs5.getString("status"), rs5.getString("creatorId"), rs5.getString("image"), img, rs5.getString("description"));
                    posts.add(postDetailsOb);
                }
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dao.connectionOb.closeConnection();
        return posts;


//        try {
//            Statement statement = dao.connectionOb.conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT id,title,status,creatorId,image \n" +
//                    "FROM event UNION \n" +
//                    "SELECT id,title,status,creatorId,image\n" +
//                    "FROM sale UNION\n" +
//                    "SELECT id,title,status,creatorId,image\n" +
//                    "FROM job;");
//            while (rs.next()) {
//                PostDetails postDetailsOb = new PostDetails(rs.getString("id"), rs.getString("title"), rs.getString("status"), rs.getString("creatorId"), rs.getString("image"));
//                System.out.println(rs.getString("id"));
//                posts.add(postDetailsOb);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("catch");
//            e.printStackTrace();
//        }
//        System.out.println(posts.size());
//
//        dao.connectionOb.closeConnection();
//
//        return posts;
    }

    public ArrayList<Event> addEventDetails(String postId) {
        ArrayList<Event> eventArrayList = new ArrayList<>();

        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM event WHERE id = '" + postId + "';");
            while (rs.next()) {
                Event eventOb = new Event(rs.getString("id"), rs.getString("title"), rs.getString("description"),
                        rs.getString("creatorId"), "Open", null, rs.getString("venue"), rs.getString("date"),
                        rs.getInt("capacity"), rs.getInt("attendeeCount"));
                eventArrayList.add(eventOb);
                statement.close();
            }

            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.connectionOb.closeConnection();
        return eventArrayList;
    }

    public ArrayList<Sale> addSaleDetails(String postId) {
        ArrayList<Sale> saleArrayList = new ArrayList<>();
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM sale WHERE id = '" + postId + "';");
            while (rs.next()) {
                Sale saleOb = new Sale(rs.getString("id"), rs.getString("title"), rs.getString("description"),
                        rs.getString("creatorId"), "Open", null, rs.getDouble("askingPrice"), rs.getDouble("highestOffer"), rs.getDouble("minimumRaise"));
                saleArrayList.add(saleOb);
                statement.close();
            }
            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        return saleArrayList;
    }

    public ArrayList<Job> addJobDetails(String postId) {
        ArrayList<Job> jobArrayList = new ArrayList<>();
        dao.connectionOb.openConnection();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM job WHERE id = '" + postId + "';");
            while (rs.next()) {
                Job jobOb = new Job(rs.getString("id"), rs.getString("title"), rs.getString("description"),
                        rs.getString("creatorId"), "Open", null, rs.getInt("proposedPrice"), rs.getInt("lowestOffer"));
                jobArrayList.add(jobOb);
                statement.close();

            }
            dao.connectionOb.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        return jobArrayList;
    }

//    public ArrayList<Reply> seeReplies(String postId) {
//
//        dao.connectionOb.openConnection();
//        ArrayList<Reply> replyArrayList = new ArrayList<>();
//        try {
//            Statement statement = dao.connectionOb.conn.createStatement();
//
//            String command = "";
//            command = "Select * from reply where postID = \"" + postId + "\"";
//            ResultSet rs = statement.executeQuery(command);
//            while (rs.next()) {
//                Reply replyOb = new Reply(rs.getString("postID"), rs.getDouble("value"), rs.getString("respondentID"));
//                replyArrayList.add(replyOb);
//            }
//            dao.connectionOb.closeConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//            dao.connectionOb.closeConnection();
//        }
//        return replyArrayList;
//    }

    public void newReply(String username, String postId, double values, String status) {

        //Check reply table does not have same values
        //Insert the 3 fields reply into table
        //If conditions for post type to update their tables

        PreparedStatement pst;
        PreparedStatement pst2;
        double response = 0;
        String respondentId = "";
        dao.connectionOb.openConnection();
        try {
            if (status.equalsIgnoreCase("Open")) {
                if (postId.startsWith("EVE")) {
                    int attendeeCount = 0;
                    int capacity = 0;


                    Statement statement2 = dao.connectionOb.conn.createStatement();

                    ResultSet rs = statement2.executeQuery("SELECT capacity, attendeeCount FROM event WHERE id= \"" + postId + "\";");
                    while (rs.next()) {
                        attendeeCount = rs.getInt("attendeeCount");
                        capacity = rs.getInt("capacity");
                        ++attendeeCount;
                    }

                    ResultSet rs2 = statement2.executeQuery("SELECT value, respondentID FROM reply WHERE postID = \"" + postId + "\";");
                    while(rs2.next()){
                        response = rs2.getDouble("value");
                        respondentId = rs2.getString("respondentID");
                    }

                    if(respondentId.equalsIgnoreCase(username)&& response >= 1) {
                        System.out.println("Respondent already exists");
                    }
                    else {
                        System.out.println("Response is being added");
                        pst = dao.connectionOb.conn.prepareStatement("UPDATE event SET attendeeCount =" + attendeeCount + " WHERE id = \"" + postId + "\";");
                        pst.executeUpdate();


                        if (attendeeCount == capacity) {
                            pst2 = dao.connectionOb.conn.prepareStatement("UPDATE event SET status = \"Closed\" WHERE id = \"" + postId + "\";");
                            pst2.executeUpdate();
                            statement2.close();
                            dao.connectionOb.closeConnection();
                        }

                        //Inserting into reply details
                        dao.connectionOb.openConnection();
                        statement2 = dao.connectionOb.conn.createStatement();
                        statement2.execute("INSERT INTO reply(\"postID\",\"value\",\"respondentID\")" +
                                "VALUES (\"" + postId + "\",\"" + attendeeCount + "\", \"" + username + "\");");
                        dao.connectionOb.closeConnection();
                    }

                    dao.connectionOb.closeConnection();
                } else if (postId.startsWith("SAL")) {
                    try {
                        double askingPrice = 0;
                        double minimumRaise = 0;
                        double highestOffer = 0;
                        Statement statement = dao.connectionOb.conn.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT askingPrice, minimumRaise, highestOffer FROM sale WHERE id = \"" + postId + "\";");
                        while (rs.next()) {
                            askingPrice = rs.getDouble("askingPrice");
                            minimumRaise = rs.getDouble("minimumRaise");
                            highestOffer = rs.getDouble("highestOffer");
                        }

                        if(values == 0){
                            System.out.println("Cannot be null");
                        }
                        else {
                            if (values < askingPrice) {
                                if (highestOffer == 0) {
                                    pst = dao.connectionOb.conn.prepareStatement("UPDATE sale SET highestOffer =" + values + " WHERE id = \"" + postId + "\";");
                                    pst.executeUpdate();
                                } else if (highestOffer > 0) {
                                    if (values >= highestOffer + minimumRaise) {
                                        pst = dao.connectionOb.conn.prepareStatement("UPDATE sale SET highestOffer =" + values + " WHERE id = \"" + postId + "\";");
                                        pst.executeUpdate();
                                    } else if (values < highestOffer + minimumRaise) {
                                        System.out.println("Please enter the appropriate offer");
                                    }
                                }
                            } else if (values >= askingPrice) {
                                pst = dao.connectionOb.conn.prepareStatement("UPDATE sale SET status = \"Closed\" WHERE id = \"" + postId + "\";");
                                System.out.println("-------SALE REPLY-------");
                                System.out.println("This item has been sold to " + username);
                                pst.executeUpdate();
                            }

                            statement.close();
                            dao.connectionOb.closeConnection();


                            //Inserting into reply details
                            dao.connectionOb.openConnection();
                            Statement statement2 = dao.connectionOb.conn.createStatement();
                            statement2.execute("INSERT INTO reply(\"postID\",\"value\",\"respondentID\")" +
                                    "VALUES (\"" + postId + "\",\"" + values + "\", \"" + username + "\");");
                            System.out.println("-------SALE REPLY-------");
                            System.out.println("Sale reply added");
                            System.out.println("");
                            dao.connectionOb.closeConnection();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (postId.startsWith("JOB")) {
                    try {
                        double proposedPrice = 0;
                        double lowestOffer = 0;
                        Statement statement = dao.connectionOb.conn.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT proposedPrice, lowestOffer FROM job WHERE id = \"" + postId + "\";");
                        while (rs.next()) {
                            proposedPrice = rs.getDouble("proposedPrice");
                            lowestOffer = rs.getDouble("lowestOffer");
                        }

                        if (values < proposedPrice && values != 0 && values < lowestOffer) {
                            pst = dao.connectionOb.conn.prepareStatement("UPDATE job SET lowestOffer =" + values + " WHERE id = \"" + postId + "\";");
                            pst.executeUpdate();
                            //Inserting into reply details
                            dao.connectionOb.openConnection();
                            Statement statement2 = dao.connectionOb.conn.createStatement();
                            statement2.execute("INSERT INTO reply(\"postID\",\"value\",\"respondentID\")" +
                                    "VALUES (\"" + postId + "\",\"" + values + "\", \"" + username + "\");");
                            System.out.println("-------JOB REPLY-------");
                            System.out.println("Job reply added");
                            System.out.println("");
                            dao.connectionOb.closeConnection();

                        } else if (values >= proposedPrice || values >= lowestOffer) {
                            System.out.println("Please enter a correct bid");
                            dao.connectionOb.openConnection();
                            Statement statement2 = dao.connectionOb.conn.createStatement();
                            statement2.execute("INSERT INTO reply(\"postID\",\"value\",\"respondentID\")" +
                                    "VALUES (\"" + postId + "\",\"" + values + "\", \"" + username + "\");");
                            dao.connectionOb.closeConnection();
                        }
                        else if(values == 0 ){
                            System.out.println("Please enter the correct bid");
                        }
                        if (values < 0) {
                            pst = dao.connectionOb.conn.prepareStatement("UPDATE job SET status = \"Closed\" WHERE id = \"" + postId + "\";");
                            System.out.println("-------JOB REPLY-------");
                            System.out.println("This job has been assigned to " + username);
                            pst.executeUpdate();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();

        }


    }


    public ObservableList<Reply> seeReplies2(String postId) {
        ObservableList<Reply> replyArrayList = FXCollections.observableArrayList();
        dao.connectionOb.openConnection();
        //ArrayList<Reply> replyArrayList = new ArrayList<>();
        try {
            Statement statement = dao.connectionOb.conn.createStatement();

            String command = "";
            command = "Select * from reply where postID = \"" + postId + "\"";
            ResultSet rs = statement.executeQuery(command);
            while (rs.next()) {
                Reply replyOb = new Reply(rs.getString("postID"), rs.getDouble("value"), rs.getString("respondentID"));
                replyArrayList.add(replyOb);
            }
            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            dao.connectionOb.closeConnection();
        }
        return replyArrayList;
    }


    public void updateEventDetails(String postId, String eventName, String eventDescription, String username, String status, String venue, String date, int capacity, int attendeeCount, String imageEvent) {
        dao.connectionOb.openConnection();
        PreparedStatement pst = null;

        try {
            pst = dao.connectionOb.conn.prepareStatement("UPDATE event SET title = \"" + eventName + "\", description = \"" + eventDescription + "\", " +
                    "venue = \"" + venue + "\", status = \"" + status + "\", date = \"" + date + "\", capacity = \"" + capacity + "\", image = \"" + imageEvent + "\" WHERE " +
                    "id = \"" + postId + "\";");

            pst.executeUpdate();
            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.connectionOb.closeConnection();

    }


    public void updateSaleDetails(String postId, String saleName, String saleDescription, String username, String status, double askingPrice, double minimumRaise, String saleImage) {

        dao.connectionOb.openConnection();
        PreparedStatement pst = null;

        try {
            pst = dao.connectionOb.conn.prepareStatement("UPDATE sale SET title = \"" + saleName + "\", description = \"" + saleDescription + "\"," +
                    "status = \"" + status + "\", askingPrice = \"" + askingPrice + "\", minimumRaise = \"" + minimumRaise + "\", image = \"" + saleImage + "\" WHERE " +
                    "id = \"" + postId + "\";");

            pst.executeUpdate();
            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.connectionOb.closeConnection();

    }


    public void updateJobDetails(String postId, String jobName, String jobDescription, String username, String status, int proposedPrice,String jobImage){
        dao.connectionOb.openConnection();
        PreparedStatement pst = null;

        try {
            pst = dao.connectionOb.conn.prepareStatement("UPDATE job SET title = \"" + jobName + "\", description = \"" + jobDescription + "\"," +
                    "status = \"" + status + "\", proposedPrice = \"" + proposedPrice + "\", image = \"" + jobImage + "\" WHERE " +
                    "id = \"" + postId + "\";");

            pst.executeUpdate();
            dao.connectionOb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.connectionOb.closeConnection();
    }


    public void exportFile(String path){
        String filename = path + "\\export_data.txt";
        System.out.println(filename);
            try {
                FileWriter fw = new FileWriter(filename);
                dao.connectionOb.openConnection();

                String query = "SELECT * FROM event";
                String query2 = "SELECT * FROM sale";
                String query3 = "SELECT * FROM job";
                String query4 = "SELECT * FROM reply";
                Statement statement = dao.connectionOb.conn.createStatement();
                int res = -22;
               // res = dao.connectionOb.conn.createStatement().executeUpdate("backup to " + path + "\\databaseNew.db");
                System.out.println("Executed backup. Got value " + res);
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    fw.append('E');
                    fw.append('V');
                    fw.append('E');
                    fw.append('N');
                    fw.append('T');
                    fw.append('\n');
                    fw.append(rs.getString(1));
                    fw.append(',');
                    fw.append(rs.getString(2));
                    fw.append(',');
                    fw.append(rs.getString(3));
                    fw.append(',');
                    fw.append(rs.getString(4));
                    fw.append(',');
                    fw.append(rs.getString(5));
                    fw.append(',');
                    fw.append(rs.getString(6));
                    fw.append(',');
                    fw.append(rs.getString(7));
                    fw.append(',');
                    fw.append(rs.getString(8));
                    fw.append(',');
                    fw.append(rs.getString(9));
                    fw.append(',');
                    fw.append(rs.getString(10));
                    fw.append('\n');
                }

                ResultSet rs2 = statement.executeQuery(query2);
                while (rs2.next()) {
                    fw.append('S');
                    fw.append('A');
                    fw.append('L');
                    fw.append('E');
                    fw.append('\n');
                    fw.append(rs2.getString(1));
                    fw.append(',');
                    fw.append(rs2.getString(2));
                    fw.append(',');
                    fw.append(rs2.getString(3));
                    fw.append(',');
                    fw.append(rs2.getString(4));
                    fw.append(',');
                    fw.append(rs2.getString(5));
                    fw.append(',');
                    fw.append(rs2.getString(6));
                    fw.append(',');
                    fw.append(rs2.getString(7));
                    fw.append(',');
                    fw.append(rs2.getString(8));
                    fw.append(',');
                    fw.append(rs2.getString(9));
                    fw.append('\n');
                }
                ResultSet rs3 = statement.executeQuery(query3);
                while (rs3.next()) {
                    fw.append('J');
                    fw.append('O');
                    fw.append('B');
                    fw.append('\n');
                    fw.append(rs3.getString(1));
                    fw.append(',');
                    fw.append(rs3.getString(2));
                    fw.append(',');
                    fw.append(rs3.getString(3));
                    fw.append(',');
                    fw.append(rs3.getString(4));
                    fw.append(',');
                    fw.append(rs3.getString(5));
                    fw.append(',');
                    fw.append(rs3.getString(6));
                    fw.append(',');
                    fw.append(rs3.getString(7));
                    fw.append(',');
                    fw.append(rs3.getString(8));
                    fw.append('\n');

                }
                ResultSet rs4 = statement.executeQuery(query4);
                while (rs4.next()) {
                    fw.append('R');
                    fw.append('E');
                    fw.append('P');
                    fw.append('L');
                    fw.append('Y');
                    fw.append('\n');
                    fw.append(rs4.getString(1));
                    fw.append(',');
                    fw.append(rs4.getString(2));
                    fw.append(',');
                    fw.append(rs4.getString(3));
                    fw.append('\n');
                }
                fw.flush();
                fw.close();

                dao.connectionOb.closeConnection();
                System.out.println("File has been exported");

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
    }


    public void importFile(String path) {

//        try {
//            dao.connectionOb.openConnection();
//            int res = -22;
//            res = dao.connectionOb.conn.createStatement().executeUpdate("jdbc:sqlite;restoreFrom=" + path);
//            System.out.println("Executed restore. Got value " + res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}









