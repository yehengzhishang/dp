package org.yu.zz.dagger;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

public class AndroidAac {
    public static final Application app = new Application();

    public static void main(String[] args) {

        new Activity().onCreate();
    }

    public interface IRepository {
        String getTag();
    }

    public interface ApiService {
        String requestTag();
    }

    @Component(modules = ViewModelModule.class, dependencies = AppComponent.class)
    public interface ActivityComponent {
        ViewModel getViewModel();
    }


    @Component(modules = AppModule.class)
    public interface AppComponent {
        Application getApp();
    }

    public static class Application {
        String getTagSecond() {
            return "2";
        }
    }

    public static class Activity {
        void onCreate() {
            ViewModel model1 = new ViewModel(new Repository(new ApiServiceImpl(), new DataSource(app)));
            model1.printTag();

            AndroidAac.ActivityComponent component = DaggerAndroidAac_ActivityComponent.builder()
                    .viewModelModule(new ViewModelModule())
                    .appComponent(DaggerAndroidAac_AppComponent.builder().appModule(new AppModule(app)).build())
                    .build();
            ViewModel model2 = component.getViewModel();
            model2.printTag();
        }
    }

    public static class ViewModel {
        private final IRepository mRepo;

        ViewModel(IRepository repo) {
            this.mRepo = repo;
        }

        void printTag() {
            System.out.println(mRepo.getTag());
        }
    }

    public static class Repository implements IRepository {
        private final ApiService mService;
        private final DataSource mDataSource;

        Repository(ApiService service, DataSource dataSource) {
            this.mService = service;
            this.mDataSource = dataSource;
        }

        @Override
        public String getTag() {
            return toString() + "==" + mService.requestTag() + mDataSource.getTag();
        }
    }

    public static class DataSource {
        private final Application app;

        DataSource(Application app) {
            this.app = app;
        }

        String getTag() {
            return app.getTagSecond() + "3";
        }
    }

    public static class ApiServiceImpl implements ApiService {
        @Override
        public String requestTag() {
            return "1";
        }
    }

    @Module
    public static class ViewModelModule {
        @Provides
        ApiService provideService() {
            return new ApiServiceImpl();
        }

        @Provides
        IRepository provideRepo(ApiService service, DataSource dataSource) {
            return new Repository(service, dataSource);
        }

        @Provides
        DataSource provideDataSource(Application app) {
            return new DataSource(app);
        }

        @Provides
        ViewModel provideViewModel(IRepository repo) {
            return new ViewModel(repo);
        }
    }

    @Module
    public static class AppModule {
        final Application application;

        AppModule(Application application) {
            this.application = application;
        }

        @Provides
        Application provideApplication() {
            return application;
        }
    }
}
