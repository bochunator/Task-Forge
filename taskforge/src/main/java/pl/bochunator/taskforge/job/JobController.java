package pl.bochunator.taskforge.job;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<CreateJobResponse> createJob(
            @Valid @RequestBody CreateJobRequest request
    ) {
        var response = jobService.createJob(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .location(URI.create("/api/jobs/" + response.jobId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public JobResponse getJob(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @GetMapping
    public List<JobResponse> getJobs() {
        return jobService.getJobs();
    }
}
