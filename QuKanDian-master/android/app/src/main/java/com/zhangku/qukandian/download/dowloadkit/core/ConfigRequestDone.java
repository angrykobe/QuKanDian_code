package com.zhangku.qukandian.download.dowloadkit.core;

import com.zhangku.qukandian.download.dowloadkit.query.DownloadHandle;

import java.io.File;


/**
 * Created by hocgin on 2017/9/25.
 */

public interface ConfigRequestDone {
	DownloadHandle download();
	
	DownloadHandle download(String noticeTitle,
                            String noticeDescription);

	DownloadHandle download(String noticeTitle,
                            String noticeDescription,
                            File destinationFile);
	
}
