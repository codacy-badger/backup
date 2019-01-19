package backup.configurator

import spock.lang.Specification

import backup.Configurator
import backup.FileConfigurator

import java.lang.reflect.Field

class ConfiguratorTest extends Specification {

    def "update configuration from defaults"() {
        setup:
        Field defaultPlaces = FileConfigurator.getDeclaredField("APPLICABLE_CONFIGURATION_PLACES")
        defaultPlaces.setAccessible(true)

        File configFile = new File(getClass().getClassLoader().getResource("test_configuration.json").getFile())
        String[] path = {configFile.getAbsolutePath()}

        Configurator configurator = new FileConfigurator()
        defaultPlaces.set(configurator, path)

        when:
        configurator.updateConfiguration(configFile.getAbsolutePath())

        then:
        !configurator.backupTargets.isEmpty()
        configurator.getBackupTargets().size() == 1
    }

    def "update configuration from custom file"() {
        setup:
        File configFile = new File(getClass().getClassLoader().getResource("test_configuration.json").getFile())
        Configurator configurator = new FileConfigurator()

        when:
        configurator.updateConfiguration(configFile.getAbsolutePath())

        then:
        !configurator.backupTargets.isEmpty()
        configurator.getBackupTargets().size() == 1
    }

    def "update configuration with non-existing config file"() {
        setup:
        Configurator configurator = new FileConfigurator()

        when:
        configurator.updateConfiguration()

        then:
        def exception = thrown(Exception)
        exception.getMessage() == "Can't find any configuration file"
    }
}
