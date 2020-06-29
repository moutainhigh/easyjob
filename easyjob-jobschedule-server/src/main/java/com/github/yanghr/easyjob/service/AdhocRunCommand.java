package com.github.yanghr.easyjob.service;

import com.github.yanghr.easyjob.jobexecution.domainmodel.JobOperation;

public class AdhocRunCommand extends AbstractCommand {
    @Override
    public JobOperation getOperation() {
        return JobOperation.RUN;
    }
}
