package com.softserve.identityservice.converter;

import com.softserve.identityservice.model.AppUser;
import com.softserve.identityservice.model.EmailVerificationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class AppUserDtoToEmailVerificationDtoConverter implements Converter<AppUser, EmailVerificationDto> {
    private final String PATH;

    public AppUserDtoToEmailVerificationDtoConverter(
            @Value("${activation-url}") String path) {
        PATH = path;
    }
    
    @Override
    public EmailVerificationDto convert(AppUser appUser) {
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto();
        emailVerificationDto.setEmail(appUser.getEmail());
        emailVerificationDto.setVerificationPath(PATH + appUser.getVerifyToken());
        return emailVerificationDto;
    }
}
