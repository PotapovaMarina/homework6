import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String ... args) {
          startTextServer();
    }
    private static void startTextServer() {
        try(ServerSocket serverSocket = new ServerSocket(8180)){
            System.out.println("Server is listening");
            try(Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                Scanner sc= new Scanner(System.in);
            ){
                System.out.println("Client is connected");
                out.println("Hello client");
                out.flush();
                String myMessage = "";

                Thread clientReader = new Thread(() -> {
                    String clientMessage = "";
                    try {
                        while(!socket.isClosed()) {
                            clientMessage = in.readLine();
                            System.out.println(clientMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                clientReader.start();

                do{
                    myMessage = sc.nextLine();
                    out.println(myMessage);
                    out.flush();
                }while(!myMessage.equalsIgnoreCase("stop"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
Написать консольный вариант клиент\серверного приложения,
в котором пользователь может писать сообщения, как на клиентской стороне,
так и на серверной. Т.е. если на клиентской стороне написать «Привет», нажать Enter,
 то сообщение должно передаться на сервер и там отпечататься в консоли.
 Если сделать то же самое на серверной стороне,
 то сообщение передается клиенту и печатается у него в консоли.
 Есть одна особенность, которую нужно учитывать:
 клиент или сервер может написать несколько сообщений подряд.
 Такую ситуацию необходимо корректно обработать.
 */