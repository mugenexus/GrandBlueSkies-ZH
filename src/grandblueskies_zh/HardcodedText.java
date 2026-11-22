package grandblueskies_zh;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HardcodedText {
    private static final Pattern HP_OMEN = Pattern.compile(
        "This enemy will prepare a powerful attack when under (\\d+)% health unless it is stunned\\.");
    private static final Pattern STACK_OMEN = Pattern.compile(
        "This enemy will prepare a powerful attack when (it|the player) has (-?\\d+) stacks of (.+) or (more|less)\\.");

    static String omen(String text) {
        if ("This enemy will prepare a powerful attack when it loses all its block".equals(text)) {
            return "该敌人失去全部格挡时，将准备发动一次强力攻击。";
        }
        if (text == null) return null;
        Matcher hp = HP_OMEN.matcher(text);
        if (hp.matches()) return "该敌人生命低于 " + hp.group(1) + "% 时，将准备发动一次强力攻击，除非它已眩晕。";
        Matcher stacks = STACK_OMEN.matcher(text);
        if (stacks.matches()) {
            return ("it".equals(stacks.group(1)) ? "该敌人" : "你") + "身上的「" + stacks.group(3)
                + "」" + ("more".equals(stacks.group(4)) ? "至少为 " : "至多为 ") + stacks.group(2)
                + " 层时，该敌人将准备发动一次强力攻击。";
        }
        return text;
    }
    static String opening(String text) {
        if (Settings.language == Settings.GameLanguage.ZHS && ("A warm light envelops you, and you suddenly find "
            + "yourself floating. Above you is a clear night sky. Bellow you is a sea of clouds.").equals(text)) {
            return "温暖的光芒将你笼罩。转眼间，你已悬浮在空中。抬头是澄澈的夜空，脚下是茫茫云海。";
        }
        return text;
    }

    @SpirePatch(cls = "granbluebosses.acts.BaseAct", method = "getAfterSelectText",
        requiredModId = "granblueskies", optional = true)
    public static class Opening {
        public static String Postfix(String result) {
            return opening(result);
        }
    }

    @SpirePatch(cls = "granbluebosses.acts.Act1SkiesBak", method = "getAfterSelectText",
        requiredModId = "granblueskies", optional = true)
    public static class AlternateOpening {
        public static String Postfix(String result) {
            return opening(result);
        }
    }

    @SpirePatches({
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenByString",
            paramtypez = {String.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenByHp",
            paramtypez = {int.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenByHp",
            paramtypez = {float.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenByXPowerOrHigher",
            paramtypez = {AbstractPower.class, int.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenXPowerOrLower",
            paramtypez = {AbstractPower.class, int.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenXPowerOrHigherOnPlayer",
            paramtypez = {AbstractPower.class, int.class}, requiredModId = "granblueskies", optional = true),
        @SpirePatch(cls = "granbluebosses.powers.stanceOmens.StanceOmen", method = "setUpOmenXPowerOrLowerOnPlayer",
            paramtypez = {AbstractPower.class, int.class}, requiredModId = "granblueskies", optional = true)
    })
    public static class StanceOmen {
        public static void Postfix(AbstractPower instance) {
            if (Settings.language == Settings.GameLanguage.ZHS) instance.description = omen(instance.description);
        }
    }

    @SpirePatch(cls = "granbluebosses.rewards.GoldBrickReward", method = SpirePatch.CONSTRUCTOR,
        paramtypez = {}, requiredModId = "granblueskies", optional = true)
    public static class GoldBrick {
        public static void Postfix(RewardItem instance) {
            if (Settings.language == Settings.GameLanguage.ZHS && "Upgrade all cards in your deck".equals(instance.text)) {
                instance.text = "升级牌组中所有可升级的牌";
            }
        }
    }
}
