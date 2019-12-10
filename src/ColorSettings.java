public class ColorSettings {
    private int bobberColor;
    private int bitingColor;
    private int bobberSensitivity;
    private int bitingSensitivity;

    public ColorSettings(int bobberColor, int bobberSensitivity, int bitingColor, int bitingSensitivity) {
        this.bobberColor = bobberColor;
        this.bobberSensitivity = bobberSensitivity;
        this.bitingColor = bitingColor;
        this.bitingSensitivity = bitingSensitivity;
    }

    public int getBobberColor() {
        return bobberColor;
    }

    public void setBobberColor(int bobberColor) {
        this.bobberColor = bobberColor;
    }

    public int getBitingColor() {
        return bitingColor;
    }

    public void setBitingColor(int bitingColor) {
        this.bitingColor = bitingColor;
    }

    public int getBobberSensitivity() {
        return bobberSensitivity;
    }

    public void setBobberSensitivity(int bobberSensitivity) {
        this.bobberSensitivity = bobberSensitivity;
    }

    public int getBitingSensitivity() {
        return bitingSensitivity;
    }

    public void setBitingSensitivity(int bitingSensitivity) {
        this.bitingSensitivity = bitingSensitivity;
    }
}
