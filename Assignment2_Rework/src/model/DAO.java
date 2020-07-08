package model;

import java.sql.*;

public class DAO {


    DbConnection connectionOb = new DbConnection();
    private Reply reply;

    public void create_PostTable(){
        try  {
            Statement statement = connectionOb.conn.createStatement();

//            statement.execute("CREATE TABLE IF NOT EXISTS post"
//                    + "(id TEXT, title TEXT, description TEXT, creatorId TEXT, status TEXT, replies TEXT, venue TEXT, date TEXT, capacity INTEGER," +
//                    "attendeeCount INTEGER, askingPrice NUMERIC, highestOffer NUMERIC, minimumRaise NUMERIC, proposedPrice INTEGER, lowestOffer INTEGER, PRIMARY KEY(\"id\"));");


            statement.execute("CREATE TABLE IF NOT EXISTS event"
                    + "(id TEXT, title TEXT, description TEXT, creatorId TEXT, status TEXT, venue TEXT, date TEXT, capacity INTEGER, attendeeCount INTEGER, image TEXT);");

            statement.execute("CREATE TABLE IF NOT EXISTS sale"
                    +"(id TEXT, title TEXT, description TEXT, creatorId TEXT, status TEXT, askingPrice NUMERIC, highestOffer NUMERIC, minimumRaise NUMERIC, image TEXT);");

            statement.execute("CREATE TABLE IF NOT EXISTS job"
                    +"(id TEXT, title TEXT, description TEXT, creatorId TEXT, status TEXT,proposedPrice INTEGER, lowestOffer INTEGER, image TEXT);");

            statement.execute("CREATE TABLE IF NOT EXISTS counter"
                    + "(rowNo INTEGER, eventCounter INTEGER, saleCounter INTEGER, jobCounter INTEGER);");


//          Adding Predefined values to counter table

            ResultSet r = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM counter");
            r.next();
            int count = r.getInt("rowcount") ;
            r.close() ;
            if(count == 0){
                statement.execute("INSERT INTO counter (rowNo,eventCounter,saleCounter,jobCounter) " +
                   "VALUES (\""+ 1 +"\", \"" + 0 + "\", \"" + 0 + "\", \"" + 0 + "\"); ");
            }else
                System.out.println("Counter already exists");

            System.out.println(" Tables created");

        }catch(SQLException e){
            System.out.println("Create Table Error to database");
            e.getStackTrace();
        }
        ;

    }

    public void create_ReplyTable(){
        try  {
            Statement statement = connectionOb.conn.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS \"reply\" (\n" +
                    "\t\"postID\"\tTEXT,\n" +
                    "\t\"value\"\tINTEGER,\n" +
                    "\t\"respondentID\"\tTEXT\n" +
                    ");");
        }catch(Exception e){
            System.out.println("Create Reply Table Error to database");
            e.getStackTrace();
            e.getMessage();
        }
    }

    public void dao_Event(Event event) {
        connectionOb.openConnection();
        String imageEvent = null;


        try {

            Statement statement = connectionOb.conn.createStatement();
            System.out.println(event.getId());
            statement.execute("INSERT INTO \"event\" (\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"venue\",\"date\",\"capacity\",\"attendeeCount\", \"imageEvent\") " +
                    "VALUES (\"" + event.getId() + "\",\"" + event.getTitle() + "\",\"" + event.getDescription() + "\",\"" + event.getCreatorId() + "\"," +
                    " \"Open\" ,\"" + event.getVenue() + "\",\"" + event.getDate() + "\"," +
                    "\"" + event.getCapacity() + "\",\"" + 0 + "\", \"" + imageEvent + "\");");

            statement.close();
            connectionOb.closeConnection();


        } catch (NullPointerException ne){
            System.out.println("FAILED : Event statement not added to database");
            ne.printStackTrace();
            connectionOb.closeConnection();

        } catch (Exception ex) {
            System.out.println("FAILED : Event statement not added to database");
            ex.printStackTrace();
            connectionOb.closeConnection();
        }
    }
    public void dao_Sale(Sale sale){
        try {
            connectionOb.openConnection();

            Statement statement = connectionOb.conn.createStatement();
            statement.execute("INSERT INTO sale(\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"askingPrice\",\"minimumRaise\", \"imageSale\") " +
                    "VALUES (\"" + sale.getId() + "\",\"" + sale.getTitle() + "\",\"" + sale.getDescription() + "\"," +
                    "\"" + sale.getCreatorId() + "\", \"Open\" ,\"" + sale.getAskingPrice() + "\", \"" + sale.getMinimumRaise() + "\", \"" + null + "\");");
            statement.close();
            connectionOb.closeConnection();


        }catch (NullPointerException ne){
            System.out.println("FAILED : Event statement not added to database");
            ne.printStackTrace();
            connectionOb.closeConnection();

        } catch (Exception ex) {
            System.out.println("Sale statement added to database");
            ex.printStackTrace();
            connectionOb.closeConnection();
        }
    }
    public void dao_Job(Job job){

        try {
            connectionOb.openConnection();

            Statement statement = connectionOb.conn.createStatement();
            statement.execute("INSERT INTO job(\"id\",\"title\",\"description\",\"creatorId\",\"status\",\"proposedPrice\",\"lowestOffer\") " +
                    "VALUES (\"" + job.getId() + "\",\"" + job.getTitle() + "\",\"" + job.getDescription() + "\",\"" + job.getCreatorId() + "\"," +
                    " \"Open\" ,\"" + job.getProposedPrice() + "\", \"" + 0 + "\");");
            System.out.println("Job statement has been added to database");
            statement.close();
            connectionOb.closeConnection();


        }catch (NullPointerException ne){
            System.out.println("FAILED : Job statement not added to database");
            ne.printStackTrace();
            connectionOb.closeConnection();

        } catch (Exception ex) {
            connectionOb.closeConnection();
            ex.printStackTrace();
        }
    }


    public void add_attendeeCount(String postId,String username){
        connectionOb.openConnection();

        PreparedStatement pst = null;

        int attendeeCount = 0;
        try { Statement statement = connectionOb.conn.createStatement();
           ResultSet rs = statement.executeQuery("SELECT attendeeCount FROM event WHERE id= \""+ postId +"\";");
           while(rs.next()){
               attendeeCount =rs.getInt("attendeeCount");
               ++attendeeCount;
           }
            pst = connectionOb.conn.prepareStatement("UPDATE event SET attendeeCount =" + attendeeCount + " WHERE id = \"" + postId + "\";");
            pst.executeUpdate();
            statement.close();
            connectionOb.closeConnection();

            //Inserting into reply details
            connectionOb.openConnection();
            Statement statement2 = connectionOb.conn.createStatement();
            statement2.execute("INSERT INTO reply(\"postID\",\"value\",\"respondentID\")" +
                    "VALUES (\"" + postId + "\",\"" + attendeeCount + "\", \"" + username + "\");");
            connectionOb.closeConnection();

        } catch (NullPointerException ne){
            System.out.println("FAILED : Attendee Count not added to event");
            ne.printStackTrace();
            connectionOb.closeConnection();

        } catch (Exception ex) {
            System.out.println("FAILED : Attendee Count not added to event");
            ex.printStackTrace();
            connectionOb.closeConnection();
        }

    }

    //    public void dao_ClosePost(Post post){
//        PreparedStatement pst = null;
//        try{
//            connectionOb.openConnection();
//            if(post.getId().startsWith("EVE")) {
//                pst = connectionOb.conn.prepareStatement("UPDATE event SET status =? WHERE creatorId=? AND id=?");
//                pst.setString(1, "Closed");
//                pst.setString(2, post.getCreatorId());
//                pst.setString(3, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update event is completed");
//            } else if(post.getId().startsWith("SAL")){
//                pst = connectionOb.conn.prepareStatement("UPDATE sale SET status =? WHERE creatorId=? AND id=?");
//                pst.setString(1, "Closed");
//                pst.setString(2, post.getCreatorId());
//                pst.setString(3, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update sale is completed");
//            } else if(post.getId().startsWith("JOB")){
//                pst = connectionOb.conn.prepareStatement("UPDATE job SET status =? WHERE creatorId=? AND id=?");
//                pst.setString(1, "Closed");
//                pst.setString(2, post.getCreatorId());
//                pst.setString(3, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update Job is completed");
//            }
//            connectionOb.closeConnection();
//
//        }catch(Exception ex){
//            System.out.println("FAILED: Error closing post from Database");
//            ex.getStackTrace();
//            connectionOb.closeConnection();
//        }
//    }
//    public void dao_DeletePost(Post post){
//        PreparedStatement pst = null;
//        try{
//            if(post.getId().startsWith("EVE")) {
//                connectionOb.openConnection();
//                pst = connectionOb.conn.prepareStatement("DELETE FROM event WHERE creatorId=? AND id=?");
//                pst.setString(1, post.getCreatorId());
//                pst.setString(2, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update is completed");
//            } else if(post.getId().startsWith("SAL")) {
//                connectionOb.openConnection();
//                pst = connectionOb.conn.prepareStatement("DELETE FROM sale WHERE creatorId=? AND id=?");
//                pst.setString(1, post.getCreatorId());
//                pst.setString(2, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update is completed");
//            } else if(post.getId().startsWith("JOB")){
//                connectionOb.openConnection();
//                pst = connectionOb.conn.prepareStatement("DELETE FROM job WHERE creatorId=? AND id=?");
//                pst.setString(1, post.getCreatorId());
//                pst.setString(2, post.getId());
//                pst.executeUpdate();
//                System.out.println("Update is completed");
//            }
//            connectionOb.closeConnection();
//
//        }catch(Exception ex){
//            System.out.println("FAILED: Error closing post from Database");
//            ex.getStackTrace();
//            connectionOb.closeConnection();
//        }
//    }


}


