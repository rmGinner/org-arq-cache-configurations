public enum CacheConfigurationStrategy {

    DIRECT_MAPPING_ONE {
        @Override
        CacheConfiguration getCacheConfiguration() {
            var cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setTag(10);
            cacheConfiguration.setLine(3);
            cacheConfiguration.setWord(2);
            cacheConfiguration.setWordByte(1);
            cacheConfiguration.setCacheLines(8);
            cacheConfiguration.setCacheLineWords(4);

            return cacheConfiguration;
        }
    },
    DIRECT_MAPPING_TWO {
        @Override
        CacheConfiguration getCacheConfiguration() {
            var cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setTag(10);
            cacheConfiguration.setLine(4);
            cacheConfiguration.setWord(1);
            cacheConfiguration.setWordByte(1);
            cacheConfiguration.setCacheLines(16);
            cacheConfiguration.setCacheLineWords(2);

            return cacheConfiguration;
        }
    };


    abstract CacheConfiguration getCacheConfiguration();
}
