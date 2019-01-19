package backup;


import org.json.JSONObject;

import javax.naming.ConfigurationException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileConfigurator implements Configurator {

    private final String[] APPLICABLE_CONFIGURATION_PLACES = {
            "./backup.conf",
            "/etc/backup.conf"
    };

    private List<String> backupTargets = new ArrayList<>();

    public void updateConfiguration() throws Exception {
        String configFile = searchDefaultConfigurationFile(APPLICABLE_CONFIGURATION_PLACES);
        this.updateConfiguration(configFile);
    }

    public void updateConfiguration(String configFilePath) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(configFilePath));
        JSONObject configuration = new JSONObject(new String(bytes));
        List targets = configuration.getJSONArray("targets").toList();
        setBackupTargets(targets);
    }

    private String searchDefaultConfigurationFile(String[] applicablePlaces) throws Exception {
        for (String confFile : applicablePlaces) {
            File file = new File(confFile);
            if (file.exists() && file.isFile()) {
                return file.getAbsolutePath();
            } else {
                System.out.printf("Can't find file %s\n", confFile);
            }
        }
        throw new ConfigurationException("Can't find any configuration file");
    }

    public List<String> getBackupTargets() {
        return backupTargets;
    }

    private void setBackupTargets(List<String> targets) {
        this.backupTargets = targets;
    }
}
