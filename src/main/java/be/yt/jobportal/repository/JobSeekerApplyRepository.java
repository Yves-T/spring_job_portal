package be.yt.jobportal.repository;

import be.yt.jobportal.entity.JobPostActivity;
import be.yt.jobportal.entity.JobSeekerApply;
import be.yt.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {
    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity jobId);
}
