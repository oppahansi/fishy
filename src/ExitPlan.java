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

    public ExitPlan(boolean isEndTimeSelected, LocalTime endTime, boolean isHooksSelected, int hooks, boolean isCastsSelected, int casts, boolean isHoursSelected, int hours, boolean isNeverSelected, boolean isBuffsUsedUpSelected) {
        this.isEndTimeSelected = isEndTimeSelected;
        this.endTime = endTime;
        this.isHooksSelected = isHooksSelected;
        this.hooks = hooks;
        this.isCastsSelected = isCastsSelected;
        this.casts = casts;
        this.isHoursSelected = isHoursSelected;
        this.isNeverSelected = isNeverSelected;
        this.isBuffsUsedUpSelected = isBuffsUsedUpSelected;

        if (isHoursSelected)
            this.endTime = LocalDateTime.now().toLocalTime().plusHours(hours);
    }

    public boolean shouldExit(int currentHooks, int currentCasts, int buffCount) {
        if (isNeverSelected) return false;
        if (isBuffsUsedUpSelected && buffCount == 0) return true;
        if (isEndTimeSelected || isHoursSelected) return endTime.compareTo(LocalDateTime.now().toLocalTime()) < 0;
        if (isHooksSelected) return hooks <= currentHooks;
        if (isCastsSelected) return casts <= currentCasts;

        return true;
    }
}
