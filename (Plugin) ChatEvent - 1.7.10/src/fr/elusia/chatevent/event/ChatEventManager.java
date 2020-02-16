package fr.elusia.chatevent.event;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;

import com.google.common.collect.Lists;

import fr.elusia.chatevent.ChatEventPlugin;
import fr.elusia.chatevent.api.utils.Utils;
import fr.elusia.chatevent.api.utils.jsons.DiscUtil;
import fr.elusia.chatevent.api.utils.jsons.Saveable;
import lombok.Getter;

public class ChatEventManager extends Saveable {

	private @Getter HashMap<Difficulty, List<String>> words;
	
	public ChatEventManager(ChatEventPlugin plugin) {
		super(plugin, "ChatEvent");
		registerHashMap();
	}
	
	private void registerHashMap() {
		this.words = new HashMap<>();
		words.put(Difficulty.EASY, Lists.newArrayList());
		words.put(Difficulty.NORMAL, Lists.newArrayList());
		words.put(Difficulty.HARD, Lists.newArrayList());
	}
	
	@Override
	public File getFile() {return new File(this.getPlugin().getDataFolder(), "words.json");}

	@Override
	public void loadData() {
		String content = DiscUtil.readCatch(this.getFile());
		if (content != null) {
			Type type = new TypeToken<HashMap<Difficulty, List<String>>>() {}.getType();
			HashMap<Difficulty, List<String>> words = this.getGson().fromJson(content, type);
			this.words = words;
		}
	}

	@Override
	public void saveData() {DiscUtil.writeCatch(this.getFile(), this.getGson().toJson(this.words));}

	public void addWord(Difficulty diff, String word) {this.words.get(diff).add(word);}
	
	public void removeWord(Difficulty diff, String word) {this.words.get(diff).remove(word);}
	
	public String listWord(Difficulty diff) {
		List<String> words = this.getWords().get(diff);
		StringBuilder builder = new StringBuilder();
		
		builder.append(Utils.LINE);
		builder.append("§eListe des §amots §ede catégorie §c" + diff + "§e:\n");
		for (String string : words) {
			builder.append("§c§l• §e"+ string + "\n");
		}
		builder.append(Utils.LINE);
		return builder.toString();
	}
	
	public enum Difficulty {
		EASY("easy", "facile"),
		NORMAL("normal", "moyen"),
		HARD("hard", "difficile"),
		;
		
		private @Getter String[] name;

		private Difficulty(String... name) {
			this.name = name;
		}
		
		public static Difficulty getDifficultyByString(String name) {
			return Arrays.asList(Difficulty.values()).stream().filter(
					d -> Arrays.asList(d.name).contains(name)).findAny().orElse(null);
		}
	}
	
}
