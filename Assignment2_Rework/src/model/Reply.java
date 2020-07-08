package model;

public class Reply {
    private String postId;
    private double value;
    private String responderId;

    public Reply(String postId, double value, String responderId) {
        this.postId = postId;
        this.value = value;
        this.responderId = responderId;
    }


    public String getPostId() {
        return postId;
    }

    public double getValue() {
        return value;
    }

    public String getResponderId() {
        return responderId;
    }

    public String getReplyDetails(){
        return ("ID: " + this.postId + "\nValue: " + this.value + "\nResponderId: "
                + this.responderId);
    }
}
