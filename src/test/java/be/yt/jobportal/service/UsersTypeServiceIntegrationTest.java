package be.yt.jobportal.service;

import be.yt.jobportal.entity.UsersType;
import be.yt.jobportal.repository.UsersTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UsersTypeServiceIntegrationTest {

    @Autowired
    private UsersTypeService usersTypeService;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Test
    public void testGetAllUserTypes() {
        // Create user types
        UsersType recruiterType = new UsersType();
        recruiterType.setUserTypeName("Recruiter");

        UsersType jobSeekerType = new UsersType();
        jobSeekerType.setUserTypeName("Job Seeker");

        // Save user types
        usersTypeRepository.save(recruiterType);
        usersTypeRepository.save(jobSeekerType);

        // Get all user types
        List<UsersType> userTypes = usersTypeService.getAll();

        // Verify user types were retrieved
        assertNotNull(userTypes);
        assertFalse(userTypes.isEmpty());
        assertTrue(userTypes.size() >= 2);
        
        // Verify user type names
        boolean hasRecruiter = false;
        boolean hasJobSeeker = false;
        
        for (UsersType userType : userTypes) {
            if ("Recruiter".equals(userType.getUserTypeName())) {
                hasRecruiter = true;
            } else if ("Job Seeker".equals(userType.getUserTypeName())) {
                hasJobSeeker = true;
            }
        }
        
        assertTrue(hasRecruiter, "Should have a Recruiter user type");
        assertTrue(hasJobSeeker, "Should have a Job Seeker user type");
    }
}