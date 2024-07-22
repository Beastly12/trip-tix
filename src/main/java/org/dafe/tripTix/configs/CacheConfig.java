//package org.dafe.tripTix.configs;
//
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.config.builders.ExpiryPolicyBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.config.units.EntryUnit;
//import org.ehcache.jsr107.Eh107Configuration;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.cache.CacheManager;
//import javax.cache.Caching;
//import javax.cache.spi.CachingProvider;
//import java.time.Duration;
//import java.util.Properties;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    @Bean
//    public CacheManager cacheManager() {
//        // Ehcache configuration
//        org.ehcache.config.CacheConfiguration<Object, Object> ehCacheConfig = CacheConfigurationBuilder
//                .newCacheConfigurationBuilder(Object.class, Object.class,
//                        ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                .heap(1000, EntryUnit.ENTRIES))
//                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(1)))
//                .build();
//
//        // Create Ehcache CacheManager
//        org.ehcache.CacheManager ehCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//                .withCache("bookings", ehCacheConfig)
//                .build(true);
//
//        // JCache configuration
//        CachingProvider provider = Caching.getCachingProvider();
//        CacheManager cacheManager = provider.getCacheManager(
//                provider.getDefaultURI(),
//                provider.getDefaultClassLoader(),
//                (Properties) Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig)
//        );
//
//        return cacheManager;
//    }
//}
