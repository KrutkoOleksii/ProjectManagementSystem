package ua.goit.util;

import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ScriptExecutor {
    private final static String PACKAGE_NAME = "migration";

    @SneakyThrows
    public static void start() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        List<String> scripts = sortScripts(getScripts(PACKAGE_NAME));
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        for (String script : scripts) {
            try(Reader reader = new InputStreamReader(ScriptExecutor.class.getClassLoader().getResourceAsStream(script))) {
                scriptRunner.runScript(reader);
            }
        }
    }

    private static Set<String> getScripts(String packageName) {
        final Reflections reflections = new Reflections(packageName, new ResourcesScanner());
        return reflections.getResources(Pattern.compile(".*\\.sql"));
    }

    private static List<String> sortScripts(Iterable<String> scripts) {
        return StreamSupport.stream(scripts.spliterator(),false)
                .sorted((script1,script2) -> {
                    final Integer s1 = parseScriptName(script1);
                    final Integer s2 = parseScriptName(script2);
                    if (s1==null && s2==null) return 0;
                    if (s1==null) return  1;
                    if (s2==null) return -1;
                    return s1.compareTo(s2);
                })
                .collect(Collectors.toList());
    }

    private static Integer parseScriptName(String scriptName) {
        final String nameWithoutPackage = scriptName.substring(PACKAGE_NAME.length()+1,scriptName.length());
        return Integer.valueOf(nameWithoutPackage.split("_")[0]);
    }

}
