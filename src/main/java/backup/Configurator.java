package backup;

import java.util.List;

public interface Configurator {
    void updateConfiguration() throws Exception;
    void updateConfiguration(String configFilePath) throws Exception;
    List<String> getBackupTargets();
}
