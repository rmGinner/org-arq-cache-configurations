import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectMappingCacheRunner {

    public static void runConfiguration(List<String> mainMemory, CacheConfigurationStrategy cacheConfigurationStrategy) {
        var cacheConfiguration = cacheConfigurationStrategy.getCacheConfiguration();

        Map<String, DirectCache> directCacheMap = new HashMap<>(cacheConfiguration.getCacheLines());

        Integer totalHits = 0;
        Integer totalMisses = 0;

        for (var memoryAddress : mainMemory) {

            var tag = memoryAddress.substring(0, cacheConfiguration.getTag());
            var line = memoryAddress.substring(
                    cacheConfiguration.getTag(),
                    cacheConfiguration.getTag() + cacheConfiguration.getLine()
            );

            var word = memoryAddress.substring(
                    cacheConfiguration.getTag() + cacheConfiguration.getLine(),
                    cacheConfiguration.getTag() + cacheConfiguration.getLine() + cacheConfiguration.getWord()
            );

            var wordByte = memoryAddress.substring(
                    memoryAddress.length() - 1
            );


            if (directCacheMap.containsKey(tag + line)) {
                totalHits++;
                directCacheMap.get(tag + line).addTotalUse();
            } else {
                totalMisses++;

                var directCache = new DirectCache();
                directCache.setData(memoryAddress);
                directCache.setTag(tag);
                directCache.setLine(line);
                directCache.addTotalUse();

                if (directCacheMap.size() < cacheConfiguration.getCacheLines()) {
                    directCacheMap.put(tag + line, directCache);
                } else {
                    //Replacement policy -- least frequently used
                    Map.Entry<String, DirectCache> directCacheMin = null;

                    for (Map.Entry<String, DirectCache> value : directCacheMap.entrySet()) {
                        if (directCacheMin == null || directCacheMin.getValue().getTotalUses() > value.getValue().getTotalUses()) {
                            directCacheMin = value;
                        }
                    }

                    directCacheMap.put(directCacheMin.getKey(), directCache);

                }
            }

            //Print cache parts only for testing.
//            System.out.println(memoryAddress);
//            System.out.println(tag);
//            System.out.println(line);
//            System.out.println(word);
//            System.out.println(wordByte);
        }

        var missRatio = (totalMisses * 100) / (totalHits + totalMisses);
        System.out.printf("Total of Hits for %s:  %d \n", cacheConfigurationStrategy.name(), totalHits);
        System.out.printf("Total of Misses for %s:  %d \n", cacheConfigurationStrategy.name(), totalMisses);
        System.out.printf("Total of Hit ratio for %s: %d%%  \n", cacheConfigurationStrategy.name(), 100 - missRatio);
        System.out.printf("Total of Miss ratio for %s: %d%%  \n\n\n", cacheConfigurationStrategy.name(), missRatio);
    }
}
