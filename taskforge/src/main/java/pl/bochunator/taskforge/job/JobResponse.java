package pl.bochunator.taskforge.job;

import java.time.LocalDateTime;

public record JobResponse(
        Long id,
        JobStatus status,
        Integer numbersCount,
        Integer progress,
        Long minimumValue,
        Long maximumValue,
        Double averageValue,
        Long sumValue,
        String errorMessage,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getStatus(),
                job.getNumbersCount(),
                job.getProgress(),
                job.getMinimumValue(),
                job.getMaximumValue(),
                job.getAverageValue(),
                job.getSumValue(),
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getStartedAt(),
                job.getCompletedAt()
        );
    }
}
