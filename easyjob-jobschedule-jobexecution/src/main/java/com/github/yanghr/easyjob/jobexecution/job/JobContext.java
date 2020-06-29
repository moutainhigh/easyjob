package com.github.yanghr.easyjob.jobexecution.job;

import com.github.yanghr.easyjob.jobexecution.job.impl.AbstractJob;
import com.github.yanghr.easyjob.jobexecution.vo.TaskMessage;
import com.github.yanghr.easyjob.utils.EasyStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * 一个作业实例的上下文.
 *
 * @version
 * @author
 */

public class JobContext {

    public static final String DEFAULT_JOBINSTANCE_ID = "default-null-jobInstanceId";

    public static final String DEFAULT_PARAM_OUTPUT_TOTALCOUNT = "default.output.param.total.count";

    private static final Map<String, JobContext> JOBINSTANCEID_JOBCONTEXT_MAP = new ConcurrentHashMap<>();

    private boolean killed = false;

    private List<Exception> exceptions = new ArrayList<>();

    private final String jobInstanceId;

    private String jobType;

    private TaskMessage taskMessage;

    private final Map<String, String> parameters = new ConcurrentHashMap<>();

    private final Map<String, String> outputParams = new ConcurrentHashMap<>();

    private final ThreadGroup threadGroup = new ThreadGroup("JobExecution");

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4, new SimpleThreadFactory());

    private final Map<String, Object> sharedVariable = new ConcurrentHashMap<>();

    /**
     * 记录处理的百分比相关。
     */
    private Long totalCount = 0L;
    private Map<String, Long> currentProcessCount = new ConcurrentHashMap<>();

    /**
     * 当作业成功的时候，存储成功返回的消息。
     */
    private List<String> comments = new ArrayList<>();


    /**
     * 创建job上下文
     * @param jobInstanceId jobInstanceId
     * @param jobType jobType
     * @param parameters 参数
     */
    public JobContext(String jobInstanceId, String jobType, Map<String, String> parameters) {
        this.jobInstanceId = jobInstanceId;
        for (Entry<String, String> e : parameters.entrySet()) {
            if (!EasyStringUtils.isEmpty(e.getValue())) {
                this.parameters.put(e.getKey(), e.getValue());
            }
        }
        this.jobType = jobType;
    }

    /**
     * 创建job上下文
     * @param jobInstanceId jobInstanceId
     * @param parameters 参数
     */
    public JobContext(String jobInstanceId, Map<String, String> parameters) {
        this.jobInstanceId = jobInstanceId;
        this.taskMessage = new TaskMessage();
        this.parameters.putAll(parameters);

    }

    /**
     * 获取作业id.
     */
    public String getJobInstanceId() {
        return jobInstanceId;
    }

    /**
     * 计算当前的percentage。
     *
     * @return
     */
    public int getPercentage() {
        if (totalCount == 0) {
            return 0;
        } else {
            return (int) (this.currentProcessCount.values().stream().mapToLong(s -> s).sum() * 100
                    / totalCount);
        }
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    /**
     * @return the executor
     */
    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    /**
     * @return the exceptions
     */
    public List<Exception> getExceptions() {
        return exceptions;
    }

    /**
     * @param exceptions the exceptions to set
     */
    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }


    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(TaskMessage taskMessage) {
        this.taskMessage = taskMessage;
    }

    /**
     * 保存过程中的消息.
     *
     * @return
     */
    public List<String> getComments() {
        return comments;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, Long> getCurrentProcessCount() {
        return currentProcessCount;
    }

    public void setCurrentProcessCount(Map<String, Long> currentProcessCount) {
        this.currentProcessCount = currentProcessCount;
    }

    public Map<String, Object> getSharedVariable() {
        return sharedVariable;
    }



    public Map<String, String> getOutputParams(){
        return outputParams;
    }

    /**
     * 获取jobInstanceId与jobContext对应的map.
     * @return JobContext
     */
    public static Map<String, JobContext> getJobContextMap() {
        return JOBINSTANCEID_JOBCONTEXT_MAP;
    }

    /**
     * 获取jobInstanceId与jobContext.
     *
     * @param jobInstanceId
     * @return JobContext
     */
    public static JobContext getJobContextById(String jobInstanceId) {
        return JOBINSTANCEID_JOBCONTEXT_MAP.get(jobInstanceId);
    }

    /**
     * 获取当前线程的jobContext.
     * @return JobContext
     */
    public static JobContext getCurrentJobContext() {
        return JOBINSTANCEID_JOBCONTEXT_MAP.get(AbstractJob.getJobInstanceId());
    }

    /**
     * 为测试准备的方法
     * @param jobType jobType
     * @param parameters
     * @return JobContext
     */
    public static JobContext buildContext(String jobType, Map<String, String> parameters) {
        String jobInstanceId = "MOCK-JOB";
        JobContext p = new JobContext(jobInstanceId, parameters);
        p.setJobType(jobType);
        JobContext.getJobContextMap().put(jobInstanceId, p);
        AbstractJob.setJobInstanceId(jobInstanceId);
        return p;
    }

    /**
     * 为测试准备的方法
     * @param jobType jobType
     * @return JobContext
     */
    public static JobContext buildContext(String jobType) {
        return buildContext(jobType, new HashMap<String, String>());
    }

    /**
     * Simple ThreadFactory.
     *
     *
     * @version Seurat v1.0
     * @author Yu Tao, 2012-7-3
     */
    class SimpleThreadFactory implements ThreadFactory {

        AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {

            return new Thread(threadGroup, r, "JOB-" + jobInstanceId + "-" + count.getAndIncrement());
        }
    }
}
