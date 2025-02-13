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
    public Response createUser(UserDto userDto, @Context SecurityContext securityContext) {
        String email = jwtToken.getClaim(Claims.email);
        String uid = securityContext.getUserPrincipal().getName();
        userDto.setEmail(email);
        userDto.setUid(uid);
        userService.createUser(userDto);
        return Response.status(Response.Status.CREATED).build();
    }
    @GET
    @Path("/users/me")
    @Authenticated
    public RestResponse<UserDto> getCurrentUser(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        String email = jwtToken.getClaim(Claims.email);
        if (uid == null || email == null) {
            return RestResponse.status(RestResponse.Status.UNAUTHORIZED); //TODO question : on ne peut jamais rentrer dans cette boucle a cause du AUthenticated  faut il la laisser ?
            //Réponse Frédéric: effectivement vu qu'on ne peut jamais atteindre ce code il faut l'effacer.
        }
        Optional<UserDto> userDtoSearched = userService.findUserByUID(uid, email);
        if (userDtoSearched.isPresent()) {
            return RestResponse.ok(userDtoSearched.get());
        }
        return RestResponse.status(RestResponse.Status.NOT_FOUND);
        //TODO question : on ne peut fonctionnellement jamais atteindre ce code car un utilisateur connecté existe forcément en BDD
        //Réponse: si la requete users/me et faite avant que l'utilisateur soit créé on peut atteindre ce code, il faut donc le tester
    }
}
