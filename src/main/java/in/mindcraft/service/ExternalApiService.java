package in.mindcraft.service;

import in.mindcraft.dto.ExternalUserDto;

public interface ExternalApiService {

    ExternalUserDto getUserSimple();

    ExternalUserDto getUserWithHeaders();

    ExternalUserDto getUserWithJwt(String authorizationHeader);
}