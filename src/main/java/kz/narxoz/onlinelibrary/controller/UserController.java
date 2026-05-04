package kz.narxoz.onlinelibrary.controller;

import jakarta.validation.Valid;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping public List<AppUser> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public AppUser getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public AppUser create(@Valid @RequestBody AppUser user) { return service.create(user); }
    @PutMapping("/{id}") public AppUser update(@PathVariable Long id, @Valid @RequestBody AppUser user) { return service.update(id, user); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
