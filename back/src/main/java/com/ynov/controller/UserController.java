package com.ynov.controller;

import com.ynov.dto.UserDto;
import com.ynov.service.IUserService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Optional;

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
        return Response.ok().build();
    }
    @GET
    @Path("/users/me")
    @Authenticated
    public RestResponse<UserDto> getCurrentUser(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        String email = jwtToken.getClaim(Claims.email);
        if (uid == null || email == null) {
            return RestResponse.status(RestResponse.Status.UNAUTHORIZED);
        }
        Optional<UserDto> userDtoSearched = userService.findUserByUID(uid, email);
        if (userDtoSearched.isPresent()) {
            return RestResponse.ok(userDtoSearched.get());
        }
        return RestResponse.status(RestResponse.Status.NOT_FOUND);
    }
}
