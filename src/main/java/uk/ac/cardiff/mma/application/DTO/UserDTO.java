package uk.ac.cardiff.mma.application.DTO;

public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String birth;
    private String email;
    private String type;


    public UserDTO(String username, String firstName, String lastName, String birth, String email, String type) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.email = email;
        this.type = type;
    }

    public String getUsername(){
        return username;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getBirth(){
        return birth;
    }
    public String getEmail(){
        return email;
    }
    public  String getType(){
        return type;
    }
}
