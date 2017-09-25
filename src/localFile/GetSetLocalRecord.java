package localFile;

import java.io.*;

public class GetSetLocalRecord {
    private File localRecordFile;

    // constructor
    public GetSetLocalRecord() {
        // create file
        this.localRecordFile = new File("localRecord.dat");

        if (!this.localRecordFile.exists()) {
            boolean createRecordFileSuccess = false;
            try {
                createRecordFileSuccess = this.localRecordFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!createRecordFileSuccess) {
                System.err.println("create record file failed");
            }

            // initial file content
            LocalRecordDataStructure localRecordDataStructure = new LocalRecordDataStructure();
            try {
                this.updateRecord(localRecordDataStructure);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRecord(LocalRecordDataStructure newRecord) throws IOException {
        FileOutputStream fos = new FileOutputStream(this.localRecordFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(newRecord);
    }

    public LocalRecordDataStructure readRecord() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(this.localRecordFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (LocalRecordDataStructure) ois.readObject();
    }
}
