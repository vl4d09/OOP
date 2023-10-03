public enum StudyField {
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    SOFTWARE_ENGINEERING("Software Engineering"),
    FOOD_TECHNOLOGY("Food Technology"),
    URBANISM_ARCHITECTURE("Urbanism and Architecture"),
    VETERINARY_MEDICINE("Veterinary Medicine");

    private String displayName;

    private StudyField(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
