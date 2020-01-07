public class FishingPlan {
    private int fishingCycle;
    private int searchDelay;
    private boolean shiftLoot;
    private boolean useBuff;
    private int buffDuration;
    private int buffCount;

    public FishingPlan(int fishingCycle, int searchDelay, boolean shiftLoot, boolean useBuff, int buffDuration, int buffCount) {
        this.fishingCycle = fishingCycle;
        this.searchDelay = searchDelay;
        this.shiftLoot = shiftLoot;
        this.useBuff = useBuff;
        this.buffDuration = buffDuration * 60 * 1000;
        this.buffCount = buffCount;
    }

    public boolean canBuff() {
        return useBuff && buffCount > 0;
    }

    public void reduceBuffCount(int amount) {
        buffCount -= amount;
        if (buffCount < 0) buffCount = 0;
    }

    public int getFishingCycle() {
        return fishingCycle;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public boolean isShiftLoot() {
        return shiftLoot;
    }

    public boolean isUseBuff() {
        return useBuff;
    }

    public int getBuffDuration() {
        return buffDuration;
    }

    public int getBuffCount() {
        return buffCount;
    }
}
