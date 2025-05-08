package com.backend.hormonalcare.profile.application.internal.outboundservices.acl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("profileSupabaseProperties")
@ConfigurationProperties(prefix = "supabase")
public class SupabasePropertiesProfile {
    private String url;
    private String key;
    private String bucket;

    // Getters y setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getBucket() { return bucket; }
    public void setBucket(String bucket) { this.bucket = bucket; }
}