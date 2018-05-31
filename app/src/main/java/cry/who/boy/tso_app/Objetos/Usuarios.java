package cry.who.boy.tso_app.Objetos;

public class Usuarios {
    String Email;
    String Username;

    public Usuarios() {

    }

    public Usuarios(String Email, String Username) {
        this.Email = Email;
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
