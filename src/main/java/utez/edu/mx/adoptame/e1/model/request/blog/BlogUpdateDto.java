package utez.edu.mx.adoptame.e1.model.request.blog;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class BlogUpdateDto {

    @NotNull(message = "Debe inidcar esta propiedad")
    @Positive(message = "Debe ser positivo")
    private Long id;

    @NotEmpty(message = "Debe de indicar el titulo del blog")
    @Pattern(regexp = "[a-zA-Z ]{3,70}", message = "Valor no aceptado")
    private String title;

    @NotNull(message = "De de indicar si el blog es principal")
    private Boolean isPrincipal;

    @NotEmpty(message = "Debe de inidcar el contenido del blog")
    private String content;

  
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(Boolean principal) {
        this.isPrincipal = principal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BlogUpdateDto [content=" + content + ", id=" + id + ", image=" + image + ", isPrincipal=" + isPrincipal
                + ", title=" + title + "]";
    }

    
}
