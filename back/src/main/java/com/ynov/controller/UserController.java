package com.ynov.controller;

import com.ynov.dto.UserDto;
import com.ynov.service.IUserService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Path("/api")
public class UserController {
    private final IUserService userService;

    @POST
    @Path("/users")
    @Authenticated
    public void createUser(UserDto userDto, @Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        userService.createUser(userDto, uid);
    }
}
