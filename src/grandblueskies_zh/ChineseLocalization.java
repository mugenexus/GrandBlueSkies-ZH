package grandblueskies_zh;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;

@SpirePatch(
    cls = "granbluebosses.GranblueBosses",
    method = "localizationPath",
    paramtypez = {String.class, String.class},
    requiredModId = "granblueskies",
    optional = true
)
public final class ChineseLocalization {
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
                return translated;
            }
        }
        return original;
    }
}
