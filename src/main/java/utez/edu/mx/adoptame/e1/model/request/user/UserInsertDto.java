package utez.edu.mx.adoptame.e1.model.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import utez.edu.mx.adoptame.e1.entity.Rol;


public class UserInsertDto {

    @NotEmpty(message = "Debe de indicar el nombre del usuario")
    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,70}", message = "Valor no aceptado")
    private String name;

    @NotEmpty(message = "Debe de indicar el apellido del usuario")
    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,70}", message = "Valor no aceptado")
    private String firstLastname;

    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]", message = "Valor no aceptado")
    private String secondLastname;

    @NotEmpty(message = "Debe de indicar el username del usuario")
    @Email
    private String username;

    @NotEmpty(message = "Debe de indicar la contraseña del usuario")
    private String password;
    
    @NotNull(message = "Debe de indicar el rol del usuario")
    private Rol rol;


    
    public UserInsertDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public void setFirstLastname(String firstLastname) {
        this.firstLastname = firstLastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public UserInsertDto(
            @NotEmpty(message = "Debe de indicar el nombre del usuario") @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,70}", message = "Valor no aceptado") String name,
            @NotEmpty(message = "Debe de indicar el apellido del usuario") @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,70}", message = "Valor no aceptado") String firstLastname,
            @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]", message = "Valor no aceptado") String secondLastname,
            @NotEmpty(message = "Debe de indicar el username del usuario") @Email String username,
            @NotEmpty(message = "Debe de indicar la contraseña del usuario") String password,
            @NotEmpty(message = "Debe de indicar el rol del usuario") Rol rol) {
        this.name = name;
        this.firstLastname = firstLastname;
        this.secondLastname = secondLastname;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "UserInsertDto [firstLastname=" + firstLastname + ", name=" + name + ", password=" + password + ", rol="
                + rol + ", secondLastname=" + secondLastname + ", username=" + username + "]";
    }



}
