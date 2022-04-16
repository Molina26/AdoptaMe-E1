package utez.edu.mx.adoptame.e1.model.request.user;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import utez.edu.mx.adoptame.e1.entity.Role;


public class UserInsertDto {

    @NotEmpty(message = "Debe de indicar el nombre del usuario")
    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,150}", message = "Valor no aceptado")
    private String name;

    @NotEmpty(message = "Debe de indicar el apellido del usuario")
    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,150}", message = "Valor no aceptado")
    private String firstLastname;

    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ]{3,150}", message = "Valor no aceptado")
    private String secondLastname;

    @NotEmpty(message = "Debe de indicar el username del usuario")
    @Email(message = "Debe ser un correo electrónico con formato correcto")
    private String username;

    @NotEmpty(message = "Debe de indicar la contraseña del usuario")
    private String password;
    
    @NotNull(message = "Debe de indicar el rol del usuario")
    private Role role;

    
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInsertDto [firstLastname=" + firstLastname + ", name=" + name + ", password=" + password + ", role="
                + role + ", secondLastname=" + secondLastname + ", username=" + username + "]";
    }

    


}
