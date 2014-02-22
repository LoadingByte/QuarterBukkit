/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.NoCommandFoundException;
import com.quartercode.quarterbukkit.api.exception.NoCommandPermissionException;
import com.quartercode.quarterbukkit.api.exception.NoDefaultCommandFoundException;

/**
 * This class is a {@link CommandExecutor} for easy creating commands like /command help 1 without using millions of ifs.
 * It's easy to use and created for a fast developement progress.
 */
public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {

    private final Plugin               plugin;
    private final List<CommandHandler> commandHandlers = new ArrayList<CommandHandler>();

    /**
     * Creates a new CommandExecutor which can be used as Bukkit-{@link CommandExecutor}.
     * It binds the defined commands automatically to this executor.
     * 
     * @param plugin The plugin for the CommandExecutor.
     * @param commands The commands this executor bind to.
     */
    public CommandExecutor(Plugin plugin, String... commands) {

        this.plugin = plugin;

        for (String command : commands) {
            Bukkit.getPluginCommand(command).setExecutor(this);
            Bukkit.getPluginCommand(command).setTabCompleter(this);
        }
    }

    /**
     * The main method called by the Bukkit-Command-System.
     * This is only for the Bukkit-System, don't call it manually!
     * 
     * @param sender The {@link CommandSender} who executed the command.
     * @param command The Bukkit-Command.
     * @param label The first command label.
     * @param arguments The unparsed (raw) arguments behind the first label (seperated with spaces).
     * @return If the command was executed (here always true).
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] arguments) {

        if (arguments.length > 0) {
            for (CommandHandler commandHandler : commandHandlers) {
                CommandInfo info = commandHandler.getInfo();

                for (String commandLabel : info.getLabels()) {
                    if (!commandLabel.equalsIgnoreCase("<empty>")) {
                        if (info.isIgnoreCase()) {
                            if (commandLabel.equalsIgnoreCase(arguments[0])) {
                                executeCommand(sender, commandHandler, label, arguments);
                                return true;
                            }
                        } else {
                            if (commandLabel.equals(arguments[0])) {
                                executeCommand(sender, commandHandler, label, arguments);
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            for (CommandHandler commandHandler : commandHandlers) {
                CommandInfo info = commandHandler.getInfo();

                for (String commandLabel : info.getLabels()) {
                    if (commandLabel.equalsIgnoreCase("<empty>")) {
                        executeCommand(sender, commandHandler, label, new String[0]);
                        return true;
                    }
                }
            }
        }

        if (arguments.length > 0) {
            Command command2 = new Command(sender, label, arguments[0], new ArrayList<String>(Arrays.asList(arguments)).subList(1, arguments.length).toArray(new String[arguments.length - 1]));
            ExceptionHandler.exception(new NoCommandFoundException(plugin, command2, this, "No command " + arguments[0] + " found"));
        } else {
            ExceptionHandler.exception(new NoDefaultCommandFoundException(plugin, new Command(sender, label, null), this, "No default command found"));
        }

        return true;
    }

    private void executeCommand(CommandSender sender, CommandHandler commandHandler, String rawLabel, String[] rawArguments) {

        Command command;
        if (rawArguments.length > 0) {
            command = new Command(sender, rawLabel, rawArguments[0], new ArrayList<String>(Arrays.asList(rawArguments)).subList(1, rawArguments.length).toArray(new String[rawArguments.length - 1]));
        } else {
            command = new Command(sender, rawLabel, null);
        }

        if (sender instanceof Player) {
            if (commandHandler.getInfo().getPermission() == null || commandHandler.getInfo().getPermission().isEmpty()) {
                commandHandler.execute(command);
            } else {
                if (sender.hasPermission(commandHandler.getInfo().getPermission())) {
                    commandHandler.execute(command);
                } else {
                    String commandDisplay = "/" + rawLabel;
                    commandDisplay += rawArguments.length > 0 ? " " + rawArguments[0] : "";
                    if (!ExceptionHandler.exception(new NoCommandPermissionException(plugin, commandHandler.getInfo().getPermission(), sender, command, sender.getName() + " has no permissions for command " + commandDisplay))) {
                        commandHandler.execute(command);
                    }
                }
            }
        } else {
            commandHandler.execute(command);
        }
    }

    /**
     * The tab completition method called by the Bukkit-Command-System.
     * This is only for the Bukkit-System, don't call it manually!
     * 
     * @param sender The {@link CommandSender} who executed the command.
     * @param command The Bukkit-Command.
     * @param alias The first command label.
     * @param arguments The unparsed (raw) arguments behind the first label (seperated with spaces).
     * @return All tab complete proposals.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] arguments) {

        List<String> proposals = new ArrayList<String>();

        if (arguments.length == 1) {
            for (CommandHandler commandHandler : commandHandlers) {
                for (String label : commandHandler.getInfo().getLabels()) {
                    for (String argument : arguments) {
                        if (!label.equalsIgnoreCase("<empty>") && label.toLowerCase().startsWith(argument.toLowerCase())) {
                            if (commandHandler.getInfo().getPermission() == null || commandHandler.getInfo().getPermission().isEmpty() || sender.hasPermission(commandHandler.getInfo().getPermission())) {
                                proposals.add(label);
                            }
                        }
                    }
                }
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (String argument : arguments) {
                    if (player.getName().toLowerCase().startsWith(argument.toLowerCase())) {
                        proposals.add(player.getName());
                    }
                }
            }
        }

        return proposals;
    }

    /**
     * Returns all registered command handlers as an unmodifiable {@link List}.
     * 
     * @return The registered command handlers.
     */
    public List<CommandHandler> getCommandHandlers() {

        return Collections.unmodifiableList(commandHandlers);
    }

    /**
     * Returns the {@link CommandHandler} which contains a defined label.
     * Throws an {@link IllegalStateException} if no {@link CommandHandler} with the label is registered.
     * 
     * @param label The command label.
     * @return The {@link CommandHandler}.
     */
    public CommandHandler getCommandHandler(String label) {

        for (CommandHandler commandHandler : commandHandlers) {
            boolean ignoreCase = commandHandler.getInfo().isIgnoreCase();
            for (String commandLabel : commandHandler.getInfo().getLabels()) {
                if (ignoreCase && commandLabel.equalsIgnoreCase(label)) {
                    return commandHandler;
                } else if (!ignoreCase && commandLabel.equals(label)) {
                    return commandHandler;
                }
            }
        }

        throw new IllegalStateException("No CommandHandler for " + label + " registered");
    }

    /**
     * Returns if a {@link CommandHandler} is registered.
     * Use this for checking before other {@link CommandHandler}-functions in this class, like get, register, unregister.
     * 
     * @param commandHandler The {@link CommandHandler} to check.
     * @return If the {@link CommandHandler} is registered.
     */
    public boolean containsCommandHandler(CommandHandler commandHandler) {

        return commandHandlers.contains(commandHandler);
    }

    /**
     * Returns if a {@link CommandHandler} with a defined label is registered.
     * Use this for checking before other {@link CommandHandler}-functions in this class, like get, register, unregister.
     * 
     * @param label The label to check.
     * @return If a {@link CommandHandler} with the label is registered.
     */
    public boolean containsCommandHandler(String label) {

        try {
            getCommandHandler(label);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * Registers a {@link CommandHandler}.
     * Throws an {@link IllegalStateException} if the {@link CommandHandler} is already registered or there's already a {@link CommandHandler} with a label of the one to register.
     * 
     * @param commandHandler The {@link CommandHandler} to register.
     */
    public void addCommandHandler(CommandHandler commandHandler) {

        if (commandHandlers.contains(commandHandler)) {
            throw new IllegalStateException("This CommandHandler is already registered");
        }
        for (String label : commandHandler.getInfo().getLabels()) {
            if (containsCommandHandler(label)) {
                throw new IllegalStateException("There's already a CommandHandler with the label " + label);
            }
        }

        commandHandlers.add(commandHandler);
    }

    /**
     * Unregisters a {@link CommandHandler}.
     * Throws an {@link IllegalStateException} if the {@link CommandHandler} isn't registered.
     * 
     * @param commandHandler The {@link CommandHandler} to unregister.
     */
    public void removeCommandHandler(CommandHandler commandHandler) {

        if (!commandHandlers.contains(commandHandler)) {
            throw new IllegalStateException("This CommandHandler is not registered");
        }

        commandHandlers.remove(commandHandler);
    }

    /**
     * Unregisters a {@link CommandHandler} which has this label.
     * Throws an {@link IllegalStateException} if no {@link CommandHandler} with the label is registered.
     * 
     * @param label The {@link CommandHandler} with this label to unregister.
     */
    public void removeCommandHandler(String label) {

        commandHandlers.remove(getCommandHandler(label));
    }

    @Override
    public int hashCode() {

        int prime = 31;
        int result = 1;
        result = prime * result + (commandHandlers == null ? 0 : commandHandlers.hashCode());
        result = prime * result + (plugin == null ? 0 : plugin.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CommandExecutor other = (CommandExecutor) obj;
        if (commandHandlers == null) {
            if (other.commandHandlers != null) {
                return false;
            }
        } else if (!commandHandlers.equals(other.commandHandlers)) {
            return false;
        }
        if (plugin == null) {
            if (other.plugin != null) {
                return false;
            }
        } else if (!plugin.equals(other.plugin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [plugin=" + plugin + ", commandHandlers=" + commandHandlers + "]";
    }

}
