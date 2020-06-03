package net.benjaminurquhart.condense;

import org.bukkit.plugin.java.JavaPlugin;

public class Condense extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("condense").setExecutor(new Condenser());
	}
}
