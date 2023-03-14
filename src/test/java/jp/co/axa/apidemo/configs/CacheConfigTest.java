package jp.co.axa.apidemo.configs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** class to build cache config */
class CacheConfigTest {

  private CacheConfig cacheConfig;

  @BeforeEach
  void beforeEach() {
    cacheConfig = new CacheConfig();
  }

  /**
   * config redis cache
   *
   * @return cache configuration
   */
  @Test
  void testCacheConfiguration() {
    assertDoesNotThrow(() -> cacheConfig.cacheConfiguration());
  }

  @Test
  void testCaffeineConfig() {
    assertDoesNotThrow(() -> cacheConfig.caffeineConfig());
  }

  @Test
  void testCacheManager() {
    assertDoesNotThrow(() -> cacheConfig.cacheManager(cacheConfig.caffeineConfig()));
  }
}
