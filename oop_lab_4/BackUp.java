import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BackUp {
    //fields of our backup
    List<RestorePoint> restorePoints = new ArrayList<>();
    List<FileInfo> filesToBackup = new ArrayList<>();
    Date creationDate;
    long size;
    ClearAlgo clearAlgo;
    private int idsForRestorePoints = 0;

    //Constructor
    BackUp() {
        creationDate = new Date();
    }

    //methods
    void addFile(Path path, Path pathToStore) throws IOException { //here we add files to filestobackup
        long size = Files.size(path);
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime date = attr.creationTime();
        var cur = new FileInfo(path, size, date);
        cur.setSaveLocationPath(pathToStore);
        if (filesToBackup.contains(cur)) {
            throw new FileAlreadyReadyForBackupException();
        } else {
            filesToBackup.add(cur);
        }
    }
    void addFile(Path path) throws IOException { //here we add files to filestobackup
        long size = Files.size(path);
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime date = attr.creationTime();
        var cur = new FileInfo(path, size, date);
        if (filesToBackup.contains(cur)) {
            throw new FileAlreadyReadyForBackupException();
        } else {
            filesToBackup.add(cur);
        }
    }
    void removeFile(Path path) { //here we remove from filestobackup
        for (int i = 0; i < filesToBackup.size(); i++) {
            var cur = filesToBackup.get(i);
            if (cur.getPath() == path) {
                filesToBackup.remove(cur);
                return;
            }
        }
        throw new NoSuchFileForBackupException();
    }
    void commitBackup() { //here we commit ordinary backup
        if (filesToBackup.isEmpty()) {
            throw new NoFilesToBackUpException();
        } else {
            List<FileInfo> kek = new ArrayList<>();
            kek.addAll(filesToBackup);
            var newRestorePoint = new RestorePoint(idsForRestorePoints++, kek);
            restorePoints.add(newRestorePoint);
            size += newRestorePoint.getSize();
            if (clearAlgo != null) {
                clearenceAlgo();
            }
        }
    }
    void commitIncrementalBackup() { //here we commit incrementalBackup
        if (restorePoints.isEmpty()) {
            throw new NoRestorePointFromWhereToCreateIncrBackupException();
        } else {
            if (filesToBackup.isEmpty()) {
                throw new NoFilesToBackUpException();
            } else {
                //here we will calc our delta
                List<FileInfo> kek = new ArrayList<>();
                kek.addAll(filesToBackup);
                var previousPoint = restorePoints.get(restorePoints.size() - 1);
                var filesFromPrevPoint = previousPoint.getListOfFiles();
                List<Path> oldFilePaths = new ArrayList<>();
                for (int i = 0; i < filesFromPrevPoint.size(); i++) {
                    oldFilePaths.add(filesFromPrevPoint.get(i).getPath());
                }
                List<FileInfo> deltaFiles = new ArrayList<>();
                for (int i = 0; i < kek.size(); i++) {
                    var newFilePath = kek.get(i).getPath();
                    if (!oldFilePaths.contains(newFilePath)) {
                        deltaFiles.add(kek.get(i));
                    }
                }
                if (deltaFiles.isEmpty()) {
                    throw new AllFilesAreTheSameException();
                } else {
                    var newIncrRestorePoint = new RestorePoint(idsForRestorePoints++, deltaFiles);
                    newIncrRestorePoint.setFather(previousPoint);
                    previousPoint.setSon(newIncrRestorePoint);
                    restorePoints.add(newIncrRestorePoint);
                    size += newIncrRestorePoint.getSize();
                    if (clearAlgo != null) {
                        clearenceAlgo();
                    }
                }
            }
        }

    }
    String showBackUpState() {
        String lul = "Total size of backup: " + size + "\n";
        lul += "Creation date: " +  creationDate.toString() + "\n";
        for (int i = 0; i < restorePoints.size(); i++) {
            lul += restorePoints.get(i).getRestorePointInfo();
        }
        return lul;
    }
    void clear() {
        filesToBackup = new ArrayList<>();
    }
    void setClearAlgo(ClearAlgo hb) {
        clearAlgo = hb;
        clearenceAlgo();
    }
    void clearenceAlgo() {
        clearAlgo.clear(restorePoints);
        recalcBackupSize();
    }
    void recalcBackupSize() {
        long newsize = 0;
        for (int i = 0; i < restorePoints.size(); i++) {
            newsize += restorePoints.get(i).getSize();
        }
        size = newsize;
    }
    long getSize() {
        return size;
    }
}

class FileInfo {
    private Path initFilePath;
    private Path saveLocationPath;
    private long filesize;
    private FileTime filecreationdate;
    private Date backupDate;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
    FileInfo(Path path, long size, FileTime date) {
        this.initFilePath = path;
        this.filesize = size;
        this.filecreationdate = date;
        saveLocationPath = path;
    }
    long getFileSize(){
        return filesize;
    }
    FileTime getFileTime(){
        return filecreationdate;
    }
    Path getPath(){
        return initFilePath;
    }
    Path getSaveLocationPath() {
        return saveLocationPath;
    }

    public void setSaveLocationPath(Path saveLocationPath) {
        String extension = "";

        int i = saveLocationPath.toString().lastIndexOf('.');
        if (i > 0) {
            extension = saveLocationPath.toString().substring(i+1);
        }
        var filename = saveLocationPath.getFileName().toString();
        var kek = saveLocationPath.getFileName().toString();// + filecreationdate.toString();
        var finale = saveLocationPath.toString();
        finale = finale.substring(0, finale.length() - kek.length());
        kek = dateFormat.format(backupDate);
        finale += filename.substring(0, filename.indexOf('.'));
        finale += "(" + kek + ")";
        finale += "." + extension;
        this.saveLocationPath = Path.of(finale);
    }

    public void setDate(Date backupDate) {
        this.backupDate = backupDate;
        setSaveLocationPath(saveLocationPath);
    }
}

class RestorePoint {
    //fields of restore point
    List<FileInfo> listOfFiles;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    Date creationDate;
    long size;
    RestorePoint Son;
    RestorePoint Father;
    int ID;

    //constructor
    RestorePoint(int ID, List<FileInfo> filesToBackup) {
        this.ID = ID;
        listOfFiles = filesToBackup;
        creationDate = new Date();
        long sz = 0;
        for (int i = 0; i < filesToBackup.size(); i++) {
            filesToBackup.get(i).setDate(creationDate);
            sz += filesToBackup.get(i).getFileSize();
        }
        size = sz;
    }

    //methods
    public long getSize() {
        return size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public List<FileInfo> getListOfFiles() {
        return listOfFiles;
    }

    public void setFather(RestorePoint father) {
        Father = father;
    }

    public void setSon(RestorePoint son) {
        Son = son;
    }

    public RestorePoint getFather() {
        return Father;
    }

    public RestorePoint getSon() {
        return Son;
    }

    String getRestorePointInfo(){
        String ans = "-Id of this restore point: " + ID + "\n";
        ans += "---Total size of restore point: " + size + "\n";
        ans += "---Date of creation of this restore point: " + dateFormat.format(creationDate) + "\n";
        ans += "---List of files in this restore point with some attr:\n";
        //ans += "\n";
        for (int i = 0; i < listOfFiles.size(); i++) {
            String cur = "";
            cur += "-----Original File path: " + listOfFiles.get(i).getPath() + "\n";
            cur += "-----Backup File path: " + listOfFiles.get(i).getSaveLocationPath() + "\n";
            cur += "-----Filesize: " + listOfFiles.get(i).getFileSize() + " bytes" + "\n";
            cur += "-----Creation Date: " + dateFormat.format(listOfFiles.get(i).getFileTime().toMillis()) + "\n";
            cur += "---------\n";
            ans += cur;
        }
        return ans;
    }

    public int getID() {
        return ID;
    }
}

interface ClearAlgo{
    default void clear(List<RestorePoint> restorePoints){}
    default List<RestorePoint> clearforhybrid(List<RestorePoint> restorePoints){
        return restorePoints;
    }
}

class AmountOfPointsAlgo implements ClearAlgo {
    private int pointamounts;
    AmountOfPointsAlgo(int n) {
        pointamounts = n;
    }
    public void clear(List<RestorePoint> restorePoints) {
        if (restorePoints.size() > pointamounts) {
            int amtoelemenate = restorePoints.size() - pointamounts;
            while (amtoelemenate != 0) {
                var cur = restorePoints.get(amtoelemenate - 1);
                if (restorePoints.get(amtoelemenate - 1).getSon() != null) {
                    restorePoints.remove(amtoelemenate - 1);
                    amtoelemenate--;
                } else {
                    //int curam = amtoelemenate;
                    int sum = 0;
                    var kek = restorePoints.get(amtoelemenate - 1);
                    while (kek.getSon() != null) {
                        kek = kek.getSon();
                        sum++;
                    }
                    if (sum > amtoelemenate) {
                        throw new CanNotElemenateSinceHasSonsException();
                    } else {
                        kek = restorePoints.get(amtoelemenate - 1);
                        while (kek.getSon() != null) {
                            var temp = kek.getSon();
                            //kek = kek.getSon();
                            restorePoints.remove(kek);
                            kek = temp;
                        }
                        //curam -= sum;
                        amtoelemenate -= sum;
                    }
                }
                restorePoints.remove(amtoelemenate - 1);
                amtoelemenate--;
            }
        } else {
            return;
        }
    }
    public List<RestorePoint> clearforhybrid(List<RestorePoint> restorePoints) {
        List<RestorePoint> pointsToRemove = new ArrayList<>();
        if (restorePoints.size() > pointamounts) {
            int amtoelemenate = restorePoints.size() - pointamounts;
            while (amtoelemenate != 0) {
                var cur = restorePoints.get(amtoelemenate - 1);
                if (restorePoints.get(amtoelemenate - 1).getSon() != null) {
                    restorePoints.add(restorePoints.get(amtoelemenate - 1));
                    //restorePoints.remove(amtoelemenate - 1);
                    amtoelemenate--;
                } else {
                    //int curam = amtoelemenate;
                    int sum = 0;
                    var kek = restorePoints.get(amtoelemenate - 1);
                    while (kek.getSon() != null) {
                        kek = kek.getSon();
                        sum++;
                    }
                    if (sum > amtoelemenate) {
                        throw new CanNotElemenateSinceHasSonsException();
                    } else {
                        kek = restorePoints.get(amtoelemenate - 1);
                        while (kek.getSon() != null) {
                            var temp = kek.getSon();
                            //kek = kek.getSon();
                            //restorePoints.remove(kek);
                            pointsToRemove.add(kek);
                            kek = temp;
                        }
                        //curam -= sum;
                        amtoelemenate -= sum;
                    }
                }
                pointsToRemove.add(restorePoints.get(amtoelemenate - 1));
                //restorePoints.remove(amtoelemenate - 1);
                amtoelemenate--;
            }
        }
        return pointsToRemove;
    }

}

class SizeAlgo implements ClearAlgo {
    private long size;
    private long totalbackupsize;

    SizeAlgo(long n, long totalbackupsize) {
        size = n;
        this.totalbackupsize = totalbackupsize;
    }

    public void clear(List<RestorePoint> restorePoints) {
        while (totalbackupsize > size) {
            for (int i = 0; i < restorePoints.size(); i++) {
                var curpoint = restorePoints.get(i);
                long curpointsize = curpoint.getSize();
                if (totalbackupsize > size) {
                    if (curpoint.getSon() == null) {
                        restorePoints.remove(curpoint);
                        totalbackupsize -= curpointsize;
                    } else {
                        long totalsize = curpointsize;
                        var kek = curpoint.getSon();
                        while (kek.getSon() != null) {
                            totalsize += kek.getSize();
                            var tmp = kek.getSon();
                            restorePoints.remove(kek);
                            kek = tmp;
                        }
                        totalbackupsize -= totalsize;
                    }
                }
            }
        }
    }
    public List<RestorePoint> clearforhybrid(List<RestorePoint> restorePoints) {
        List<RestorePoint> pointsToRemove = new ArrayList<>();
        while (totalbackupsize > size) {
            for (int i = 0; i < restorePoints.size(); i++) {
                var curpoint = restorePoints.get(i);
                long curpointsize = curpoint.getSize();
                if (totalbackupsize > size) {
                    if (curpoint.getSon() == null) {
                        //restorePoints.remove(curpoint);
                        pointsToRemove.add(curpoint);
                        totalbackupsize -= curpointsize;
                    } else {
                        long totalsize = curpointsize;
                        var kek = curpoint.getSon();
                        while (kek.getSon() != null) {
                            totalsize += kek.getSize();
                            var tmp = kek.getSon();
                            //restorePoints.remove(kek);
                            pointsToRemove.add(kek);
                            kek = tmp;
                        }
                        totalbackupsize -= totalsize;
                    }
                }
            }
        }
        return pointsToRemove;
    }
    void renewSize(long sz) {
        this.totalbackupsize = sz;
    }

    public long getSize() {
        return size;
    }
}
class DateAlgo implements ClearAlgo {
    private Date date;

    DateAlgo(Date date) {
        this.date = date;
    }

    public void clear(List<RestorePoint> restorePoints) {
        for (int i = 0; i < restorePoints.size(); i++) {
            var curPoint = restorePoints.get(i);
            if (curPoint.getCreationDate().before(date)) {
                if (curPoint.getSon() == null) {
                    restorePoints.remove(curPoint);
                } else {
                    var kek = curPoint.getSon();
                    while (kek != null) {
                        var tmp = kek.getSon();
                        restorePoints.remove(kek);
                        kek = tmp;
                    }
                }
            }

        }

    }
    public List<RestorePoint> clearforhybrid(List<RestorePoint> restorePoints) {
        List<RestorePoint> pointsToRemove = new ArrayList<>();
        for (int i = 0; i < restorePoints.size(); i++) {
            var curPoint = restorePoints.get(i);
            if (curPoint.getCreationDate().before(date)) {
                if (curPoint.getSon() == null) {
                    pointsToRemove.add(curPoint);
                    //restorePoints.remove(curPoint);
                } else {
                    var kek = curPoint.getSon();
                    while (kek != null) {
                        var tmp = kek.getSon();
                        pointsToRemove.add(kek);
                        //restorePoints.remove(kek);
                        kek = tmp;
                    }
                }
            }

        }
        return pointsToRemove;
    }
}

class Hybrid implements ClearAlgo {
    enum HybridLimits {
        ONE,
        ALL;
    }
    ClearAlgo AmountOfPointsAlgo = null;
    ClearAlgo dateAlgo = null;
    ClearAlgo sizeAlgo = null;
    HybridLimits hybridLimit;
    Hybrid(HybridLimits type) {
        hybridLimit = type;
    }

    @Override
    public void clear(List<RestorePoint> restorePoints) {
        if (hybridLimit == HybridLimits.ONE) {
            if (dateAlgo != null && !dateAlgo.clearforhybrid(restorePoints).isEmpty()) {
                    dateAlgo.clear(restorePoints);
            }
            else if (AmountOfPointsAlgo != null && !AmountOfPointsAlgo.clearforhybrid(restorePoints).isEmpty()) {
                    AmountOfPointsAlgo.clear(restorePoints);
            }
            else if (sizeAlgo != null && !sizeAlgo.clearforhybrid(restorePoints).isEmpty()) {
                    sizeAlgo.clear(restorePoints);
            }
        } else if (hybridLimit == HybridLimits.ALL) {
            List<RestorePoint> kek1 = null;
            List<RestorePoint> kek2 = null;
            List<RestorePoint> kek3 = null;

            if (dateAlgo != null) {
                kek1 = dateAlgo.clearforhybrid(restorePoints);
            }
            if (AmountOfPointsAlgo != null) {
                kek2 = AmountOfPointsAlgo.clearforhybrid(restorePoints);
            }
            if (sizeAlgo != null) {
                kek3 = sizeAlgo.clearforhybrid(restorePoints);
            }
            if (kek1 != null && kek2 != null && kek3 != null) {
                for (int i = 0; i < kek1.size(); i++) {
                    if (kek2.contains(kek1.get(i)) && kek3.contains(kek1.get(i))) {
                        restorePoints.remove(kek1.get(i));
                        //kek1.remove(i);
                    }
                }
            }
            else if (kek1 != null && kek2 != null) {
                for (int i = 0; i < kek1.size(); i++) {
                    if (kek2.contains(kek1.get(i))) {
                        restorePoints.remove(kek1.get(i));
                        //kek1.remove(i);
                    }
                }
            } else if (kek2 != null && kek3 != null) {
                for (int i = 0; i < kek3.size(); i++) {
                    if (kek2.contains(kek3.get(i))) {
                        restorePoints.remove(kek3.get(i));
                        //kek3.remove(i);
                    }
                }

            } else if (kek1 != null && kek3 != null) {
                for (int i = 0; i < kek3.size(); i++) {
                    if (kek1.contains(kek3.get(i))) {
                        restorePoints.remove(kek3.get(i));
                        //kek3.remove(i);
                    }
                }
            }

        }
    }

    void setAmPointsAlgo(AmountOfPointsAlgo a) {
        AmountOfPointsAlgo = a;
    }
    void setDateAlgo(DateAlgo a) {
        dateAlgo = a;
    }
    void setSizeAlgo(SizeAlgo a) {
        sizeAlgo = a;
    }
}





//ExceptionS
class FileAlreadyReadyForBackupException extends RuntimeException {
    public FileAlreadyReadyForBackupException() {
        System.out.println("FileAlreadyReadyForBackup");
    }
}
class NoSuchFileForBackupException extends RuntimeException {
    public NoSuchFileForBackupException() {
        System.out.println("NoSuchFileForBackup");
    }
}
class NoFilesToBackUpException extends RuntimeException {
    public NoFilesToBackUpException() {
        System.out.println("NoFilesToBackUpException");
    }
}
class NoRestorePointFromWhereToCreateIncrBackupException extends RuntimeException {
    public NoRestorePointFromWhereToCreateIncrBackupException() {
        System.out.println("NoRestorePointFromWhereToCreateIncrBackupException");
    }
}
class CanNotElemenateSinceHasSonsException extends RuntimeException {
    public CanNotElemenateSinceHasSonsException() {
        System.out.println("CanNotElemenateSinceHasSonsException");
    }
}
class AllFilesAreTheSameException extends RuntimeException {
    public AllFilesAreTheSameException() {
        System.out.println("AllFilesAreTheSameException");
    }
}

