package com.ynov.controller;

import com.ynov.dto.QuizzDto;
import com.ynov.dto.QuizzResponse;
import com.ynov.service.IQuizzService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api")
@RequiredArgsConstructor
public class QuizzController {
    private final IQuizzService quizzService;
    @GET
    @Path("/quiz")
    @Authenticated
    public RestResponse<QuizzResponse> quiz(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        List<QuizzDto> quizzes = quizzService.getQuizzesByUser(uid);
        return RestResponse.ok(new QuizzResponse(quizzes));
    }
}
