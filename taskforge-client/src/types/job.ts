export type JobStatus =
  | "PENDING"
  | "PROCESSING"
  | "COMPLETED"
  | "FAILED";

export interface Job {
  id: number;
  status: JobStatus;
  numbersCount: number;
  progress: number;
  minimumValue: number | null;
  maximumValue: number | null;
  averageValue: number | null;
  sumValue: number | null;
  errorMessage: string | null;
  createdAt: string;
  startedAt: string | null;
  completedAt: string | null;
}

export interface CreateJobRequest {
    numberCounts: number;
}

export interface CreateJobResponse {
  jobId: number;
  status: JobStatus;
}