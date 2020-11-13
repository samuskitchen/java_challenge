package com.wolox.challenge.models.enums;

public enum AccessType {

    READ(1L),
    WRITE(2L),
    READ_WRITE(3L),
    UNKNOWN(0L);

    private Long idAccess;

    AccessType(Long idAccess) {
        this.idAccess = idAccess;
    }

    public static AccessType getByIdAccess(Long idAccess) {
        for (AccessType accessType : values()) {
            if (accessType.idAccess.equals(idAccess)) {
                return accessType;
            }
        }
        return UNKNOWN;
    }
}
