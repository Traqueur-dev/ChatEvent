package fr.elusia.chatevent.api.utils.jsons.adapters;

import fr.elusia.chatevent.ChatEventPlugin;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.TypeAdapter;

public abstract class GsonAdapter<T> extends TypeAdapter<T> {

	private ChatEventPlugin plugin;

	public GsonAdapter(ChatEventPlugin plugin) {
		this.plugin = plugin;
	}

	public Gson getGson() {
		return this.plugin.getGson();
	}
}
