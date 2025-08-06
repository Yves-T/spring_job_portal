package be.yt.jobportal.controller;

import be.yt.jobportal.entity.UsersType;
import be.yt.jobportal.service.UsersService;
import be.yt.jobportal.service.UsersTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsersControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private UsersTypeService usersTypeService;

    @Test
    public void testRegisterPageLoads() throws Exception {
        // Create mock user types
        UsersType recruiterType = new UsersType();
        recruiterType.setUserTypeId(1);
        recruiterType.setUserTypeName("Recruiter");

        UsersType jobSeekerType = new UsersType();
        jobSeekerType.setUserTypeId(2);
        jobSeekerType.setUserTypeName("Job Seeker");

        // Mock the service response
        when(usersTypeService.getAll()).thenReturn(Arrays.asList(recruiterType, jobSeekerType));

        // Test the register page loads correctly
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("getAllTypes"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testLoginPageLoads() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}