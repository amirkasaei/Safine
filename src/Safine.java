import java.text.DecimalFormat;
import java.util.Scanner;

public class Safine {

    //replace method
    static String spaceRemover(String temp){
        char[]tempArray = new char[temp.length()];
        for (int i = 0,t = 0; t < temp.length(); t++, i++) {
            while (temp.charAt(t) == ' ') {
                t++;
            }
            tempArray[i] = temp.charAt(t);
        }
        return new String(tempArray);
    }
    //substring method
    static String substring(String temp,int begin, int end){
        char[] tempArray = new char[end-begin];
        for (int i = 0; begin < end; i++,begin++) {
            tempArray[i] = temp.charAt(begin);
        }
        return new String(tempArray);
    }
    //header
    static void header(){
        System.out.println("\t\t\t\t ☼ Safine ☼");
        System.out.println("--------------------------------------------------\n");
    }
   //check if argo is safe or not
    static boolean safe(int[] meteoritesCoordinateX, float[] meteoritesCoordinateY, int meteoritesNum, int x){
        for (int i = 0; i < meteoritesNum; i++) {
            if (meteoritesCoordinateX[i] == x && meteoritesCoordinateY[i] == 0) {
                return false;
            }
        }
        return true;
    }
    //game process
    static void game(){
        Scanner scanner = new Scanner(System.in);

        //data
        System.out.println("\nPlease Enter The Data's:");

        //meteorites data
        String meteorites = scanner.nextLine();
        meteorites = spaceRemover(meteorites);

        //argo data
        String argo = scanner.nextLine();
        argo = spaceRemover(argo);

        //meteorites number sorting
        int n;
        for (n = 0; meteorites.charAt(n) != '(' ; n++);
        int meteoriteNum = Integer.parseInt(substring(meteorites,0,n));

        //meteorites coordinate and velocity sorting
        int[] meteoritesCoordinateX = new int[meteoriteNum];
        float[] meteoritesCoordinateY = new float[meteoriteNum];
        float[] meteoritesVelocity = new float[meteoriteNum];

        for (int x = 0, y = 0, v = 0, j, i = 0; i < meteorites.length()-1; i++) {

            if (meteorites.charAt(i) == '(') {
                for (j = i+1; meteorites.charAt(j) != ','; j++);
                meteoritesCoordinateX[x] = Integer.parseInt(substring(meteorites,i+1,j));
                x++;
            }

            else if (meteorites.charAt(i) == ',') {
                for (j = i+1; meteorites.charAt(j) != ')'; j++);
                meteoritesCoordinateY[y] = Float.parseFloat(substring(meteorites,i+1,j));
                y++;
            }

            else if (meteorites.charAt(i) == ')') {
                for (j = i+1; meteorites.charAt(j) != '_'; j++){
                    if (j == meteorites.length()-1) {
                        j++;
                        break;
                    }
                }
                meteoritesVelocity[v] = Float.parseFloat(substring(meteorites,i+1,j));
                v++;
            }
        }

        //argo velocity change and its coordinate
        int argoDataNum = 0;
        for (int i = 0; i < argo.length(); i++) {
            if (argo.charAt(i) == '-') {
                argoDataNum++;
            }
        }
        int[] argoVelocityChangeCoordinate = new int[argoDataNum];
        float[] argoVelocity = new float[argoDataNum];

        for (int i = 0,v = 0, c = 0,j; i < argo.length()-1; i++) {

            if (argo.charAt(i) == '_') {
                for (j = i+1; argo.charAt(j) != '-'; j++);
                argoVelocityChangeCoordinate[c] = Integer.parseInt(argo.substring(i+1,j));
                c++;
            }
            else if (argo.charAt(i) == '-') {
                for (j = i+1; argo.charAt(j) != '_'; j++){
                    if (j == argo.length()-1) {
                        j++;
                        break;
                    }
                }
                argoVelocity[v] = Float.parseFloat(argo.substring(i+1,j));
                v++;
            }
        }

        //situation print

        //decimal format limit
        DecimalFormat twoDigitFormat = new DecimalFormat("0.##");

        float time = 0;
        for (int x = 0,v=0; x <= meteoriteNum ; x++) {

            System.out.print("t=" + twoDigitFormat.format(time) + " R=(" + x + ",0) ");

            for (int i = 0; i < meteoriteNum; i++) {
                System.out.print("M" + (i+1) + "=(" + (i+1) + "," + twoDigitFormat.format(meteoritesCoordinateY[i]) + ") ");
            }
            //argo situation
            if (safe(meteoritesCoordinateX, meteoritesCoordinateY, meteoriteNum, x)) {
                System.out.println("SAFE~");
            }
            else {
                System.out.println("CRASHED!");
                break;
            }
            //time calculation
            if (x == argoVelocityChangeCoordinate[v]) {
                v++;
            }
            time += 1/argoVelocity[v];
            //meteorites coordinate change calculation
            for (int i = 0; i < meteoriteNum; i++) {
                meteoritesCoordinateY[i] -= (1/argoVelocity[v])*meteoritesVelocity[i];
            }
        }
        System.out.println("\n\nPress Enter To Continue ...");
        scanner.nextLine();
        menu();
    }
    //menu
    static void menu(){
        Scanner scanner = new Scanner(System.in);
        header();
        System.out.println(" 1.Start\t\t\t\t\t2.Exit");
        System.out.print("\nPlease Enter The Number You Want: ");
        byte menu = scanner.nextByte();
        switch (menu){
            //start game
            case 1:
                //game process
                game();
                break;

            //exit game
            case 2:
                return;
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
