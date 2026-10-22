package pl.bochunatr.taskforge_worker.worker;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.bochunatr.taskforge_worker.job.Job;
import pl.bochunatr.taskforge_worker.job.JobRepository;
import pl.bochunatr.taskforge_worker.job.JobStatus;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class JobWorker {

    private final JobRepository jobRepository;
    private final Random random = new Random();

    public JobWorker(JobRepository repository) {
        this.jobRepository = repository;
    }



    @Scheduled(fixedDelay = 5000)
    public void processJobs() {
        var optionalJob =
                jobRepository.findFirstByStatusOrderByCreatedAtAsc(
                        JobStatus.PENDING
                );

        if (optionalJob.isEmpty()) {
            System.out.println("There is no job to complete :P");
            return;
        }

        var job = optionalJob.get();

        processJob(job);
    }

    private void processJob(Job job) {
        try {
            markAsProcessing(job);

            performLongRunningCalculation(job);

            markAsCompleted(job);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

            markAsFailed(
                    job,
                    "Job processing was interrupted."
            );
        } catch (Exception ex) {
            markAsFailed(
                    job,
                    ex.getMessage()
            );
        }
    }

    @Transactional
    protected void markAsProcessing(Job job) {
        job.setStatus(JobStatus.PROCESSING);
        job.setProgress(0);
        job.setStartedAt(LocalDateTime.now());
        job.setErrorMessage(null);

        jobRepository.save(job);

        System.out.println(
                "Processing job " + job.getId()
        );
    }

    private void performLongRunningCalculation(Job job)
            throws InterruptedException {

        var numbersCount = job.getNumbersCount();

        long minimum = Long.MAX_VALUE;
        long maximum = Long.MIN_VALUE;
        long sum = 0;

        var processedElements = 0;
        var steps = 100;

        for (var step = 1; step <= steps; step++) {
            var targetProcessedElements =
                    (int) (
                            (numbersCount * (long) step)
                                    / steps
                    );

            while (processedElements
                    < targetProcessedElements) {

                var value =
                        random.nextInt(1_000_000);

                minimum = Math.min(
                        minimum,
                        value
                );

                maximum = Math.max(
                        maximum,
                        value
                );

                sum += value;
                processedElements++;
            }

            updateProgress(job, step);

            Thread.sleep(100);
        }

        job.setMinimumValue(minimum);
        job.setMaximumValue(maximum);
        job.setSumValue(sum);
        job.setAverageValue(
                sum / (double) numbersCount
        );
    }

    @Transactional
    protected void updateProgress(
            Job job,
            int progress) {

        job.setProgress(
                Math.min(progress, 100)
        );

        jobRepository.save(job);
    }

    @Transactional
    protected void markAsCompleted(Job job) {
        job.setStatus(JobStatus.COMPLETED);
        job.setProgress(100);
        job.setCompletedAt(LocalDateTime.now());

        jobRepository.save(job);

        System.out.println(
                "Completed job " + job.getId()
        );
    }

    @Transactional
    protected void markAsFailed(
            Job job,
            String errorMessage) {

        job.setStatus(JobStatus.FAILED);
        job.setCompletedAt(LocalDateTime.now());
        job.setErrorMessage(errorMessage);

        jobRepository.save(job);

        System.err.println(
                "Failed job "
                        + job.getId()
                        + ": "
                        + errorMessage
        );
    }
}
