package org.example.user.model.dating;

public enum ChildrenStatus {

    NO_CHILDREN("Нямам дете"),
    HAS_CHILDREN("Имам дете");

    private final String displayName;

    ChildrenStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
