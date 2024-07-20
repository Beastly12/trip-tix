//package org.dafe.tripTix.configs;
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.jcache.JCacheCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.cache.CacheManager;
//import javax.cache.Caching;
//import javax.cache.spi.CachingProvider;
//import java.net.URISyntaxException;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    @Bean
//    public CacheManager ehCacheManager() throws URISyntaxException {
//        CachingProvider cachingProvider = Caching.getCachingProvider();
//        return cachingProvider.getCacheManager(
//                getClass().getResource("/ehcache.xml").toURI(),
//                getClass().getClassLoader()
//        );
//    }
//
//    @Bean
//    public org.springframework.cache.CacheManager cacheManager(CacheManager cacheManager) {
//        return new JCacheCacheManager(cacheManager);
//    }
//}
