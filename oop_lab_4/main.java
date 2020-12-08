import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;
import java.nio.file.Path;
import java.util.GregorianCalendar;

public class main {
    public static void main(String[] args) throws IOException {
        //test case number 1
        //TEST CASE 1
        //create our backup manger
        BackUp bm1 = new BackUp();

        //add files to a backup
        Path path1 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem1.txt");
        Path path2 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem2.txt");
        bm1.addFile(path1);
        bm1.addFile(path2);

        //commit out restorepoint
        bm1.commitBackup();

        //check if our restore point wrote everything correctly
        System.out.println("First restore point check:\n");
        System.out.println(bm1.showBackUpState());

        //commit new restore point
        bm1.commitBackup();

        //check if everything is written ok
        System.out.println("Second restore point check:\n");
        System.out.println(bm1.showBackUpState());

        //Here we set our clearence algo
        ClearAlgo anmountalgo = new AmountOfPointsAlgo(1);
        bm1.setClearAlgo(anmountalgo);
        System.out.println("State of a backup after amount clearence");
        System.out.println(bm1.showBackUpState());





        //-------------------------------------------------------------------------------------------------------------
        //test case number 2
        //TEST CASE 2
        //set our 100MB files
        /*

        BackUp bm2 = new BackUp();
        Path path3 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem3.txt");
        Path path4 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem4.txt");

        //add files to a backup
        bm2.addFile(path3);
        bm2.addFile(path4);

        //commit new restorepoint
        bm2.commitBackup();
        System.out.println("First Restore point check:");
        System.out.println(bm2.showBackUpState());

        System.out.println("Second Restore point check");
        bm2.commitBackup();
        System.out.println(bm2.showBackUpState());

        //here we set our by size algo
        ClearAlgo sizealgo = new SizeAlgo(314_572_800, bm2.getSize());
        bm2.setClearAlgo(sizealgo);

        //here we check result
        System.out.println("Backup state after size clearence");
        System.out.println(bm2.showBackUpState());






         */
        //-------------------------------------------------------------------------------------------------------------
        //test case number 3 (increment usage test)
        //TEST CASE 3
        /*
        BackUp bm3 = new BackUp();

        //add files to a backup
        Path path1 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem1.txt");
        Path path2 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem2.txt");
        bm3.addFile(path1);

        //create init backup
        bm3.commitBackup();
        System.out.println("Init backup state:");
        System.out.println(bm3.showBackUpState());

        //add second file
        bm3.addFile(path2);

        //create incr backup point
        bm3.commitIncrementalBackup();
        System.out.println("Incr backup state:");
        System.out.println(bm3.showBackUpState());

         */
        //-------------------------------------------------------------------------------------------------------------
        //test case number 4 (combined clearence)
        /*

        BackUp bm4 = new BackUp();

        //add files to a backup
        Path path1 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem1.txt");
        Path path2 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem2.txt");
        Path path3 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem3.txt");
        Path path4 = Path.of("C:\\Users\\Professional\\IdeaProjects\\oop_lab4_v2\\out\\production\\oop_lab4_v2\\filesToBackup\\mem4.txt");
        bm4.addFile(path1);
        bm4.addFile(path2);
        bm4.addFile(path3);
        bm4.addFile(path4);
        bm4.commitBackup();
        System.out.println("State of a backup after init commit");
        System.out.println(bm4.showBackUpState());
        //we do second commit
        bm4.commitBackup();
        System.out.println("State of a backup after second commit");
        System.out.println(bm4.showBackUpState());

        //setting our hybrid algo
        SizeAlgo sizeAlgo = new SizeAlgo(400_000_000, bm4.getSize());
        AmountOfPointsAlgo amountAlgo = new AmountOfPointsAlgo(1);

        Hybrid newHybridAlgo = new Hybrid(Hybrid.HybridLimits.ALL);

        newHybridAlgo.setSizeAlgo(sizeAlgo);
        newHybridAlgo.setAmPointsAlgo(amountAlgo);

        bm4.setClearAlgo(newHybridAlgo);
        //show state after hybrid algo
        System.out.println("After clearence algo");
        System.out.println(bm4.showBackUpState());



         */

    }
}
