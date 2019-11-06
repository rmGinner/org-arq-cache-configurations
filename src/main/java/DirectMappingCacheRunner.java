import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectMappingCacheRunner {

    public static void runConfiguration(List<String> mainMemory, CacheConfigurationStrategy cacheConfigurationStrategy) {
        var cacheConfiguration = cacheConfigurationStrategy.getCacheConfiguration();

        Map<String, DirectCache> directCacheMap = new HashMap<>(cacheConfiguration.getCacheLines());

        Integer totalHits = 0;
        Integer totalMisses = 0;
        int i = 1;
        Boolean wasHit = null;
        for (var memoryAddress : mainMemory) {
            System.out.printf("Linha %d || ", i++);

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
                wasHit = true;
            } else {
                totalMisses++;
                wasHit = false;

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

            int decimal = Integer.parseInt(memoryAddress, 2);
            String hexStr = StringUtils.leftPad(Integer.toString(decimal, 16), 4, '0');

            //Print cache parts only for testing.
            System.out.printf("Endereço memória hex: %s || ", hexStr);
            System.out.printf("Endereço memória binário: %s || ", memoryAddress);
            System.out.printf("%s   \n\n\n\n", wasHit ? "HIT" : "MISS");
//            System.out.printf("Tag: %s || ", tag);
//            System.out.printf("Linha: %s || ", line);
//            System.out.printf("Palavra: %s || ", word);
//            System.out.printf("Byte palavra: %s || \n\n", wordByte);
        }

        var missRatio = (totalMisses * 100) / (totalHits + totalMisses);

        System.out.printf("========================PORCENTAGENS HITS e MISSES MAPEAMENTO DIRETO CONFIGURAÇÃO %d==========================\n\n",cacheConfigurationStrategy.equals(CacheConfigurationStrategy.DIRECT_MAPPING_ONE) ? 1 : 2);
        System.out.printf("Hits:  %d \n", totalHits);
        System.out.printf("Misses:  %d \n", totalMisses);
        System.out.printf("Hit ratio: %d%%  \n", 100 - missRatio);
        System.out.printf("Miss ratio: %d%%  \n\n\n", missRatio);

        i = 1;

        System.out.printf("========================RESULTADO CACHE MAPEAMENTO DIRETO CONFIGURAÇÃO %d==========================\n\n",cacheConfigurationStrategy.equals(CacheConfigurationStrategy.DIRECT_MAPPING_ONE) ? 1 : 2);
        for (Map.Entry<String, DirectCache> cacheResult : directCacheMap.entrySet()) {
            System.out.printf("%d || ",i++);
            System.out.printf("Tag: %s || Linha: %s \n\n", cacheResult.getKey().substring(0,cacheConfiguration.getTag()),cacheResult.getKey().substring(cacheConfiguration.getTag(),cacheConfiguration.getTag() + cacheConfiguration.getLine()));
        }
    }
}
