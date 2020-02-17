package fr.elusia.chatevent.event;

import java.util.List;

import org.bukkit.command.CommandSender;

import fr.elusia.chatevent.ChatEventPlugin;
import fr.elusia.chatevent.ChatEventTask;
import fr.elusia.chatevent.api.utils.Utils;
import fr.elusia.chatevent.api.utils.commands.CommandArgs;
import fr.elusia.chatevent.api.utils.commands.annontations.Command;
import fr.elusia.chatevent.event.ChatEventManager.Difficulty;

public class ChatEventCommand {
	
	private ChatEventManager manager = ChatEventPlugin.getInstance().getChatManager();
	
	@Command(name = "words.list", aliases = { "word.list" }, permission = "words.list", 
			description = "Voir la liste des mots dans la liste des mots de l'event.", usage = "/words list <difficulty|all>", inGameOnly = false)
	public void onListWord(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if(args.length() < 1) {
			player.sendMessage("§cUsage: /words list <difficulty|all>");
			return;
		}
		
		String diffS = args.getArgs(0);
		if (diffS.equalsIgnoreCase("all")) {
			for (Difficulty diff : Difficulty.values()) {
				player.sendMessage(manager.listWord(diff));
			}
			return;
		}
		
		Difficulty diff = Difficulty.getDifficultyByString(diffS);
		if (diff == null) {
			player.sendMessage("§cUsage: /words list <difficulty|all>");
			return;
		}
		
		player.sendMessage(manager.listWord(diff));
		return;
	}
	
	@Command(name = "words.add", aliases = { "word.add" }, permission = "words.add", 
			description = "Ajouter un mot dans la liste des mots de l'event.", usage = "/words add <difficulty> <word>", inGameOnly = false)
	public void onAddWord(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if(args.length() < 2) {
			player.sendMessage("§cUsage: /words add <difficulty> <word>");
			return;
		}
		
		Difficulty diff = Difficulty.getDifficultyByString(args.getArgs(0));
		if (diff == null) {
			player.sendMessage("§cUsage: /words add <difficulty> <word>");
			return;
		}
		
		List<String> words = manager.getWords().get(diff);
		if (words.contains(args.getArgs(1))) {
			player.sendMessage("§cErreur, le mot est déjà présent dans cette liste");
			return;
		}
		
		manager.addWord(diff, args.getArgs(1));
		player.sendMessage("§eVous venez d'ajouter le mot §a" + args.getArgs(1)
		+ " §eà la liste des mots de difficulté §a" + diff);
		return;
	}
	
	@Command(name = "words.remove", aliases = { "word.remove" }, permission = "words.remove", 
			description = "Supprimer un mot dans la liste des mots de l'event.", usage = "/words remove <difficulty> <word>", inGameOnly = false)
	public void onRemoveWord(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if(args.length() < 2) {
			player.sendMessage("§cUsage: /words remove <difficulty> <word>");
			return;
		}
		
		Difficulty diff = Difficulty.getDifficultyByString(args.getArgs(0));
		if (diff == null) {
			player.sendMessage("§cUsage: /words remove <difficulty> <word>");
			return;
		}
		
		List<String> words = manager.getWords().get(diff);
		if (!(words.contains(args.getArgs(1)))) {
			player.sendMessage("§cErreur, le mot n'est pas présent dans cette liste");
			return;
		}
		
		manager.removeWord(diff, args.getArgs(1));
		player.sendMessage("§eVous venez de supprimer le mot §a" + args.getArgs(1)
		+ " §eà la liste des mots de difficulté §c" + diff + "§e.");
		return;
	}
	
	@Command(name = "words.help", aliases = { "word.help" }, permission = "words.help", 
			description = "Voir la liste des commandes", usage = "/words help", inGameOnly = false)
	public void onHelpWord(CommandArgs args) {
		CommandSender player = args.getSender();
		
		player.sendMessage(Utils.LINE
				+ "§eListe des §ccommandes§e:\n"
				+ "§l§c• §e/words start\n"
				+ "§l§c• §e/words list §a<difficulty|all>\n"
				+ "§l§c• §e/words add §a<difficulty> <word>\n"
				+ "§l§c• §e/words remove §a<difficulty> <word>\n"
				+ Utils.LINE);
		
		return;
	}
	
	@Command(name = "words.start", aliases = { "word.start" }, permission = "words.start", 
			description = "Voir la liste des commandes", usage = "/words help", inGameOnly = false)
	public void onStartWord(CommandArgs args) {
		CommandSender player = args.getSender();
		ChatEventTask.start();
		player.sendMessage("§eVous venez de lancer l'évènement manuellement.");
		return;
	}
	
}
