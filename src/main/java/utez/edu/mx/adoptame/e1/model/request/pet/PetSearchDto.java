package utez.edu.mx.adoptame.e1.model.request.pet;

public class PetSearchDto {
    private Long colorId;

    private Long sizeId;

    private Long personalityId;

    private String typePet;

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getPersonalityId() {
        return personalityId;
    }

    public void setPersonalityId(Long personalityId) {
        this.personalityId = personalityId;
    }

    public String getTypePet() {
        return typePet;
    }

    public void setTypePet(String typePet) {
        this.typePet = typePet;
    }
}
