package com.mindera.school.spaceshiprent.enumerator;

import lombok.Getter;

public enum UserType {
    CUSTOMER(UserRole.CUSTOMER),
    EMPLOYEE(UserRole.EMPLOYEE);

    @Getter
    private UserRole userRole;

    UserType(UserRole userRole) {
        this.userRole = userRole;
    }
}
