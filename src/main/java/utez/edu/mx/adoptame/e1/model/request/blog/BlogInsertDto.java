package utez.edu.mx.adoptame.e1.model.request.blog;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BlogInsertDto {

    @NotEmpty(message = "Debe de indicar el titulo del blog")
    @Pattern(regexp = "[a-zA-Z ñáéíóúÁÉÍÓÚÑ¿?!¡]{3,70}", message = "Valor no aceptado")
    private String title;

    @NotNull(message = "De de indicar si el blog es principal")
    private Boolean isPrincipal;

    @NotEmpty(message = "Debe de inidcar el contenido del blog")
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(Boolean principal) {
        this.isPrincipal = principal;
    }


    @Override
    public String toString() {
        return "BlogInsertDto{" +
                "title='" + title + '\'' +
                ", isPrincipal=" + isPrincipal +
                ", content='" + content + '\'' +
                '}';
    }
}
