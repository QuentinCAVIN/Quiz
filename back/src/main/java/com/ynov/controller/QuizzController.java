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
//TODO Remplacer tout les id par IdQUestion ou IdResponse, ça sera plus clair
@Path("/api/quiz")
@RequiredArgsConstructor
public class QuizzController {
    private final IQuizzService quizzService;
    private final IQuestionService questionService;

    @GET
    @Authenticated
    public RestResponse<QuizzResponse> getAllQuizzes(@Context SecurityContext securityContext) {
        String uid = securityContext.getUserPrincipal().getName();
        List<QuizzDto> quizzes = quizzService.getQuizzesByUser(uid);
        return RestResponse.ok(new QuizzResponse(quizzes));
    }
    @POST
    @Authenticated
    public Response createQuiz(QuizzDto quizzDto, @Context SecurityContext securityContext) {
        String UID = securityContext.getUserPrincipal().getName();
        Long id = quizzService.createQuizz(quizzDto, UID);
        URI location = UriBuilder.fromPath("/api/quiz/{id}").build(id);
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

    //TODO Issue 9 pas réalisé, la question doit etre valide au moment de la création c'est a dire déja contenir des réponses
    @POST
    @Path("/{id}/questions")
    @Authenticated
    public Response createQuestion(@PathParam("id") Long quizId, QuestionDto questionDto) {
        Long questionId = questionService.createQuestion(questionDto, quizId);
        URI location = UriBuilder.fromPath("/api/quiz/questions/{questionId}").build(questionId);
        return Response.created(location).build();
    }
    @PUT
    @Path("/{quizzId}/questions/{questionId}")
    @Authenticated
    public RestResponse<?> updateQuestion(@PathParam("quizzId") Long quizzId,
                                              @PathParam("questionId") Long questionId, QuestionDto questionDto) {
        boolean updated = questionService.updateQuestion(quizzId, questionId, questionDto);
        if (!updated) {
            return RestResponse.status(RestResponse.Status.NOT_FOUND);
        }
        return RestResponse.noContent();
    }
}