import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

class Task
{
   public String Title;
   public Date Date;
   public boolean IsCompleted=false;
}

public class Server
{
   static public LinkedList <Task>TaskList = new LinkedList<>();

   public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {

        // Create socket and wait for client to connect.
        ServerSocket ss = new ServerSocket(1111);
        Socket s = ss.accept();
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(in);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        String input="";

       do {
           pw.println(TaskList.size());
           pw.flush();
           input = br.readLine();
           if (input.compareTo("create") == 0 || input.compareTo("1") == 0)
           {
               Task task = new Task();
               task.Title = br.readLine();

               while (true) {
                   try {
                       //Check the date received from the user and adds it to the task if the format is valid
                       task.Date = new SimpleDateFormat("dd-MM-yyyy").parse(br.readLine());
                       TaskList.add(task);
                       pw.println("True");
                       pw.flush();
                       break;
                   } catch (Exception e) {
                       //Date received was not valid according to the format
                       pw.println("False");
                       pw.flush();
                   }
               }

           }

           else if ((input.compareTo("markasdone") == 0 || input.compareTo("2") == 0) && TaskList.size() > 0)
           {
               while (true) {
                   try {
                       //Checks if there is any Task inside our TaskList having the name from the Client as Title
                       String title = br.readLine();
                       Task t = TaskList.stream().filter(task -> title.compareTo(task.Title) == 0).findAny().orElse(null);
                       t.IsCompleted = true;
                       pw.println("True");
                       pw.flush();
                       break;
                   } catch (Exception e) {
                       //No task with the received Title found
                       pw.println("False");
                       pw.flush();
                   }
               }
           }
           else if ((input.compareTo("delete") == 0 || input.compareTo("3") == 0) && TaskList.size() > 0)
           {
               while (true) {
                   try {
                       //Checks if there is any Task inside our TaskList having the name from the Client as Title
                       String title = br.readLine();
                       Task t = TaskList.stream().filter(task -> title.compareTo(task.Title) == 0).findAny().orElse(null);
                       if (t == null) {
                           throw new Exception();
                       }
                       TaskList.remove(t);
                       pw.println("True");
                       pw.flush();
                       break;
                   } catch (Exception e) {
                       //No task with the received Title found
                       pw.println("False");
                       pw.flush();
                   }
               }
           }
           else if (input.compareTo("mytasks") == 0 || input.compareTo("4") == 0)
           {
               pw.printf("\nTotal tasks: " + TaskList.size() + "\n\n");
               pw.flush();
               for(int i=0;i<TaskList.size();i++)
               {
                   pw.printf("%-20s Due Date: %s   %-20s  Is Completed: %s\n",
                       TaskList.get(i).Title, TaskList.get(i).Date.toString(), "", TaskList.get(i).IsCompleted);
                   pw.flush();
               }
               pw.println("True");
               pw.flush();
           }

           System.out.println("Total tasks: " + TaskList.size() + "\n");

           for(int i=0; i<TaskList.size(); i++)
           {
               System.out.printf("%-20s Due Date: %s   %-20s  Is Completed: %s \n",
                   TaskList.get(i).Title, TaskList.get(i).Date.toString(), "", TaskList.get(i).IsCompleted);
           }

           System.out.println("--------------------------------------------------------------------------------------");
       }while(input.compareTo("quit")!=0 || input.compareTo("5")!=0);

    }
}