package com.example.demo.testgradle.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by idea on 2017/3/16.
 */
public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.demo.testgradle.app.APP",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}