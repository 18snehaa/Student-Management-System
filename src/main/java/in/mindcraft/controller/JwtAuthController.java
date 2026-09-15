package in.mindcraft.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.mindcraft.dto.LoginRequestDto;
import in.mindcraft.service.JwtService;

@RestController
@RequestMapping("/auth")
public class JwtAuthController {

    private final JwtService jwtService;

    public JwtAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto request) {

        // Sample username and password
        if ("admin".equals(request.getUsername())
                && "admin123".equals(request.getPassword())) {

            String token =
                    jwtService.generateToken(
                            request.getUsername()
                    );

            return ResponseEntity.ok(
                    Map.of("token", token)
            );
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message",
                        "Invalid username or password"
                ));
    }
}