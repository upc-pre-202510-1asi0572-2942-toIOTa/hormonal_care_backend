package com.backend.hormonalcare.profile.interfaces.rest.resources;

import java.util.Date;

public record ProfileResource
        (
        String fullName,
        String gender,
        Integer age,
        String phoneNumber,
        String email,
        String Image,
        Date birthday,
        Long userId)
{
}
