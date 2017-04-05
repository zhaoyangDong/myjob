/*
 * #{copyright}#
 */

package com.hand.hap.job.service.impl;

import static com.hand.hap.job.exception.JobException.JOB_EXCEPTION;
import static com.hand.hap.job.exception.JobException.NOT_SUB_CLASS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.FieldRequiredException;
import com.hand.hap.job.AbstractJob;
import com.hand.hap.job.dto.CronTriggerDto;
import com.hand.hap.job.dto.JobCreateDto;
import com.hand.hap.job.dto.JobData;
import com.hand.hap.job.dto.JobDetailDto;
import com.hand.hap.job.dto.JobInfoDetailDto;
import com.hand.hap.job.dto.SchedulerDto;
import com.hand.hap.job.dto.SimpleTriggerDto;
import com.hand.hap.job.dto.TriggerDto;
import com.hand.hap.job.exception.JobException;
import com.hand.hap.job.mapper.CronTriggerMapper;
import com.hand.hap.job.mapper.JobDetailMapper;
import com.hand.hap.job.mapper.SchedulerMapper;
import com.hand.hap.job.mapper.SimpleTriggerMapper;
import com.hand.hap.job.mapper.TriggerMapper;
import com.hand.hap.job.service.IQuartzService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class QuartzServiceImpl implements IQuartzService {

    private final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private JobDetailMapper jobDetailMapper;

    @Autowired
    private TriggerMapper triggerMapper;

    @Autowired
    private CronTriggerMapper cronTriggerMapper;

    @Autowired
    private SimpleTriggerMapper simpleTriggerMapper;

    @Autowired
    private SchedulerMapper schedulerMapper;

    @Autowired
    private Scheduler quartzScheduler;

    @Override
    public List<TriggerDto> getTriggers(IRequest request, TriggerDto example, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return triggerMapper.selectTriggers(example);
    }

    @Override
    public CronTriggerDto getCronTrigger(String triggerName, String triggerGroup) throws SchedulerException {
        CronTriggerDto dto = new CronTriggerDto();
        dto.setSchedName(quartzScheduler.getSchedulerName());
        dto.setTriggerName(triggerName);
        dto.setTriggerGroup(triggerGroup);
        return cronTriggerMapper.selectByPrimaryKey(dto);
    }

    @Override
    public SimpleTriggerDto getSimpleTrigger(String triggerName, String triggerGroup) throws SchedulerException {
        SimpleTriggerDto dto = new SimpleTriggerDto();
        dto.setSchedName(quartzScheduler.getSchedulerName());
        dto.setTriggerName(triggerName);
        dto.setTriggerGroup(triggerGroup);
        return simpleTriggerMapper.selectByPrimaryKey(dto);
    }

    @Override
    public List<JobInfoDetailDto> getJobInfoDetails(IRequest request, JobDetailDto example, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);

        List<JobInfoDetailDto> selectJobInfoDetails = jobDetailMapper.selectJobInfoDetails(example);

        for (JobInfoDetailDto jobInfoDetailDto : selectJobInfoDetails) {
            try {
                JobKey jobKey = new JobKey(jobInfoDetailDto.getJobName(), jobInfoDetailDto.getJobGroup());
                JobDetail jobDetail = quartzScheduler.getJobDetail(jobKey);
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                String[] keys = jobDataMap.getKeys();
                List<JobData> jobDatas = new ArrayList<JobData>();
                for (String string : keys) {
                    JobData e = new JobData();
                    e.setName(string);
                    e.setValue(jobDataMap.getString(string));
                    jobDatas.add(e);
                }

                Trigger trigger = quartzScheduler.getTriggersOfJob(jobKey).get(0);
                if (trigger instanceof SimpleTrigger) {
                    jobInfoDetailDto.setTriggerType("SIMPLE");
                    jobInfoDetailDto.setRepeatCount(((SimpleTrigger) trigger).getRepeatCount());
                    jobInfoDetailDto.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
                } else if (trigger instanceof CronTrigger) {
                    jobInfoDetailDto.setCronExpression(((CronTrigger) trigger).getCronExpression());
                    jobInfoDetailDto.setTriggerType("CRON");
                }
                jobInfoDetailDto.setTriggerName(trigger.getKey().getName());
                jobInfoDetailDto.setTriggerGroup(trigger.getKey().getGroup());
                jobInfoDetailDto.setStartTime(trigger.getStartTime());
                jobInfoDetailDto.setEndTime(trigger.getEndTime());

                jobInfoDetailDto.setJobDatas(jobDatas);

                // get running state of job
                Trigger.TriggerState ts = quartzScheduler.getTriggerState(trigger.getKey());
                jobInfoDetailDto.setRunningState(ts.name());
            } catch (SchedulerException e) {
                jobInfoDetailDto.setRunningState(Trigger.TriggerState.ERROR.name());
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        return selectJobInfoDetails;
    }

    @Override
    public List<JobDetailDto> getJobDetails(IRequest request, JobDetailDto example, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return jobDetailMapper.selectJobDetails(example);
    }

    @Override
    public Map<String, Object> schedulerInformation() throws SchedulerException {
        Map<String, Object> infoMap = new HashMap<>();
        SchedulerMetaData metaData = quartzScheduler.getMetaData();
        if (metaData.getRunningSince() != null) {
            infoMap.put("runningSince", metaData.getRunningSince().getTime());
        }
        infoMap.put("numberOfJobsExecuted", metaData.getNumberOfJobsExecuted());
        infoMap.put("schedulerName", metaData.getSchedulerName());
        infoMap.put("schedulerInstanceId", metaData.getSchedulerInstanceId());
        // infoMap.put("summary", metaData.getSummary());
        infoMap.put("threadPoolSize", metaData.getThreadPoolSize());
        infoMap.put("version", metaData.getVersion());
        infoMap.put("inStandbyMode", metaData.isInStandbyMode());
        infoMap.put("jobStoreClustered", metaData.isJobStoreClustered());
        infoMap.put("jobStoreClass", metaData.getJobStoreClass());
        infoMap.put("jobStoreSupportsPersistence", metaData.isJobStoreSupportsPersistence());
        infoMap.put("started", metaData.isStarted());
        infoMap.put("shutdown", metaData.isShutdown());
        infoMap.put("schedulerRemote", metaData.isSchedulerRemote());
        return infoMap;
    }

    @Override
    public List<SchedulerDto> selectSchedulers(SchedulerDto schedulerDto, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return schedulerMapper.selectSchedulers(schedulerDto);
    }

    @Override
    public void createJob(JobCreateDto jobCreateDto)
            throws ClassNotFoundException, SchedulerException, JobException {
        if (StringUtils.isEmpty(jobCreateDto.getJobClassName())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "jobClassName" }));
        } else if (StringUtils.isEmpty(jobCreateDto.getJobName())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "jobName" }));
        } else if (StringUtils.isEmpty(jobCreateDto.getJobGroup())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "jobGroup" }));
        } else if (StringUtils.isEmpty(jobCreateDto.getTriggerName())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "triggerName" }));
        } else if (StringUtils.isEmpty(jobCreateDto.getTriggerGroup())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "triggerGroup" }));
        } else if (StringUtils.isEmpty(jobCreateDto.getTriggerType())) {
            throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "triggerType" }));
        }

        String jobClassName = jobCreateDto.getJobClassName();

        boolean assignableFrom = false;
        Class forName = null;
        try {
            forName = Class.forName(jobClassName);
            assignableFrom = AbstractJob.class.isAssignableFrom(forName);
        } catch (ClassNotFoundException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!assignableFrom || forName == null) {
            String name = AbstractJob.class.getName();
            throw new JobException(JOB_EXCEPTION, NOT_SUB_CLASS, new Object[] { jobClassName, name });
        }

        // Class jobClass = Class.forName(jobCreateDto.getJobClassName());
        JobBuilder jb = JobBuilder.newJob(forName).withIdentity(jobCreateDto.getJobName(), jobCreateDto.getJobGroup())
                .withDescription(jobCreateDto.getDescription());

        if (hasJobData(jobCreateDto)) {
            JobDataMap data = new JobDataMap();
            List<JobData> jobDatas = jobCreateDto.getJobDatas();
            for (JobData jobData : jobDatas) {
                data.put(jobData.getName(), jobData.getValue());
            }
            jb = jb.usingJobData(data);
        }
        JobDetail jobDetail = jb.build();

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobCreateDto.getTriggerName(), jobCreateDto.getTriggerGroup()).forJob(jobDetail);
        if (jobCreateDto.getStartTime() != null && jobCreateDto.getStartTime() > 0) {
            triggerBuilder.startAt(new Date(jobCreateDto.getStartTime()));
            // triggerBuilder.startAt(jobCreateDto.getStart());
        }
        if (jobCreateDto.getEndTime() != null && jobCreateDto.getEndTime() > 0) {
            triggerBuilder.endAt(new Date(jobCreateDto.getEndTime()));
            // triggerBuilder.endAt(jobCreateDto.getEnd());
        }
        ScheduleBuilder sche = null;
        if ("CRON".equalsIgnoreCase(jobCreateDto.getTriggerType())) {
            if (org.apache.commons.lang.StringUtils.isEmpty(jobCreateDto.getCronExpression())) {
                throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "cronExpression" }));
            }
            sche = CronScheduleBuilder.cronSchedule(jobCreateDto.getCronExpression());

        } else if ("SIMPLE".equalsIgnoreCase(jobCreateDto.getTriggerType())) {
            if (StringUtils.isEmpty(jobCreateDto.getRepeatInterval())) {
                throw new RuntimeException(new FieldRequiredException("hap.validation.notempty", new Object[] { "repeatInterval" }));
            }
            int interval = Integer.parseInt(jobCreateDto.getRepeatInterval());
            int count = 0;
            try {
                count = Integer.parseInt(jobCreateDto.getRepeatCount());
            } catch (Throwable thr) {
            }
            if (count < 1) {
                sche = SimpleScheduleBuilder.repeatSecondlyForever(interval);
            } else {
                sche = SimpleScheduleBuilder.repeatSecondlyForTotalCount(count, interval);
            }

        }
        Trigger trigger = triggerBuilder.withSchedule(sche).build();
        quartzScheduler.scheduleJob(jobDetail, trigger);
    }

    private boolean hasJobData(JobCreateDto jobCreateDto) {
        List<JobData> jobDatas = jobCreateDto.getJobDatas();
        return jobDatas != null && !jobDatas.isEmpty();
    }

    /**
     * 删除定时任务.
     * 
     * @see IQuartzService#deleteJob(java.lang.String, java.lang.String)
     */
    @Override
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        quartzScheduler.deleteJob(new JobKey(jobName, jobGroup));
    }

    @Override
    public Map<String, Object> start() throws SchedulerException {
        quartzScheduler.start();
        return schedulerInformation();
    }

    @Override
    public Map<String, Object> standby() throws SchedulerException {
        quartzScheduler.standby();
        return schedulerInformation();
    }

    @Override
    public Map<String, Object> pauseAll() throws SchedulerException {
        quartzScheduler.pauseAll();
        return schedulerInformation();
    }

    @Override
    public Map<String, Object> resumeAll() throws SchedulerException {
        quartzScheduler.resumeAll();
        return schedulerInformation();
    }

    @Override
    public void pauseJobs(List<JobDetailDto> list) throws SchedulerException {
        for (JobDetailDto job : list) {
            quartzScheduler.pauseJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
        }
    }

    @Override
    public void resumeJobs(List<JobDetailDto> list) throws SchedulerException {
        for (JobDetailDto job : list) {
            quartzScheduler.resumeJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
        }
    }

    @Override
    public void deleteJobs(List<JobDetailDto> list) throws SchedulerException {
        for (JobDetailDto job : list) {
            quartzScheduler.deleteJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
        }
    }

    @Override
    public void pauseTriggers(List<TriggerDto> list) throws SchedulerException {
        for (TriggerDto trigger : list) {
            quartzScheduler.pauseTrigger(TriggerKey.triggerKey(trigger.getTriggerName(), trigger.getTriggerGroup()));
        }
    }

    @Override
    public void resumeTriggers(List<TriggerDto> list) throws SchedulerException {
        for (TriggerDto trigger : list) {
            quartzScheduler.resumeTrigger(TriggerKey.triggerKey(trigger.getTriggerName(), trigger.getTriggerGroup()));
        }
    }

}