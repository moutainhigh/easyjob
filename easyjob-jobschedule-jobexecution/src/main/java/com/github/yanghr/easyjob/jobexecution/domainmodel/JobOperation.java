package com.github.yanghr.easyjob.jobexecution.domainmodel;

public enum JobOperation {

    RUN,
    FORCE_RUN,
    RERUN,
    RESUME,
    ROLLBACK,
    SUSPEND,
    KILL,
    FORCE_COMPLETED,
    FORCE_FAILED;
}
