package mate.academy.bookstore.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by email"));
    }
}
