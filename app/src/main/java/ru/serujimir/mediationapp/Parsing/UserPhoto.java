package ru.serujimir.mediationapp.Parsing;

public class UserPhoto {

    public UserPhoto(String photo_address) {
        this.photo_address = photo_address;
    }
    public String getPhoto_address() {
        return photo_address;
    }
    public void setPhoto_address(String photo_address) {
        this.photo_address = photo_address;
    }
    String photo_address;
}
