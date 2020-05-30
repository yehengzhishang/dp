package org.yu.zz.spi;

import java.util.ServiceLoader;

public class SpiMain {
    public static void main(String[] args) {
        // 不建议，建议不使用
//        Iterator<SpiService> providers = Service.providers(SpiService.class);
//        while (providers.hasNext()) {
//            providers.next().run();
//        }
//        System.out.println("====================");
        ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);
        for (SpiService spiService : load) {
            spiService.run();
        }

    }
}
