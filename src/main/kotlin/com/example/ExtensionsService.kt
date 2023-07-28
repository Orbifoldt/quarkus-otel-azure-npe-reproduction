package com.example

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/extensions")
@RegisterRestClient
interface ExtensionsService {
    @GET
    fun getById(@QueryParam("id") id: String): Set<Extension>
}

data class Extension(
    val id: String,
    val name: String,
    val shortName: String,
    val keywords: List<String>,
)
