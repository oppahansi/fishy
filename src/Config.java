

import java.io.File;
import java.io.IOException;

public class Config {
    private int fishingDuration = 26000;
    private int searchDelay = 2000;
    private int buffDuration = 10;
    private int buffCount = 20;
    private boolean shiftLoot = true;
    private boolean useBuff = true;
 /*
    public Config() {
        load();
    }

    public boolean load() {
        File iniFile = new File("fishy.ini");

        if (!iniFile.exists()) {
            try {
                if (iniFile.createNewFile())
                    return save();
                else
                    return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                Ini ini = new Ini(iniFile);
                fishingDuration = ini.get("fishy", "fishingDuration", int.class);
                searchDelay = ini.get("fishy", "searchDelay", int.class);
                buffDuration = ini.get("fishy", "buffDuration", int.class);
                buffCount = ini.get("fishy", "buffCount", int.class);
                shiftLoot = ini.get("fishy", "shiftLoot", boolean.class);
                useBuff = ini.get("fishy", "useBuff", boolean.class);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean save () {
        File iniFile = new File("fishy.ini");

        if (!iniFile.exists()) {
            return load();
        } else {
            try {
                Ini ini = new Ini(iniFile);
                ini.put("fishy", "fishingDuration", int.class);
                ini.get("fishy", "searchDelay", int.class);
                ini.get("fishy", "buffDuration", int.class);
                ini.get("fishy", "buffCount", int.class);
                ini.get("fishy", "shiftLoot", boolean.class);
                ini.get("fishy", "useBuff", boolean.class);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public int getFishingDuration() {
        return fishingDuration;
    }

    public void setFishingDuration(int fishingDuration) {
        this.fishingDuration = fishingDuration;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public void setSearchDelay(int searchDelay) {
        this.searchDelay = searchDelay;
    }

    public int getBuffDuration() {
        return buffDuration;
    }

    public void setBuffDuration(int buffDuration) {
        this.buffDuration = buffDuration;
    }

    public int getBuffCount() {
        return buffCount;
    }

    public void setBuffCount(int buffCount) {
        this.buffCount = buffCount;
    }

    public boolean isShiftLoot() {
        return shiftLoot;
    }

    public void setShiftLoot(boolean shiftLoot) {
        this.shiftLoot = shiftLoot;
    }

    public boolean isUseBuff() {
        return useBuff;
    }

    public void setUseBuff(boolean useBuff) {
        this.useBuff = useBuff;
    }*/
}
