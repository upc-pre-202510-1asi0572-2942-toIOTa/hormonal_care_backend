package com.backend.hormonalcare.profile.interfaces.acl;

import com.backend.hormonalcare.profile.domain.model.commands.CreateProfileCommand;
import com.backend.hormonalcare.profile.domain.model.queries.GetProfileByEmailQuery;
import com.backend.hormonalcare.profile.domain.model.valueobjects.Email;
import com.backend.hormonalcare.profile.domain.services.ProfileCommandService;
import com.backend.hormonalcare.profile.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProfileContextFacade {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfileContextFacade(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;

    }
/*
String firstName,
String lastName,
String gender,
Integer age,
String phoneNumber,
String email,
String Image,
Date birthday
 */
    public Long createProfile(String firstName, String lastName, String gender, Integer age, String phoneNumber, String email, String Image, Date birthday, Long userId){
        var createProfileCommand = new CreateProfileCommand(firstName, lastName, gender, age, phoneNumber, email, Image, birthday, userId);
        var profile = profileCommandService.handle(createProfileCommand);
        if (profile.isEmpty()) return 0L;
        return profile.get().getId();
    }


    public Long fetchProfileIdByEmail(String email) {
        var getProfileByEmailQuery = new GetProfileByEmailQuery(new Email(email));
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        if (profile.isEmpty()) return 0L;
        return profile.get().getId();
    }


}
