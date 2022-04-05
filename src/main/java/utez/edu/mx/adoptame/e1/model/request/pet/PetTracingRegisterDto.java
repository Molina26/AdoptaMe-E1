package utez.edu.mx.adoptame.e1.model.request.pet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import utez.edu.mx.adoptame.e1.annotation.ValueOfEnumAccepted;
import utez.edu.mx.adoptame.e1.enums.TracingRegisterPet;

public class PetTracingRegisterDto {

  @NotNull(message = "Debe de indicar este atributo")
  @Positive(message = "Este valor debe de ser positivo")
  private Long id;

  @ValueOfEnumAccepted(enumClass = TracingRegisterPet.class, message = "Este tipo de mascota no es aceptado")
  private String isAccepted;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIsAccepted() {
    return this.isAccepted;
  }

  public void setIsAccepted(String isAccepted) {
    this.isAccepted = isAccepted;
  }

}
