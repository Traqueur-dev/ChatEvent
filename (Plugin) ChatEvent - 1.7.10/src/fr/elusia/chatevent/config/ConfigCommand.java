package fr.elusia.chatevent.config;

import org.bukkit.command.CommandSender;

import fr.elusia.chatevent.ChatEventPlugin;
import fr.elusia.chatevent.ChatEventTask;
import fr.elusia.chatevent.api.utils.Utils;
import fr.elusia.chatevent.api.utils.commands.CommandArgs;
import fr.elusia.chatevent.api.utils.commands.annontations.Command;
import fr.elusia.chatevent.event.ChatEventManager.Difficulty;

public class ConfigCommand {
	
	private ConfigManager manager = ChatEventPlugin.getInstance().getConfigManager();
	
	@Command(name = "config.setreward", aliases = { "config.reward", "conf.setreward", "conf.reward" }, permission = "config.modify", 
			description = "Modifier les rewards de l'event", usage = "/config setreward <difficulty> <amount>", inGameOnly = false)
	public void onConfigReward(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if (args.length() < 2) {
			player.sendMessage("§cUsage: /config setreward <difficulty> <amount>");
			return;
		}
		
		Difficulty diff = Difficulty.getDifficultyByString(args.getArgs(0));
		if (diff == null) {
			player.sendMessage("§cUsage: /config setreward <difficulty> <amount>");
			return;
		}
		try {
			float amount = Float.parseFloat(args.getArgs(1));
			switch (diff) {
			case EASY:
				manager.setEasyReward(amount);
				break;
			case NORMAL:
				manager.setNormalReward(amount);
				break;
			case HARD:
				manager.setHardReward(amount);
				break;
			}
			player.sendMessage("§eVous venez de définir la §arécompense §ede la catégorie §c" + diff + "§e à " + amount + "$.");
			return;
		} catch (Exception e) {
			player.sendMessage("§cErreur, veuillez entrer une quantité chiffré.");
			return;
		}
		
	}
	
	@Command(name = "config.setdelay", aliases = { "config.delay", "conf.delay", "conf.setdelay" }, permission = "config.modify", 
			description = "Modifier le delay entre chaque event.", usage = "/config setdelay <delay>", inGameOnly = false)
	public void onConfigDelay(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if (args.length() < 1) {
			player.sendMessage("§cUsage: /config setdelay <delay>");
			return;
		}
		
		try {
			int delay = Integer.parseInt(args.getArgs(0));
			manager.setDelay(delay);
			ChatEventPlugin.getInstance().getServer().getScheduler().cancelTask(ChatEventPlugin.getInstance().getTask());
			ChatEventPlugin.getInstance().setTask(ChatEventPlugin.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(ChatEventPlugin.getInstance(), new ChatEventTask(), this.manager.getDelay() * 20, this.manager.getDelay() * 20));
			player.sendMessage("§eVous venez de définir le §adélais §eentre chaque event à §c" + delay + " §esecondes.");
			return;
		} catch (Exception e) {
			player.sendMessage("§cErreur, veuillez entrer une quantité chiffré.");
			return;
		}
		
	}
	
	@Command(name = "config", aliases = { "conf", "configuration" }, permission = "config.view", 
			description = "Voir la configuration.", usage = "/configuration", inGameOnly = false)
	public void onConfig(CommandArgs args) {
		CommandSender player = args.getSender();
		Config config = manager.getConfig();
		player.sendMessage(Utils.LINE
				+ "§eConfiguration:\n"
				+ "§l§c• §eRécompense niveau §afacile§e: " + config.getRewardEasy() + "$\n"
				+ "§l§c• §eRécompense niveau §amoyen§e: " + config.getRewardNormal() + "$\n"
				+ "§l§c• §eRécompense niveau §adifficile§e: " + config.getRewardHard() + "$\n"
				+ "§l§c• §eDélais entre chaque event: " + config.getDelayInSecondBetweenTwoGames() + " §csecondes§e.\n"
				+ Utils.LINE);
		return;
	}
	
	@Command(name = "config.help", aliases = { "conf.help", "configuration.help" }, permission = "config.help", 
			description = "Voir la liste des commandes de configuration.", usage = "/configuration help", inGameOnly = false)
	public void onConfigHelp(CommandArgs args) {
		CommandSender player = args.getSender();

		player.sendMessage(Utils.LINE
				+ "§eListe des §ccommandes§e:\n"
				+ "§l§c• §e/config\n"
				+ "§l§c• §e/config setreward §a<difficulty> <amount>\n"
				+ "§l§c• §e/config setdelay §a<delay>\n"
				+ Utils.LINE);
		return;
	}
	
	
}
