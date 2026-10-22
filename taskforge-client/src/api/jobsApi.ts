import type {
    CreateJobRequest,
    CreateJobResponse,
    Job,
  } from "../types/job";
  
  const API_URL = "http://localhost:8080/api/jobs";
  
  export async function getJobs(): Promise<Job[]> {
    const response = await fetch(API_URL);
  
    if (!response.ok) {
      throw new Error(
        `Nie udało się pobrać zadań. HTTP ${response.status}`,
      );
    }
  
    return response.json() as Promise<Job[]>;
  }
  
  export async function createJob(
    request: CreateJobRequest,
  ): Promise<CreateJobResponse> {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
  
    if (!response.ok) {
      const body = await response.text();
  
      throw new Error(
        body || `Nie udało się utworzyć zadania. HTTP ${response.status}`,
      );
    }
  
    return response.json() as Promise<CreateJobResponse>;
  }