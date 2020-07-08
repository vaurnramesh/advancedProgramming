package controllers;


import javafx.scene.image.ImageView;

public class PostDetails {
    private final String id;
    private final String title;
    private final String description;
    private final String status;
    private final String creatorId;
    private final String image;
    private ImageView photo;




    public PostDetails(String id, String title, String status, String creatorId, String image, ImageView photo, String description) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.creatorId = creatorId;
        this.image = image;
        this.photo = photo;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getImage() {
        return image;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }
}




