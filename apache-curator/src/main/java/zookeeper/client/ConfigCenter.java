package zookeeper.client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ConfigCenter {
    private final static String CONNECT_STR = "192.168.186.134";
    private final static Integer SESSION_TIMEOUT = 30*1000;
    private static ZooKeeper zooKeeper = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.None
                        && watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    log.info(("连接已建立"));
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        MyConfig myConfig = new MyConfig();
        myConfig.setKey("name");
        myConfig.setName("zhangsan");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);
        zooKeeper.create("/myconfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeDataChanged
                        && watchedEvent.getPath() != null && watchedEvent.getPath().equals("/myconfig")) {
                    log.info("PATH:{} 发生了变化", watchedEvent.getPath());
                    try {
                        //再次监听,达到循环监听的效果
                      byte []data =  zooKeeper.getData("/myconfig", this, null);
                        MyConfig newConfig = objectMapper.readValue(new String(data),MyConfig.class);
                        log.info("目前的数据是:{}",newConfig);
                    } catch (KeeperException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JsonMappingException e) {
                        throw new RuntimeException(e);
                    } catch (JsonParseException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

    };

            byte[] data = zooKeeper.getData("/myconfig", watcher, null);
            MyConfig originalMyConfig = objectMapper.readValue(new String(data), MyConfig.class);
            log.info("原始数据：{}",originalMyConfig.toString());
            //暂停
            Thread.sleep(1000000000);
        }

}
