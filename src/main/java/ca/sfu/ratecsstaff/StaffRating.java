package ca.sfu.ratecsstaff;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_ratings")
public class StaffRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Must be a valid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Role type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @NotNull(message = "Clarity score is required")
    @Min(value = 1, message = "Clarity must be at least 1")
    @Max(value = 10, message = "Clarity must be at most 10")
    @Column(nullable = false)
    private Integer clarity;

    @NotNull(message = "Niceness score is required")
    @Min(value = 1, message = "Niceness must be at least 1")
    @Max(value = 10, message = "Niceness must be at most 10")
    @Column(nullable = false)
    private Integer niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be at least 1")
    @Max(value = 10, message = "Knowledgeable score must be at most 10")
    @Column(nullable = false)
    private Integer knowledgeableScore;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    @Column(length = 500)
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Polymorphism helper method
    public StaffMemberProfile getProfile() {
        if (this.roleType != null) {
            switch (this.roleType) {
                case PROF:
                    return new ProfessorProfile();
                case TA:
                    return new TaProfile();
                default:
                    return new ProfessorProfile();
            }
        }
        return new ProfessorProfile();
    }

    // Constructors
    public StaffRating() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public RoleType getRoleType() { return roleType; }
    public void setRoleType(RoleType roleType) { this.roleType = roleType; }

    public Integer getClarity() { return clarity; }
    public void setClarity(Integer clarity) { this.clarity = clarity; }

    public Integer getNiceness() { return niceness; }
    public void setNiceness(Integer niceness) { this.niceness = niceness; }

    public Integer getKnowledgeableScore() { return knowledgeableScore; }
    public void setKnowledgeableScore(Integer knowledgeableScore) { this.knowledgeableScore = knowledgeableScore; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
