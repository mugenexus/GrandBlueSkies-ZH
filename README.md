# Grand Blue Skies 简中重译补丁

《杀戮尖塔》模组 Grand Blue Skies 1.2.1 的独立简体中文补丁。以英文原文为底稿，重译卡牌、关键词、状态、敌人、遗物、事件、界面及模板共 10 个本地化文件。

## 安装

1. 订阅 [Grand Blue Skies](https://steamcommunity.com/sharedfiles/filedetails/?id=3643513257) 和原条目列出的前置。
2. 订阅 [Steam 创意工坊的简中补丁](https://steamcommunity.com/sharedfiles/filedetails/?id=3842446821)，或从 [GitHub Releases](https://github.com/mugenexus/GrandBlueSkies-ZH/releases/latest) 下载补丁 JAR，放入游戏目录的 mods 文件夹。两种方式任选其一。
3. 从 Steam 选择 Play with Mods，同时勾选原模组和 **Grand Blue Skies 简中重译补丁**，将游戏语言设为简体中文。

取消勾选补丁即可恢复原译文。补丁仅对原模组 1.2.1 的简中资源生效。其他语言或版本继续读取原模组资源。

## 构建

使用 JDK 8，在仓库根目录运行以下命令，把路径替换为本机的 ModTheSpire JAR 与游戏的 desktop-1.0.jar：

    mkdir build
    javac -encoding UTF-8 -cp "ModTheSpire.jar;desktop-1.0.jar" -d build src/grandblueskies_zh/*.java
    jar cf GrandBlueSkies-ZH-Patch.jar -C build .
    jar uf GrandBlueSkies-ZH-Patch.jar -C resources .

## 致谢

原模组由 nepsi 制作。专名参考 [碧蓝幻想中文 Wiki](https://gbf.huijiwiki.com/wiki/SSR%E4%BA%BA%E7%89%A9)。原模组及其素材归各自权利人所有。
