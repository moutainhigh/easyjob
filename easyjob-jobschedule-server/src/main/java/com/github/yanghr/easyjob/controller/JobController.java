package com.github.yanghr.easyjob.controller;

import com.github.yanghr.easyjob.api.JobApi;
import com.github.yanghr.easyjob.vo.AdhocJobDTO;
import com.github.yanghr.easyjob.vo.JobInstanceVO;
import com.github.yanghr.easyjob.vo.JobVO;

import javax.validation.Valid;

public class JobController implements JobApi {
    @Override
    public JobVO findOne(String id) {
        return null;
    }

    @Override
    public JobVO put(String id, @Valid JobVO vo) {
        return null;
    }

    @Override
    public JobVO add(@Valid JobVO vo) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public JobInstanceVO runAdhoc(AdhocJobDTO jobVO) {
        return null;
    }

    @Override
    public JobInstanceVO createAdhocJobInstance(AdhocJobDTO dto) {
        return null;
    }

    @Override
    public JobInstanceVO updateAndRunJobInstance(JobInstanceVO jobInstanceVO) {
        return null;
    }
}
