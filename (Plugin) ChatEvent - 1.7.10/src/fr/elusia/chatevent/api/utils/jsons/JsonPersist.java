package fr.elusia.chatevent.api.utils.jsons;

import java.io.File;

public abstract interface JsonPersist {

	public abstract File getFile();

	public abstract void loadData();

	public abstract void saveData();

}
