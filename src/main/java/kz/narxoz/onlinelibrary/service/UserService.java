package kz.narxoz.onlinelibrary.service;

import kz.narxoz.onlinelibrary.dto.RegisterRequest;
import kz.narxoz.onlinelibrary.entity.AppUser;
import kz.narxoz.onlinelibrary.entity.Role;
import kz.narxoz.onlinelibrary.exception.BadRequestException;
import kz.narxoz.onlinelibrary.exception.ResourceNotFoundException;
import kz.narxoz.onlinelibrary.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> getAll() { return repository.findAll(); }

    public AppUser getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public AppUser register(RegisterRequest request) {
        String email = request.getEmail().toLowerCase();
        if (repository.existsByEmail(email)) throw new BadRequestException("Email already registered");
        AppUser user = AppUser.builder()
                .fullName(request.getFullName())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.READER)
                .enabled(true)
                .build();
        return repository.save(user);
    }

    public AppUser create(AppUser user) {
        String email = user.getEmail().toLowerCase();
        if (repository.existsByEmail(email)) throw new BadRequestException("Email already registered");
        user.setId(null);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        if (user.getRole() == null) user.setRole(Role.READER);
        return repository.save(user);
    }

    public AppUser update(Long id, AppUser item) {
        AppUser existing = getById(id);
        existing.setFullName(item.getFullName());
        existing.setEmail(item.getEmail().toLowerCase());
        if (item.getRole() != null) existing.setRole(item.getRole());
        existing.setEnabled(item.isEnabled());
        return repository.save(existing);
    }

    public void delete(Long id) { repository.delete(getById(id)); }

    public Optional<AppUser> findByEmail(String email) {
        return repository.findByEmail(email.toLowerCase());
    }

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            throw new BadRequestException("Authentication required");
        }
        return repository.findByEmail(authentication.getName().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));
    }

    public AppUser getCurrentUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return null;
        }
        return repository.findByEmail(authentication.getName().toLowerCase()).orElse(null);
    }
}
