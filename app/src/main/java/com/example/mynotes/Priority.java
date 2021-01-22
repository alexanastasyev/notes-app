package com.example.mynotes;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    public static Priority getPriorityByIndex(int index) {
        switch (index) {
            case 1:
                return Priority.HIGH;
            case 2:
                return Priority.MEDIUM;
            case 3:
                return Priority.LOW;
            default:
                return Priority.MEDIUM;
        }
    }
}
