package com.github.yanghr.easyjob.jobexecution.job.impl;

import com.github.yanghr.easyjob.jobexecution.job.IJob;
import com.github.yanghr.easyjob.jobexecution.job.JobContext;

import java.util.concurrent.ExecutorService;

public class AbstractJob implements IJob {
    @SuppressWarnings("unchecked")
    protected static ThreadLocal<String> jobThreadInstanceId = new ThreadLocal() {

    @Override
    protected String initialValue() {
        return "JONINSTANCEID";
       }
};

    @Override
    public void kill(JobContext context) {
        ExecutorService executor = context.getExecutor();
        executor.shutdownNow();
        context.setKilled(true);
    }

    public static String getJobInstanceId() {
        return jobThreadInstanceId.get();
    }

    public static void setJobInstanceId(String jobInstanceId) {
        jobThreadInstanceId.set(jobInstanceId);
    }

    @Override
    public void run(JobContext context) {

    }

    /**
     * rerun的处理逻辑，先调用rollback再调用run.
     * @param context
     */
    @Override
    public void rerun(JobContext context) {
        this.rollback(context);
        this.run(context);
    }

    /**
     * 子类复写逻辑
     * @param context
     */
    @Override
    public void rollback(JobContext context) {
        //
    }

    @Override
    public void forceRun(JobContext context) {
        this.run(context);
    }

}
