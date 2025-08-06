package be.yt.jobportal.service;

import be.yt.jobportal.entity.JobSeekerProfile;
import be.yt.jobportal.entity.RecruiterProfile;
import be.yt.jobportal.entity.Users;
import be.yt.jobportal.repository.JobSeekerProfileRepository;
import be.yt.jobportal.repository.RecruiterProfileRepository;
import be.yt.jobportal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(
            UsersRepository usersRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                return recruiterProfileRepository
                        .findById(userId)
                        .orElse((new RecruiterProfile()));
            } else {
                return jobSeekerProfileRepository
                        .findById(userId)
                        .orElse(new JobSeekerProfile());
            }
        }
        return null;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new java.util.Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return usersRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        }
        return null;
    }

    public Users findByEmail(String currentUserName) {
        return usersRepository
                .findByEmail(currentUserName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
