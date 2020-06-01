import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    private static void SendTitleQuery(BufferedReader br, PrintWriter pw, Scanner sc) throws IOException
    {
        boolean serverResponse;

        do{
            System.out.println("Enter task title:");
            String title = sc.nextLine();
            pw.println(title);
            pw.flush();
            serverResponse = Boolean.parseBoolean(br.readLine());

            if(!serverResponse)
            {
                System.out.println("Task does not exist!");
            }

        }while(!serverResponse);
    }

    public static void main(String[] args) throws IOException {

        // Connect to server socket.
        Socket s = new Socket("localhost", 1111);
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(in);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        String input = "";
        Scanner sc = new Scanner(System.in);
        String taskListSize;

        do{
            taskListSize = br.readLine();
            System.out.println("Operation Type (Create=1/MarkAsDone=2/Delete=3/MyTasks=4/Quit=5):");
            input = sc.nextLine();
            input = input.toLowerCase();
            pw.println(input);
            pw.flush();
            if(input.compareTo("create")==0 || input.compareTo("1")==0)
            {
                System.out.println("Enter task title:");
                String title = sc.nextLine();
                pw.println(title);
                boolean serverResponse;
                do
                {
                    System.out.println("Enter due date(dd-mm-yyyy):");
                    String date = sc.nextLine();
                    pw.println(date);
                    pw.flush();
                    serverResponse = Boolean.parseBoolean(br.readLine());

                    if(!serverResponse)
                    {
                        System.out.println("Invalid Date Format!");
                    }

                }while(!serverResponse);
            }
            else if ((input.compareTo("markasdone")==0 || input.compareTo("2")==0) && taskListSize.compareTo("0")!=0)
            {
                    SendTitleQuery(br, pw, sc);
            }
            else if((input.compareTo("delete")==0 || input.compareTo("3")==0) && taskListSize.compareTo("0")!=0)
            {
                    SendTitleQuery(br, pw, sc);
            }
            else if(input.compareTo("mytasks")==0 || input.compareTo("4")==0)
            {
                boolean serverResponse=false;
                String serverOutput;

                System.out.println("\n -----------------------------------------------------------------------------------");
                System.out.println(br.readLine());
                do
                {
                    serverOutput=br.readLine();
                    if(Boolean.parseBoolean(serverOutput))
                    {
                        serverResponse = Boolean.parseBoolean(serverOutput);
                    }
                    else
                    System.out.println(serverOutput);

                }while(!serverResponse);
                System.out.println("\n -----------------------------------------------------------------------------------\n");
            }

        }while(input.compareTo("quit")!=0 || input.compareTo("5")!=0);

    }
}