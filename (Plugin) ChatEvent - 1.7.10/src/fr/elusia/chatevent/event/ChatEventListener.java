package fr.elusia.chatevent.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.elusia.chatevent.ChatEventPlugin;
import fr.elusia.chatevent.ChatEventTask;
import fr.elusia.chatevent.api.utils.VaultUtils;
import fr.elusia.chatevent.config.ConfigManager;

public class ChatEventListener implements Listener {
	
	private ConfigManager manager = ChatEventPlugin.getInstance().getConfigManager();
	
	@EventHandler
	public void onMessageEvent(AsyncPlayerChatEvent event) {
		boolean wordIsSend = ChatEventTask.isWordIsSend();
		if (wordIsSend) {
			String word = ChatEventTask.getWord();
			long time = ChatEventTask.getTime();
			if (event.getMessage().equals(word)) {
				ChatEventTask.setWordIsSend(false);
				ChatEventTask.setWord(null);
				ChatEventTask.setTime(0l);
				time = System.currentTimeMillis() - time;
				Bukkit.broadcastMessage("§e" + event.getPlayer().getName() + " vient de §atrouver §ele mot §c" 
				+ word + " §een " + time/1000 + "§csecondes");
				VaultUtils.depositMoney(event.getPlayer(), manager.getRewardByDifficulty(ChatEventTask.getDiffOfWord()));
				return;
			}
		}
	}
	
}
