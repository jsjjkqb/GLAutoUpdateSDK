package com.ecarx.gl_autoupdatesdk.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ========================================
 * 作 者：zhaochong
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/27
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class UpdateExecutor implements IUpdateExecutor {

    private static ExecutorService pool;
    private static UpdateExecutor executor;

    private UpdateExecutor() {
        pool = Executors.newSingleThreadExecutor();
    }

    public synchronized static UpdateExecutor getInstance() {
        if (executor == null) {
            executor = new UpdateExecutor();
        }
        return executor;
    }

    @Override
    public void check(final UpdateWorker worker) {
        pool.execute(worker);
    }

}
