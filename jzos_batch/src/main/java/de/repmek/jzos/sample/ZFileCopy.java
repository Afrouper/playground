package de.repmek.jzos.sample;

import com.dovetail.jzos.ZFile;

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
