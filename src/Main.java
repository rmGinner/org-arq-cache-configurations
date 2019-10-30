import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        try (Scanner sc = new Scanner(Files.newBufferedReader(Paths.get("hex.csv"), Charset.forName("utf-8")))
                .useDelimiter(",")) {

            List<String> mainMemory = new ArrayList<>();

            while (sc.hasNext()) {
                mainMemory.add(hexToBin(sc.next().trim().toUpperCase()));
            }


            runFirstConfiguration(mainMemory);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runFirstConfiguration(List<String> mainMemory) {
        var cacheConfiguration = CacheConfigurationStrategy.DIRECT_MAPPING_ONE.getCacheConfiguration();

        var cache = new ArrayList<>(cacheConfiguration.getCacheLines());

        mainMemory.forEach(memoryAddress -> {
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

            System.out.println(memoryAddress);
            System.out.println(tag);
            System.out.println(line);
            System.out.println(word);
            System.out.println(wordByte);
        });

    }

    public static String hexToBin(String hex) {
        String binFragment = "";
        String bin = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for (int i = 0; i < hex.length(); i++) {
            iHex = Integer.parseInt("" + hex.charAt(i), 16);
            binFragment = Integer.toBinaryString(iHex);

            while (binFragment.length() < 4) {
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }

        return bin;
    }
}
