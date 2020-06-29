package com.github.yanghr.easyjob.service;


import com.github.yanghr.easyjob.dao.JobInstanceDao;
import com.github.yanghr.easyjob.domainmodel.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobInstanceService {


    @Autowired
    private JobInstanceDao jobInstanceDao;

    public void add(JobInstance jobInstance){
        jobInstanceDao.insert(jobInstance);
    }

    public JobInstance findOne(String id){
        return jobInstanceDao.selectById(id);
    }

    public void update(JobInstance jobInstance){
         jobInstanceDao.updateById(jobInstance);
    }
}
