package pl.bochunator.taskforge.job;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional
    public CreateJobResponse createJob(CreateJobRequest request) {
        var job = new Job(request.numberCounts());
        var savedJob = jobRepository.save(job);
        return new CreateJobResponse(
                savedJob.getId(),
                savedJob.getStatus()
        );
    }

    @Transactional()
    public JobResponse getJob(Long id) {
        var job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Job with id " + id + " does not exist"
                        )
                );
        return JobResponse.from(job);
    }

    @Transactional()
    public List<JobResponse> getJobs() {
        return jobRepository.findAll()
                .stream()
                .map(JobResponse::from)
                .toList();
    }
}
