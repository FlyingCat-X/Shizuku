package pe.flyingcat.shizukubot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.flyingcat.shizukubot.beans.Settings;
import pe.flyingcat.shizukubot.commands.config.LanguageCommand;
import pe.flyingcat.shizukubot.commands.guildAdministration.KickCommand;
import pe.flyingcat.shizukubot.commands.essentials.HelpCommand;
import pe.flyingcat.shizukubot.commands.essentials.InfoCommand;
import pe.flyingcat.shizukubot.commands.guildAdministration.BanCommand;
import pe.flyingcat.shizukubot.settings.SettingsManager;
import pe.flyingcat.shizukubot.util.ApplicationExitCodes;
import pe.flyingcat.shizukubot.util.Multilanguage;

/**
 *
 * @author FlyingCat
 */
public class Shizuku {

    private static JDA bot;
    private static Settings settings = SettingsManager.getInstance().getSettings();
    private static Multilanguage multiLang;
    private static final Logger log = LogManager.getLogger(Shizuku.class);

    public static void main(String[] args) {
        try {
            multiLang = new Multilanguage(settings.getLanguage());
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(settings.getBotToken());
            // Registering all help messages
            HelpCommand help = new HelpCommand();
            builder.addEventListener(help.registerCommand(help));
            builder.addEventListener(help.registerCommand(new InfoCommand()));
            builder.addEventListener(help.registerCommand(new KickCommand()));
            builder.addEventListener(help.registerCommand(new BanCommand()));
            builder.addEventListener(help.registerCommand(new LanguageCommand()));
            // Login to discord
            bot = builder.build();
            System.out.println(multiLang.getMessage("APP_LOGIN_SUCCESS"));
            log.info(multiLang.getMessage("APP_LOGIN_SUCCESS"));
        } catch (LoginException | NullPointerException e) {
            System.out.println(multiLang.getMessage("APP_LOGIN_ERR_1"));
            System.out.println(multiLang.getMessage("APP_LOGIN_ERR_2"));
            log.error(multiLang.getMessage("APP_LOGIN_ERR_1"));
            log.error(multiLang.getMessage("APP_LOGIN_ERR_2"));
            System.exit(ApplicationExitCodes.BAD_USERNAME_PASS_COMBO);
        } catch (IllegalArgumentException e) {
            System.out.println(multiLang.getMessage("APP_LOGIN_ERR_3"));
            log.error(multiLang.getMessage("APP_LOGIN_ERR_3"));
            System.exit(ApplicationExitCodes.NO_USERNAME_PASS_COMBO);
        }
    }
}
