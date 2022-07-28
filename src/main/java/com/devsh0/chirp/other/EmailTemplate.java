package com.devsh0.chirp.other;

import com.devsh0.chirp.util.Utils;
import lombok.Builder;
import lombok.Setter;

import java.io.IOException;

@Setter
@Builder
public class EmailTemplate {
    private String actionHeader;
    private String actionText;
    private String linkText;
    private String linkUrl;

    public String getHtml() throws IOException {
        var html = Utils.getTemplateResource("email-template.html");
        html = html.replace("{{action_header}}", actionHeader);
        html = html.replace("{{action_text}}", actionText);
        html = html.replace("{{link_text}}", linkText);
        html = html.replace("{{link_url}}", linkUrl);
        return html;
    }
}
