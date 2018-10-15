/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera.util.src_pd.com.android.camera.app;

import android.content.Context;
import android.net.Uri;

import com.android.camera.ImageTaskManager;

public class PanoramaStitchingManager implements ImageTaskManager {

    public PanoramaStitchingManager(Context ctx) {
    }

    @Override
    public void addTaskListener(TaskListener l) {
        // do nothing.
    }

    @Override
    public void removeTaskListener(TaskListener l) {
        // do nothing.
    }

    @Override
    public int getTaskProgress(Uri uri) {
        return -1;
    }
}
