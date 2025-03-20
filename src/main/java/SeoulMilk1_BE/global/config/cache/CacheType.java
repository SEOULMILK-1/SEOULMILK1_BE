package SeoulMilk1_BE.global.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    TOKEN_CACHE("tokenCache", 7, 1000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }
}
