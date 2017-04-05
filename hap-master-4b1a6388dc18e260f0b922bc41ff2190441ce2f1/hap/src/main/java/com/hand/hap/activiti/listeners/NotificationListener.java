package com.hand.hap.activiti.listeners;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.activiti.engine.task.IdentityLinkType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.hand.hap.core.AppContextInitListener;

public class NotificationListener implements TaskListener, AppContextInitListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private GroupDataManager groupDataManager;

    private Collection<IUserTaskNotifier> notifiers = Collections.emptyList();

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskEntity task = (TaskEntity) delegateTask;
        String assignee = task.getAssignee();
        List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();

        if (StringUtils.isNotEmpty(assignee)) {
            UserEntity user = userDataManager.findById(assignee);
            sendNotification(user, task);
        }

        for (IdentityLinkEntity link : identityLinks) {
            if (IdentityLinkType.CANDIDATE.equals(link.getType())) {
                if (link.isUser()) {
                    UserEntity user = userDataManager.findById(link.getUserId());
                    sendNotification(user, task);
                }
                if (link.isGroup()) {
                    sendNotification(groupDataManager.findById(link.getGroupId()), task);
                }
            }
        }

    }

    public void sendNotification(GroupEntity groupEntity, TaskEntity taskEntity) {
        log.debug("send email notification to group:" + groupEntity);
        for (IUserTaskNotifier notifier : notifiers) {
            notifier.onTaskCreate(taskEntity, groupEntity);
        }
    }

    public void sendNotification(UserEntity user, TaskEntity task) {
        log.debug("send email notification to user:" + user);
        for (IUserTaskNotifier notifier : notifiers) {
            notifier.onTaskCreate(task, user);
        }
        if (user == null) {
            throw new ActivitiException("Employee Not Found.");
        }

        String message = user.getFirstName() + " 你好:<br/>" + "你有一个工作流需要审批.";

        if (true) {
            log.debug(message);
            return;
        }
        //
        // HtmlEmail email = new HtmlEmail();
        // email.setCharset("GBK");
        // try {
        // email.setHtmlMsg(message);
        // email.addTo(user.getEmail());
        // email.setSubject("[HAP]工作流提醒");
        // email.setFrom("hap_dev@126.com");
        //
        // setMailServerProperties(email);
        //
        // email.send();
        // } catch (EmailException e) {
        // throw new ActivitiException("Could not send e-mail:" +
        // e.getMessage(), e);
        // }
    }

    protected void setMailServerProperties(Email email) {
        ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();

        String host = processEngineConfiguration.getMailServerHost();
        if (host == null) {
            throw new ActivitiException("Could not send email: no SMTP host is configured");
        }
        email.setHostName(host);

        int port = processEngineConfiguration.getMailServerPort();
        email.setSmtpPort(port);

        String user = processEngineConfiguration.getMailServerUsername();
        String password = processEngineConfiguration.getMailServerPassword();
        if (user != null && password != null) {
            email.setAuthentication(user, password);
        }
    }

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, IUserTaskNotifier> map = applicationContext.getBeansOfType(IUserTaskNotifier.class);
        notifiers = map.values();
    }
}