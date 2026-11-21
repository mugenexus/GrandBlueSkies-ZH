package grandblueskies_zh;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import java.io.InputStream;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.Properties;

@SpirePatch(
    cls = "granbluebosses.GranblueBosses",
    method = "localizationPath",
    paramtypez = {String.class, String.class},
    requiredModId = "granblueskies",
    optional = true
)
public final class ChineseLocalization {
    private static Boolean compatible;

    @SpirePostfixPatch
    public static String postfix(String original, String language, String file) {
        if (!"zhs".equals(language) || Loader.MODINFOS == null) {
            return original;
        }
        String translated = "grandblueskies_zh/zhs/" + file;
        if (ChineseLocalization.class.getResource("/" + translated) == null) {
            return original;
        }
        for (ModInfo mod : Loader.MODINFOS) {
            if ("granblueskies".equals(mod.ID) && mod.ModVersion != null
                && "1.2.1".equals(mod.ModVersion.toString())) {
                if (compatible == null) compatible = matchesSource();
                return compatible ? translated : original;
            }
        }
        return original;
    }

    private static boolean matchesSource() {
        Properties expected = new Properties();
        try (InputStream manifest = ChineseLocalization.class.getResourceAsStream(
            "/grandblueskies_zh/source-sha256.properties")) {
            if (manifest == null) return false;
            expected.load(manifest);
            if (expected.size() != 10) return false;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            for (String file : expected.stringPropertyNames()) {
                try (InputStream source = ChineseLocalization.class.getResourceAsStream(
                    "/granbluebosses/localization/eng/" + file)) {
                    if (source == null) return false;
                    int count;
                    while ((count = source.read(buffer)) != -1) digest.update(buffer, 0, count);
                    String hash = String.format("%064x", new BigInteger(1, digest.digest()));
                    if (!hash.equals(expected.getProperty(file))) return false;
                }
            }
            return true;
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException e) {
            return false;
        }
    }
}
