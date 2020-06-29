package com.github.yanghr.easyjob.jobexecution.domainmodel;
/**
 * JobStatus.
 *
 * @version
 * @author
 */
public enum JobStatus {

    SCHEDULED(new JobOperation[]{JobOperation.RUN, JobOperation.FORCE_RUN}),
    WAITING(new JobOperation[]{JobOperation.RUN, JobOperation.FORCE_RUN}),
    QUEUED(null),
    PROCESSING(new JobOperation[]{JobOperation.SUSPEND, JobOperation.KILL}),
    COMPLETED(new JobOperation[]{JobOperation.RERUN, JobOperation.ROLLBACK}),
    FAILED(new JobOperation[]{JobOperation.RESUME, JobOperation.RERUN, JobOperation.ROLLBACK}),
    SUSPENDING(null),
    SUSPENDED(new JobOperation[]{JobOperation.RESUME, JobOperation.RERUN, JobOperation.ROLLBACK}),
    KILLING(null),
    KILLED(new JobOperation[]{JobOperation.RERUN, JobOperation.ROLLBACK}),
    ROLLBACKING(null),
    ROLLBACKED(new JobOperation[]{JobOperation.RERUN}),
    ROLLBACK_FAILED(new JobOperation[]{JobOperation.ROLLBACK}),

    /**
     * 中间状态，此状态代表JobInstance需要被删除.
     */
    TO_BE_DELETE(null);

    private JobOperation[] allowableOperations;

    private JobStatus(JobOperation[] allowableOperations){
        this.allowableOperations = allowableOperations;
    }

    public JobOperation[] getAllowableOperations(){
        return this.allowableOperations;
    }
}
