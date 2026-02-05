public class Usuario {
    String usuario;
    String password;
    boolean esDatosValidos(String usuario, String password){
        if(this.usuario.equals(usuario)&& this.password.equals(password)){
            return true;
        }
        return false;
    }
}
