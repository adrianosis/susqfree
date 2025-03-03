package br.com.susqfree.emergency_care.domain.enums;

public enum PriorityLevel {
    EMERGENCY("R"),
    URGENT("Y"),
    PRIORITY("G"),
    NON_PRIORITY("B");

    private final String prefix;

    PriorityLevel(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
