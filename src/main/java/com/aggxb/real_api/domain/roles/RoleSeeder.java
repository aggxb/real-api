package com.aggxb.real_api.domain.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (RoleType role : RoleType.values()) {
            if (!roleRepository.existsByName(role.name())) {
                RoleEntity newRole = RoleEntity.builder()
                        .name(role.name())
                        .build();

                roleRepository.save(newRole);
            }
        }
    }
}
