package brayan0428.androidgooglesheet.com.android_googlesheet.POJOS;

public class Users {
    String Id,Names,LastNames,Address,Email,Image;

    public Users(String Id,String Names,String LastNames,String Address,String Email,String Image){
        this.Id = Id;
        this.Names = Names;
        this.LastNames = LastNames;
        this.Address = Address;
        this.Email = Email;
        this.Image = Image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getLastNames() {
        return LastNames;
    }

    public void setLastNames(String lastNames) {
        LastNames = lastNames;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
