package be.yt.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {
    @Id
    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;

    @Transient
    public String photosImagePath() {
        if (profilePhoto == null) {
            return null;
        }
        return "/photos/recruiter/" + userAccountId + "/" + profilePhoto;
    }

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public RecruiterProfile(Users users) {
        this.userId = users;
    }
}
