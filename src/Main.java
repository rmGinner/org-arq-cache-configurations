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


            directMapping(mainMemory);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void directMapping(List<String> mainMemory) {
        var cacheConfiguration = CacheConfigurationStrategy.DIRECT_MAPPING_ONE.getCacheConfiguration();

        
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
