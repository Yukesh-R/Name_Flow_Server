package com.Name_Flow.Name_Flow_Server.emailsender;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    VERIFY_EMAIL("verify_email"),

    ACCESS_NOTIFY("access_notify");

    private final String templateName;

    EmailTemplateName(String templateName){
        this.templateName=templateName;
    }

}
