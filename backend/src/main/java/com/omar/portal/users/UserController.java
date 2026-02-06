package com.omar.portal.users;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserController {
    private final UserRepository users;

    public UserController(UserRepository users) { this.users = users; }

    public record ProfileResponse(Long id, String email, String role, String displayName) {}
    public record UpdateProfileRequest(@NotBlank String displayName) {}

    @GetMapping
    public ProfileResponse me(@AuthenticationPrincipal User user) {
        return new ProfileResponse(user.getId(), user.getEmail(), user.getRole().name(), user.getDisplayName());
    }

    @PatchMapping
    public ProfileResponse patch(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest req) {
        user.setDisplayName(req.displayName());
        users.save(user);
        return me(user);
    }
}
