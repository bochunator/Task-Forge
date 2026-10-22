package pl.bochunator.taskforge.job;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(nullable = false)
    private Integer numbersCount;

    @Column(nullable = false)
    private Integer progress;

    private Long minimumValue;
    private Long maximumValue;
    private Double averageValue;
    private Long sumValue;

    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    protected Job() {
    }

    public Job(Integer numbersCount) {
        this.numbersCount = numbersCount;
        this.status = JobStatus.PENDING;
        this.progress = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Integer getNumbersCount() {
        return numbersCount;
    }

    public void setNumbersCount(Integer numbersCount) {
        this.numbersCount = numbersCount;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Long getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(Long minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Long getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(Long maximumValue) {
        this.maximumValue = maximumValue;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Long getSumValue() {
        return sumValue;
    }

    public void setSumValue(Long sumValue) {
        this.sumValue = sumValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
