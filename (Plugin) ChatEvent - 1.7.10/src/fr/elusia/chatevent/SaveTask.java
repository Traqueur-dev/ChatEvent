package fr.elusia.chatevent;

import org.bukkit.Bukkit;

public class SaveTask implements Runnable {

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		ChatEventPlugin.getInstance().getChatManager().saveData();
		ChatEventPlugin.getInstance().getConfigManager().saveData();
		time = System.currentTimeMillis() - time;
		Bukkit.broadcastMessage("§7[§eChatEvent§7] §eSauvegarde des données §6efféctués §e(§c"+ time + "§ems).");
	}
	
}
