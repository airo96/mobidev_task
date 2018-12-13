package ua.i74.movieapp.utils;

import java.util.concurrent.Executor;

public class AppExecutor {
    private Executor networkExecutor;
    private Executor diskExecutor;
    private Executor mainThreadExecutor;

    public AppExecutor(Executor networkExecutor, Executor diskExecutor, Executor mainThreadExecutor) {
        this.networkExecutor = networkExecutor;
        this.diskExecutor = diskExecutor;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public Executor getNetworkExecutor() {
        return networkExecutor;
    }

    public Executor getDiskExecutor() {
        return diskExecutor;
    }

    public Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }
}
