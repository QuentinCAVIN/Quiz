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
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Path("/api/users")
public class UserController {
    private final IUserService userService;
    private final JsonWebToken jwtToken;

    @POST
    @Authenticated
    public Response createUser(UserDto userDto, @Context SecurityContext securityContext) {
        String email = jwtToken.getClaim(Claims.email);
        String uid = securityContext.getUserPrincipal().getName();
        userDto.setEmail(email);
        userDto.setUid(uid);
        userService.createUser(userDto);
        URI location = UriBuilder.fromPath("/users/me").build();
        return Response.created(location).build();
    }
    @GET
    @Path("/me")
    @Authenticated
    public RestResponse<UserDto> getCurrentUser(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        String email = jwtToken.getClaim(Claims.email);
        if (email == null) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
        Optional<UserDto> userDtoSearched = userService.findUserByUID(uid, email);
        if (userDtoSearched.isPresent()) {
            return RestResponse.ok(userDtoSearched.get());
        }
        return RestResponse.status(RestResponse.Status.NOT_FOUND);
    }
}
