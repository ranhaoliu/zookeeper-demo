package zookeeper.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
      byte []data =  {123, 34, 107, 101, 121, 34, 58, 34, 110, 97, 109, 101, 34, 44, 34, 110, 97, 109, 101, 34, 58, 34, 122, 104, 97, 110, 103, 115, 97, 110, 34, 125};
//       String name ="1234";
//       byte[]b = name.getBytes();
////        byte []d={104, 101, 108, 108, 111};
//       ObjectMapper objectMapper = new ObjectMapper();
//      MyConfig originalMyConfig = objectMapper.readValue(new String(b),MyConfig.class);
//        System.out.println(originalMyConfig);
       String n = new String(data);
        System.out.println(n);
    }
}
