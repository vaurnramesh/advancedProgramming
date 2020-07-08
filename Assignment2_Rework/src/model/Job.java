package model;

import java.util.ArrayList;

public class Job extends Post {
    private int proposedPrice;
    private int lowestOffer;
    private ArrayList<Reply> replyList = new ArrayList<Reply>();


    public Job(String id, String title, String description, String creatorId, String status, String replies, int proposedPrice, int lowestOffer) {
        super(id, title, description, creatorId, status, replies);
        this.proposedPrice = proposedPrice;
        this.lowestOffer = lowestOffer;
    }

    public int getProposedPrice() {
        return proposedPrice;
    }

    public int getLowestOffer() {
        return lowestOffer;
    }

    @Override
    public String getPostDetails() {

        if(this.lowestOffer > this.proposedPrice){
            return super.getPostDetails() + "\nProposed Price: " + this.proposedPrice;
        }

        return super.getPostDetails() + "\nProposed Price: " + this.proposedPrice +
                "\nLowest Offer: " + this.lowestOffer;
    }

    @Override
    public boolean handleReply(Reply reply) {
        if (this.getStatus().equalsIgnoreCase("OPEN")) {
            if (reply.getValue() < this.lowestOffer) {
                this.lowestOffer = (int)reply.getValue();
                replyList.add(reply);
            }
            if (reply.getValue() >= this.lowestOffer) {
                if(reply.getValue() >= this.proposedPrice){
                    this.setStatus("Closed");
                    System.out.println("Job offered!");
                    return true;
                }
                return false;
            }




        }
        return false;
    }



    @Override
    public String getReplyDetails() {
        //System.out.println("IN reply details");
        for(Reply r : this.replyList) {
            System.out.println((r.getResponderId() + ": " + r.getValue() + "\n"));
        }
        return "";
    }
}
