package com.example

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class ExampleResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/hello")
            .then()
            .statusCode(200)
            .body(containsString("REST Client Classic"))
    }

}
