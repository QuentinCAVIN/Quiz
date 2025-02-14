package com.ynov.controller;

import com.ynov.dto.QuizzDto;
import com.ynov.dto.QuizzResponse;
import com.ynov.dto.QuizzResponses;
import com.ynov.service.IQuizzService;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Path("/api/quiz")
@RequiredArgsConstructor
public class QuizzController {
    private final IQuizzService quizzService;

    @GET
    @Authenticated
    public RestResponse<QuizzResponse> quiz(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        List<QuizzDto> quizzes = quizzService.getQuizzesByUser(uid);
        return RestResponse.ok(new QuizzResponse(quizzes));
    }
    @POST
    @Authenticated
    public Response createQuiz(QuizzDto quizzDto, @Context SecurityContext securityContext) {
        String UID = securityContext.getUserPrincipal().getName();
        Long id = quizzService.createQuizz(quizzDto, UID);
        URI location = UriBuilder.fromPath("/quiz/{id}").build(id);
        return Response.created(location).build();
    }
    @GET
    @Path("/{id}")
    @Authenticated
    public RestResponse<QuizzResponses> createQuiz(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        List<QuizzDto> quizzes = quizzService.getQuizzesByUser(uid); // TODO QuizzDto à la place de QuestionDto car non implementé
        QuizzDto quizzDto = quizzService.getQuizzById(id);
        return RestResponse.ok(new QuizzResponses(quizzDto, quizzes));
    }
}
