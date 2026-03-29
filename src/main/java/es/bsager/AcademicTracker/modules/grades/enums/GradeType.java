package es.bsager.AcademicTracker.modules.grades.enums;

import java.util.Set;

public enum GradeType {
    MODULE_1, MODULE_2, MODULE_3, MODULE_4;

    public int getIndex() {
        return this.ordinal(); // MODULE_1 = 0, MODULE_2 = 1, etc.
    }

    public static Set<GradeType> getModules() {
        return Set.of(MODULE_1, MODULE_2, MODULE_3, MODULE_4);
    }
}