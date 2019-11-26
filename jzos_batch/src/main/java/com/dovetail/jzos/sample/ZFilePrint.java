/*
 * Copyright 2004 Dovetailed Technologies, LLC.
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
package com.dovetail.jzos.sample;

import com.dovetail.jzos.ZFile;
import com.dovetail.jzos.ZUtil;

/**
 * Sample program that prints an EBCDIC MVS dataset pointed to by //SYSIN DD
 * to System.out (stdout).
 * The dataset is opened using the ZFile class in record mode.
 * Note that "noseek" is used so that the file is opened in sequential 
 * mode, which dramatically increases I/O performance.
 * 
 * @see com.dovetail.jzos.ZFile
 */
public class ZFilePrint {
    public static void main(String[] args) throws Exception {
        ZFile zFile = new ZFile("//DD:INPUT", "rb,type=record,noseek");
        try {
            byte[] recBuf = new byte[zFile.getLrecl()];
            int nRead;
            while((nRead = zFile.read(recBuf)) > 0) {
            	String line = new String(recBuf,0,nRead, 
            							ZUtil.getDefaultOutputEncoding());
                System.out.println(line);
            };
        } finally {
           zFile.close();
        }
    }
}
