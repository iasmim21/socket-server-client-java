import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static ServerProtocol response;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String enterKey;

        do {
            System.out.println("Digite 'start' para iniciar sua jornada");
            enterKey = scanner.nextLine();

        } while (!enterKey.equals("start"));

        startGame(args[0]);
    }

    private static void startGame(String ip) throws IOException, ClassNotFoundException {
        Socket socket = null;

        try {
            socket = new Socket(ip, 5555);

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            ClientProtocol protocol = new ClientProtocol(Status.START);

            output.writeObject(protocol);
            output.flush();

            response = (ServerProtocol) input.readObject();

            while (!response.getStatus().equals(Status.COMPLETED)) {
                if (response.getStatus().equals(Status.CHOOSE_AVATAR)) {
                    System.out.println(response.getMessage() + "\n\n");

                    printAvatarTable();

                    int avatarId = Integer.parseInt(scanner.nextLine());

                    List<Avatar> avatars = response.getAvatars();

                    if (avatarId >= 1 && avatarId <= avatars.size()) {
                        protocol = new ClientProtocol(Status.CHOOSE_AVATAR);
                        protocol.setStepId(1);
                        protocol.setSelectedOptionId(avatarId);

                        output.writeObject(protocol);
                        output.flush();

                        response = (ServerProtocol) input.readObject();
                        System.out.println(response.getMessage());

                        processStep();
                    } else {
                        System.out.println("Erro: Avatar inválido");
                    }
                } else {
                    processStep();
                }

                response = (ServerProtocol) input.readObject();
            }

            System.out.println(response.getMessage());

            System.out.println("FFFFFF  II   M   M     DDDD   EEEEE      J  OOOOO   GGGG   OOOOO");
            System.out.println("F       II   MM MM     D   D  E          J  O   O  G   G   O   O");
            System.out.println("FFF     II   M M M     D   D  EEEE       J  O   O  G       O   O");
            System.out.println("F       II   M   M     D   D  E       J  J  O   O  G   GG  O   O");
            System.out.println("F       II   M   M     DDDD   EEEEE    JJ   OOOOO   GGGG   OOOOO");
        } catch (ConnectException e) {
            System.err.println("Erro: Conexão recusada. Certifique-se de que o servidor está em execução.");
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }

    private static void processStep() throws IOException {
        System.out.println(response.getMessage());

        List<Option> options = response.getOptions();

        for (Option option : options){
            System.out.println(option.getNextStepId() + " - " + option.getDescription());
        }

        int nextStepId;

        while (true) {
            try {
                nextStepId = Integer.parseInt(scanner.nextLine());

                int finalNextStepId = nextStepId;

                boolean isValidOption = options.stream()
                        .anyMatch(option -> option.getNextStepId() == finalNextStepId);

                if (isValidOption) {
                    break;
                } else {
                    System.out.println("Opção inválida. Por favor informe novamente o número da sua escolha.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Insira um número válido");
            }
        }

        ClientProtocol protocol = new ClientProtocol(Status.EXECUTION);
        protocol.setSelectedOptionId(nextStepId);

        output.writeObject(protocol);
        output.flush();
    }

    private static void printAvatarTable() {
        List<Avatar> avatars = response.getAvatars();

        System.out.printf("%-5s%-20s%-15s%-8s%-13s%-8s\n", "ID", "Nome", "Resistência", "Força", "Inteligência", "Agilidade");

        for (Avatar avatar : avatars) {
            System.out.printf("%-5d%-20s%-15d%-8d%-13d%-8d\n",
                    avatar.getId(),
                    avatar.getName(),
                    avatar.getResistance(),
                    avatar.getForce(),
                    avatar.getIntelligence(),
                    avatar.getAgility());
        }
    }
}
