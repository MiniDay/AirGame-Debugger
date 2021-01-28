package net.airgame.bukkit.debug;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.util.Scanner;

public final class DebugPlugin extends JavaPlugin {
    private ScriptEngine engine;

    public static void main(String[] args) {
    }

    @Override
    public void onEnable() {
        if (getDataFolder().mkdirs()) {
            System.out.println("创建插件存档文件夹.");
        }
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final ClassLoader cl = this.getClass().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
        Thread.currentThread().setContextClassLoader(loader);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            final File file = new File(this.getDataFolder(), "debug.js");
            final Scanner scanner = new Scanner(file);
            final StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            final String s = builder.toString();
            System.out.println(this.engine.eval(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

