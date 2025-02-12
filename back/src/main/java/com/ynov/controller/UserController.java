package com.ynov.controller;

import com.ynov.dto.UserDto;
import com.ynov.service.IUserService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@RequiredArgsConstructor
@Path("/api")
public class UserController {
    private final IUserService userService;
    private final JsonWebToken jwtToken;

    @POST
    @Path("/users")
    @Authenticated
    public void createUser(UserDto userDto, @Context SecurityContext securityContext) {
        String email = jwtToken.getClaim(Claims.email);
        String uid = securityContext.getUserPrincipal().getName();
        userDto.setEmail(email);
        userDto.setUid(uid);
        userService.createUser(userDto);
    }
}
