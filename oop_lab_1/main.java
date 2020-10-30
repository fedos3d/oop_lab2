import java.io.IOException;

public class main {
    public static void main(String args[]) throws IOException {
        //Here we create our parser
        String inifilepath = "src\\test.ini";
        parser newparser = new parser(inifilepath);

        //Here we check wrong creatinon of our pasrser
        //String wroninifilepath = "serc\\test.inin"; //wrong file extestion
        //parser wrongparse = new parser(wroninifilepath);


        //Here we check things that must give correct answer:
        System.out.println(newparser.getString("ADC_DEV", "Driver"));
        System.out.println(newparser.getInt("COMMON", "OpenMPTThreadsCount"));
        System.out.println(newparser.getDouble("ADC_DEV", "SampleRate"));
        //Here we check non valid stuff:
        //System.out.println(newparser.getString("ABRval", "SUS")); //No such section
        //System.out.println(newparser.getInt("NCMD", "kek")); //No such key
        //System.out.println(newparser.getInt("ADC_DEV", "SampleRate")); //wrong parsing method
    }
}
