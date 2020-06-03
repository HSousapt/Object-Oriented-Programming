/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.stream.Stream;
import java.lang.Iterable;

/**
 *
 * @author eu
 */
public class JavaFactura {
    private static Scanner stdin = new Scanner(System.in);

    public static void main (String[] args)
    {
        BD b;
        try{
            b = BD.carrega();
        }catch(Exception e){
            System.out.println("Nao gravou!"+e.getMessage());
            b = new BD();
        }

        main_menu(b);

        try{
            b.gravaTxt();
        }catch(Exception e1){
            return;
        }
    }

    private static void main_menu (BD u)
    {
        while(true){
            clean();
            System.out.println("1.Login\n"+
                    "2.Sign up\n"+
                    "\n0.Exit");
            int x = scanAndTestInt(2);
            clean();
            switch(x){
                case(1):
                    login(u);
                    break;
                case(2):
                    signUp(u);
                    break;
                case(0):
                    return;
            }
        }
    }

    /**
     * Responsavel pela validaçao do login
     *
     * @param  u    Estado atual da BD
     */
    private static void login(BD u){
        int x =-1;
        Contribuinte c = null;
        while (true) {
            System.out.println("Introduza o NIF:" );
            x = scanAndTestInt();
            if (x == 0)
                break;

            c = u.getContribuinteByNif(x);
            if (c != null)
                break;
            else
                System.out.println("Nif nao registado\n");
        }

        System.out.println("Password:" );
        String y="";
        boolean certa = false;
        while(!certa){
            y=scanAndTestString();
            certa = (x == 0) ?
                        y.equals("0"):
                        c.getPass().equals(y);
        }
        clean();

        if (x == 0) {
            paiConAdmin(u);
            return;
        }

        String sn = c.getClass().getSimpleName();

        if (sn.equals("ContribuinteIndividual"))
            paiConIndividual((ContribuinteIndividual)c,u);
        else if (sn.equals("ContribuinteEmpresa") || sn.equals("ContribuinteEmpresaInterior"))
            paiConEmpresa((ContribuinteEmpresa)c,u);

        clean();
    }

    /**
     * Menu de signUp para um ContribuinteIndividual
     *
     * @param bd A base de dados
     */
    private static void signUpContribuinteIndividual (BD bd)
    {
        System.out.println("NIF:");
        int nif = scanAndTestInt();

        System.out.println("Email:");
        String email = scanAndTestString();

        System.out.println("Nome:");
        String nome = scanAndTestString();

        System.out.println("Morada:");
        String morada = scanAndTestString();

        System.out.println("Password:");
        String password = scanAndTestString();

        System.out.println("Numero do agregado familiar:" );
        int nagregado = scanAndTestInt();
        if (nagregado != 0) {
            System.out.println("NIF do agredado familiar:" );
        }
        Set<Integer> nfiscais = new TreeSet();

        while (nfiscais.size() < nagregado) {
            System.out.println("NIF "+ nfiscais.size() +":");
            Integer nifs = scanAndTestInt();
            nfiscais.add(nifs);
        }

        ContribuinteIndividual c = (nagregado > 4) ?
            new ContribuinteFamiliaNumerosa(nif, email, nome, morada, password, nfiscais):
            new ContribuinteIndividual(nif, email, nome, morada, password, nfiscais);

        bd.addContribuinteIndividual(c);
    }

    /**
     * Menu de signUp para um ContribuinteEmpresa
     *
     * @param bd A base de dados
     */
    private static void signUpContribuinteEmpresa (BD bd)
    {
        System.out.println("NIF:");
        int nif = scanAndTestInt();

        System.out.println("Email:");
        String email = scanAndTestString();

        System.out.println("Nome:");
        String nome = scanAndTestString();

        System.out.println("Morada:");
        String morada = scanAndTestString();

        System.out.println("Password:");
        String password = scanAndTestString();

        int n = 0;
        while (n == 0 ) {
            System.out.println("Indique o numero de actividades economicas nas quais atuam\n");
            n = scanAndTestInt(5);
        }

        Set<Atividades> atividades = new HashSet();

        while (atividades.size() < n) {
            System.out.println("Indique as actividades economicas nas quais atuam\n"+
                    "1.Transportes\n"+
                    "2.Entretenimento\n"+
                    "3.Saude\n"+
                    "4.Restauracao\n"+
                    "5.Educacao\n");

            int op = scanAndTestInt(5);
            switch (op) {
                case 1:
                    atividades.add(new Transportes());
                    break;
                case 2:
                    atividades.add(new Entretenimento());
                    break;
                case 3:
                    atividades.add(new Saude());
                    break;
                case 4:
                    atividades.add(new Restauracao());
                    break;
                case 5:
                    atividades.add(new Educacao());
                    break;
            }
        }

        System.out.println("Escolha a zona a que pertence\n"+
                "0.Concelhos do Interior\n"+
                "1.Outros\n");

        int op = scanAndTestInt(1);
        ContribuinteEmpresa c = (op == 0) ?
            new ContribuinteEmpresaInterior(atividades, nif, email, nome,morada,password):
            new ContribuinteEmpresa(atividades, nif, email, nome,morada,password);

        bd.addContribuinteEmpresa(c);
    }

    /**
     * Menu de signUp generico (para escolher entre ContribuinteIndividual ContribuinteEmpresa)
     *
     * @param bd A base de dados
     */
    private static void signUp (BD u)
    {
        System.out.println("1.Contribuinte Individual\n"+
                "2.Empresa\n"+
                "\n0.Fim de sessao");

        int op = scanAndTestInt(2);
        if (op == 0)
            return;

        clean();

        switch (op) {
            case 1:
                signUpContribuinteIndividual(u);
                break;
            case 2:
                signUpContribuinteEmpresa(u);
                break;
        }

        clean();

        try {
            u.gravaTxt();
        } catch (Exception e1) {
            System.out.println("Nao gravou "+e1.getMessage());
        }
    }

    /**
     * Metodo responsavel pelo painel de controlo do Contribuinte Individual
     *
     * @param  c    Objecto tipo Contribuinte Individual
     * @param  u    Estado atual da BD
     */
    private static void paiConIndividual(ContribuinteIndividual c,BD u){
        clean();
        while(true){
            System.out.println("1.Despesas\n"+
                    "2.Deduçao Geral Individual\n"+
                    "3.Deduçao do Agregado\n"+
                    "4.Classificar Factura\n"+
                    "5.Corrigir Factura\n"+
                    "\n"+
                    "0.LogOut");
            int x = scanAndTestInt(5);
            clean();
            switch(x){
                case(1):
                    despesa(c,u);
                    break;
                case(2):
                    deducao(c, u);
                    break;
                case(3):
                    deducaoagregado(c, u);
                    break;
                case 4:
                    paiConIndividualClassificar(u, c);
                    break;
                case(5):
                    corrigirFactura(c, u);
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Menu de classificacao de facturas por classificar
     *
     * @param  c    O ContribuinteIndividual
     * @param  u    Estado atual da BD
     */
    private static void paiConIndividualClassificar (BD bd, ContribuinteIndividual c)
    {
        /** As facturas do contribuinte */
        ArrayList<Factura> facts = new ArrayList<Factura>( bd.getFacturasIndividualPorClassificar(c));

        /** As linhas pro menu */
        ArrayList<String> opts;

        { /* Preencher o ArrayList com a lista de opcoes */
            opts = new ArrayList<String>(facts.size() + 1);
            opts.add("Voltar atras\n");
            for (Factura f : facts)
                opts.add(f.toString());
        }

        System.out.println("Facturas por classificar:");

        int i = printOptionList(opts);
        if (i == 0) /* opcao "voltar atras" */
            return;

        Factura f = facts.get(i - 1);

        bd.removeFactura(f);

        {
            System.out.println("Indique a actividade economica da despesa");

            {
                opts.clear();
                opts.add("Cancelar\n");
                opts.add("Transportes");
                opts.add("Entretenimento");
                opts.add("Saude");
                opts.add("Restauracao");
                opts.add("Educacao");
            }

            int op = printOptionList(opts);
            switch (op) {
                case 1:
                    f.setActividade(new Transportes());
                    break;
                case 2:
                    f.setActividade(new Entretenimento());
                    break;
                case 3:
                    f.setActividade(new Saude());
                    break;
                case 4:
                    f.setActividade(new Restauracao());
                    break;
                case 5:
                    f.setActividade(new Educacao());
                    break;
            }
        }

        bd.addFactura(f);
    }

    /**
     * Auxiliar responsavel por listar todas as depesas de um determinado ContribuinteIndividual
     *
     * @param c ContribuinteIndividual
     * @param u Base de Dados do programa JavaFactura
     */
    private static void despesa(ContribuinteIndividual c, BD u){

        System.out.println("---------As suas Facturas---------\n");
        Set<Factura> s = u.getFacturasIndividual(c.getNif());
        for(Factura f: s){
            System.out.println(f.toString());
        }
        System.out.println("----------------------------------\n");
        while(true){
            System.out.println("0.Voltar atras\n");
            int x_desp = scanAndTestInt();
            if(x_desp == 0) {clean(); return;}
            else System.out.println("Introduza um dos  numeros das opcoes apresentados\n");

        }
    }

    /**
     * Auxiliar responsavel por listar as deduçoes de um determinado ContribuinteIndividual
     *
     * @param c ContribuinteIndividual
     * @param u Base de Dados do programa JavaFactura
     */
    private static void deducao(ContribuinteIndividual c, BD u){
        System.out.println("---------Deduçao Total Individual---------\n");
        System.out.println("Valor: "+u.getDeducao(c)+"€\n");
        while(true){
            System.out.println("0.Voltar atras\n");
            int x = scanAndTestInt();
            if(x == 0) break;
            else System.out.println("Introduza um dos  numeros das opcoes apresentados\n");

        }
    }

    /**
     * Auxiliar responsavel por listar as deduçoes de um agregado
     *
     * @param c ContribuinteIndividual
     * @param u Base de Dados do programa JavaFactura
     */
    private static void deducaoagregado(ContribuinteIndividual c, BD u){
        System.out.println("---------Deduçao Total do Agregado---------\n");
        double soma = c.getNFiscais().stream().mapToDouble( nif -> u.getDeducao(nif)).sum();
        soma += u.getDeducao(c.getNif());
        System.out.println("Valor: "+soma+"€\n");
        while(true){
            System.out.println("0.Voltar atras\n");
            int x = scanAndTestInt();
            if(x == 0) break;
            else System.out.println("Introduza um dos  numeros das opcoes apresentados\n");

        }
    }

    /**
    * Auxiliar responsvel por corrigiar as faturas para um contribuinte individual
    */
    public static void corrigirFactura(ContribuinteIndividual c, BD bd){
        
        while(true){
            List<Factura> facts = new ArrayList<Factura>( bd.getClassificadas(c.getNif()));
            int i = 1;
            System.out.println("---------Facturas possivies de correcao---------\n");
            for(Factura f: facts){
                System.out.println("["+i+"] --> "+f.toString());
                i++;
            }
            System.out.println("------------------------------------------------\n\n");
            System.out.println("0.Voltar atras\n");
            System.out.println("Escolha o numero da factura para qual pretende corrigir ou a opção 'Voltar Atras'.\n");
            int op = scanAndTestInt(facts.size());
            
            if(op > 0 && op <= facts.size() ){
                Factura fa = facts.get(op - 1);
                bd.removeFactura(fa);

                ArrayList<String> atividades = new ArrayList<String>();
                atividades.add("Voltar atras\n");
                atividades.add("Transportes");
                atividades.add("Entretenimento");
                atividades.add("Saude");
                atividades.add("Restauracao");
                atividades.add("Educacao");

                int op_ativ = printOptionList(atividades);
                switch (op_ativ) {
                    case 1:
                        fa.setActividade(new Transportes());
                        break;
                    case 2:
                        fa.setActividade(new Entretenimento());
                        break;
                    case 3:
                        fa.setActividade(new Saude());
                        break;
                    case 4:
                        fa.setActividade(new Restauracao());
                        break;
                    case 5:
                        fa.setActividade(new Educacao());
                        break;
                    case 0:
                        return;
                }
                bd.addFacturaCorrigida(fa);
                break;

            }else{
                //entra aqui quando o input e 0 = Voltar Atras
                return;
            }
        }
    }

     /* Painel de controlo de um ContribuinteEmpresa
     *
     * @param c O ContribuinteEmpresa
     * @param u Base de Dados do programa JavaFactura
     */
    private static void paiConEmpresa(ContribuinteEmpresa e, BD u)
    {
        while(true){
            clean();

            System.out.println("1.Criar Factura\n"+
                    "2.Lista de Facturas\n"+
                    "3.Total Facturado\n"+
                    "\n"+
                    "0.LogOut");
            int x=scanAndTestInt(3);
            clean();
            switch(x){
                case(1):
                    int nife = e.getNif();
                    String emitente = e.getNome();
                    LocalDate data = LocalDate.now();
                    System.out.println("Introduza o NIF do cliente: ");
                    int nifc = scanAndTestInt();
                    System.out.println("Descreva a despesa: ");
                    String descricao = scanAndTestString();
                    System.out.println("Introduza o valor da despesa: ");
                    double valor = scanAndTestDouble();
                    Atividades ativ;
                    Factura f = new Factura(nife, emitente, data, nifc, descricao, valor);
                    System.out.println(e.getAtividades());
                    if (e.getAtividades().size() == 1)
                    {
                        ativ = (Atividades) e.getAtividades().toArray()[0];
                        f = new Factura(nife,emitente,data,nifc,descricao, valor,ativ);
                    }
                    System.out.println(f.toString());
                    System.out.println("Confirmar: \n"+
                            "1.Sim\n"+
                            "2.Nao");
                    int y=scanAndTestInt(2);
                    switch(y){
                        case(1):
                            u.addFactura(f);
                        case(2):
                            clean();
                            break;
                    }
                    break;
                case(2):
                    System.out.println("1.Lista de facturas emitidas(por data)\n"+
                            "2.Lista de facturas emitidas(por valor)\n"+
                            "3.Lista de facturas num determinado espaço de tempo\n"+
                            "4.Lista de facturas de um determinado contribuinte\n"+
                            "\n"+
                            "0.Voltar atras");

                    int y1 = scanAndTestInt(4);
                    switch(y1){
                        case(1):
                            u.facturaPorData(e).forEach(factura->System.out.println(factura +"\n"));
                            pressionarQlqTecla();
                            clean();
                            break;
                        case(2):
                            u.facturaPorValor(e).forEach(factura->System.out.println(factura +"\n"));
                            pressionarQlqTecla();
                            clean();
                            break;
                        case(3):
                            LocalDate d;
                            while(true){
                                System.out.println("Apartir de que data?"+
                                        "\nAno:");
                                int d1= scanAndTestInt();
                                System.out.println("Mes:");
                                int d2= scanAndTestInt();
                                System.out.println("Dia:");
                                int d3= scanAndTestInt();
                                try{
                                    d= LocalDate.of(d1,d2,d3);
                                    break;//verificar depois
                                }catch(DateTimeException e1){
                                    System.out.println("Data invalida");
                                }
                            }

                            LocalDate ds;
                            while(true){
                                System.out.println("Ate que data?"+
                                        "\nAno:");
                                int d1= scanAndTestInt();
                                System.out.println("Mes:");
                                int d2= scanAndTestInt();
                                System.out.println("Dia:");
                                int d3= scanAndTestInt();
                                try{
                                    ds= LocalDate.of(d1,d2,d3);
                                    break;//verificar depois
                                }catch(DateTimeException e1){
                                    System.out.println("Data invalida");
                                }

                            }
                            clean();
                            Set<Factura> lista = new HashSet<Factura>();
                            lista=u.listaFacturas(e,d,ds);
                            for(Factura f1 : lista)
                            {
                                System.out.println(f1.toString()+"\n");
                            }
                            pressionarQlqTecla();
                            break;
                        case(4):
                            System.out.println("Indique o NIF do cliente\n");
                            int n=scanAndTestInt();
                            u.facturaPorValor(e,n).forEach(factura-> System.out.println(factura+"\n"));
                            pressionarQlqTecla();
                            clean();
                            break;
                        case(0):
                            break;
                    }
                    break;
                case(3):
                LocalDate d;
                while(true){
                        System.out.println("Apartir de que data?"+
                                "\nAno:");
                        int d1= scanAndTestInt();
                        System.out.println("Mes:");
                        int d2= scanAndTestInt();
                        System.out.println("Dia:");
                        int d3= scanAndTestInt();
                        try{
                            d= LocalDate.of(d1,d2,d3);
                            break;//verificar depois
                        }catch(DateTimeException e1){
                            System.out.println("Data invalida");
                        }
                        }

                LocalDate ds;
                while(true){
                    System.out.println("Ate que data?"+
                            "\nAno:");
                    int d1= scanAndTestInt();
                    System.out.println("Mes:");
                    int d2= scanAndTestInt();
                    System.out.println("Dia:");
                    int d3= scanAndTestInt();
                    try{
                        ds= LocalDate.of(d1,d2,d3);
                        break;//verificar depois
                    }catch(DateTimeException e1){
                        System.out.println("Data invalida");
                    }

                }
                    clean();
                    System.out.println(u.totalFacturado(e,d,ds));
                    pressionarQlqTecla();
                    break;
                case(0):
                    return;
            }
        }
    }

    /**
     * Funcao para listar os contribuintes que mais gastam
     *
     * @param bd Base de Dados do programa JavaFactura
     */
    private static void adminListarContribuintesQueMaisGastam (BD bd)
    {
        Map<Integer, ContribuinteIndividual> cs = bd.getIndividuais();
        Set<Integer> nifs = cs.keySet();
        Map<Integer, Double> facts = new TreeMap<Integer, Double>();

        for (Integer nif : nifs) {
            facts.put(nif, bd.getDespesaTotal(nif));
        }

        facts.entrySet()
            .stream() // toList
            .sorted(new Comparator<Map.Entry<Integer, Double>>() {
                public int compare (Map.Entry<Integer, Double> e1, Map.Entry<Integer, Double> e2) {
                    // ordenar pelo gasto total
                    return Double.compare(e1.getValue(), e2.getValue());
                }
            })
        .limit(10) // take 10
            .forEach(e -> System.out.println("NIF: "
                        + e.getKey()
                        + ": "
                        + e.getValue()
                        + "€"));
    }

    /**
     * Funcao para listar as empresas que mais facturas emitem
     *
     * @param bd Base de Dados do programa JavaFactura
     */
    private static void adminListarNEmpresasQueMaisFacturasEmitem (BD bd)
    {
        System.out.println("Quantas empresas?\n");
        int ne = scanAndTestInt();

        Set<Integer> nifs = bd.getEmpresas().keySet();
        Map<Integer, Integer> nfacts = new TreeMap<Integer, Integer>();

        for (int nif : nifs)
            nfacts.put(nif, bd.getFacturasEmpresa(nif).size());

        nfacts.entrySet()
            .stream()
            .sorted(new Comparator<Map.Entry<Integer, Integer>>() {
                public int compare (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                    return Integer.compare(e1.getValue(), e2.getValue());
                }
            })
            .limit(ne)
            .forEach(e -> System.out.println("NIF: "
                        + e.getKey()
                        + " "
                        + e.getValue()));
    }

    /**
     * Painel de controlo do Admin
     *
     * @param u A base de dados
     */
    private static void paiConAdmin (BD u)
    {
        while (true) {
            System.out.println("1.Listar os 10 contribuintes que mais gastam\n"+
                    "2.Listar as N empresas que mais facturas emitem\n"+
                    "3.Listar Tudo da BD\n"+
                    "4.Ver Facturas Corrigidas\n"+
                    "\n"+
                    "0.LogOut");

            int op = scanAndTestInt(4);
            switch (op) {
                case 1:
                    adminListarContribuintesQueMaisGastam(u);
                    break;
                case 2:
                    adminListarNEmpresasQueMaisFacturasEmitem(u);
                    break;
                case 3:
                    verBD(u);
                    break;
                case 4:
                    verCorrigidas(u);
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Ver todas as Faturas que foram corrigidas(sofreram alteracoes na atividade)
     *
     * @param u A base de dados
     */
    private static void verCorrigidas(BD u){
        
        for(Factura f: u.getFacturaCorrigida()){
            System.out.println(f.toString());
        }
        System.out.println("\n");

    }
    /**
     * Ver o que esta registado na BD
     *
     * @param u A base de dados
     */
    private static void verBD(BD u){

        Map<Integer, ContribuinteEmpresa> m = u.getEmpresas();
        for(ContribuinteEmpresa e: m.values()){

            System.out.println(e.toString());
        }

        Map<Integer, ContribuinteIndividual> c = u.getIndividuais();
        for(ContribuinteIndividual e: c.values()){
            System.out.println(e.toString());
        }

        Set<Factura> f = u.getTodasFacturas();
        for(Factura fa: f){
            System.out.println(fa.toString());
        }
        pressionarQlqTecla();


    }

    /**
     * Funcao que espera que o utilizador pressione alguma tecla
     */
    private static void pressionarQlqTecla(){
        //JavaFactura.stdin.useDelimiter("");
        System.out.println("\nPressione qualquer tecla para recuar");
        JavaFactura.stdin.next();
        /*System.out.println("\nPressione qualquer tecla para recuar");
        String x = scanAndTestString();*/
    }

    /**
     * Auxiliar responsavel por ler um input tipo int do teclado
     *
     * @return  x  retorna um int introduzido pelo utilizador
     */
    private static int scanAndTestInt(){
        int x=-1;
        while(x < 0){
            try{
                x = JavaFactura.stdin.nextInt();
            }
            catch(NoSuchElementException e){
                x=-1;
            }
        }
        return x;
    }

    /**
     * Auxiliar responsavel por ler um input tipo string do teclado
     *
     * @return  s  retorna uma string introduzida pelo utilizador
     */
    private static String scanAndTestString(){
        String s="";
        while(s.equals("")){
            try{
                s = JavaFactura.stdin.nextLine();
            }
            catch(NoSuchElementException e){
                s="";
            }
        }
        return s;
    }

    /**
     * Auxiliar responsavel por limpar o ecrã
     *
     */
    private final static void clean(){
        System.out.print("\033c");
    }

    /**
     * Auxiliar responsavel por ler um input tipo int do teclado
     *
     * @param   y   inteiro maior que zero
     *
     * @return  x  retorna um int introduzido pelo utilizador maior ou igual a zero e menor que y+1
     */
    private static int scanAndTestInt(int y){
        int x=-1;
        while(x<0 || x>y){
            try{
                x = JavaFactura.stdin.nextInt();
            }
            catch(NoSuchElementException e){
                x=-1;
            }
        }
        return x;
    }

        /**
     * Auxiliar responsavel por ler um input tipo double do teclado
     *
     * @return  x  retorna um double introduzido pelo utilizador
     */
    private static double scanAndTestDouble(){
        double x=-1;
        while(x < 0){
            try{
                x = JavaFactura.stdin.nextDouble();
            }
            catch(NoSuchElementException e){
                x=-1;
            }
        }
        return x;
    }

    /**
     * Auxiliar que imprime uma lista de opcoes do menu, le e devolve um
     * inteiro no intervalo certo para ser usado com a lista
     *
     * @param opts A lista de opcoes
     * @return O inteiro lido do teclado
     */
    private static int printOptionList (ArrayList<String> opts)
    {
        int i = 0;
        for (String opt : opts) {
            System.out.println(i + ". " + opt);
            i++;
        }
        return scanAndTestInt(opts.size());
    }
}
