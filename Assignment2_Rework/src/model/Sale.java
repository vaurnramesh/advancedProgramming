package model;

import java.util.ArrayList;

public class Sale extends Post {
    private double askingPrice;
    private double highestOffer;
    private double minimumRaise;
    private ArrayList<Reply> replyList = new ArrayList<Reply>();


    public Sale(String id, String title, String description, String creatorId, String status, String replies, double askingPrice, double highestOffer, double minimumRaise) {
        super(id, title, description, creatorId, status, replies);
        this.askingPrice = askingPrice;
        this.highestOffer = highestOffer;
        this.minimumRaise = minimumRaise;
    }

    public double getAskingPrice() {
        return askingPrice;
    }

    public double getHighestOffer() {
        return highestOffer;
    }

    public double getMinimumRaise() {
        return minimumRaise;
    }

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    @Override
    public String getPostDetails() {

        return super.getPostDetails() + ("\nHighest Offer: " + this.highestOffer + "\nMinimum Raise: "
                + this.minimumRaise);
    }

    @Override
    public boolean handleReply(Reply reply) {
        if(this.getStatus().equalsIgnoreCase("OPEN")){
            replyList.add(reply);

            if(replyList.size() == 0){
                this.highestOffer = reply.getValue();

                return true;
            }
            else{
                if(reply.getValue() >= this.askingPrice){
                    this.setStatus("Closed");
                    System.out.println("Item has been sold to " + reply.getResponderId());
                    return true;
                }
                if(reply.getValue() >= this.highestOffer + this.minimumRaise){
                    this.highestOffer = reply.getValue();
                    System.out.println("Offer accepted but Items still on sale. Increase your offer price");

                    return true;
                }
                if(reply.getValue() <= this.highestOffer){
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String getReplyDetails() {
        ArrayList<Double>offerHistory = new ArrayList<Double>();

        System.out.println("\nAsking Price: " + this.askingPrice +"\n--Offer History--");

        for(Reply r : this.replyList){
            System.out.println(r.getResponderId() + " : $" + r.getValue());
        }

        return "";
    }

}
