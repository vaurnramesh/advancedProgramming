package model;

public abstract class Post {


    private String id;
    private String title;
    private String description;
    private String creatorId;
    private String status;
    private String replies;

    public Post(String id, String title, String description, String creatorId, String status, String replies) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.status = status;
        this.replies = replies;
    }


    public String getPostDetails() {
        return ("ID: " + this.id + "\nTitle: " + this.title + "\nDescription: "
                + this.description + "\nCreator ID: " + this.creatorId + "\nStatus: " + this.status);

    }

    public abstract boolean handleReply(Reply reply);

    public abstract String getReplyDetails();

    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getCreatorId() {
        return creatorId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReplies() {
        return replies;
    }

}