package utez.edu.mx.adoptame.e1.model.request.blog;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class BlogInsertDto {

    @NotEmpty(message = "Debe de indicar el titulo del blog")
    @Pattern(regexp = "[a-zA-Z ] {3,70}", message = "Valor no aceptado")
    private String title;

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
}
