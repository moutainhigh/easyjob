package com.github.yanghr.easyjob.service;

import com.github.yanghr.easyjob.dao.JobInstanceDao;
import com.github.yanghr.easyjob.domainmodel.JobInstance;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobOperation;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobStatus;
import com.github.yanghr.easyjob.jobexecution.vo.TaskMessage;
import com.github.yanghr.easyjob.utils.EasyBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Slf4j
public abstract class AbstractCommand {


    @Autowired
    private JobInstanceDao oprJobInstanceDao;
    @Autowired
    private JobInstanceService jobInstanceService;

    /**
     * doExecute.
     *
     * @param jobInstance jobInstance
     */
    protected void doExecute(JobInstance jobInstance) {
        templateTaskExecute(jobInstance, JobStatus.QUEUED);
    }

    /**
     * 原interface Command中的方法.
     *
     * @return JobOperation
     */
    public abstract JobOperation getOperation();


    /**
     * 公共的执行方法.
     *
     * @param jobInstance jobInstance
     * @param jobStatus jobStatus
     */
    protected void templateTaskExecute(JobInstance jobInstance, JobStatus jobStatus) {
        jobInstance.setJobStatus(jobStatus);
        // 修改提交人
        jobInstanceService.update(jobInstance);

        TaskMessage task = new TaskMessage();
        BeanUtils.copyProperties(jobInstance, task);
        task.setApplicationCode(jobInstance.getAppCode());
        task.setJobInstanceId(jobInstance.getJobInstanceId());
        task.setJobOperation(this.getOperation());
//        this.getMessageQueueService().sendMessage(queueName, task);
    }



    /**
     * execute.update job instance before execute or change will be ignore
     *
     * @param jobInstance jobInstance
     */
    public void execute(JobInstance jobInstance) throws Exception{
        // refresh jobInstance
        EasyBeanUtils.copyNotNullProperties(
                oprJobInstanceDao.selectById(jobInstance.getJobInstanceId()), jobInstance);
        JobOperation[] oprs = jobInstance.getJobStatus().getAllowableOperations();
        if (oprs != null && Arrays.asList(oprs).contains(getOperation())) {
            log.debug("JobInstance {} execute.", jobInstance.getJobInstanceId());
            this.doExecute(jobInstance);

        } else {
            throw new Exception("Not Supported Operation!");
        }
    }

}
