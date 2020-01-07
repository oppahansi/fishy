import java.time.LocalTime;

public class FishyConfiguration {
    private static FishyConfiguration INSTANCE;
    private int bobberColor;
    private int bobberColorSens;
    private int bitingColor;
    private int bitingColorSens;
    private int fishingCycle;
    private int searchDelay;
    private int buffCount;
    private int buffDuration;
    private boolean useBuff;
    private boolean useShiftLoot;
    private boolean endTimeSelected;
    private LocalTime endTime;
    private boolean castsSelected;
    private int casts;
    private boolean hooksSelected;
    private int hooks;
    private boolean hoursSelected;
    private boolean buffsUsedUpSelected;
    private boolean neverSelected;
    private boolean logout;
    private boolean shutDownPc;
    private FishyConfiguration() {
    }

    public static FishyConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FishyConfiguration();
        }

        return INSTANCE;
    }

    public int getBobberColor() {
        return bobberColor;
    }

    public void setBobberColor(int bobberColor) {
        this.bobberColor = bobberColor;
    }

    public int getBobberColorSens() {
        return bobberColorSens;
    }

    public void setBobberColorSens(int bobberColorSens) {
        this.bobberColorSens = bobberColorSens;
    }

    public int getBitingColor() {
        return bitingColor;
    }

    public void setBitingColor(int bitingColor) {
        this.bitingColor = bitingColor;
    }

    public int getBitingColorSens() {
        return bitingColorSens;
    }

    public void setBitingColorSens(int bitingColorSens) {
        this.bitingColorSens = bitingColorSens;
    }

    public int getFishingCycle() {
        return fishingCycle;
    }

    public void setFishingCycle(int fishingCycle) {
        this.fishingCycle = fishingCycle;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public void setSearchDelay(int searchDelay) {
        this.searchDelay = searchDelay;
    }

    public int getBuffCount() {
        return buffCount;
    }

    public void setBuffCount(int buffCount) {
        this.buffCount = buffCount;
    }

    public int getBuffDuration() {
        return buffDuration;
    }

    public void setBuffDuration(int buffDuration) {
        this.buffDuration = buffDuration;
    }

    public boolean isUseBuff() {
        return useBuff;
    }

    public void setUseBuff(boolean useBuff) {
        this.useBuff = useBuff;
    }

    public boolean isUseShiftLoot() {
        return useShiftLoot;
    }

    public void setUseShiftLoot(boolean useShiftLoot) {
        this.useShiftLoot = useShiftLoot;
    }

    public boolean isEndTimeSelected() {
        return endTimeSelected;
    }

    public void setEndTimeSelected(boolean endTimeSelected) {
        this.endTimeSelected = endTimeSelected;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isCastsSelected() {
        return castsSelected;
    }

    public void setCastsSelected(boolean castsSelected) {
        this.castsSelected = castsSelected;
    }

    public int getCasts() {
        return casts;
    }

    public void setCasts(int casts) {
        this.casts = casts;
    }

    public boolean isHooksSelected() {
        return hooksSelected;
    }

    public void setHooksSelected(boolean hooksSelected) {
        this.hooksSelected = hooksSelected;
    }

    public int getHooks() {
        return hooks;
    }

    public void setHooks(int hooks) {
        this.hooks = hooks;
    }

    public boolean isHoursSelected() {
        return hoursSelected;
    }

    public void setHoursSelected(boolean hoursSelected) {
        this.hoursSelected = hoursSelected;
    }

    public boolean isBuffsUsedUpSelected() {
        return buffsUsedUpSelected;
    }

    public void setBuffsUsedUpSelected(boolean buffsUsedUpSelected) {
        this.buffsUsedUpSelected = buffsUsedUpSelected;
    }

    public boolean isNeverSelected() {
        return neverSelected;
    }

    public void setNeverSelected(boolean neverSelected) {
        this.neverSelected = neverSelected;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public boolean isShutDownPc() {
        return shutDownPc;
    }

    public void setShutDownPc(boolean shutDownPc) {
        this.shutDownPc = shutDownPc;
    }
}
