import { useCallback, useEffect, useState } from "react";
import { createJob, getJobs } from "./api/jobsApi";
import type { Job } from "./types/job";
import "./App.css";

function App() {
  const [jobs, setJobs] = useState<Job[]>([]);
  const [numberCounts, setNumberCounts] = useState(1_000_000);
  const [loading, setLoading] = useState(false);
  const [creating, setCreating] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadJobs = useCallback(async () => {
    try {
      setLoading(true);
      const loadedJobs = await getJobs();

      setJobs(
        [...loadedJobs].sort((a, b) => b.id - a.id),
      );

      setError(null);
    } catch (caughtError) {
      setError(
        caughtError instanceof Error
          ? caughtError.message
          : "Wystąpił nieznany błąd.",
      );
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadJobs();

    const intervalId = window.setInterval(() => {
      void loadJobs();
    }, 1000);

    return () => window.clearInterval(intervalId);
  }, [loadJobs]);

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>,
  ) {
    event.preventDefault();

    try {
      setCreating(true);
      setError(null);

      await createJob({ numberCounts });
      await loadJobs();
    } catch (caughtError) {
      setError(
        caughtError instanceof Error
          ? caughtError.message
          : "Nie udało się utworzyć zadania.",
      );
    } finally {
      setCreating(false);
    }
  }

  return (
    <main className="page">
      <section className="header">
        <div>
          <p className="eyebrow">Distributed job processing</p>
          <h1>TaskForge</h1>
          <p>
            Tworzenie i obserwowanie długotrwałych zadań
            wykonywanych przez osobny serwis Worker.
          </p>
        </div>
      </section>

      <section className="card">
        <h2>Nowe zadanie</h2>

        <form onSubmit={handleSubmit} className="create-form">
          <label>
            Liczba elementów
            <input
              type="number"
              min={1000}
              max={10_000_000}
              value={numberCounts}
              onChange={(event) =>
                setNumberCounts(Number(event.target.value))
              }
              required
            />
          </label>

          <button type="submit" disabled={creating}>
            {creating ? "Tworzenie..." : "Uruchom zadanie"}
          </button>
        </form>
      </section>

      {error && <div className="error">{error}</div>}

      <section className="card">
        <div className="jobs-header">
          <h2>Zadania</h2>

          <button
            type="button"
            className="secondary-button"
            onClick={() => void loadJobs()}
            disabled={loading}
          >
            {loading ? "Odświeżanie..." : "Odśwież"}
          </button>
        </div>

        {jobs.length === 0 ? (
          <p>Brak utworzonych zadań.</p>
        ) : (
          <div className="jobs">
            {jobs.map((job) => (
              <article key={job.id} className="job">
                <div className="job-title">
                  <strong>Zadanie #{job.id}</strong>
                  <span className={`status status-${job.status}`}>
                    {job.status}
                  </span>
                </div>

                <div className="progress-row">
                  <progress
                    value={job.progress}
                    max={100}
                  />
                  <span>{job.progress}%</span>
                </div>

                <p>
                  Liczba elementów:{" "}
                  {job.numbersCount.toLocaleString("pl-PL")}
                </p>

                {job.status === "COMPLETED" && (
                  <div className="result">
                    <span>Minimum: {job.minimumValue}</span>
                    <span>Maksimum: {job.maximumValue}</span>
                    <span>
                      Średnia:{" "}
                      {job.averageValue?.toFixed(2)}
                    </span>
                    <span>
                      Suma:{" "}
                      {job.sumValue?.toLocaleString("pl-PL")}
                    </span>
                  </div>
                )}

                {job.status === "FAILED" && (
                  <p className="failure">
                    {job.errorMessage ?? "Nieznany błąd"}
                  </p>
                )}
              </article>
            ))}
          </div>
        )}
      </section>
    </main>
  );
}

export default App;