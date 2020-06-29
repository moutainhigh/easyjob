package com.github.yanghr.easyjob.jobexecution.constants;

import java.util.concurrent.TimeUnit;

public final class Constant {
    public static final String PUBSUB_TYPE_PUB = "PUB";
    public static final String PUBSUB_TYPE_SUB = "SUB";
    public static final String STATUS = "opra:v1:status:";
    public static final long MAX_TIMETOLIVE = 86400L;
    public static final TimeUnit TIME_UNIT;
    public static final long TIMINGCHECK_SLEEP_TIME = 10000L;
    public static final long ADP_TIMEOUT_SECONDS = 6000L;
    public static final String ENDSEND = "{###!!###}";

    private Constant() {
    }

    static {
        TIME_UNIT = TimeUnit.SECONDS;
    }
}