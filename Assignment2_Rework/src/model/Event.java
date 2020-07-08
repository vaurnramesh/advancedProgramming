package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Event extends Post {
    private String venue;
    private String date;
    private int capacity;
    private int attendeeCount;
    private ArrayList<Reply> replyList = new ArrayList<Reply>();
    DbConnection connectionOb = new DbConnection();
    DAO dao = new DAO();


    public Event(String id, String title, String description, String creatorId, String status, String replies, String venue, String date, int capacity, int attendeeCount) {
        super(id, title, description, creatorId, status, replies);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;
        this.attendeeCount = attendeeCount;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    @Override
    public String getPostDetails() {
        return super.getPostDetails()+ ("\nVenue: " + this.venue + "\nDate " + this.date
                +"\nCapacity: " + this.capacity + "\nAttendees: " + this.attendeeCount) ;
    }

    @Override
    public boolean handleReply(Reply reply) {

        Iterator<Reply> replyIterator = replyList.iterator();
        while(replyIterator.hasNext()){
            if(replyIterator.next().getResponderId().equalsIgnoreCase(reply.getResponderId())){
                System.out.println("Cannot reply again to this post.");
                return false;
            }
        }
        if(this.getStatus().equalsIgnoreCase("OPEN")){
            if(reply.getValue() == 1 && (this.attendeeCount < this.capacity)){
                this.attendeeCount++;
                //dao.add_attendeeCount(reply.getPostId(),reply.getResponderId()); //Updating attendee count in database
                replyList.add(reply);
                if(this.attendeeCount == this.capacity){
                    this.setStatus("Closed");
                }
                return true;
            }
            else {
                System.out.println("Capacity full. Cannot attend.");
                return false;
            }
        }
        return false;
    }

    @Override
    public String getReplyDetails() {


        ArrayList<String> attendees = new ArrayList<String>();
        //Iterator<model.Reply>replyIterator = replyList.iterator();
        for(Reply r : this.replyList){
            attendees.add(r.getResponderId());
            System.out.println(r.getResponderId());
        }
        String a = String.join(",", attendees);
        return ("\nAttendee list: " + a);
    }
}

