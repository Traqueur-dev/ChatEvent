package fr.elusia.chatevent.api.utils.jsons;

import java.io.File;

import fr.elusia.chatevent.ChatEventPlugin;
import lombok.Getter;
import net.minecraft.util.com.google.gson.Gson;

public abstract class Saveable implements JsonPersist {

	public boolean needDir, needFirstSave;
	
	@Getter
	private ChatEventPlugin plugin;

	public Saveable(ChatEventPlugin plugin, String name) {
		this(plugin, name, false, false);
		this.plugin = plugin;
	}

	public Saveable(ChatEventPlugin plugin, String name, boolean needDir, boolean needFirstSave) {
		this.needDir = needDir;
		this.needFirstSave = needFirstSave;
		if (this.needDir) {
			if (this.needFirstSave) {
				this.saveData();
			} else {
				File directory = this.getFile();
				if (!directory.exists()) {
					try {
						directory.mkdir();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}

	public Gson getGson() {
		return ChatEventPlugin.getInstance().getGson();
	}
}
