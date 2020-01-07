import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExitPlan {
    private boolean isEndTimeSelected;
    private LocalTime endTime;
    private boolean isHooksSelected;
    private int hooks;
    private boolean isCastsSelected;
    private int casts;
    private boolean isHoursSelected;
    private boolean isNeverSelected;
    private boolean isBuffsUsedUpSelected;
    private boolean isLogoutSelected;
    private boolean isShutDownPcSelected;

    public ExitPlan(boolean isEndTimeSelected, LocalTime endTime, boolean isHooksSelected, int hooks,
                    boolean isCastsSelected, int casts, boolean isHoursSelected, boolean isNeverSelected,
                    boolean isBuffsUsedUpSelected, boolean isLogoutSelected, boolean isShutDownPcSelected) {
        this.isEndTimeSelected = isEndTimeSelected;
        this.endTime = endTime;
        this.isHooksSelected = isHooksSelected;
        this.hooks = hooks;
        this.isCastsSelected = isCastsSelected;
        this.casts = casts;
        this.isHoursSelected = isHoursSelected;
        this.isNeverSelected = isNeverSelected;
        this.isBuffsUsedUpSelected = isBuffsUsedUpSelected;
        this.isLogoutSelected = isLogoutSelected;
        this.isShutDownPcSelected = isShutDownPcSelected;
    }

    public boolean shouldExit(int currentHooks, int currentCasts, int buffCount) {
        if (isNeverSelected) return false;
        if (isBuffsUsedUpSelected && buffCount == 0) return true;
        if (isEndTimeSelected || isHoursSelected) return endTime.compareTo(LocalDateTime.now().toLocalTime()) < 0;
        if (isHooksSelected) return hooks <= currentHooks;
        if (isCastsSelected) return casts <= currentCasts;

        return true;
    }

    public boolean isLogoutSelected() {
        return isLogoutSelected;
    }

    public boolean isShutDownPcSelected() {
        return isShutDownPcSelected;
    }
}
