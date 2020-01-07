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

    public int getBitingColor() {
        return bitingColor;
    }

    public int getBobberSensitivity() {
        return bobberSensitivity;
    }

    public int getBitingSensitivity() {
        return bitingSensitivity;
    }
}
