package fr.elusia.chatevent;

import java.lang.reflect.Modifier;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import fr.elusia.chatevent.api.utils.commands.CommandFramework;
import fr.elusia.chatevent.api.utils.jsons.JsonPersist;
import fr.elusia.chatevent.config.ConfigCommand;
import fr.elusia.chatevent.config.ConfigManager;
import fr.elusia.chatevent.event.ChatEventCommand;
import fr.elusia.chatevent.event.ChatEventListener;
import fr.elusia.chatevent.event.ChatEventManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

public class ChatEventPlugin extends JavaPlugin {

	private static @Getter ChatEventPlugin instance;
	private @Getter Gson gson;
	
	private CommandFramework framework;
	private @Getter ChatEventManager chatManager;
	private @Getter ConfigManager configManager;
	
	private List<JsonPersist> persists;
	
	private @Getter @Setter int task;
	
	@Override
	public void onEnable() {
		instance = this;
		this.gson = this.createGsonBuilder().create();
		this.persists = Lists.newArrayList();
		this.chatManager = new ChatEventManager(this);
		this.configManager = new ConfigManager(this);
		
		this.framework = new CommandFramework(this);
		this.framework.registerCommands(new ChatEventCommand());
		this.framework.registerCommands(new ConfigCommand());
		this.framework.registerHelp();

		this.registerListener(new ChatEventListener());
		this.registerPersist(this.chatManager);
		this.registerPersist(this.configManager);
		
		this.loadPersists();
	
		this.task = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ChatEventTask(), this.configManager.getDelay() * 20, this.configManager.getDelay() * 20);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(), 20, 20 * 30 * 60);
	}

	public void registerPersist(JsonPersist persist) {
		this.persists.add(persist);
	}

	public void registerListener(Listener listener) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(listener, this);
	}

	public void loadPersists() {
		for (JsonPersist persist : this.persists) {
			persist.loadData();
		}
	}

	public void savePersists() {
		for (JsonPersist persist : this.persists) {
			persist.saveData();
		}
	}

	private GsonBuilder createGsonBuilder() {
		GsonBuilder ret = new GsonBuilder();

		ret.setPrettyPrinting();
		ret.disableHtmlEscaping();
		ret.excludeFieldsWithModifiers(Modifier.TRANSIENT);

		return ret;
	}
	
	@Override
	public void onDisable() {
		this.savePersists();
		super.onDisable();
	}

}
