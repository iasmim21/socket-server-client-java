import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Avatar avatar;

    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static Socket socket;
    private static Server server;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
            server = new Server();
            System.out.println("Aguardando conexão...");

            server.createServerSocket(5555);

            socket = server.waitConnection();
            System.out.println("Cliente conectado");

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while (true) {
                System.out.println("Processar");
                server.treatConnection();
            }
    }

    private void createServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void treatConnection() throws IOException, ClassNotFoundException {
            ClientProtocol clientProtocol = (ClientProtocol) input.readObject();
            Status status = clientProtocol.getStatus();

            ServerProtocol serverProtocol = new ServerProtocol();
            System.out.println("status client:" + status.toString());

            if (status.equals(Status.START)) {
                serverProtocol.setMessage("Vamos lá, primeiro escolha seu avatar (informe o id):");
                serverProtocol.setStatus(Status.CHOOSE_AVATAR);
                serverProtocol.setAvatars(getAvatars());

                output.writeObject(serverProtocol);
                output.flush();
            } else if (status.equals(Status.CHOOSE_AVATAR)) {
                List<Avatar> avatars = getAvatars();

                avatar = avatars.stream()
                        .filter(a -> a.getId() == clientProtocol.getSelectedOptionId())
                        .findFirst()
                        .orElse(null);

                Step step = generateSteps()[0];

                serverProtocol.setMessage(step.getMessage());
                serverProtocol.setOptions(step.getOptions());
                serverProtocol.setStatus(Status.EXECUTION);

                output.writeObject(serverProtocol);
                output.flush();
            } else {
                selectNextStep(clientProtocol, serverProtocol);
            }
    }

    private void selectNextStep(ClientProtocol clientProtocol, ServerProtocol serverProtocol) throws IOException {
        Step[] steps = generateSteps();

        int selectedNextStep = clientProtocol.getSelectedOptionId();

        Step selectedStep = Arrays.stream(steps)
                .filter(step -> step.getId() == selectedNextStep)
                .findFirst()
                .orElse(null);

        boolean isCompleted = isCompleted(selectedStep.getId());

        serverProtocol.setMessage(selectedStep.getMessage());
        serverProtocol.setOptions(selectedStep.getOptions());
        serverProtocol.setStatus(isCompleted ? Status.COMPLETED : Status.EXECUTION);

        output.writeObject(serverProtocol);
        output.flush();

        if(isCompleted){
            input.close();
            output.close();
            socket.close();
        }
    }

    private Socket waitConnection() throws IOException {
        return serverSocket.accept();
    }

    private static boolean isCompleted(int stepId) {
        int[] completedIds = {4, 6, 8, 10, 12, 13,14, 16, 18,19};

        return Arrays.stream(completedIds).anyMatch(id -> id == stepId);
    }

    private List<Avatar> getAvatars(){
        List<Avatar> avatars = new ArrayList<>();
        avatars.add(new Avatar(1, "Lilian (Mago)", 2, 1, 4, 3));
        avatars.add(new Avatar(2, "Robert (Espadachim)", 3, 4, 1, 2));
        avatars.add(new Avatar(3, "Dentuço (Gnomo)", 1, 2, 3, 4));
        avatars.add(new Avatar(4, "Thomas (Golem)", 4, 3, 2, 1));

        return avatars;
    }

    private Step[] generateSteps() {
        List<Option> fim = new ArrayList<>();

        List<Option> options1 = new ArrayList<>();
        options1.add(new Option(2, "A Cachoeira de Nivergton (Resistência)"));
        options1.add(new Option(3, "Caverna de Novigard (Inteligência)"));

        List<Option> options2 = new ArrayList<>();
        options2.add(new Option(11, "Seguir pelo rio"));
        options2.add(new Option(12, "Procurar outro caminho"));

        List<Option> options3 = new ArrayList<>();
        options3.add(new Option(5, "Sim"));
        options3.add(new Option(4, "Não"));

        List<Option> options5 = new ArrayList<>();
        options5.add(new Option(6, "Direita"));
        options5.add(new Option(7, "Esquerda"));

        List<Option> options7 = new ArrayList<>();
        options7.add(new Option(8, "Esquerda"));
        options7.add(new Option(9, "Meio"));
        options7.add(new Option(10, "Direita"));

        List<Option> options11 = new ArrayList<>();
        options11.add(new Option(13, "Uma pedra à esquerda\n"));
        options11.add(new Option(14, "Nadar um pouco para conseguir alcançar um cipó um pouco a frente\n"));
        options11.add(new Option(15, "Nadar para o outro lado e tentar alcançar a outra margem"));

        List<Option> options15 = new ArrayList<>();
        options15.add(new Option(17, "Balançar-se e tentar alcançar a margem do outro lado\n"));
        options15.add(new Option(16, "Seguir pendurados no cipó\n"));

        List<Option> options17 = new ArrayList<>();
        options15.add(new Option(18, "Corre em direção à vegetação\n"));
        options15.add(new Option(19, "Vai até Pabllo\n"));

        Step[] steps = {
                new Step(
                        1,
                        "Um grupo de aventureiros de Yergen celebra a vitória sobre invasores bárbaros em uma taverna. \n" +
                                "Pararam no primeiro bar que haviam encontrado no caminho e compraram algumas bebidas. \n" +
                                "Lilian, a maga, logo pegou duas garrafas de rum e virou-as na frente de seus colegas. \n" +
                                "Pabllo, o líder, colocou a mão na bolsa que estava cheia de bebidas e retirou um frasco pequeno de vidro.  \n" +
                                "Ele estava determinado em manter a sobriedade mas não queria ficar de fora da farra dos seus colegas.  \n" +
                                "Arrancou a rolha do frasco e bebeu o conteúdo dentro dele. \n" +
                                "Tomaz e Dentuço seguravam garrafas de hidromel e competiam para quem iria esvaziá-las mais rápido.  \n" +
                                "Robert, o espadachim, já havia bebido 3 garrafas de rum nesse processo e se encontrava completamente bêbado.\n" +
                                "\n" +
                                "Todos estavam muito animados e festivos, em que gargalhavam alto e dançavam a caminho da Pousada que estavam hospedados na cidade.  \n" +
                                "Ao acordar, Lilian é surpreendida por um rato, que gritava informando que era Pabllo. " +
                                "Pabllo havia sido transformado em um rato, Após alguns minutos tentando convencer Lilian que era realmente a pessoa que dizia ser.  \n" +
                                "Lilian acordou os outros aventureiros para ajudá-la a encontrar uma solução.\n" +
                                "\n" +
                                "Dentro da bolsa de garrafas vazias, encontraram o frasco que Pabllo havia bebido. O frasco possui uma inscrição no rótulo.  \n" +
                                "Modo de Uso: Beba uma pequena dose para diminuir de tamanho e conseguir pegar objetos pequenos abaixo dos móveis.  \n" +
                                "Atenção: Beba apenas se tiver o antídoto. Produzido por A Bruxa..\n" +
                                "\n" +
                                "Os aventureiros vão até a taverna que compraram as bebidas em busca de respostas. " +
                                "Ao falarem com o atendente, eles informam que não vendem  produtos da Bruxa na taverna e, caso fizessem, não poderiam vender  \n" +
                                "aquele frasco sem o antídoto. Sem antídoto e sem respostas, se veem obrigados a buscar pela Bruxa.\n" +
                                "\n" +
                                "O grande mistério é sobre sua exata localização, há um bom tempo não veem notícias sobre ela.  \n" +
                                "De bom grado, o atendente da taverna informa 4 lugares onde ela costumava estar.  \n" +
                                "A Cachoeira de Nivergton e Caverna de Novigard.\n" +
                                "\n" +
                                "Decididos a reverter a transformação, os nossos aventureiros iniciam a sua jornada. a caminho de:\n\n",
                        "",
                        options1
                ),
                new Step(
                        2,
                        "Os aventureiros tem conhecimento que encontrar a Bruxa não é uma tarefa fácil. \n" +
                                "Muitas pessoas buscaram ela para conceder desejos fúteis ou obrigá-la a realizar feitiços e, por conta disso, \n" +
                                "ela foi se escondendo com o passar dos tempos. A dúvida é, será que ela vai ajudar com a transformação do Pabllo?\n" +
                                "\n" +
                                "Após dois dias de viagem ao norte do reino, em busca da grandiosa Cachoeira de Nivergton, " +
                                "os aventureiros deparam-se com um dilema. Não há mais uma estrada a seguir, o caminho até a cachoeira é pela correnteza do rio.\n" +
                                "\n" +
                                "Você, " + avatar.getName() + ", deseja seguir pelo rio ou procurar outro caminho?\n",
                        "",
                        options2
                ),

                new Step(
                        3,
                        "Os aventureiros tem conhecimento que encontrar a Bruxa não é uma tarefa fácil. \n" +
                                "Muitas pessoas buscaram ela para conceder desejos fúteis ou obrigá-la a realizar feitiços e, \n" +
                                "por conta disso, ela foi se escondendo com o passar dos tempos. A dúvida é, será que ela vai ajudar com a \n" +
                                "transformação do Pabllo?\n\n" +
                                avatar.getName() + " você deseja entrar na caverna?",
                        "",
                        options3
                ),

                new Step(
                        4,
                        "Após se recusar a entrar na caverna, você e seu grupo acampam na entrada. \n" +
                                "O que não esperavam era que, enquanto dormiam, seriam o jantar de uma família de ursos que dormiam dentro da caverna.",
                        "",
                        fim
                ),

                new Step(
                        5,
                        "Adentrando a caverna, você se depara com duas bifurcações. Qual lado você escolhe?",
                        "Help Message 2",
                        options5
                ),
                new Step(
                        6,
                        "",
                        "",
                        fim
                ),
                new Step(
                        7,
                        "Após passar por essa bifurcação surgem três novas, \n" +
                                "investigando um pouco é possível sentir um cheiro de enxofre na bifurcação mais à esquerda, \n" +
                                "na do meio é possível sentir uma brisa suave e na direita você sente um calafrio ao se aproximar da entrada.\n",
                        "",
                        options7
                ),
                new Step(
                        8,
                        "Entrando na bifurcação escolhida, um pouco à frente você é surpreendido com alguns goblins ágeis que acabam \n" +
                                "nocauteando o seu grupo em um piscar de olhos, vocês não conseguiram chegar até a bruxa",
                        "",
                        fim
                ),
                new Step(
                        9,
                        "Percorrendo alguns metros, o grupo encontra uma moça com vestes claras segurando um velho lampião aceso. Era A Bruxa." +
                                "\n\n" +
                                "Bruxa: Ora, parece que não sou a única procurando Cogumelos Fumegônicos;" +
                                "\n\n" +
                                "Você explicou à bruxa o que havia acontecido com Pabllo. \n" +
                                "Ela prontamente pegou algumas ervas dentro de uma bolsa que levava consigo, ajoelhou no chão da caverna, \n" +
                                "misturou-as com a mão recitando palavras incompreensíveis. \n" +
                                "Ela abriu a mão e um flagelo queimou as folhas que ali estavam, restando apenas uma pequena quantidade de cinzas no centro da palma.\n" +
                                " Ela pediu para que Pabllo abrisse a boca e salpicou um pouco daquilo em sua pequena língua de roedor." +
                                "\n\n" +
                                "Pabllo se afogou um pouco e engoliu seco. Uns segundos se passaram e ainda não tinha tido efeito. Desesperançado, \n" +
                                "Pabllo começou a xingar a bruxa quando começou a flutuar no chão." +
                                "\n\n" +
                                "Suas patinhas viraram mãos novamente, seu rabo desapareceu, seus dentes enormes haviam encolhido e ele começou a crescer." +
                                "\n\n" +
                                "Bruxa: acho melhor encontrar algo para ele vestir.\n" +
                                "\n\n" +
                                "Pabllo estava de volta ao normal, porém agora precisava de roupas.\n",
                        "",
                        fim
                ),
                new Step(
                        10,
                        " Entrando na bifurcação escolhida, o grupo anda por alguns metros e se deparam com múltiplos ossos de seres humanos. \n" +
                                "Com o auxílio do lampião que você está segurando, ilumina o caminho. \n" +
                                "Um pouco à frente dos ossos está um grande urso pardo adormecido. \n" +
                                "É melhor voltar em silêncio até o início da bifurcação.\n",
                        "",
                        fim
                ),
                new Step(
                        11,
                        "A equipe adentra as águas doces pela direita do rio, segurando-se em uma grande raiz de árvore à beira do rio. \n" +
                                "A correnteza não é leve, e alguns metros à frente as raízes se esgotam. \n" +
                                "É preciso segurar-se em algo para não ser levado pelas águas. O que você escolhe?\n",
                        "",
                        options11
                ),
                new Step(
                        12,
                        "Ao tentar se esgueirar pelas bordas do rio, as pedras revelam-se muito escorregadias. \n" +
                                "Teria sido melhor tentar seguir pelo rio desde o início.\n",
                        "",
                        fim
                ),
                new Step(
                        13,
                        "Ao escolher a pedra à esquerda, você consegue agarrar se à pedra. \n" +
                                "No entanto, a pedra está mais escorregadia que o normal, possuía limo. \n" +
                                "Infelizmente, você escorrega e é levado pela correnteza.\n",
                        "",
                        fim
                ),
                new Step(
                        14,
                        "Ao tentar nadar para o outro lado, a correnteza parece ainda mais forte. \n" +
                                "Você não conseguiu chegar ao outro lado e foi levado pelo rio.\n",
                        "",
                        fim
                ),
                new Step(
                        15,
                        "Ao nadar em direção ao cipó à frente, vocês conseguem agarrá-lo com sucesso. \n" +
                                "Ao olhar ao redor, no outro lado do rio, há um caminho para seguir. \n" +
                                "Agora, têm a opção de:\n",
                        "",
                        options15
                ),
                new Step(
                        16,
                        "Infelizmente o cipó não é tão resistente para aguentar por muito tempo 2 humanos, 1 rato, 1 gnomo e uma estátua. \n" +
                                "Portanto, depois de alguns minutos pendurados, o cipó se parte e os aventureiros são levados pela correnteza\n\n",
                        "",
                        fim
                ),
                new Step(
                        17,
                        "Juntos, vocês balançam o cipó e se jogam para alcançar a margem do outro lado. Vocês conseguiram chegar ao outro lado do rio.\n" +
                                "Seguindo pela margem esquerda, vocês enfrentam um terreno enlameado e com vegetação densa. " +
                                "Foi difícil percorrer aquele caminho sem escorregar, mas seu grupo conseguiu chegar até a cachoeira de Nivergton.\n" +
                                "\n" +
                                "Na ponta da cachoeira, lá está ela, a Bruxa. Ela estava olhando o horizonte enquanto colocava a mão nas águas do rio.\n" +
                                "\n" +
                                "Pabllo foi correndo até ela pedir ajuda. Ao olhar em direção ao Pabllo, a Bruxa apresentava olhos escuros e uma aparência pálida. \n" +
                                "Aquela não era a Bruxa e, agora, ela levantou e virou em direção à vocês.\n" +
                                "O que você faz?\n\n",
                        "",
                        options17
                ),
                new Step(
                        18,
                        "Você e o grupo seguem em direção à vegetação e correm o mais longe possível.  \n" +
                                "Vocês fugiram do seu objetivo e deixaram Pabllo para trás.\n",
                        "",
                        fim
                ),
                new Step(
                        19,
                        "Você corre em direção ao Pabllo e da figura que se passava pela Bruxa. \n" +
                                "Ao chegar mais perto, percebe que era apenas uma ilusão. \n" +
                                "A verdadeira Bruxa aparece à sua frente, rindo suavemente.\n" +
                                "\n" +
                                "Bruxa: Ah, então és corajoso o suficiente para enfrentar uma ilusão. \n" +
                                "Vocês realmente estão desesperados, não é?\n" +
                                "\n" +
                                "Pabllo: Porque você fez isso? Todos os pelos do meu corpo de roedor estão arrepiados.\n" +
                                "\n" +
                                "Bruxa: As pessoas frequentemente buscam magia sem entender as consequências. \n" +
                                "Eu precisava garantir que vocês realmente precisavam da minha ajuda. \n" +
                                "\n" +
                                "Pabllo explica a situação à bruxa e ela, prontamente, abre um pequeno saco que trazia consigo nas viagens à Nivergton. \n" +
                                "Ela retirou um frasco muito parecido com o que Pabllo havia ingerido, colocou umas gotas em um copinho de folhas e ofereceu à Pabllo. \n" +
                                "\n" +
                                "Pabllo bebe do frasco e logo volta à sua forma humana.\n\n" +
                                "Bruxa: Ó espíritos do dragão, eu sempre esqueço de falar que eles precisam pegar roupas antes!\n" +
                                "\n" +
                                "Pabllo estava de volta ao normal, porém agora precisava de roupas.\n",
                        "",
                        fim
                )
        };

        return steps;
    }
}