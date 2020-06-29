package com.github.yanghr.easyjob.jobexecution.job;

public interface IJob {
    void run(JobContext context);

    void rerun(JobContext context);

    void rollback(JobContext context);

    void kill(JobContext context);

    void forceRun(JobContext context);
}
