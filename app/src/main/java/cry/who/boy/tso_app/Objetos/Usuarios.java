package cry.who.boy.tso_app.Objetos;

public class Usuarios {
    String Clave;
    String Contrasenia;

    public Usuarios(){

    }

    public Usuarios(String Clave, String Contrasenia){
        this.Clave = Clave;
        this.Contrasenia = Contrasenia;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }
}
