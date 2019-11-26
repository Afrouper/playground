package de.repmek.jzos.sample;

import com.dovetail.jzos.ZFile;
import com.dovetail.jzos.ZUtil;


public class ZFilePrint {
    public static void main(String[] args) throws Exception {
        ZFile zFile = new ZFile("//DD:INPUT", "rb,type=record,noseek");
        try {
            byte[] recBuf = new byte[zFile.getLrecl()];
            int nRead;
            while((nRead = zFile.read(recBuf)) > 0) {
            	String line = new String(recBuf,0,nRead, ZUtil.getDefaultOutputEncoding());
                System.out.println(line);
            };
        } finally {
           zFile.close();
        }
    }
}
