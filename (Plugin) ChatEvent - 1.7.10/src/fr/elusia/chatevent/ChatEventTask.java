package fr.elusia.chatevent;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.elusia.chatevent.api.utils.Utils;
import fr.elusia.chatevent.event.ChatEventManager;
import fr.elusia.chatevent.event.ChatEventManager.Difficulty;
import lombok.Getter;
import lombok.Setter;

public class ChatEventTask implements Runnable {

	private static ChatEventManager manager = ChatEventPlugin.getInstance().getChatManager();
	private static @Getter @Setter boolean wordIsSend = false;
	private static @Getter @Setter String word = null;
	private static @Getter @Setter long time = 0l;
	private static @Getter @Setter Difficulty diffOfWord = null;
	
	
	public static void start() {
		Difficulty difficulties[] = Difficulty.values();
		int rand = new Random().nextInt(difficulties.length);
		Difficulty difficulty = difficulties[rand];
		List<String> words = manager.getWords().get(difficulty);
		
		if (words.isEmpty() || words.size() == 0) {
			for (Player p : Utils.getOnlinePlayers()) {
				if (p.hasPermission("words.list")) {
					p.sendMessage("§cErreur, lors du choix du mot. La liste est vide");
				}
			}
			return;
		}
		
		int randWord = new Random().nextInt(words.size());
		String word = words.get(randWord);
		Bukkit.broadcastMessage("§eSoyez le premier à reécrire ce mot: §c" + word 
				+ "\n§eVous gagnerez §a" + ChatEventPlugin.getInstance().getConfigManager().getRewardByDifficulty(difficulty) + "§e$.");
		ChatEventTask.word = word;
		ChatEventTask.wordIsSend = true;
		ChatEventTask.time = System.currentTimeMillis();
		ChatEventTask.diffOfWord = difficulty;
		return;
	}
	
	@Override
	public void run() {
		start();
	}

}
