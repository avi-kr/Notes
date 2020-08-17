package com.abhishek.notes.util;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import org.junit.jupiter.api.extension.*;

/**
 * Created by Abhishek Kumar on 17/08/20.
 * (c)2020 VMock. All rights reserved.
 */
public class InstantExecutorExtension implements AfterEachCallback, BeforeEachCallback {


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(null);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance()
                .setDelegate(new TaskExecutor() {
                    @Override
                    public void executeOnDiskIO(@NonNull Runnable runnable) {
                        runnable.run();
                    }

                    @Override
                    public void postToMainThread(@NonNull Runnable runnable) {
                        runnable.run();
                    }

                    @Override
                    public boolean isMainThread() {
                        return true;
                    }
                });
    }
}