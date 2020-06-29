package com.github.yanghr.easyjob.jobexecution.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobOperation;
import com.github.yanghr.easyjob.utils.json.JacksonHelper;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskMessage implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private String jobId;

    private String jobType;

    private String description;

    private String applicationCode;

    private String submitter;

    private String jobInstanceId;

    private Map<String, String> parameters = new HashMap<>();

    private JobOperation jobOperation;

    public static void main(String[] args) {
        TaskMessage task = new TaskMessage();
        task.getParameters().put("filePath", "/opra/test/1.dat");
        task.setJobId("testJobId");
        task.setJobType("JOB-TYPE");
        task.setApplicationCode("SAL");
        task.setJobInstanceId("uuid");
        task.setJobOperation(JobOperation.RUN);

        System.out.println(JacksonHelper.entityToJson(task));
    }
}
