package com.ynov.controller;

import com.ynov.dto.Ping;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class CheckingController {
    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public Ping ping() {
        return new Ping("OK");
    }
}
