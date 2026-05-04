package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.dto.RegisterRequest;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public String registerPage(@ModelAttribute RegisterRequest request) {
        userService.register(request);
        return "redirect:/login?registered";
    }

    @PostMapping("/api/public/register")
    @ResponseBody
    public ResponseEntity<AppUser> registerApi(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}
