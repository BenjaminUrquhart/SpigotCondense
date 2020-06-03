package net.benjaminurquhart.condense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Condenser implements CommandExecutor {
	
	// Maps? What are those?
	public static List<ItemStack> condense(List<ItemStack> items) {
		List<ItemStack> stacks = new ArrayList<>();
		Set<ItemStack> toRemove = new HashSet<>();
		int count, max;
		
		ItemStack tmp;
		for(ItemStack stack : items) {
			if(stack == null) continue;
			
			max = stack.getMaxStackSize();
			count = stack.getAmount();
			for(ItemStack other : stacks) {
				if(other.isSimilar(stack) && other.getAmount() < other.getMaxStackSize()) {
					count += other.getAmount();
					toRemove.add(other);
				}
			}
			stacks.removeAll(toRemove);
			while(count > max) {
				tmp = stack.clone();
				tmp.setAmount(max);
				stacks.add(tmp);
				count -= max;
			}
			stack.setAmount(count);
			stacks.add(stack);
			
			toRemove.clear();
		}
		return stacks;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			// Yes yes command blocks are not the console, shut up
			sender.sendMessage("The console doens't have an inventory, silly");
		}
		else {
			Player player = (Player) sender;
			PlayerInventory inventory = player.getInventory();
			long start = System.currentTimeMillis();
			
			List<ItemStack> items = Condenser.condense(Arrays.asList(inventory.getStorageContents()));
			inventory.setStorageContents(Condenser.condense(items).toArray(ItemStack[]::new));
			sender.sendMessage("Done in " + (System.currentTimeMillis()-start) + " ms");
		}
		return true;
	}
}
