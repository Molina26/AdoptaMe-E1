package utez.edu.mx.adoptame.e1.model.request.adoption;

import utez.edu.mx.adoptame.e1.annotation.ValueOfEnumAccepted;
import utez.edu.mx.adoptame.e1.enums.StateAdoptionApplication;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdoptionUpdateDto {

    @NotNull(message = "Debe indicar un valor para este campo")
    private Long id;

    @ValueOfEnumAccepted(enumClass = StateAdoptionApplication.class, message = "Este valor no es aceptado para el estado de la adopción")
    @NotEmpty(message = "Debe de indicar un valor")
    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
