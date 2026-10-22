package pl.bochunator.taskforge.job;

public record CreateJobResponse (
        Long jobId,
        JobStatus status
) {
}
