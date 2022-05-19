package run.itlife.enums;

public enum Sex {

    MALE ("Мужской"),
    FEMALE ("Женский");

    private String title;

    Sex(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
