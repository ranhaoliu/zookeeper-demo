package zookeeper.client;

public class MyConfig {
    private String key;
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyConfig() {
    }

    @Override
    public String toString() {
        return "MyConfig{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
