package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Department.class.getName() + ".employees", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Task.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Task.class.getName() + ".jobs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Employee.class.getName() + ".jobs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Job.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Job.class.getName() + ".tasks", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.JobHistory.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
