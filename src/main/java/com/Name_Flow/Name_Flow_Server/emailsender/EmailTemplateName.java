package com.Name_Flow.Name_Flow_Server.emailsender;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    VERIFY_EMAIL("verify_email");

    private final String templateName;

    EmailTemplateName(String templateName){
        this.templateName=templateName;
    }

}
