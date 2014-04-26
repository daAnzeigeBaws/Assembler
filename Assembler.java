import java.io.*;

class Assembler
{
  static long[] store = new long[10];
  static String[][] commands;
  static long ccount;
  static long akkumulator;
  static long stepcounter;
  
  public static void main(String[] s)
  {
    try
    {
      BufferedReader br;
      if(s.length != 0)
      {
        br = new BufferedReader(new FileReader(s[0]));
      }
      else
      {
        br = new BufferedReader(new FileReader("default.txt"));
      }
      getReady(br);
    }
    catch(Exception e)
    {
      System.out.println("an error occured");
      e.printStackTrace();
    }
  }
  
  private static void getReady(BufferedReader br)
  {
    String store = "@/";
    try
    {
      String line = null;
      while((line = br.readLine()) != null)
      {
        store += line + "/";
      }
      store += "@";
    }
    catch(Exception e){System.out.println("Read Error");}
    store = store.substring(2,store.length()-2);
    //System.out.println(store);
    
    String[] tmp = store.split("/");
    
    commands = new String[tmp.length][2];
    
    for(int i = 0; i < tmp.length; i++)
    {
      String[] temp = tmp[i].split(":");
      
      commands[i][0] = temp[0].trim();
      commands[i][1] = temp[1].trim();
    }
    
    run();
  }
  
  private static void run()
  {
    while(ccount != -10)
    {
      /*System.out.println("Current Akk: "+akkumulator);
      System.out.println("Current Line: "+ccount);
      System.out.println("Store 0: "+store[0]);
      System.out.println("Store 1: "+store[1]);
      System.out.println("Store 2: "+store[2]);
      System.out.println("Store 3: "+store[3]);
      System.out.println("Store 4: "+store[4]);
      System.out.println("\n");*/
      stepcounter++;
      analyse();
      if(stepcounter > 10000)
      {
        System.out.println("The Program needs more than 10000 Steps");
        System.out.println("It was canceled");
        break;
      }
    }
    System.out.println(akkumulator);
  }
  
  private static void analyse()
  {
    String line = commands[(int)ccount][1];
    line = line.toLowerCase();
    
    
    if(line.contains("dload"))
    {
      akkumulator = Long.parseLong(line.substring(5).trim());
      ccount++;
    }
    else if(line.contains("load"))
    {
      akkumulator = store[Integer.parseInt(line.substring(4).trim())];
      ccount++;
    }
    else if(line.contains("store"))
    {
      store[Integer.parseInt(line.substring(5).trim())] = akkumulator;
      ccount++;
    }
    else if(line.contains("add"))
    {
      akkumulator += store[Integer.parseInt(line.substring(3).trim())];
      ccount++;
    }
    else if(line.contains("sub"))
    {
      akkumulator -= store[Integer.parseInt(line.substring(3).trim())];
      ccount++;
    }
    else if(line.contains("mult"))
    {
      akkumulator *= store[Integer.parseInt(line.substring(4).trim())];
      ccount++;
    }
    else if(line.contains("div"))
    {
      akkumulator /= store[Integer.parseInt(line.substring(3).trim())];
      ccount++;
    }
    else if(line.contains("jump"))
    {
      ccount = Long.parseLong(line.substring(4).trim());
      ccount--;
    }
    else if(line.contains("jge"))
    {
      if(akkumulator >= 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("jgt"))
    {
      if(akkumulator > 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("jle"))
    {
      if(akkumulator <= 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("jlt"))
    {
      if(akkumulator < 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("jeq"))
    {
      if(akkumulator == 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("jne"))
    {
      if(akkumulator != 0)
      {
        ccount = Long.parseLong(line.substring(3).trim());
        ccount--;
      }
      else
      ccount++;
    }
    else if(line.contains("end"))
    {
      ccount = -10;
    }
  }
}