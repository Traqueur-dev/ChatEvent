package fr.elusia.chatevent.config;

import java.io.File;
import java.lang.reflect.Type;

import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;

import fr.elusia.chatevent.ChatEventPlugin;
import fr.elusia.chatevent.api.utils.jsons.DiscUtil;
import fr.elusia.chatevent.api.utils.jsons.Saveable;
import fr.elusia.chatevent.event.ChatEventManager.Difficulty;
import lombok.Getter;

public class ConfigManager extends Saveable {

	private @Getter Config config;
	
	public ConfigManager(ChatEventPlugin plugin) {
		super(plugin, "Config");
		this.config = initConfig();
	}
	
	private Config initConfig() {
		Config config = new Config();
		
		config.setDelayInSecondBetweenTwoGames(60 * 30);
		config.setRewardEasy(500.0f);
		config.setRewardNormal(750.0f);
		config.setRewardHard(1000.0f);
		
		return config;
	}
	
	@Override
	public File getFile() {return new File(this.getPlugin().getDataFolder(), "config.json");}

	@Override
	public void loadData() {
		String content = DiscUtil.readCatch(this.getFile());
		if (content != null) {
			Type type = new TypeToken<Config>() {}.getType();
			Config config = this.getGson().fromJson(content, type);
			this.config = config;
		}
	}

	@Override
	public void saveData() {DiscUtil.writeCatch(this.getFile(), this.getGson().toJson(this.config));}
	
	public void setEasyReward(float reward) {
		this.config.setRewardEasy(reward);
	}
	
	public void setNormalReward(float reward) {
		this.config.setRewardNormal(reward);
	}
	
	public void setHardReward(float reward) {
		this.config.setRewardHard(reward);
	}
	
	public float getRewardByDifficulty(Difficulty diff) {
		switch (diff) {
		case EASY:
			return this.config.getRewardEasy();
		case NORMAL:
			return this.config.getRewardNormal();
		case HARD:
			return this.config.getRewardHard();
		}
		return 0.0f;
	}
	
	/**
	 * @param delay in second.
	 * Any verification is done
	 **/
	public void setDelay(int delay) {
		this.config.setDelayInSecondBetweenTwoGames(delay);
	}
	
	public int getDelay() {
		return this.config.getDelayInSecondBetweenTwoGames();
	}
}
