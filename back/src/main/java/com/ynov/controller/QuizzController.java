package com.ynov.controller;

import com.ynov.dto.*;
import com.ynov.service.IQuestionService;
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
import java.util.Optional;

@Path("/api/quiz")
@RequiredArgsConstructor
public class QuizzController {
    private final IQuizzService quizzService;
    private final IQuestionService questionService;

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
    public RestResponse<QuizzDto> getQuiz(@PathParam("id") Long id) {
        Optional<QuizzDto> quizzDto = quizzService.getQuizzById(id);
       if (quizzDto.isPresent()){
           return RestResponse.ok(quizzDto.get());
       } else {
           return RestResponse.status(RestResponse.Status.NOT_FOUND);
       }
    }

    @POST
    @Path("/{id}/questions")
    @Authenticated
    public RestResponse<QuestionDto> createQuestion(@PathParam("id") Long id, QuestionDto questionDto) {
        questionService.createQuestion(questionDto, id);
        return RestResponse.status(RestResponse.Status.CREATED);
    }
}
