package com.backend.hormonalcare.profile.interfaces.rest.resources;

import java.util.Date;

public record CreateProfileResource(
        String firstName,
        String lastName,
        String gender,
        Integer age,
        String phoneNumber,
        String email,
        String Image,
        Date birthday,
        Long userId) {
}
