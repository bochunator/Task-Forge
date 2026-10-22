package pl.bochunator.taskforge.job;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequest(

    @NotNull
    @Min(1_000)
    @Max(10_000_000)
    Integer numberCounts

){}
