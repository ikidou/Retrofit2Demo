/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ikidou.util;

import java.text.DecimalFormat;


public class FileSizeUtil {
    public static String toReadable(long size) {
        if (size <= 0) {
            return "0B";
        }
        if (size < 1024) {
            return size + "B";
        }
        String[] sizeUnits = new String[]{"B", "KiB", "MiB", "GiB", "TiB", "PiB"};
        int unitIndex = 0;
        long newSize = size;
        for (; unitIndex < sizeUnits.length - 1; unitIndex++) {
            size = size / 1024;
            if (size < 1000) {
                unitIndex++;
                break;
            } else {
                newSize = size;
            }
        }
        float result = newSize / 1024f;
        DecimalFormat df = new DecimalFormat("####.##");
        return df.format(result) + sizeUnits[unitIndex];
    }
}
