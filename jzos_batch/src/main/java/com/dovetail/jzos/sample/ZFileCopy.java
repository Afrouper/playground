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

/**
 * Sample program that uses the ZFile class to copy an MVS dataset in 
 * record mode from DD INPUT to DD OUTPUT.
 * Note that "noseek" is used so that the file is opened in sequential 
 * mode, which dramatically increases I/O performance.
 * 
 * @see com.dovetail.jzos.ZFile
 */
public class ZFileCopy {
    public static void main(String[] args) throws Exception {
        ZFile zFileIn = new ZFile("//DD:INPUT", "rb,type=record,noseek");
        ZFile zFileOut = new ZFile("//DD:OUTPUT", "wb,type=record,noseek");
        System.out.println("Recfm=" + zFileIn.getRecfm());
        long count = 0;
        try {
            byte[] recBuf = new byte[zFileIn.getLrecl()];
            int nRead;
            while((nRead = zFileIn.read(recBuf)) > 0) {
            	zFileOut.write(recBuf, 0, nRead);
            	count++;
            };
            System.out.println("ZFileCopy: " + count + " records copied");
        } finally {
           zFileIn.close();
           zFileOut.close();
        }
    }
}
