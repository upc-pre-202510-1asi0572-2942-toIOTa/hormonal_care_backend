package com.backend.hormonalcare.profile.interfaces.acl;

public class ProfileDetails {
    private final Long profileId;
    private final String fullName;
    private final String image;
    private final String gender; // Added gender attribute
    private final String phoneNumber; // Added phoneNumber attribute
    private final String birthday; // Added birthday attribute

    public ProfileDetails(Long profileId, String fullName, String image, String gender, String phoneNumber, String birthday) {
        this.profileId = profileId;
        this.fullName = fullName;
        this.image = image;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }
}
