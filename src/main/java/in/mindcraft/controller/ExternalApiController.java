package in.mindcraft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.mindcraft.dto.ExternalUserDto;
import in.mindcraft.service.ExternalApiService;
import in.mindcraft.service.JwtService;

@RestController
@RequestMapping("/external-api")
public class ExternalApiController {

    private final ExternalApiService externalApiService;
    private final JwtService jwtService;

    public ExternalApiController( ExternalApiService externalApiService,JwtService jwtService) 
    {
        this.externalApiService = externalApiService;
        this.jwtService = jwtService;
    }

    // TASK 1

    @GetMapping("/task1")
    public ResponseEntity<ExternalUserDto> task1() {

        ExternalUserDto response = externalApiService.getUserSimple();
        return ResponseEntity.ok(response);
    }

    // TASK 2

    @GetMapping("/task2")
    public ResponseEntity<ExternalUserDto> task2()
    {
        ExternalUserDto response = externalApiService.getUserWithHeaders();
        return ResponseEntity.ok(response);
    }

    // TASK 3 - 

    @GetMapping("/task3")
    public ResponseEntity<ExternalUserDto> task3(
            @RequestHeader( value = "Authorization", required = false)
            String authorizationHeader) {

        return ResponseEntity.ok(
                externalApiService.getUserWithJwt(
                        authorizationHeader
                )
        );
    }


    // INTERNAL / SIMULATED EXTERNAL API


    @GetMapping("/user")
    public ResponseEntity<?> externalUser(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            )
            String authorizationHeader) {

        // Check Authorization header
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization Bearer token is required");
        }

        // Extract JWT
        String token =
                authorizationHeader.substring(7);

        // Validate JWT
        if (!jwtService.isTokenValid(token)) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired JWT token");
        }

        // Get username from token
        String username =
                jwtService.extractUsername(token);

        // Create response
        ExternalUserDto response =
                new ExternalUserDto();

        response.setId(1);
        response.setName("Admin User");
        response.setUsername(username);
        response.setEmail("admin@example.com");


        return ResponseEntity.ok(response);
    }
}