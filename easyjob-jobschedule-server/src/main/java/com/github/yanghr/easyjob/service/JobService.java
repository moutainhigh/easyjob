package com.github.yanghr.easyjob.service;

import com.github.yanghr.easyjob.dao.JobDao;
import com.github.yanghr.easyjob.domainmodel.Job;
import com.github.yanghr.easyjob.domainmodel.JobInstance;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobStatus;
import com.github.yanghr.easyjob.utils.EasyQueryWrapper;
import com.github.yanghr.easyjob.utils.json.JacksonHelper;
import com.github.yanghr.easyjob.vo.AdhocJobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * JobService.
 *
 * @version
 * @author
 */
@Service
public class JobService {
    public static final String JOBTYPE = "jobType";

    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobInstanceService jobInstanceService;

    @Autowired
    private Environment environment;


    protected JobDao getDao() {
        return jobDao;
    }



    /**
     * 创建jobInstance不执行(为了支持异步)
     *
     * @param dto dto
     * @return JobInstance
     */
    @Transactional
    public JobInstance createAdhocJobInstance(AdhocJobDTO dto) {
        JobInstance jobInstance = null;
        jobInstance = new JobInstance();
        jobInstance.setJobId(dto.getJobType());
        jobInstance.setJobType(dto.getJobType());
        jobInstance.setAppCode(dto.getApplicationCode());
        jobInstance.setExpectedStarttime(new Date());
        jobInstance.setDescription(dto.getJobType());
        jobInstance.setName(dto.getJobType());
        jobInstance.appendComments("JobInstance is Created. by " + Thread.currentThread()
                + ". Current is " + environment.getProperty("spring.cloud.client.ip-address"));
        jobInstance.setSubmitter(dto.getSubmitter());
        jobInstance.setParameters(JacksonHelper.entityToJson(dto.getParameters()));
        jobInstance.setJobStatus(JobStatus.SCHEDULED);
        this.jobInstanceService.add(jobInstance);

        return jobInstance;
    }

    /**
     * 执行作业
     *
     * @param jobInstance jobInstance
     * @return jobInstance
     */
    @Transactional
    public JobInstance updateAndRunJobInstance(JobInstance jobInstance) throws Exception{

        if (jobInstanceService.findOne(jobInstance.getJobInstanceId()) == null) {
            throw new Exception("job instance not exists");
        }
        jobInstanceService.update(jobInstance);
        new AdhocRunCommand().execute(jobInstance);

        return jobInstance;
    }

    /**
     * 执行adhoc作业
     *
     * @param dto AdhocJobDTO
     * @return jobInstance
     */
    @Transactional
    public JobInstance runAdhoc(AdhocJobDTO dto) throws Exception {
        return updateAndRunJobInstance(createAdhocJobInstance(dto));
    }


//
//    /**
//     * 根据appcode job type查询job
//     *
//     * @param appCode appCode
//     * @param jobType jobType
//     * @return job
//     */
//    public Job queryJob(String appCode, String jobType) {
//        Job appCode1 = JobDao.selectOne(
//                new EasyQueryWrapper<Job>().eq("appCode", appCode).eq(JOBTYPE, jobType));
//        return appCode1;
//    }

}
