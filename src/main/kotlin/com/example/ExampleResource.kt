package com.example

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RestClient

@Path("/hello")
class ExampleResource(
    @RestClient val extensionsService: ExtensionsService
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = extensionsService.getById("io.quarkus:quarkus-rest-client")
}
